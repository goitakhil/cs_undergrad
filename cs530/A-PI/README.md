# Calculating PI

## Building
To build:
```
make
```
If you receive errors when trying to run make on R2, try running `module load gcc` before you make.

## Running
```
./pi
  -n <iterations>         :  Number of iterations to run
  -p <decimal precision>  :  Number of correct decimal places to calculate
  -h                      :  This help page

./pi_pthreads
  -n <iterations>         :  Number of iterations to run
  -p <decimal precision>  :  Number of correct decimal places to calculate
  -t <threads>  :  Number of threads to use
  -h                      :  This help page

./pi_omp
  -n <iterations>         :  Number of iterations to run
  -p <decimal precision>  :  Number of correct decimal places to calculate
  -h                      :  This help page

./pi_mpi
  -n <samples>         :  Number of samples to run
  -s <seed>            :  Seed for srand
  -d                   :  Print debug info (overrides timing mode)
  -t                   :  Time the code
  -h                   :  This help page
```

## Running Tests
To run all non-mpi tests:
```
make test
```

To test MPI implementation:
```
mpirun -n 4 bin/pi_mpi -n 100000 -s 1 -d
```
The -d option tells the program to print comparison values.
Using 4 threads with 100000 samples and a seed of 1, the result should be:
```
3.15711999999999999989667986977082136945682577788829803466796875000000000000000000
```

