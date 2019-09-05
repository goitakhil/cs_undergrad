BIN=../bin
OBJ=../obj
CFLAGS+=-I../include -g
CXXFLAGS+=-I../include -g

all jacobi2d: $(BIN)/jacobi2d

$(BIN)/jacobi2d: $(OBJ)/jacobi2d.o $(OBJ)/main.o
	$(CXX) $(LDFLAGS) $(CXXFLAGS) -o $@ $^

$(OBJ)/%.o: %.cpp
	$(CXX) $(LDFLAGS) $(CXXFLAGS) -c -o $@ $^


all pthreads: $(BIN)/jacobi2d_pthreads

$(BIN)/jacobi2d_pthreads: $(OBJ)/jacobi2d_pthreads.o $(OBJ)/main.o
	$(CXX) -lpthread $(LDFLAGS) $(CXXFLAGS) -o $@ $^

$(OBJ)/jacobi2d_pthreads.o: jacobi2d_pthreads.c
	$(CXX) -lpthread $(LDFLAGS) $(CXXFLAGS) -c -o $@ $^


all openmp: $(BIN)/jacobi2d_omp

$(BIN)/jacobi2d_omp: $(OBJ)/jacobi2d_omp.o $(OBJ)/main.o
	$(CXX) -fopenmp $(LDFLAGS) $(CXXFLAGS) -o $@ $^

$(OBJ)/jacobi2d_omp.o: jacobi2d_omp.c
	$(CXX) -fopenmp $(LDFLAGS) $(CXXFLAGS) -c -o $@ $^



clean:
	rm -rf *.o jacobi2d
	rm -rf *.o jacobi2d_pthreads
	rm -rf *.o jacobi2d_omp
