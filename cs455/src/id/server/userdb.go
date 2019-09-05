package main

import (
	//"encoding/gob"
	"errors"
	//"os"
	"id/interfaces"
	"sync"

	"github.com/google/uuid"
	//"github.com/pierrec/lz4"
	redigo "github.com/gomodule/redigo/redis"
	log "github.com/sirupsen/logrus"
	"golang.org/x/crypto/bcrypt"
)

// interfaces.Record contains the data associated with a user id.
//type interfaces.Record struct {
//	UUID     string
//	RealName string
//	Password []byte
//}

// UserDB is a map of user ids to interfaces.Records and a mutex.
type UserDB struct {
	Mutex    sync.Mutex
	Hashmaps redigo.Conn
	IDmaps   redigo.Conn
}

// NewUserDB returns an empty database struct.
func NewUserDB(address string) (*UserDB, error) {
	database := new(UserDB)
	var err error
	database.Hashmaps, err = redigo.Dial("tcp", address, redigo.DialDatabase(0))
	database.IDmaps, err = redigo.Dial("tcp", address, redigo.DialDatabase(1))
	return database, err
}

// AddUser inserts a new user into the database, or errors if needed.
func (D *UserDB) AddUser(username string, realname string, password [32]byte, ipAddr string) (string, error) {
	D.Mutex.Lock()
	defer D.Mutex.Unlock()
	//record, exists := D.Data[username]
	exists, err := redigo.Bool(D.Hashmaps.Do("EXISTS", username))
	if err != nil {
		log.WithFields(log.Fields{
			"error": err,
		}).Warn("AddUser failed due to connection error with Hashmaps server.")
		return "", err
	}
	if exists {
		log.WithFields(log.Fields{
			"username": username,
		}).Warn("AddUser failed because user already exists.")
		return "", errors.New("Username Already Exists")
	}
	newid := uuid.New().String()
	hash, _ := bcrypt.GenerateFromPassword(password[:], bcrypt.DefaultCost)
	D.Hashmaps.Do("HMSET", username, "id", newid, "name", realname, "password", hash, "created-by", ipAddr)
	D.IDmaps.Do("SET", newid, username)
	cluster.Clock++
	log.WithFields(log.Fields{
		"username": username,
		"eventid":  cluster.Clock,
	}).Info("AddUser success.")
	return newid, nil
}

// LookupUser searches the database for a user, or errors if needed.
func (D *UserDB) LookupUser(username string) interfaces.Record {
	D.Mutex.Lock()
	defer D.Mutex.Unlock()
	var id string
	str, err := D.Hashmaps.Do("HGET", username, "id")
	if err != nil {
		log.WithFields(log.Fields{
			"error": err,
		}).Warn("Lookup failed due to connection error with Hashmaps server.")
		return interfaces.Record{}
	}
	if str != nil {
		id, _ = redigo.String(str, nil)
	}
	if id != "" {
		var newrecord interfaces.Record
		newrecord.Username = username
		newrecord.Realname, _ = redigo.String(D.Hashmaps.Do("HGET", username, "name"))
		newrecord.UUID = id
		log.WithFields(log.Fields{
			"username": username,
		}).Info("Lookup success.")
		return newrecord
	}
	log.WithFields(log.Fields{
		"username": username,
	}).Warn("Lookup found no results.")
	return interfaces.Record{}
}

// ReverseLookup searches the database for a UUID, or errors if needed.
func (D *UserDB) ReverseLookup(id string) interfaces.Record {
	D.Mutex.Lock()
	defer D.Mutex.Unlock()
	var username string
	str, err := D.IDmaps.Do("GET", id)
	if err != nil {
		log.WithFields(log.Fields{
			"error": err,
		}).Warn("Lookup failed due to connection error with Hashmaps server.")
		return interfaces.Record{}
	}
	if str != nil {
		username, _ = redigo.String(str, nil)
	}
	if username != "" {
		var newrecord interfaces.Record
		newrecord.Username = username
		newrecord.Realname, _ = redigo.String(D.Hashmaps.Do("HGET", username, "name"))
		newrecord.UUID = id
		log.WithFields(log.Fields{
			"id": id,
		}).Info("Lookup success.")
		return newrecord
	}
	log.WithFields(log.Fields{
		"id": id,
	}).Info("Lookup found no results.")
	return interfaces.Record{}
}

