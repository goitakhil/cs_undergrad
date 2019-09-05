package main

import (
	"fmt"
	"net/rpc"
	"os"
	"strings"

	"github.com/droundy/goopt"
)

var config struct {
	server string
	port   string
	client *rpc.Client
	ip     string
}

func parseArgs() {

	goopt.Version = version
	createHelp := "<username> [<real name>] [-p | --password <password>]"
	modifyHelp := "<old username> <new username> [-p | --password <password>]"
	deleteHelp := "<username> [-p | --password <password>]"

	server := goopt.String([]string{"-s", "--server"}, "<server>", "Domain name or ip of server")
	port := goopt.String([]string{"-n", "--numport"}, "<port>", "Port in which to connect to server")
	password := goopt.String([]string{"-p", "--password"}, "", "Optional argument for some calls")

	create := goopt.Flag([]string{"-c", "--create"}, []string{}, createHelp, "")

	modify := goopt.Flag([]string{"-m", "--modify"}, []string{}, modifyHelp, "")
	delete := goopt.Flag([]string{"-d", "--delete"}, []string{}, deleteHelp, "")
	lookup := goopt.Flag([]string{"-l", "--lookup"}, []string{}, "<username>", "")
	reverse := goopt.Flag([]string{"-r", "--reverse-lookup"}, []string{}, "<UUID>", "")
	get := goopt.Flag([]string{"-g", "--get"}, []string{}, "users|uuids|all", "")
	goopt.Parse(nil)

	config.server = *server
	config.port = *port

	if strings.TrimSpace(*port) == "<port>" {
		config.port = "5185"
	}

	var arg0, arg1 string
	if len(goopt.Args) == 0 {
		fmt.Println(goopt.Usage())
		os.Exit(1)
	}
	arg0 = goopt.Args[0]

	if len(goopt.Args) > 1 {
		arg1 = goopt.Args[1]
	}
	err := connect(config.server, config.port)
	if err != nil {
		fmt.Println(err)
		os.Exit(1)
	}

	switch {
	case *create:
		err := sendCreate(arg0, arg1, *password)
		checkErr(err)
	case *modify:
		if strings.TrimSpace(arg1) == "" {
			fmt.Println(goopt.Usage())
			os.Exit(1)
		}
		err := sendModify(arg0, arg1, *password)
		checkErr(err)
	case *delete:
		err := sendDelete(arg0, *password)
		checkErr(err)
	case *lookup:
		err := sendLookup(arg0)
		checkErr(err)
	case *reverse:
		err := sendReverse(arg0)
		checkErr(err)
	case *get:
		err := sendGet(arg0)
		checkErr(err)
	}

	if len(os.Args) < 3 {
		fmt.Printf(goopt.Help())
		os.Exit(1)
	}

}

func checkErr(err error) {
	if err != nil {
		fmt.Println(err)
		os.Exit(1)
	}
}
