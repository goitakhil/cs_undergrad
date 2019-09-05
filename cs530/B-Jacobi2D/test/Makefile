BIN=../bin
OBJ=../obj
GTEST_DIR=../../googletest/googletest
CXXFLAGS+=-I../include -I../../googletest/googletest/include -fopenmp
LDFLAGS+=-lpthread -fopenmp
GTEST_SRCS_ = $(GTEST_DIR)/src/*.cc $(GTEST_DIR)/src/*.h


test: $(BIN)/jacobi2d_test
	$(BIN)/jacobi2d_test
	$(BIN)/jacobi2d_pthreads_test
	$(BIN)/jacobi2d_omp_test

$(OBJ)/gtest-all.o: $(GTEST_SRCS_)
	${CXX} ${CXXFLAGS} -I${GTEST_DIR}/include -I${GTEST_DIR} -pthread -c ${GTEST_DIR}/src/gtest-all.cc -o $@

$(BIN)/jacobi2d_test: $(OBJ)/jacobi2d_test.o $(OBJ)/jacobi2d.o $(OBJ)/gtest-all.o
	$(CXX) $(LDFLAGS) $(CXXFLAGS) -o $@ $^

$(OBJ)/jacobi2d_test.o: jacobi2d_test.cpp
	$(CXX) $(CXXFLAGS) -c -o $@ $^

$(OBJ)/%.o:
	$(MAKE) -C ../src $@



pthreads: $(BIN)/jacobi2d_pthreads_test

$(BIN)/jacobi2d_pthreads_test: $(OBJ)/jacobi2d_test.o $(OBJ)/jacobi2d_pthreads.o $(OBJ)/gtest-all.o
	$(CXX) $(LDFLAGS) $(CXXFLAGS) -o $@ $^

$(OBJ)/%.o:
	$(MAKE) -C ../src $@



openmp: $(BIN)/jacobi2d_omp_test

$(BIN)/jacobi2d_omp_test: $(OBJ)/jacobi2d_test.o $(OBJ)/jacobi2d_omp.o $(OBJ)/gtest-all.o
	$(CXX) $(LDFLAGS) $(CXXFLAGS) -o $@ $^

$(OBJ)/%.o:
	$(MAKE) -C ../src $@



clean:
	rm -rf *.o jacobi2d_test
	rm -rf *.o jacobi2d_omp_test
	rm -rf *.o jacobi2d_pthreads_test
