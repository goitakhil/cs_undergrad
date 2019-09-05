
all jacobi2d:
	mkdir -p obj bin
	$(MAKE) -C src $@

test:
	mkdir -p obj bin
	$(MAKE) -C test $@

pthreads:
	mkdir -p obj bin
	$(MAKE) -C test $@

openmp:
	mkdir -p obj bin
	$(MAKE) -C test $@

clean:
	$(MAKE) -C test clean
	$(MAKE) -C src clean
	rm -rf bin/* obj/*
	rm result.txt

.PHONY: test clean
