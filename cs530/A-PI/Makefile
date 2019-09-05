CXX=g++
OBJ=obj
SRC=src
BIN=bin
GTEST_DIR=../googletest/googletest
MPICXX ?= mpicxx
CXXFLAGS= -I$(GTEST_DIR) -I$(GTEST_DIR)/include -std=c++11 -pthread -fopenmp -g -Wall

all: $(BIN)/pi $(BIN)/pi_pthreads $(BIN)/pi_omp $(BIN)/pi_mpi

$(BIN)/pi: $(OBJ)/main.o $(OBJ)/calculate_pi.o
	mkdir -p bin obj
	$(CXX) $(CXXFLAGS) -o $@ $^

$(BIN)/pi_pthreads: $(OBJ)/main_pthreads.o $(OBJ)/calculate_pi_pthreads.o
	mkdir -p bin obj
	$(CXX) $(CXXFLAGS) -o $@ $^

$(BIN)/pi_omp: $(OBJ)/main.o $(OBJ)/calculate_pi_omp.o
	mkdir -p bin obj
	$(CXX) $(CXXFLAGS) -o $@ $^

$(BIN)/pi_mpi: $(SRC)/main_mpi.cpp $(SRC)/calculate_pi_mpi.cpp
	mkdir -p bin obj
	$(MPICXX) $(CXXFLAGS) -o $@ $^

$(BIN)/pi_test: $(OBJ)/calculate_pi_test.o $(OBJ)/gtest-all.o $(OBJ)/gtest_main.o $(OBJ)/calculate_pi.o
	mkdir -p bin obj
	$(CXX) $(CXXFLAGS) -o $@ $^

$(BIN)/pi_pthreads_test: $(OBJ)/calculate_pi_pthreads_test.o $(OBJ)/gtest-all.o $(OBJ)/gtest_main.o $(OBJ)/calculate_pi_pthreads.o
	mkdir -p bin obj
	$(CXX) $(CXXFLAGS) -o $@ $^

$(BIN)/pi_omp_test: $(OBJ)/calculate_pi_test.o $(OBJ)/gtest-all.o $(OBJ)/gtest_main.o $(OBJ)/calculate_pi_omp.o
	mkdir -p bin obj
	$(CXX) $(CXXFLAGS) -o $@ $^

GTEST_SRCS_ = $(GTEST_DIR)/src/*.cc $(GTEST_DIR)/src/*.h $(GTEST_HEADERS)

$(OBJ)/gtest-all.o:
	mkdir -p bin obj
	$(CXX) $(CXXFLAGS) -c ${GTEST_DIR}/src/gtest-all.cc -o $(OBJ)/gtest-all.o

$(OBJ)/gtest_main.o : $(GTEST_SRCS_)
	mkdir -p bin obj
	$(CXX) $(CXXFLAGS) -I${GTEST_DIR}/include -I$(GTEST_DIR) -c \
		$(GTEST_DIR)/src/gtest_main.cc -o $@

$(OBJ)/%.o: $(SRC)/%.cpp
	mkdir -p bin obj
	$(CXX) $(CXXFLAGS) -c -o $@ $<


gtest test: $(BIN)/pi_test $(BIN)/pi_pthreads_test $(BIN)/pi_omp_test $(BIN)/pi_mpi_test
	$(BIN)/pi_test 
	$(BIN)/pi_pthreads_test
	$(BIN)/pi_omp_test
	mpirun -n 2 $(BIN)/pi_mpi_test

clean:
	rm -f $(BIN)/*
	rm -f $(OBJ)/*
	
.PHONY: test clean mkdir
