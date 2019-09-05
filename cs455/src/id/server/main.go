package main

import (
	"crypto/x509"
	"flag"
	"fmt"
	"math/rand"
	"net"
	"os"
	"os/exec"
	"os/signal"
	"time"

	"id/multicast"

	log "github.com/sirupsen/logrus"
)

var database *UserDB
var sigchannel chan os.Signal
var dblocation string
var cluster multicast.Cluster
var redisProcess *exec.Cmd

func main() {
	// Argument parsing
	portnum := flag.Int("numport", 5185, "Port number to listen on. Default: 5185")
	verbose := flag.Bool("verbose", false, "Print debugging information.")
	//redis := flag.String("redis", "localhost:5186", "Address of Redis server.")
	flag.Parse()
	log.SetOutput(os.Stdout)
	if *verbose {
		log.SetLevel(log.DebugLevel)
	} else {
		log.SetLevel(log.WarnLevel)
	}
	log.WithFields(log.Fields{
		"portnum": *portnum,
		"verbose": *verbose,
	}).Info("Starting up Identity Server")
	tlsInit()

	// Initialize cluster identity
	time.Sleep(time.Millisecond * time.Duration(rand.Intn(500)))
	cluster.Init()
	log.WithFields(log.Fields{
		"Rank":       cluster.Rank,
		"Master":     cluster.MasterRank,
		"MasterAddr": cluster.MasterAddress,
	}).Info("Connected to cluster")

	if !cluster.AmMaster() {
		log.Info("Cloning Database")
		err := cloneDatabase(cluster.MasterAddress)
		tries := 0
		for err != nil && tries < 30 {
			time.Sleep(time.Second)
			log.Info("Retrying Database Clone")
			mrank, maddr := multicast.DiscoverMaster()
			if mrank == 0 {
				cluster.PerformElection()
			} else if mrank != cluster.MasterRank {
				cluster.MasterRank = mrank
				cluster.MasterAddress = maddr
			}
			err = cloneDatabase(cluster.MasterAddress)
			tries = tries + 1
		}
		if err != nil {
			log.WithFields(log.Fields{
				"error": err,
			}).Fatal("An error occured while cloning database.")
		}
	}

	cmd := exec.Command("redis", "--port 5186")
	if err := cmd.Start(); err != nil {
		fmt.Printf("ERROR Unable to run %v: %s", cmd, err.Error())
	} else {
		log.Info("Started up Redis")
		redisProcess = cmd
	}

	var err error
	database, err = NewUserDB("127.0.0.1:5186")
	tries := 0
	for err != nil && tries < 300 {
		time.Sleep(time.Millisecond * 100)
		database, err = NewUserDB("127.0.0.1:5186")
	}
	if err != nil {
		log.WithFields(log.Fields{
			"error": err,
		}).Error("An error occured while connecting to Redis.")
		os.Exit(1)
	}
	log.Info("Connected to Redis")

	sigchannel = make(chan os.Signal, 5)
	signal.Notify(sigchannel, os.Interrupt, os.Kill)

	go SavingThread()
	go RPCMain(*portnum)
	go PeerDiscover()
	go CloneListen()
	SignalCatcher()
}

// SignalCatcher is the function called by interrupt and kill signals.
func SignalCatcher() {
	sig := <-sigchannel
	log.WithFields(log.Fields{
		"signal": sig,
	}).Info("Got a signal, shutting down")
	log.Debug("Saving database to disk")
	database.Hashmaps.Do("SAVE")
	database.IDmaps.Do("SAVE")
	redisProcess.Process.Kill()
	os.Exit(0)
}

// SavingThread tells the database to sync to the disk periodically.
func SavingThread() {
	ticker := time.NewTicker(time.Second * 60)
	defer ticker.Stop()
	for {
		<-ticker.C
		// BGSAVE saves without blocking the clients.
		database.Hashmaps.Do("BGSAVE")
		database.IDmaps.Do("BGSAVE")
		log.Info("Database Saved")
	}
}

// PeerDiscover updates the peer list and pings the server to check if an election is needed.
func PeerDiscover() {
	roots := x509.NewCertPool()
	ok := roots.AppendCertsFromPEM([]byte(certBlock))
	if !ok {
		panic("failed to parse root certificate")
	}
	for {
		time.Sleep(time.Second * 1)
		cluster.Peers = cluster.GetPeers()
		masterRank, _ := multicast.DiscoverMaster()
		if masterRank == 0 {
			cluster.PerformElection()
		}
		if cluster.NeedResync {
			cluster.NeedResync = false
			if !cluster.AmMaster() {
				cloneDatabase(cluster.MasterAddress)
				log.Info("Resyncronized the database from master")
				cmd := exec.Command("redis", "--port 5186")
				if err := cmd.Start(); err != nil {
					fmt.Printf("ERROR Unable to run %v: %s", cmd, err.Error())
				} else {
					log.Info("Started up Redis")
					redisProcess = cmd
				}
			}

			var err error
			database, err = NewUserDB("127.0.0.1:5186")
			tries := 0
			for err != nil && tries < 300 {
				time.Sleep(time.Millisecond * 100)
				database, err = NewUserDB("127.0.0.1:5186")
			}
			if err != nil {
				log.WithFields(log.Fields{
					"error": err,
				}).Error("An error occured while connecting to Redis.")
				os.Exit(1)
			}
		}
	}
}

func cloneDatabase(master string) error {
	if redisProcess != nil {
		redisProcess.Process.Kill()
		time.Sleep(time.Second)
	}
	newconn, err := net.Dial("tcp", master+":5187")
	if err != nil {
		return err
	}
	err = ReceiveDB(newconn, "dump.rdb")
	if err != nil {
		return err
	}
	cluster.NeedResync = false
	return nil
}

// CloneListen waits for clone requests and serves them.
func CloneListen() {
	listener, err := net.Listen("tcp", ":5187")
	if err != nil {
		log.Fatal("Unable to open clone port.")
	}
	for {
		conn, err := listener.Accept()
		database.Hashmaps.Do("SAVE")
		database.IDmaps.Do("SAVE")
		err = SendDB(conn)
		if err != nil {
			log.WithFields(log.Fields{
				"error": err,
			}).Error("An error occured while sending clone.")
		}
		cluster.Peers = cluster.GetPeers()
	}
}
