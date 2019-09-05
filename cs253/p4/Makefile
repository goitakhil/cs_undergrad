
INC=-I .
LIB=-L .
CC=gcc
CFLAGS=-g -Wall $(INC) $(LIB) -MMD

hello: main.o f1.o f2.o f3.o
	$(CC) $(CFLAGS) $^ -o $@

-include *.d

default: hello

clean:
	rm -f *.o *.d hello

run: hello
	./hello
