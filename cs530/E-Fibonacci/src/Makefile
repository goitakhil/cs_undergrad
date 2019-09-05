BIN=../bin
OBJ=../obj
CFLAGS+=-I../include
CXXFLAGS+=-I../include

all fib: $(BIN)/fibonacci $(BIN)/pthreadFibonacci $(BIN)/OMPFibonacci

$(BIN)/fibonacci: $(OBJ)/Fibonacci.o $(OBJ)/FibMain.o
	$(CXX) $(LDFLAGS) $(CXXFLAGS) -o $@ $^

$(BIN)/pthreadFibonacci: $(OBJ)/PthreadFibonacci.o $(OBJ)/PthreadFibMain.o
	$(CXX) $(LDFLAGS) -pthread $(CXXFLAGS) -o $@ $^

$(BIN)/OMPFibonacci: $(OBJ)/OMPFibonacci.o $(OBJ)/FibMain.o
	$(CXX) $(LDFLAGS) -fopenmp $(CXXFLAGS) -o $@ $^

$(OBJ)/%.o: %.cpp
	$(CXX) $(LDFLAGS) $(CXXFLAGS) -c -o $@ $^

clean:
	rm -rf $(OBJ)/*.o $(BIN)/fibonacci $(BIN)/pthreadFibonacci $(BIN)/OMPFibonacci
