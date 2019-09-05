package main

import (
	"io/ioutil"
	"net"
	"os"
	"testing"
)

// TestClone test the clone functions.
func TestClone(t *testing.T) {
	var conn1, conn2 = net.Pipe()
	var err error
	sourcefile, _ := os.Create("dump.rdb")
	sourcefile.WriteString("THIS IS OUR TEST FILE CONTENTS\n")
	defer sourcefile.Close()
	go func() {
		_ = SendDB(conn1)
	}()
	err = ReceiveDB(conn2, "dump2.rdb")
	if err != nil {
		t.Error(err)
	}
	result, _ := ioutil.ReadFile("dump2.rdb")
	if string(result) != "THIS IS OUR TEST FILE CONTENTS\n" {
		t.Fail()
	} else {
		os.Remove("dump2.rdb")
	}
	go func() {
		_ = SendDB(conn1)
	}()
	err = ReceiveDB(conn2, "/dump2.rdb")
	if err == nil {
		t.Error(err)
	}
	os.Remove("dump.rdb")

}
