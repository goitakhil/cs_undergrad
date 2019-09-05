CPP=g++
OBJ=obj
SRC=src
TEST=test
BIN=bin
CPPFLAGS=-I./include 
GTEST_DIR=../googletest/googletest

all fibonacci:
	mkdir -p bin obj
	$(MAKE) -C src $@

gtest:
	mkdir -p obj 
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

test: serial_test pthread_test omp_test

serial_test: $(OBJ)/gtest_main.a $(OBJ)/Fibonacci.o
	$(CPP) -I${GTEST_DIR}/include -pthread $(TEST)/FibonacciTests.cpp \
         $(OBJ)/gtest_main.a $(OBJ)/Fibonacci.o -o FibonacciTests 
	./FibonacciTests
pthread_test: $(OBJ)/gtest_main.a $(OBJ)/PthreadFibonacci.o
	$(CPP) -I${GTEST_DIR}/include -pthread $(TEST)/FibonacciTests.cpp \
         $(OBJ)/gtest_main.a $(OBJ)/PthreadFibonacci.o -o PthreadFibonacciTests 
	./PthreadFibonacciTests

omp_test: $(OBJ)/gtest_main.a $(OBJ)/OMPFibonacci.o
	$(CPP) -I${GTEST_DIR}/include -pthread -fopenmp $(TEST)/FibonacciTests.cpp \
         $(OBJ)/gtest_main.a $(OBJ)/OMPFibonacci.o -o OMPFibonacciTests 
	./OMPFibonacciTests
clean:
	rm -f FibonacciTests PthreadFibonacciTests OMPFibonacciTests
	$(MAKE) -C src clean
	
