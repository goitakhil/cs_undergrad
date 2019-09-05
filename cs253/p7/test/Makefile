#build the grader from the file test.c

CC=gcc
CFLAGS=-Wall -I. -I../include -g

grader: test-threads.o
	$(CC) $(CFLAGS) -L../lib -lring -lpthread -o $@ $^ 

clean:
	/bin/rm -f *.o grader
