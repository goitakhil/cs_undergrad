CXX=g++ -g -lpthread -fopenmp 
CUDACXX= nvcc
BIN=../bin
OBJ=../obj
CFLAGS+=-I.../include
CXXFLAGS+=-I../include -ggdb
MPICXX=mpicxx

#make: 
	#gcc -o matrix_matrix main.c matrix_matrix.c matrix_matrix.hpp -g

matrix_matrix: $(BIN)/matrix_matrix
	echo "first target"

$(OBJ)/%.o: %.cpp
	$(CXX) $(LDFLAGS) $(CXXFLAGS) -c -o $@ $^
 
 $(OBJ)/%.o: %.c
	$(CXX) $(LDFLAGS) $(CXXFLAGS) -c -o $@ $^


$(BIN)/matrix_matrix: $(OBJ)/matrix_matrix.o $(OBJ)/main.o $(OBJ)/Configuration.o
	$(CXX) $(LDFLAGS) $(CXXFLAGS) -o $@ $^


pthreads: $(BIN)/matrix_matrix_pthreads

$(OBJ)/%.o: %.cpp
	$(CXX) $(LDFLAGS) $(CXXFLAGS) -c -o $@ $^
 
 $(OBJ)/%.o: %.c
	$(CXX) $(LDFLAGS) $(CXXFLAGS) -c -o $@ $^


$(BIN)/matrix_matrix_pthreads: $(OBJ)/matrix_matrix_pthreads.o $(OBJ)/main_parallel.o $(OBJ)/Configuration.o
	$(CXX) $(LDFLAGS) $(CXXFLAGS) -o $@ $^


openmp: $(BIN)/matrix_matrix_openmp

$(OBJ)/%.o: %.cpp
	$(CXX) $(LDFLAGS) $(CXXFLAGS) -c -o $@ $^
 
 $(OBJ)/%.o: %.c
	$(CXX) $(LDFLAGS) $(CXXFLAGS) -c -o $@ $^


$(BIN)/matrix_matrix_openmp: $(OBJ)/matrix_matrix_openmp.o $(OBJ)/main_parallel.o $(OBJ)/Configuration.o
	$(CXX) $(LDFLAGS) $(CXXFLAGS) -o $@ $^


mpi: $(BIN)/matrix_matrix_mpi
$(OBJ)/%.o: %.cpp
	$(MPICXX) $(LDFLAGS) $(CXXFLAGS) -c -o $@ $^
	
$(OBJ)/%.o: %.c
	$(MPICXX) $(LDFLAGS) $(CXXFLAGS) -c -o $@ $^


$(BIN)/matrix_matrix_mpi: $(OBJ)/matrix_matrix_mpi.o $(OBJ)/main.o $(OBJ)/Configuration.o
	$(MPICXX) $(LDFLAGS) $(CXXFLAGS) -o $@ $^


cuda: $(BIN)/matrix_matrix_cuda
$(OBJ)/%.o: %.cpp
	$(CXX) $(LDFLAGS) -c -o $@ $^

$(OBJ)/%.o: %.cu
	$(CUDACXX) $(LDFLAGS) -c -o $@ $^

$(BIN)/matrix_matrix_cuda: $(OBJ)/matrix_matrix_cuda.o $(OBJ)/main_cuda.o $(OBJ)/Configuration.o
	$(CUDACXX) $(LDFLAGS) -o $@ $^

.PHONY: clean

clean:
	rm -f *.o 
