package main

import (
	"crypto/tls"
	"encoding/gob"
	"fmt"
	"net"
	"net/rpc"
	"os"

	log "github.com/sirupsen/logrus"
)

// RPCMain contains the main loop for the rpc listening thread.
func RPCMain(portnum int) {
	var certs []tls.Certificate
	tlsInit()
	cert, err := tls.X509KeyPair(certBlock, keyBlock)
	certs = append(certs, cert)
	socket, err := tls.Listen("tcp", fmt.Sprintf(":%v", portnum), &tls.Config{
		Certificates: certs,
	})
	if err != nil {
		log.WithFields(log.Fields{
			"error": err,
		}).Error("Unable to bind tls port")
		os.Exit(1)
	}
	var service RPCServer
	srv := rpc.NewServer()
	err = srv.Register(&service)
	if err != nil {
		log.WithFields(log.Fields{
			"error": err,
		}).Error("Unable to register functions.")
		os.Exit(1)
	}
	for {
		conn, err := socket.Accept()
		if err != nil {
			log.WithFields(log.Fields{
				"error": err,
			}).Error("Error occured while accepting connection")
		} else {
			log.WithFields(log.Fields{
				"client": conn.RemoteAddr().String(),
			}).Info("Accepted Connection")
			e := gob.NewEncoder(conn)
			addr, _, _ := net.SplitHostPort(conn.RemoteAddr().String())
			e.Encode(addr)
			go srv.ServeConn(conn)
		}
	}
}
