package main

import (
	"crypto/sha256"
	"crypto/tls"
	"crypto/x509"
	"encoding/gob"
	"errors"
	"fmt"
	. "id/interfaces"
	"id/multicast"
	"net/rpc"
	"os"
	"strings"
	"text/tabwriter"
	"time"
)

func connect(server, port string) error {
	var err error
	const rootPEM = `-----BEGIN CERTIFICATE-----
Certificate has been removed for obvious reasons.
-----END CERTIFICATE-----`

	roots := x509.NewCertPool()
	ok := roots.AppendCertsFromPEM([]byte(rootPEM))
	if !ok {
		panic("failed to parse root certificate")
	}
	if server == "<server>" {
		var rank int
		rank, server = multicast.DiscoverMaster()
		if rank == 0 {
			return errors.New("Unable to detect a running server")
		}
	}
	conn, err := tls.Dial("tcp", server+":"+port, &tls.Config{
		RootCAs:    roots,
		ServerName: "team19",
	})
	if err != nil {
		return err
	}
	gob.NewDecoder(conn).Decode(&config.ip)
	config.client = rpc.NewClient(conn)
	return nil
}

func sendCreate(username, realname, password string) error {

	pass := sha256.Sum256([]byte(password))
	args := CreateArgs{username, realname, pass, config.ip, true}
	var record string
	c1 := make(chan string, 1)
	go func() {
		err := config.client.Call("RPCServer.Create", args, &record)
		if err != nil {
			c1 <- err.Error()
		} else {
			c1 <- ""
		}
	}()

	select {
	case res := <-c1:
		if res != "" {
			return errors.New(res)
		}
	case <-time.After(5 * time.Second):
		return errors.New("Connection Timeout\n Connection to server lost")
	}
	fmt.Println("Successfully created user " + username + " with uid:")
	fmt.Println(record)

	return nil
}

func sendLookup(username string) error {
	var result Record
	c1 := make(chan string, 1)
	go func() {
		err := config.client.Call("RPCServer.Lookup", username, &result)
		if err != nil {
			c1 <- err.Error()
		} else {
			c1 <- ""
		}
	}()

	select {
	case res := <-c1:
		if res != "" {
			return errors.New(res)
		}
	case <-time.After(5 * time.Second):
		return errors.New("Connection Timeout\n Connection to server lost")
	}

	if strings.TrimSpace(result.Username) == "" {
		fmt.Println("No result found for username " + username)
		return nil
	}
	fmt.Println("Lookup returned the following record:")
	fmt.Println(result.Username)
	fmt.Println(result.Realname)
	fmt.Println(result.UUID)
	return nil
}
func sendReverse(uuid string) error {
	var result Record

	c1 := make(chan string, 1)
	go func() {
		err := config.client.Call("RPCServer.ReverseLookup", uuid, &result)
		if err != nil {
			c1 <- err.Error()
		} else {
			c1 <- ""
		}
	}()

	select {
	case res := <-c1:
		if res != "" {
			return errors.New(res)
		}
	case <-time.After(5 * time.Second):
		return errors.New("Connection Timeout\n Connection to server lost")
	}

	if strings.TrimSpace(result.Username) == "" {
		fmt.Println("No record found for UUID:")
		fmt.Println(uuid)
	} else {
		fmt.Println("Reverse Lookup returned the following record:")
		fmt.Println(result.Username)
		fmt.Println(result.Realname)
		fmt.Println(result.UUID)
	}
	return nil
}
func sendModify(username, newusername, password string) error {
	pass := sha256.Sum256([]byte(password))
	args := ModifyArgs{username, newusername, pass, config.ip, true}
	var success string
	c1 := make(chan string, 1)
	go func() {
		err := config.client.Call("RPCServer.Modify", args, &success)
		if err != nil {
			c1 <- err.Error()
		} else {
			c1 <- ""
		}
	}()

	select {
	case res := <-c1:
		if res != "" {
			return errors.New(res)
		}
	case <-time.After(5 * time.Second):
		return errors.New("Connection Timeout\n Connection to server lost")
	}
	fmt.Println("Successfully changed " + username + " to " + newusername)
	return nil
}

func sendDelete(username, password string) error {
	pass := sha256.Sum256([]byte(password))
	args := DeleteArgs{username, pass, true}
	var success string
	c1 := make(chan string, 1)
	go func() {
		err := config.client.Call("RPCServer.Delete", args, &success)
		if err != nil {
			c1 <- err.Error()
		} else {
			c1 <- ""
		}
	}()

	select {
	case res := <-c1:
		if res != "" {
			return errors.New(res)
		}
	case <-time.After(5 * time.Second):
		return errors.New("Connection Timeout\n Connection to server lost")
	}
	fmt.Println("Successfully deleted " + username)
	return nil
}
func sendGet(subcommand string) error {

	switch strings.ToLower(strings.TrimSpace(subcommand)) {
	case "users":
		return getUsers()
	case "uuids":
		return getUUIDs()
	case "all":
		return getAll()
	default:
		return errors.New("subcommands: users|uuids|all")
	}

	return nil
}

func getUsers() error {
	fmt.Println("Getting Userlist")
	var result []string
	c1 := make(chan string, 1)
	go func() {
		err := config.client.Call("RPCServer.GetUsers", 0, &result)
		if err != nil {
			c1 <- err.Error()
		} else {
			c1 <- ""
		}
	}()

	select {
	case res := <-c1:
		if res != "" {
			return errors.New(res)
		}
	case <-time.After(5 * time.Second):
		return errors.New("Connection Timeout\n Connection to server lost")
	}
	for _, v := range result {
		fmt.Println(v)
	}
	return nil
}

func getUUIDs() error {
	fmt.Println("Getting UUID List")
	var result []string
	c1 := make(chan string, 1)
	go func() {
		err := config.client.Call("RPCServer.GetUUIDS", 0, &result)
		if err != nil {
			c1 <- err.Error()
		} else {
			c1 <- ""
		}
	}()

	select {
	case res := <-c1:
		if res != "" {
			return errors.New(res)
		}
	case <-time.After(5 * time.Second):
		return errors.New("Connection Timeout\n Connection to server lost")
	}

	for _, v := range result {
		fmt.Println(v)
	}
	return nil
}

func getAll() error {
	fmt.Println("Getting All Records")

	var result []Record
	c1 := make(chan string, 1)
	go func() {
		err := config.client.Call("RPCServer.GetAll", 0, &result)
		if err != nil {
			c1 <- err.Error()
		} else {
			c1 <- ""
		}
	}()

	select {
	case res := <-c1:
		if res != "" {
			return errors.New(res)
		}
	case <-time.After(5 * time.Second):
		return errors.New("Connection Timeout\n Connection to server lost")
	}

	w := tabwriter.NewWriter(os.Stdout, 0, 0, 1, ' ', tabwriter.Debug)

	for _, v := range result {
		fmt.Fprintln(w, v.Username+"\t"+v.Realname+"\t"+v.CreatedBy+"\t"+v.UUID)
	}
	w.Flush()
	return nil
}