// RenameUser move a user record from one username to another, or errors if needed.
func (D *UserDB) RenameUser(oldname string, newname string, password [32]byte, ipAddr string) error {
	D.Mutex.Lock()
	defer D.Mutex.Unlock()
	// If username does not exist
	exists, err := redigo.Bool(D.Hashmaps.Do("EXISTS", oldname))
	if err != nil {
		log.WithFields(log.Fields{
			"error": err,
		}).Warn("RenameUser failed due to connection error with Hashmaps server.")
		return err
	}
	if !exists {
		log.WithFields(log.Fields{
			"username": oldname,
		}).Warn("RenameUser failed because user does not exist.")
		return errors.New("Username Not Found")
	}
	// If target is already in use
	exists, _ = redigo.Bool(D.Hashmaps.Do("EXISTS", newname))
	if exists {
		log.WithFields(log.Fields{
			"username": newname,
		}).Warn("RenameUser failed because username already in use.")
		return errors.New("Username Already In Use")
	}
	// Check Password
	hash, _ := redigo.Bytes(D.Hashmaps.Do("HGET", oldname, "password"))
	if bcrypt.CompareHashAndPassword(hash, password[:]) != nil {
		log.WithFields(log.Fields{
			"username": oldname,
		}).Warn("RenameUser failed because of password failure.")
		return errors.New("Password does not match")
	}
	//rename in redis
	id, _ := redigo.String(D.Hashmaps.Do("HGET", oldname, "id"))
	D.Hashmaps.Do("RENAME", oldname, newname)
	D.Hashmaps.Do("HMSET", newname, "created-by", ipAddr)
	D.IDmaps.Do("SET", id, newname)
	cluster.Clock++
	log.WithFields(log.Fields{
		"oldname": oldname,
		"newname": newname,
		"eventid": cluster.Clock,
	}).Info("RenameUser success.")
	return nil
}

// DeleteUser removes a user from the database, given a correct password. Errors if fails.
func (D *UserDB) DeleteUser(username string, password [32]byte) error {
	D.Mutex.Lock()
	defer D.Mutex.Unlock()
	// If username does not exist
	exists, err := redigo.Bool(D.Hashmaps.Do("EXISTS", username))
	if err != nil {
		log.WithFields(log.Fields{
			"error": err,
		}).Warn("DeleteUser failed due to connection error with Hashmaps server.")
		return err
	}
	if !exists {
		log.WithFields(log.Fields{
			"username": username,
		}).Warn("Delete failed because user does not exist.")
		return errors.New("Username Not Found")
	}
	// Check Password
	hash, _ := redigo.Bytes(D.Hashmaps.Do("HGET", username, "password"))
	if bcrypt.CompareHashAndPassword(hash, password[:]) != nil {
		log.WithFields(log.Fields{
			"username": username,
		}).Warn("DeleteUser failed because of password failure.")
		return errors.New("Password does not match")
	}

	id, _ := redigo.String(D.Hashmaps.Do("HGET", username, "id"))
	D.Hashmaps.Do("DEL", username)
	D.IDmaps.Do("DEL", id)
	cluster.Clock++
	log.WithFields(log.Fields{
		"username": username,
		"eventid":  cluster.Clock,
	}).Info("DeleteUser success.")
	return nil
}

// GetUsers returns the list of all usernames
func (D *UserDB) GetUsers() ([]string, error) {
	usernames, err := redigo.Strings(D.Hashmaps.Do("KEYS", "*"))
	if err != nil {
		log.WithFields(log.Fields{
			"error": err,
		}).Warn("GetUsers failed due to connection error with Hashmaps server.")
		return nil, err
	}
	log.WithFields(log.Fields{
		"NumUsers": len(usernames),
	}).Info("GetUsers success.")
	return usernames, nil
}

// GetUUIDS returns the list of all identifiers
func (D *UserDB) GetUUIDS() ([]string, error) {
	ids, err := redigo.Strings(D.IDmaps.Do("KEYS", "*"))
	if err != nil {
		log.WithFields(log.Fields{
			"error": err,
		}).Warn("GetUUIDS failed due to connection error with Hashmaps server.")
		return nil, err
	}
	log.WithFields(log.Fields{
		"NumUsers": len(ids),
	}).Info("GetUUIDS success.")
	return ids, nil
}

// GetAll returns a slice of all user records
func (D *UserDB) GetAll() ([]interfaces.Record, error) {
	usernames, err := redigo.Strings(D.Hashmaps.Do("KEYS", "*"))
	if err != nil {
		log.WithFields(log.Fields{
			"error": err,
		}).Warn("GetAll failed due to connection error with Hashmaps server.")
		return nil, err
	}
	allrecords := []interfaces.Record{}
	for _, user := range usernames {
		var newrecord interfaces.Record
		newrecord.Username = user
		newrecord.Realname, _ = redigo.String(D.Hashmaps.Do("HGET", user, "name"))
		newrecord.UUID, _ = redigo.String(D.Hashmaps.Do("HGET", user, "id"))
		newrecord.CreatedBy, _ = redigo.String(D.Hashmaps.Do("HGET", user, "created-by"))
		allrecords = append(allrecords, newrecord)
	}
	log.WithFields(log.Fields{
		"NumUsers": len(allrecords),
	}).Info("GetAll success.")
	return allrecords, nil
}
