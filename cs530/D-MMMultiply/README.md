To use the Makefile, navigate to the D-MMMultiply directory (same directory that this README.md file is in).

## How to build and run D-MMMultiply:
Slurm log files can be found in log/\<project version\>/ directories.
#### Serial
1) Compile with `make`
2) Run with `./bin/matrix_matrix <matrixA.txt> <matrixB.txt>`
3) Result file named `result_serial.txt` will be created in the top level directory of this project.

#### Pthreads
1) Compile with `make pthreads`
2) Run with `./bin/matrix_matrix_pthreads <matrixA.txt> <matrixB.txt> <num of threads>`
3) Result file named `result_pthreads.txt` will be created in the top level directory of this project.

#### OpenMP
1) Compile with `make openmp`
2) Run with `./bin/matrix_matrix_openmp <matrixA.txt> <matrixB.txt> <num of threads>`
3) Result file named `result_openmp.txt` will be created in the top level directory of this project.

#### MPI
1) If on R2 load mpi with `module load openmpi/gcc`
2) Compile with `make mpi`
3) Run slurm job `sbatch MMM_mpi.slurm` 
4) Slurm file can be modified as desired. 
5) Result file named `result_mpi.txt` will be created in the top level directory of this project.

#### CUDA
1) If on R2 purge modules with `module purge`
2) Load cuda module `module load cuda91`
3) compile with `make cuda`
4) Run slurm job `sbatch MMM_cuda.slurm`
5) Slurm file can be modified to include different input matrix files.
6) Result file named `result_cuda.txt` will be created in the top level directory of this project.
------------------------
##### CUDA Notes
- We decided to go with a grid layout that was half of the number of rows in matrix A by the number of columns in matrix B. We selected these numbers because anything larger than the actual amount of rows and columns would give an incorrect result, but felt that having that an equal number of blocks to the amount of rows and collumns would be excessive. Using fewer blocks in the grid work too, but we weren't sure where to draw the line for the best option for all of our test cases.
- We used [this Nvidia documentation](http://developer.download.nvidia.com/compute/cuda/3_2_prod/toolkit/docs/CUDA_C_Programming_Guide.pdf) for reference in order to get dynamic grid size working (or anything besides a 1x1 grid).

##### MPI Notes
- The MPI version only works when the ntasks are a power of two and a square of the matrix size, and only when the matrix is square. For example, running a 4x4 matrix will need to have 16 ntasks, and an 8x8 matrix will need 64 ntasks.
- To execute, run `mpirun -n <ntasks> ./bin/matrix_matrix_mpi <MatrixB.txt> <MatrixB.txt>`
- To run with debug mode, use the option `-d` flag on the end, like this: `mpirun -n 4 ./bin/matrix_matrix_mpi <MatrixB.txt> <MatrixB.txt> -d`

#### All
1) Compile with `make all`
2) All versions of D-MMultiply will have an executable built and placed in the `bin` directory.

## Running Tests
#### Serial
1) Run tests with `make test`

#### Pthreads
1) Run tests with `make test_pthreads`

#### OpenMP
1) Run tests with `make test_openmp`

#### MPI
Do not try to run this on Onyx, it will not work (unless you want to edit the Makefile to remove MPI). The Makefile will work on R2. 

We also do not have unit tests for MPI. Instead, we run it manually. Here are the tests we ran for 4, 16, and 64 cores:
- `mpirun -n 4./bin/matrix_matrix_mpi MatrixB.txt MatrixB.txt`
- `mpirun -n 16 ./bin/matrix_matrix_mpi MatrixD.txt MatrixD.txt`
- `mpirun -n 64 ./bin/matrix_matrix_mpi MatrixE.txt MatrixE.txt`

Their results are the correct matrices expected, which were compared to their known solutions.

##### 2x2 Matrix results on 4 cores
```
2
2
11.000000 15.000000
21.000000 29.000000
```

##### 4x4 Matrix results on 16 cores
```
4
4
0.000000 6.000000 12.000000 18.000000
0.000000 6.000000 12.000000 18.000000
0.000000 6.000000 12.000000 18.000000
0.000000 6.000000 12.000000 18.000000
```

##### 8x8 Matrix results on 64 cores
```
8
8
0.000000 28.000000 56.000000 84.000000 112.000000 140.000000 168.000000 196.000000
0.000000 28.000000 56.000000 84.000000 112.000000 140.000000 168.000000 196.000000
0.000000 28.000000 56.000000 84.000000 112.000000 140.000000 168.000000 196.000000
0.000000 28.000000 56.000000 84.000000 112.000000 140.000000 168.000000 196.000000
0.000000 28.000000 56.000000 84.000000 112.000000 140.000000 168.000000 196.000000
0.000000 28.000000 56.000000 84.000000 112.000000 140.000000 168.000000 196.000000
0.000000 28.000000 56.000000 84.000000 112.000000 140.000000 168.000000 196.000000
0.000000 28.000000 56.000000 84.000000 112.000000 140.000000 168.000000 196.000000
```

#### CUDA
We ran our tests via R2 and using our Matrix*.txt files. Their results are the correct matrices expected, which were compared to their known solutions.

