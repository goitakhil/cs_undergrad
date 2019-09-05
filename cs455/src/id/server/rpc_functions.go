package main

import (
	//log "github.com/sirupsen/logrus"
	iface "id/interfaces"
)

// RPCServer is the typedef to instantiate an rpc server.
type RPCServer int

// Create implements interfaces.RPCServer.Create
func (t *RPCServer) Create(args iface.CreateArgs, res *string) error {
	newid, err := database.AddUser(args.Username, args.Realname, args.Password, args.IPAddr)
	if err != nil {
		return err
	}
	(*res) = newid
	if args.Initial {
		args.Initial = false
		ready := make(chan int)
		//log.Info("Replicating")
		go Replicate(args, ready)
		<-ready
	}
	return nil
}

// Lookup implements interfaces.RPCServer.Lookup
func (t *RPCServer) Lookup(username string, res *iface.Record) error {
	record := database.LookupUser(username)
	(*res) = record
	return nil
}

// ReverseLookup implements interfaces.RPCServer.ReverseLookup
func (t *RPCServer) ReverseLookup(id string, res *iface.Record) error {
	record := database.ReverseLookup(id)
	(*res) = record
	return nil
}

// Modify implements interfaces.RPCServer.Modify
func (t *RPCServer) Modify(args iface.ModifyArgs, res *string) error {
	err := database.RenameUser(args.OldUsername, args.NewUsername, args.Password, args.IPAddr)
	(*res) = ""
	if err != nil {
		return err
	}
	if args.Initial {
		args.Initial = false
		ready := make(chan int)
		//log.Info("Replicating")
		go Replicate(args, ready)
		<-ready
	}
	return nil
}

// Delete implements interfaces.RPCServer.Delete
func (t *RPCServer) Delete(args iface.DeleteArgs, res *string) error {
	err := database.DeleteUser(args.Username, args.Password)
	(*res) = ""
	if err != nil {
		return err
	}
	if args.Initial {
		args.Initial = false
		ready := make(chan int)
		//log.Info("Replicating")
		go Replicate(args, ready)
		<-ready
	}
	return nil
}

// GetUsers returns all usernames in the database.
func (t *RPCServer) GetUsers(args int, res *[]string) error {
	results, err := database.GetUsers()
	(*res) = results
	return err
}

// GetUUIDS returns all uuids in the database.
func (t *RPCServer) GetUUIDS(args int, res *[]string) error {
	results, err := database.GetUUIDS()
	(*res) = results
	return err
}

// GetAll returns all user data in the database.
func (t *RPCServer) GetAll(args int, res *[]iface.Record) error {
	results, err := database.GetAll()
	(*res) = results
	return err
}

// Ping returns the string Pong
func (t *RPCServer) Ping(args int, res *string) error {
	(*res) = "PONG"
	return nil
}
