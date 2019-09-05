package multicast

import (
	"fmt"
	"net"
	"strconv"
	"strings"
	"time"

	log "github.com/sirupsen/logrus"
)

const (
	rankAddr        = "230.230.248.1:5189"
	masterAddr      = "230.230.248.2:5189"
	maxDatagramSize = 8192
)

// DiscoverMaster returns the rank and address of the master server
func DiscoverMaster() (int, string) {
	addr, _ := net.ResolveUDPAddr("udp", masterAddr)
	sendSocket, _ := net.DialUDP("udp", nil, addr)
	listenSocket, _ := net.ListenMulticastUDP("udp", nil, addr)
	listenSocket.SetReadBuffer(maxDatagramSize)
	go func() {
		time.Sleep(time.Millisecond * 50)
		sendSocket.Write([]byte("GETMASTER"))
	}()
	masterRank := 0
	masterAddress := ""

	b := make([]byte, maxDatagramSize)
	for {
		listenSocket.SetDeadline(time.Now().Add(time.Millisecond * 500))
		n, _, err := listenSocket.ReadFromUDP(b)
		if err != nil {
			break
		}
		message := strings.Split(string(b[:n]), " ")
		if message[0] == "MASTER" {
			prevMaster := masterRank
			prevAddr := masterAddress
			masterRank, _ = strconv.Atoi(message[1])
			masterAddress = message[2]
			if prevMaster != 0 && prevAddr != masterAddr {
				return 0, ""
			}
		}
	}

	return masterRank, masterAddress
}

// Below is the cluster object for servers //

type Cluster struct {
	Rank          int
	MasterRank    int
	MasterAddress string
	Clock         int
	Peers         []string
	NeedResync    bool
}

func (c *Cluster) Init() {
	c.join()
	go c.rankListener()
	rank, address := DiscoverMaster()
	c.MasterRank = rank
	c.MasterAddress = address
	go c.masterListener()
	if rank == 0 {
		c.PerformElection()
	}
	c.Peers = c.GetPeers()
	c.NeedResync = false
}

func (c *Cluster) PerformElection() {
	addr, _ := net.ResolveUDPAddr("udp", rankAddr)
	sendSocket, _ := net.DialUDP("udp", nil, addr)
	listenSocket, _ := net.ListenMulticastUDP("udp", nil, addr)
	listenSocket.SetReadBuffer(maxDatagramSize)
	sendSocket.Write([]byte("GETRANK"))
	master := c.Rank
	masterAddress := sendSocket.LocalAddr()
	masterClock := c.Clock

	b := make([]byte, maxDatagramSize)
	for {
		listenSocket.SetDeadline(time.Now().Add(time.Millisecond * 500))
		n, src, err := listenSocket.ReadFromUDP(b)
		if err != nil {
			break
		}
		message := strings.Split(string(b[:n]), " ")
		if message[0] == "RANK" {
			rank, _ := strconv.Atoi(message[1])
			clock, _ := strconv.Atoi(message[2])
			if clock > masterClock || (rank < master && clock == masterClock) {
				masterAddress = src
				master = rank
				masterClock = clock
			}
		}
	}
	addr, _ = net.ResolveUDPAddr("udp", masterAddr)
	sendSocket, _ = net.DialUDP("udp", nil, addr)
	address, _, _ := net.SplitHostPort(masterAddress.String())
	sendSocket.Write([]byte(fmt.Sprintf("NEWMASTER %v %v %v", master, address, masterClock)))
}

func (c *Cluster) rankListener() {
	addr, _ := net.ResolveUDPAddr("udp", rankAddr)
	listenSocket, _ := net.ListenMulticastUDP("udp", nil, addr)
	listenSocket.SetReadBuffer(maxDatagramSize)
	sendSocket, _ := net.DialUDP("udp", nil, addr)
	b := make([]byte, maxDatagramSize)
	for {
		n, _, err := listenSocket.ReadFromUDP(b)
		if err == nil {
			message := strings.Split(string(b[:n]), " ")
			if message[0] == "GETRANK" {
				sendSocket.Write([]byte(fmt.Sprintf("RANK %v %v", c.Rank, c.Clock)))
			}
		}
	}
}

