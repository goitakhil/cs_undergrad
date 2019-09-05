CPP=g++
GCC=gcc
OBJ=obj
SRC=src
BIN=bin
CPPFLAGS=-I$(SRC) 
GTEST_DIR=../googletest/googletest
all:
	make serial_mvmult
	make pthreads_mvmult
	make omp_mvmult

serial_mvmult: $(SRC)/main.o $(OBJ)/mvmultiply.o
	mkdir -p obj bin
	$(CPP) -I$(SRC) -std=c++11 -o $@ $^
	mv serial_mvmult bin

pthreads_mvmult: $(SRC)/main.o $(OBJ)/mvm_pthreads.o
	mkdir -p obj bin
	$(CPP) -I$(SRC) -pthread -std=c++11 -o $@ $^
	mv pthreads_mvmult bin

omp_mvmult: $(SRC)/main.o $(OBJ)/mvm_omp.o
	mkdir -p obj bin
	$(CPP) -I$(SRC) -fopenmp -std=c++11 -o $@ $^
	mv omp_mvmult bin
gtest:
	mkdir -p obj bin
	${CPP} -I${GTEST_DIR}/include -I${GTEST_DIR} \
	-pthread -c ${GTEST_DIR}/src/gtest-all.cc -o $(OBJ)/gtest-all.o
	ar -rv $(OBJ)/libgtest.a $(OBJ)/gtest-all.o

GTEST_SRCS_ = $(GTEST_DIR)/src/*.cc $(GTEST_DIR)/src/*.h $(GTEST_HEADERS)

$(OBJ)/gtest_main.o : $(GTEST_SRCS_)
	$(CXX) $(CPPFLAGS) -I${GTEST_DIR}/include -I$(GTEST_DIR) $(CXXFLAGS) -c \
	$(GTEST_DIR)/src/gtest_main.cc -o $@

$(OBJ)/gtest_main.a : $(OBJ)/gtest-all.o $(OBJ)/gtest_main.o
	$(AR) $(ARFLAGS) $@ $^

$(OBJ)/%.o: $(SRC)/%.cpp
	$(CPP) $(CPPFLAGS) $(CXXFLAGS) -c -o $@ $<

.PHONY: test clean

omp_test: $(OBJ)/gtest_main.a $(OBJ)/mvm_omp.o
	mkdir -p obj bin
	$(CPP) -I${GTEST_DIR}/include -pthread -fopenmp $(SRC)/mvm_test.cpp \
	$(OBJ)/gtest_main.a $(OBJ)/mvm_omp.o -o omp_test 
	mv omp_test bin
	./bin/omp_test


serial_test: $(OBJ)/gtest_main.a $(OBJ)/mvmultiply.o
	mkdir -p obj bin
	$(CPP) -I${GTEST_DIR}/include -pthread $(SRC)/mvm_test.cpp \
	$(OBJ)/gtest_main.a $(OBJ)/mvmultiply.o -o serial_test 
	mv serial_test bin
	./bin/serial_test
pthreads_test: $(OBJ)/gtest_main.a $(OBJ)/mvm_pthreads.o
	mkdir -p obj bin
	$(CPP) -I${GTEST_DIR}/include -pthread $(SRC)/mvm_test.cpp \
	$(OBJ)/gtest_main.a $(OBJ)/mvm_pthreads.o -o pthreads_test 
	mv pthreads_test bin
	./bin/pthreads_test

test: 
	make pthreads_test
	make omp_test
	make serial_test
	./bin/serial_test
	./bin/pthreads_test
	./bin/omp_test

clean:
	rm -rf bin/
	rm -rf obj/
