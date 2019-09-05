Identity Server Project
===================================
CS 455 - Distributed Systems
Team 19:
 - Phil Gore
 - Tyler Bevan


## Overview

 This project is an implementation of a simple identity server using RPC over
 SSL. The server will connect to a redis server in which to store data provided
 by the client. Utilizing election, multicasting and lamport timestamps, any two
 servers which can communicate over multicast are bonded together for
 replication and relibility, if one server in a cluster goes down, there are
 other copies in the system for use.

## Manifest

 | Filename                          |Description                                     |
 |-----------------------------------|------------------------------------------------|
 | ./Makefile                        |GNU makefile for this project                   |
 | ./README.md                       |This file                                       |
 | ./whitepaper.md                   |Our whitpaper describing the project in detail  |
 | ./whitepaper.pdf                  |PDF generation of the whitepaper                |
 | ./Dockerfile                      |Id server and client, default runs a client     |
 | ./docker-compose.yml              |Runs a three node id server cluster in docker   |
 | ./src/id/client/actions.go        |Client RPC call functions                       |
 | ./src/id/client/arguments.go      |Client argument parsing                         |
 | ./src/id/client/main.go           |Client main                                     |
 | ./src/id/client/shell.go          |Client shell interface                          |
 | ./src/id/interfaces/interface.go  |Shared interfaces between client and server     |
 | ./src/id/multicast/multicast.go   |Multicast implementation and synchronicity.     |
 | ./src/id/server/main.go           |Server main                                     |
 | ./src/id/server/replicate.go      |Replication algorithm for id servers            |
 | ./src/id/server/clone.go          |Clones the db files,                            |
 | ./src/id/server/clone_test.go     |DB cloning unit tests                           |
 | ./src/id/server/rpc_test.go       |RPC unit tests and benchmarks                   |
 | ./src/id/server/rpc_functions.go  |Server RPC functions                            |
 | ./src/id/server/rpc_server.go     |RPC Server config and connection handling       |
 | ./src/id/server/tls_certs.go      |Server TLS certs                                |
 | ./src/id/server/userdb.go         |Server database handling                        |
 | ./src/id/server/userdb_test.go    |Server database test functions                  |
 | ./start-server.bash               |Script to build a redis DB, and start the server|


## Building the project

To build the project run the following in the top level folder:

`make`

Be aware that the build can take some time, as it needs to download all of the
dependencies for this project nd compile redis from source.
This should only take place on the first build.

The project is also built with Docker, so to use a Docker build, you can use the
makefile to build the Docker containers with `make docker` 

## Features and usage

To run the server do:
```
./server \
--numport <port to listen on. Default: 5185> \
--redis <address of the redis server default "localhost:5186"> 
```

The client has two modes, an immediate mode, in which you can can functions
directly, and a shell mode to use interactively.

Immediate Mode:

```
./client --server <servername> --numport <port in which to connect>  <subcommand>
```

 There are six subcommands the client knows about, each one has a long and short
 version for ease of use.
 
  `-c, --create           <username> [<real name>] [-p | --password <password>]`
 
  Create will make a new user with the username provided, You can optionally
  provide a real name in quotes, and an optional -p flag in order to give the
  user a password.

  `-m, --modify           <old username> <new username> [-p | --password <password>]`
 
  Modify will change an existing user's username in the database. If a password
  was given upon creation, then it will be necessary in order to modify the
  user.

  `-d, --delete           <username> [-p | --password <password>]`
 
  Delete will remove a user from the database. If the user has a password set,
  it will be required in order to delete the user.
 
  `-l, --lookup           <username>`

  Lookup will check for a given username in the database and return the user's
  UUID if there is a match.

  `-r, --reverse-lookup   <UUID>`
 
  Reverse Lookup will check for a given UUID in the database and return the
  user's username if there is a match.

  `-g, --get              users|uuids|all`

   Get will take in a secondary subcommand and return either: a list of
   usernames, a list of uuid's, or a list of all usernames, uuids, and real names in
   the database.

Shell Mode:

```
./client
```

   Running the executable with no flags will drop you into a CLI in which to
   use the subcommands listed above. There are a couple of key differences to be
   aware of:
 
   1. Using the shell requires you to first call the `connect` command.
      This will connect the CLI to the server in which you are using like so:

      `connect localhost 5015`

   2. The password flag is not used in leu of a password entry box. If an
      account has no password, then entering an empty password is proper
      execution.

   3. Subcommands should be called as executables in the path. As in the
      following syntax:
      
        `create pgore "Phil Gore"`
      
      In this case create will prompt for a password.

   4. There are no short versions of commands as there are for the immediate
      mode. If you want to delete a user, you must use the delete command.

