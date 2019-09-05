package main

import (
	"errors"
	"id/multicast"
	"os"
	"strings"

	ishell "gopkg.in/abiosoft/ishell.v2"
)

func shellCommands(shell *ishell.Shell) {
	shell.AddCmd(&ishell.Cmd{
		Name: "greet",
		Help: "greet",
		Func: func(c *ishell.Context) {
			c.Println("Hello " + os.Getenv("USER"))
		},
	})
	shell.AddCmd(&ishell.Cmd{
		Name: "connect",
		Help: "connect <IP or FQDN> <port>",
		Func: shellConnect,
	})
	shell.AddCmd(&ishell.Cmd{
		Name: "create",
		Help: "create username \"Real Name\"",
		Func: shellCreate,
	})
	shell.AddCmd(&ishell.Cmd{
		Name: "modify",
		Help: "modify username newusername",
		Func: shellModify,
	})
	shell.AddCmd(&ishell.Cmd{
		Name: "delete",
		Help: "delete username",
		Func: shellDelete,
	})
	shell.AddCmd(&ishell.Cmd{
		Name: "get",
		Help: "get users|uuids|all",
		Func: shellGet,
	})
	shell.AddCmd(&ishell.Cmd{
		Name: "lookup",
		Help: "lookup username",
		Func: shellLookup,
	})
	shell.AddCmd(&ishell.Cmd{
		Name: "reverse-lookup",
		Help: "reverse-lookup uuid",
		Func: shellReverse,
	})
}

func shellConnect(c *ishell.Context) {
	if len(c.Args) == 0 {
		rank, addr := multicast.DiscoverMaster()
		if rank == 0 {
			c.Err(errors.New("Unable to detect a running server"))
			return
		} else {
			config.server = addr
		}
	} else {
		config.server = c.Args[0]
	}
	//if the client is connected, then close the connection.
	if config.client != nil {
		config.client.Close()
	}
	if len(c.Args) > 1 {
		config.port = c.Args[1]
	} else {
		config.port = "5185"
	}
	err := connect(config.server, config.port)
	if err != nil {
		c.Err(err)
	}
}

func shellCreate(c *ishell.Context) {
	err := checkConfig()
	if err != nil {
		c.Err(err)
		return
	}
	if len(c.Args) == 0 {
		c.Err(errors.New("Usage: create username \"Real Name\" "))
		return
	}

	var arg0, arg1 string
	arg0 = c.Args[0]
	if len(c.Args) > 1 {
		arg1 = c.Args[1]
	}

	c.ShowPrompt(false)
	c.Printf("Password:")
	pass := c.ReadPassword()
	err = sendCreate(arg0, arg1, pass)
	c.ShowPrompt(true)

	if err != nil {
		c.Err(err)
	}
}

func shellModify(c *ishell.Context) {
	err := checkConfig()
	if err != nil {
		c.Err(err)
		return
	}
	if len(c.Args) < 2 {
		c.Err(errors.New("Usage: modify username newusername"))
		return
	}
	c.ShowPrompt(false)
	c.Printf("Password:")
	password := c.ReadPassword()
	c.ShowPrompt(true)

	var arg0, arg1 string
	arg0 = c.Args[0]
	if len(c.Args) > 1 {
		arg1 = c.Args[1]
	}
	if strings.TrimSpace(arg0) == "" {
		c.Err(errors.New("Usage: modify username newusername"))
	}
	if strings.TrimSpace(arg1) == "" {
		c.Err(errors.New("Usage: modify username newusername"))
	}

	err = sendModify(arg0, arg1, password)
	if err != nil {
		c.Err(err)
		return
	}
}

func shellLookup(c *ishell.Context) {
	err := checkConfig()
	if err != nil {
		c.Err(err)
		return
	}
	if len(c.Args) < 1 {
		c.Err(errors.New("Usage: lookup username"))
		return
	}
	err = sendLookup(c.Args[0])
	if err != nil {
		c.Err(err)
	}
}

func shellReverse(c *ishell.Context) {
	err := checkConfig()
	if err != nil {
		c.Err(err)
		return
	}
	if len(c.Args) < 1 {
		c.Err(errors.New("Usage: reverse-lookup uuid"))
		return
	}
	err = sendReverse(c.Args[0])
	if err != nil {
		c.Err(err)
	}
}

func shellDelete(c *ishell.Context) {
	err := checkConfig()
	if err != nil {
		c.Err(err)
		return
	}
	if len(c.Args) == 0 {
		c.Err(errors.New("Usage: delete username"))
		return
	}

	var arg0 string
	arg0 = c.Args[0]

	c.ShowPrompt(false)
	c.Printf("Password:")
	pass := c.ReadPassword()
	err = sendDelete(arg0, pass)
	c.ShowPrompt(true)

	if err != nil {
		c.Err(err)
	}

}

func shellGet(c *ishell.Context) {
	err := checkConfig()
	if err != nil {
		c.Err(err)
		return
	}
	if len(c.Args) < 1 {
		c.Err(errors.New("Usage: get users|uuids|all"))
		return
	}
	err = sendGet(c.Args[0])
	if err != nil {
		c.Err(err)
	}
}

func checkConfig() error {
	if strings.TrimSpace(config.server) == "" {
		return errors.New("No connection to server")
	}
	if strings.TrimSpace(config.port) == "" {
		return errors.New("No connection to server")
	}
	return nil
}