func (c *Cluster) masterListener() {
	addr, _ := net.ResolveUDPAddr("udp", masterAddr)
	listenSocket, _ := net.ListenMulticastUDP("udp", nil, addr)
	listenSocket.SetReadBuffer(maxDatagramSize)
	sendSocket, _ := net.DialUDP("udp", nil, addr)
	b := make([]byte, maxDatagramSize)
	for {
		n, _, err := listenSocket.ReadFromUDP(b)
		if err == nil {
			message := strings.Split(string(b[:n]), " ")
			if message[0] == "NEWMASTER" {
				newMaster, err := strconv.Atoi(message[1])
				if err == nil && c.MasterAddress != message[2] {
					c.MasterRank = newMaster
					c.MasterAddress = message[2]
					newClock, _ := strconv.Atoi(message[3])
					if newClock > c.Clock {
						c.NeedResync = true
					}
					log.WithFields(log.Fields{
						"MasterRank": c.MasterRank,
						"MasterAddr": c.MasterAddress,
					}).Info("New cluster master elected.")
					if c.AmMaster() {
						log.Info("I AM THE MASTER COMMANDER!")
					}
				}
			}
			if message[0] == "GETMASTER" && c.Rank == c.MasterRank {
				sendSocket.Write([]byte(fmt.Sprintf("MASTER %v %v %v", c.MasterRank, c.MasterAddress, c.Clock)))
			}
		}
	}
}

func (c *Cluster) join() {
	addr, _ := net.ResolveUDPAddr("udp", rankAddr)
	sendSocket, _ := net.DialUDP("udp", nil, addr)
	listenSocket, _ := net.ListenMulticastUDP("udp", nil, addr)
	listenSocket.SetReadBuffer(maxDatagramSize)
	go func() {
		time.Sleep(time.Millisecond * 50)
		sendSocket.Write([]byte("GETRANK"))
	}()
	c.Rank = 1
	b := make([]byte, maxDatagramSize)
	for {
		listenSocket.SetDeadline(time.Now().Add(time.Millisecond * 500))
		n, _, err := listenSocket.ReadFromUDP(b)
		if err != nil {
			break
		}
		message := strings.Split(string(b[:n]), " ")
		if message[0] == "RANK" {
			rank, _ := strconv.Atoi(message[1])
			if rank >= c.Rank {
				c.Rank = rank + 1
			}
		}
	}
}

func (c *Cluster) AmMaster() bool {
	return c.Rank == c.MasterRank
}

func (c *Cluster) GetPeers() []string {
	var peers []string
	addr, _ := net.ResolveUDPAddr("udp", rankAddr)
	sendSocket, _ := net.DialUDP("udp", nil, addr)
	listenSocket, _ := net.ListenMulticastUDP("udp", nil, addr)
	listenSocket.SetReadBuffer(maxDatagramSize)
	sendSocket.Write([]byte("GETRANK"))
	myAddr, _, _ := net.SplitHostPort(sendSocket.LocalAddr().String())

	b := make([]byte, maxDatagramSize)
	for {
		listenSocket.SetDeadline(time.Now().Add(time.Millisecond * 500))
		n, src, err := listenSocket.ReadFromUDP(b)
		if err != nil {
			break
		}
		message := strings.Split(string(b[:n]), " ")
		if message[0] == "RANK" {
			address, _, _ := net.SplitHostPort(src.String())
			if address != myAddr {
				peers = append(peers, address)
			}
		}
	}
	return Unique(peers)
}

func Unique(inarray []string) []string {
	outarray := make([]string, 0, len(inarray))
	seen := make(map[string]bool)

	for _, val := range inarray {
		if _, ok := seen[val]; !ok {
			seen[val] = true
			outarray = append(outarray, val)
		}
	}

	return outarray
}