### Docker 

When using Docker, the above instructions are still appropriate, but should be
predicated with:

```
docker run id server <options>
```

or

```
docker run id client <options>
```

Using the client's immediate/interactive mode will work if given the flags for
an interactive docker container `-it` like so:

```
docker run -it id client 
```


### Onyx

To run the project on Onyx, simply ssh to however many nodes you want to run
the server on. `cd` to the project directory and run `make run`. 

On any node aside from the head node, run the client without the `-s / --server`
flag to autoconnect to the cluster controller on the network.


## Testing

For unit tests, we used the go testing library. To run the unit tests do:
`make test`

System level tests are also done with the go testing framework and is covered
when using the above command. If all goes well, you'll get the following output:

```
=== RUN   TestClone
--- PASS: TestClone (0.00s)
=== RUN   TestRPC
Getting Userlist
Getting UUID List
Getting All Records
--- PASS: TestRPC (6.50s)
=== RUN   TestUserDB
=== RUN   TestUserDB/Create
=== RUN   TestUserDB/Lookup
=== RUN   TestUserDB/Reverse_Lookup
=== RUN   TestUserDB/Get_Users
=== RUN   TestUserDB/Get_UUIDS
=== RUN   TestUserDB/Get_All
=== RUN   TestUserDB/Rename
=== RUN   TestUserDB/Delete
=== RUN   TestUserDB/Existing_Create
=== RUN   TestUserDB/Nonexistant_Lookup
=== RUN   TestUserDB/Nonexistant_Reverse_Lookup
=== RUN   TestUserDB/Nonexistant_Rename
=== RUN   TestUserDB/Rename_Collision
=== RUN   TestUserDB/Nonexistant_Delete
=== RUN   TestUserDB/Dead_Create
=== RUN   TestUserDB/Dead_Lookup
=== RUN   TestUserDB/Dead_Reverse_Lookup
=== RUN   TestUserDB/Dead_Get_Users
=== RUN   TestUserDB/Dead_Get_UUIDS
=== RUN   TestUserDB/Dead_Get_All
=== RUN   TestUserDB/Dead_Rename
=== RUN   TestUserDB/Dead_Delete
--- PASS: TestUserDB (5.56s)
    --- PASS: TestUserDB/Create (0.10s)
    --- PASS: TestUserDB/Lookup (0.00s)
    --- PASS: TestUserDB/Reverse_Lookup (0.00s)
    --- PASS: TestUserDB/Get_Users (0.00s)
    --- PASS: TestUserDB/Get_UUIDS (0.00s)
    --- PASS: TestUserDB/Get_All (0.00s)
    --- PASS: TestUserDB/Rename (0.09s)
    --- PASS: TestUserDB/Delete (0.10s)
    --- PASS: TestUserDB/Existing_Create (0.00s)
    --- PASS: TestUserDB/Nonexistant_Lookup (0.00s)
    --- PASS: TestUserDB/Nonexistant_Reverse_Lookup (0.00s)
    --- PASS: TestUserDB/Nonexistant_Rename (0.09s)
    --- PASS: TestUserDB/Rename_Collision (0.00s)
    --- PASS: TestUserDB/Nonexistant_Delete (0.09s)
    --- PASS: TestUserDB/Dead_Create (0.00s)
    --- PASS: TestUserDB/Dead_Lookup (0.00s)
    --- PASS: TestUserDB/Dead_Reverse_Lookup (0.00s)
    --- PASS: TestUserDB/Dead_Get_Users (0.00s)
    --- PASS: TestUserDB/Dead_Get_UUIDS (0.00s)
    --- PASS: TestUserDB/Dead_Get_All (0.00s)
    --- PASS: TestUserDB/Dead_Rename (0.00s)
    --- PASS: TestUserDB/Dead_Delete (0.00s)
PASS
ok  	id/server	12.073s
```


### Known Bugs

As of this moment, we have no known bugs.

## Discussion

## Sources used

[Golang Standard Library](https://golang.org/pkg/)
[INI Parsing Library](https://github.com/zieckey/goini/)
[High Quality Logging Framework](https://github.com/Sirupsen/logrus/)
[LZ4 Stream Compression Library](https://github.com/pierrec/lz4/)