##### MatrixA.txt multiplied by MatrixB.txt result
```
3
2
8.500000 11.500000
12.850000 18.350000
29.150000 40.050000
```
##### MatrixC.txt multiplied by MatrixC.txt result
```
10
10
0.000000 45.000000 90.000000 135.000000 180.000000 225.000000 270.000000 315.000000 360.000000 405.000000
0.000000 45.000000 90.000000 135.000000 180.000000 225.000000 270.000000 315.000000 360.000000 405.000000
0.000000 45.000000 90.000000 135.000000 180.000000 225.000000 270.000000 315.000000 360.000000 405.000000
0.000000 45.000000 90.000000 135.000000 180.000000 225.000000 270.000000 315.000000 360.000000 405.000000
0.000000 45.000000 90.000000 135.000000 180.000000 225.000000 270.000000 315.000000 360.000000 405.000000
0.000000 45.000000 90.000000 135.000000 180.000000 225.000000 270.000000 315.000000 360.000000 405.000000
0.000000 45.000000 90.000000 135.000000 180.000000 225.000000 270.000000 315.000000 360.000000 405.000000
0.000000 45.000000 90.000000 135.000000 180.000000 225.000000 270.000000 315.000000 360.000000 405.000000
0.000000 45.000000 90.000000 135.000000 180.000000 225.000000 270.000000 315.000000 360.000000 405.000000
0.000000 45.000000 90.000000 135.000000 180.000000 225.000000 270.000000 315.000000 360.000000 405.000000
```

##### MatrixD.txt multiplied by MatrixD.txt result
```
4
4
0.000000 6.000000 12.000000 18.000000
0.000000 6.000000 12.000000 18.000000
0.000000 6.000000 12.000000 18.000000
0.000000 6.000000 12.000000 18.000000
```

##### MatrixF.txt multiplied by MatrixF.txt result
```
16
16
0.000000 120.000000 240.000000 360.000000 480.000000 600.000000 720.000000 840.000000 960.000000 1080.000000 1200.000000 1320.000000 1440.000000 1560.000000 1680.000000 1800.000000
0.000000 120.000000 240.000000 360.000000 480.000000 600.000000 720.000000 840.000000 960.000000 1080.000000 1200.000000 1320.000000 1440.000000 1560.000000 1680.000000 1800.000000
0.000000 120.000000 240.000000 360.000000 480.000000 600.000000 720.000000 840.000000 960.000000 1080.000000 1200.000000 1320.000000 1440.000000 1560.000000 1680.000000 1800.000000
0.000000 120.000000 240.000000 360.000000 480.000000 600.000000 720.000000 840.000000 960.000000 1080.000000 1200.000000 1320.000000 1440.000000 1560.000000 1680.000000 1800.000000
0.000000 120.000000 240.000000 360.000000 480.000000 600.000000 720.000000 840.000000 960.000000 1080.000000 1200.000000 1320.000000 1440.000000 1560.000000 1680.000000 1800.000000
0.000000 120.000000 240.000000 360.000000 480.000000 600.000000 720.000000 840.000000 960.000000 1080.000000 1200.000000 1320.000000 1440.000000 1560.000000 1680.000000 1800.000000
0.000000 120.000000 240.000000 360.000000 480.000000 600.000000 720.000000 840.000000 960.000000 1080.000000 1200.000000 1320.000000 1440.000000 1560.000000 1680.000000 1800.000000
0.000000 120.000000 240.000000 360.000000 480.000000 600.000000 720.000000 840.000000 960.000000 1080.000000 1200.000000 1320.000000 1440.000000 1560.000000 1680.000000 1800.000000
0.000000 120.000000 240.000000 360.000000 480.000000 600.000000 720.000000 840.000000 960.000000 1080.000000 1200.000000 1320.000000 1440.000000 1560.000000 1680.000000 1800.000000
0.000000 120.000000 240.000000 360.000000 480.000000 600.000000 720.000000 840.000000 960.000000 1080.000000 1200.000000 1320.000000 1440.000000 1560.000000 1680.000000 1800.000000
0.000000 120.000000 240.000000 360.000000 480.000000 600.000000 720.000000 840.000000 960.000000 1080.000000 1200.000000 1320.000000 1440.000000 1560.000000 1680.000000 1800.000000
0.000000 120.000000 240.000000 360.000000 480.000000 600.000000 720.000000 840.000000 960.000000 1080.000000 1200.000000 1320.000000 1440.000000 1560.000000 1680.000000 1800.000000
0.000000 120.000000 240.000000 360.000000 480.000000 600.000000 720.000000 840.000000 960.000000 1080.000000 1200.000000 1320.000000 1440.000000 1560.000000 1680.000000 1800.000000
0.000000 120.000000 240.000000 360.000000 480.000000 600.000000 720.000000 840.000000 960.000000 1080.000000 1200.000000 1320.000000 1440.000000 1560.000000 1680.000000 1800.000000
0.000000 120.000000 240.000000 360.000000 480.000000 600.000000 720.000000 840.000000 960.000000 1080.000000 1200.000000 1320.000000 1440.000000 1560.000000 1680.000000 1800.000000
0.000000 120.000000 240.000000 360.000000 480.000000 600.000000 720.000000 840.000000 960.000000 1080.000000 1200.000000 1320.000000 1440.000000 1560.000000 1680.000000 1800.000000
```

## File Format Requirements
In order for MMMultiply to properly read from a text file, the file must be structured the following way (disregard the comments):
```
3           // rows
2           // cols
1.0 2.0     // data
3.2 2.3
4.5 6.4
```
#### Note:
This below format works too, but the above version can be easier to visualize due to how the data is modeled as a 2D array in the program.
```
3                           // rows
2                           // cols
1.0 2.0 3.2 2.3 4.5 6.4     // data
```
