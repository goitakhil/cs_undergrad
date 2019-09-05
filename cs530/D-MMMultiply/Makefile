BIN=bin
CXX=gcc


mm:
	mkdir -p obj bin
	$(MAKE) -C src matrix_matrix

pthreads:
	mkdir -p obj bin
	$(MAKE) -C src pthreads

openmp:
	mkdir -p obj bin
	$(MAKE) -C src openmp

mpi:
	mkdir -p obj bin
	$(MAKE) -C src mpi

cuda:
	mkdir -p obj bin
	$(MAKE) -C src cuda

all: mm pthreads openmp mpi cuda
	
.PHONY: test test_pthreads test_openmp clean

test:
	mkdir -p obj bin
	$(MAKE) -C src 
	$(MAKE) -C test $@

test_pthreads:
	mkdir -p obj bin
	$(MAKE) -C src pthreads
	$(MAKE) -C test $@

test_openmp:
	mkdir -p obj bin
	$(MAKE) -C src openmp
	$(MAKE) -C test $@

test_mpi:
	mkdir -p obj bin
	$(MAKE) -C src mpi
	$(MAKE) -C test $@

clean:
	$(MAKE) -C test clean
	$(MAKE) -C src clean
	rm -f bin/* obj/*.o
