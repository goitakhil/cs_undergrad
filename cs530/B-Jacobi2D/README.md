# Jacobi 2D

## Compiling
To compile execute the following command:
```
make
```
This should produce 3 executables in the bin directory.
* jacobi2d (serial implementation)
* jacobi2d_omp (openMP implementation)
* jacobi2d_pthreads (pthreads implementation)

The executables produced in the bin directory all take an input filename as
the first argument and a delta value (float) as the second argument.

Example:
```
./jacobi2d <filename> <delta value>
```
The program will output a file called "results.txt" in your current working directory.

If you cannot remember the required arguments and need help, use the "-h" flag
as follows:

./jacobi2d -h

This will print a usage message.

## Testing
To run tests execute the following commands:
Note: It's important to run the first two commands before the last one or it
      won't work
```
make pthreads
make openmp
make test
```
This will run the testing suit on each version of jacobi2d. 

## File Format
File need to be in the following format.

Line1: number of rows
Line2: number of columns
Line3: First row of matrix, each value separated by a space

Example:

4
4
1 1 1 1
1 0 0 1
1 0 0 1
1 1 1 1

Look at input_file_example.txt for example.
