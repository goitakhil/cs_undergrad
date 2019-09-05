package main

import (
	"encoding/gob"
	"io"
	"io/ioutil"
	"net"
	"os"

	"github.com/pierrec/lz4"
)

// SendDB uses buffered io to send the database over the network.
func SendDB(conn net.Conn) error {
	var err error
	target, err := ioutil.ReadFile("dump.rdb")
	if err != nil {
		conn.Close()
		return err
	}
	compressor := lz4.NewWriter(conn)
	_, err = compressor.Write(target)
	compressor.Flush()
	gob.NewEncoder(conn).Encode(cluster.Clock)
	conn.Close()
	return err
}

// ReceiveDB catches the database clone send.
func ReceiveDB(conn net.Conn, path string) error {
	var err error
	decompressor := lz4.NewReader(conn)
	target, err := os.Create(path)
	if err != nil {
		conn.Close()
		return err
	}
	defer target.Close()
	_, err = io.Copy(target, decompressor)
	var clock int
	gob.NewDecoder(conn).Decode(&clock)
	cluster.Clock = clock
	conn.Close()
	return err
}
