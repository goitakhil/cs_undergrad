BIN=../bin
OBJ=../obj
GTEST_DIR=../../googletest/googletest
CXXFLAGS+=-g -I../include -I../../googletest/googletest/include
LDFLAGS+=-lpthread -fopenmp
CXX=g++

.PHONY: test

test: $(BIN)/matrix_matrix_unittest $(BIN)/Configuration_unittests
	$(BIN)/Configuration_unittests
	$(BIN)/matrix_matrix_unittest

GTEST_SRCS_ = $(GTEST_DIR)/src/*.cc $(GTEST_DIR)/src/*.h

$(OBJ)/gtest-all.o: $(GTEST_SRCS_)
	${CXX} ${CXXFLAGS} -I${GTEST_DIR}/include -I${GTEST_DIR} -pthread -c ${GTEST_DIR}/src/gtest-all.cc -o $@

#gtest:
	#${CPP} -I${GTEST_DIR}/include -I${GTEST_DIR} \
    #-pthread -c ${GTEST_DIR}/src/gtest-all.cc -o $(OBJ)/gtest-all.o
	#ar -rv $(OBJ)/libgtest.a $(OBJ)/gtest-all.o

$(OBJ)/gtest_main.o : $(GTEST_SRCS_)
	$(CXX) $(CPPFLAGS) -I${GTEST_DIR}/include -I$(GTEST_DIR) $(CXXFLAGS) -c \
            $(GTEST_DIR)/src/gtest_main.cc -o $@

$(OBJ)/gtest_main.a : $(OBJ)/gtest-all.o $(OBJ)/gtest_main.o
	$(AR) $(ARFLAGS) $@ $^

$(BIN)/Configuration_unittests: $(OBJ)/Configuration.o
	$(CXX) -I${GTEST_DIR}/include -pthread Configuration_unittests.cpp \
         $(OBJ)/gtest_main.a $(OBJ)/Configuration.o -o $(BIN)/Configuration_unittests

$(BIN)/matrix_matrix_unittest: $(OBJ)/matrix_matrix_unittest.o $(OBJ)/matrix_matrix.o $(OBJ)/gtest-all.o        
	$(CXX) $(LDFLAGS) $(CXXFLAGS) -o $@ $^      

$(OBJ)/matrix_matrix_unittest.o: matrix_matrix_unittest.cpp
	$(CXX) $(CXXFLAGS) -c -o $@ $^  

$(OBJ)/matrix_matrix_parallel_unittest.o: matrix_matrix_parallel_unittest.cpp
	$(CXX) $(CXXFLAGS) -c -o $@ $^  

$(OBJ)/%.o: $(OBJ)/gtest_main.a 
	$(MAKE) -C ../src $@
 

test_pthreads: $(BIN)/matrix_matrix_unittest_pthreads $(BIN)/Configuration_unittests
	$(BIN)/Configuration_unittests
	$(BIN)/matrix_matrix_unittest_pthreads

$(BIN)/matrix_matrix_unittest_pthreads: $(OBJ)/matrix_matrix_parallel_unittest.o $(OBJ)/matrix_matrix_pthreads.o $(OBJ)/gtest-all.o
	$(CXX) $(LDFLAGS) $(CXXFLAGS) -o $@ $^      

test_openmp: $(BIN)/matrix_matrix_unittest_openmp $(BIN)/Configuration_unittests
	$(BIN)/Configuration_unittests
	$(BIN)/matrix_matrix_unittest_openmp

$(BIN)/matrix_matrix_unittest_openmp: $(OBJ)/matrix_matrix_parallel_unittest.o $(OBJ)/matrix_matrix_openmp.o $(OBJ)/gtest-all.o    
	$(CXX) $(LDFLAGS) $(CXXFLAGS) -o $@ $^

test_mpi:  $(BIN)/matrix_matrix_unittest_mpi $(BIN)/Configuration_unittests
	$(BIN)/Configuration_unittests
	$(BIN)/matrix_matrix_unittest_mpi

$(BIN)/matrix_matrix_unittest_mpi: $(OBJ)/matrix_matrix_parallel_unittest.o $(OBJ)/matrix_matrix_mpi.o $(OBJ)/gtest-all.o    
	$(CXX) $(LDFLAGS) $(CXXFLAGS) -o $@ $^


clean: 
	rm -rf *.o matrix_matrix_unittest
	rm -rf *.o matrix_matrix_unittest_pthreads
	rm -rf *.o matrix_matrix_unittest_openmp
	rm -rf *.o matrix_matrix_unittest_mpi
