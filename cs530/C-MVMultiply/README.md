### INSTRUCTIONS FOR RUNNING TESTS

Excecute the following commands in the C-MVMultiply directory(in order)
## Compiling
* make gtest
* make serial_test
* make pthreads_test
* make omp_test
* make test

## Running
To run main:
* make serial_mvmult
* make pthreads_mvmult
* make omp_mvmult
Then run with ./bin/(desired progrm) <inputfile>

To run the main serial version:
make serial_mvmult
./bin/serial_mvmult <filename> <num_threads>

To run the main pthreads version:
make pthreads_mvmult
./bin/pthreadsmvmult <filename> <num_threads>

To run the main omp version:
make omp_mvmult
./bin/omp_mvmult <filename> <num_threads>

The result will output to result.txt

## Input File format

rows
columns
matrix
vector
