CC = gcc
CFLAGS = -O3 -march=native

all: vectorize

vectorize: vectorize.o
	$(CC) $(CFLAGS) $(LDFLAGS) -o $@ $^

%.o: %.c
	$(CC) $(CFLAGS) $(CPPFLAGS) -c -o $@ $<

clean:
	rm -rf *.o vectorize
