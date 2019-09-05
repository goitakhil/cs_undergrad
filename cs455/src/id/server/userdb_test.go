package main

import (
	"fmt"
	"os/exec"
	"testing"
	"time"

	log "github.com/sirupsen/logrus"
)

var testDatabase *UserDB
var id string

func TestUserDB(t *testing.T) {
	log.SetLevel(log.ErrorLevel)
	cmd := exec.Command("../../../redis", "--port 5186")
	if err := cmd.Start(); err != nil {
		fmt.Printf("ERROR Unable to run %v: %s", cmd, err.Error())
	} else {
		log.Info("Started up Redis")
		redisProcess = cmd
		// Wait for redis to spin up
		time.Sleep(time.Second * 5)
	}
	defer cmd.Process.Kill()

	var err error
	testDatabase, err = NewUserDB("127.0.0.1:5186")
	if err != nil {
		t.Fail()
	}
	_, err = testDatabase.IDmaps.Do("FLUSHALL")
	if err != nil {
		t.Fail()
	}

	// Test correct usage
	t.Run("Create", create)
	t.Run("Lookup", lookup)
	t.Run("Reverse Lookup", revLookup)
	t.Run("Get Users", getUsersTest)
	t.Run("Get UUIDS", getUUIDSTest)
	t.Run("Get All", getAllTest)
	t.Run("Rename", rename)
	t.Run("Delete", delete)

	// Test invalid requests, non fatal
	create(t)
	t.Run("Existing Create", createFail)
	t.Run("Nonexistant Lookup", lookupFail)
	t.Run("Nonexistant Reverse Lookup", revLookupFail)
	t.Run("Nonexistant Rename", renameNotFound)
	t.Run("Rename Collision", renameCollide)
	t.Run("Nonexistant Delete", deleteFail)

	// Nuke redis process to simulate failure of database
	redisProcess.Process.Kill()
	t.Run("Dead Create", createFail)
	t.Run("Dead Lookup", lookupFail)
	t.Run("Dead Reverse Lookup", revLookupFail)
	t.Run("Dead Get Users", getUsersFail)
	t.Run("Dead Get UUIDS", getUUIDSFail)
	t.Run("Dead Get All", getAllFail)
	t.Run("Dead Rename", renameNotFound)
	t.Run("Dead Delete", deleteFail)

}

func create(t *testing.T) {
	var password [32]byte
	copy(password[:], "Passw0rd!")
	var err error
	id, err = testDatabase.AddUser("testuser", "Real Name", password, "127.0.0.1")
	if err != nil || id == "" {
		t.Fail()
	}
}

func lookup(t *testing.T) {
	record := testDatabase.LookupUser("testuser")
	if id != record.UUID {
		t.Fail()
	}
}

func revLookup(t *testing.T) {
	record := testDatabase.ReverseLookup(id)
	if record.Username != "testuser" {
		t.Fail()
	}
}

func getUsersTest(t *testing.T) {
	users, err := testDatabase.GetUsers()
	if users[0] != "testuser" || err != nil {
		t.Fail()
	}
}

func getUUIDSTest(t *testing.T) {
	ids, err := testDatabase.GetUUIDS()
	if ids[0] != id || err != nil {
		t.Fail()
	}
}

func getAllTest(t *testing.T) {
	records, err := testDatabase.GetAll()
	if records[0].UUID != id || records[0].Username != "testuser" || err != nil {
		t.Fail()
	}
}

func rename(t *testing.T) {
	var password [32]byte
	copy(password[:], "Passw0rd!")
	err := testDatabase.RenameUser("testuser", "renameduser", password, "127.0.0.1")
	if err != nil {
		t.Fail()
	}
	record := testDatabase.LookupUser("renameduser")
	if id != record.UUID {
		t.Fail()
	}
	record = testDatabase.ReverseLookup(id)
	if record.Username != "renameduser" {
		t.Fail()
	}
}

func delete(t *testing.T) {
	var password [32]byte
	copy(password[:], "Passw0rd!")
	err := testDatabase.DeleteUser("renameduser", password)
	if err != nil {
		t.Fail()
	}
}

func createFail(t *testing.T) {
	var password [32]byte
	copy(password[:], "Passw0rd!")
	var err error
	id, err = testDatabase.AddUser("testuser", "Real Name", password, "127.0.0.1")
	if err == nil || id != "" {
		t.Fail()
	}
}

func lookupFail(t *testing.T) {
	record := testDatabase.LookupUser("baduser")
	if record.UUID != "" {
		t.Fail()
	}
}

func revLookupFail(t *testing.T) {
	record := testDatabase.ReverseLookup("not a valid id")
	if record.Username != "" {
		t.Fail()
	}
}

func renameNotFound(t *testing.T) {
	var password [32]byte
	copy(password[:], "Passw0rd!")
	err := testDatabase.RenameUser("baduser", "renameduser", password, "127.0.0.1")
	if err == nil {
		t.Fail()
	}
	copy(password[:], "WRONG")
	err = testDatabase.RenameUser("testuser", "renameduser", password, "127.0.0.1")
	if err == nil {
		t.Fail()
	}
}

func renameCollide(t *testing.T) {
	var password [32]byte
	copy(password[:], "Passw0rd!")
	err := testDatabase.RenameUser("testuser", "testuser", password, "127.0.0.1")
	if err == nil {
		t.Fail()
	}
}

func deleteFail(t *testing.T) {
	var password [32]byte
	copy(password[:], "Passw0rd!")
	err := testDatabase.DeleteUser("baduser", password)
	if err == nil {
		t.Fail()
	}
	copy(password[:], "Incorrect")
	err = testDatabase.DeleteUser("testuser", password)
	if err == nil {
		t.Fail()
	}
}

func getUsersFail(t *testing.T) {
	_, err := testDatabase.GetUsers()
	if err == nil {
		t.Fail()
	}
}

func getUUIDSFail(t *testing.T) {
	_, err := testDatabase.GetUUIDS()
	if err == nil {
		t.Fail()
	}
}

func getAllFail(t *testing.T) {
	_, err := testDatabase.GetAll()
	if err == nil {
		t.Fail()
	}
}
