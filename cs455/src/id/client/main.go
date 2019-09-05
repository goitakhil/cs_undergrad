package main

import (
	"os"

	ishell "gopkg.in/abiosoft/ishell.v2"
)

var version = "no version specified in build"

func main() {
	if len(os.Args) != 1 {
		parseArgs()
		os.Exit(0)
	}

	shell := ishell.New()
	shell.Println("IDClient CLI version 0.25")
	shellCommands(shell)
	shell.Run()

	shell.Close()
}
