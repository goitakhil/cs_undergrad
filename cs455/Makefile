export GOPATH = $(shell pwd)

all: id-server id-client redis

id-server:
	go get -v id/server
	go build -v id/server

id-client:
	go get -v id/client
	go build -v -ldflags "-X main.version=`git rev-parse HEAD`" id/client

test: all redis
	-killall -q redis
	export PATH=$(GOPATH):$$PATH ; go test -v id/server

bench: all redis
	-killall -q redis
	export PATH=$(GOPATH):$$PATH ; go test -bench=. -benchtime=2s id/server

clean:
	rm -rf bin server client

fmt:
	go fmt id/server id/client

redis:
	curl http://download.redis.io/releases/redis-stable.tar.gz | tar xz
	make -C redis-stable
	mv redis-stable/src/redis-server redis

run:
	export PATH=$(GOPATH):$$PATH; server

docker:
	docker build . -t id:latest

docker-distclean: distclean
	docker rm id
	docker 
	docker-compose rm

distclean: clean
	rm -rf src/github.com src/golang.org src/gopkg.in pkg redis-stable redis dump.rdb
