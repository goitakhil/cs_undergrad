package main

import (
	"crypto/tls"
	"crypto/x509"
	"encoding/gob"
	iface "id/interfaces"
	"net/rpc"
	"time"

	log "github.com/sirupsen/logrus"
)

// Replicate sends a create command to the cluster nodes.
func Replicate(args interface{}, once chan int) {
	if len(cluster.Peers) == 0 {
		log.Info("No peers found")
		once <- 0
		return
	}
	roots := x509.NewCertPool()
	ok := roots.AppendCertsFromPEM([]byte(certBlock))
	if !ok {
		once <- 0
		panic("failed to parse root certificate")
	}
	failureDetected := false
	firstRun := true
	for _, node := range cluster.Peers {
		conn, err := tls.Dial("tcp", node+":5185", &tls.Config{
			RootCAs:    roots,
			ServerName: "team19",
		})
		if err != nil {
			once <- 0
			log.WithFields(log.Fields{
				"error": err,
			}).Error("TLS Dial failure")
			failureDetected = true
			continue
		}
		var ip string
		gob.NewDecoder(conn).Decode(&ip)
		rpcclient := rpc.NewClient(conn)
		var retval string
		block := make(chan int)
		go func() {
			switch a := args.(type) {
			case iface.CreateArgs:
				err = rpcclient.Call("RPCServer.Create", a, &retval)
			case iface.ModifyArgs:
				err = rpcclient.Call("RPCServer.Modify", a, &retval)
			case iface.DeleteArgs:
				err = rpcclient.Call("RPCServer.Delete", a, &retval)
			}
			block <- 0
		}()
		select {
		case <-block:
			if err == nil && firstRun {
				firstRun = false
				once <- 0
			}
			if err != nil {
				log.WithFields(log.Fields{
					"error": err,
				}).Error("RPC returned error.")
			}
		case <-time.After(time.Millisecond * 200):
			failureDetected = true
		}
	}
	if failureDetected {
		cluster.Peers = cluster.GetPeers()
	}
	once <- 0
	return
}
