#!/bin/bash
#SBATCH -J FIBONACCI
#SBATCH -o log_fibonacci.%j.log
#SBATCH -n 1
#SBATCH --cpus-per-task=28
#SBATCH -p shortq
#SBATCH -t 00:05:00

module purge
module load slurm

for THREADS in 1 2 4 8 16 28 ; do
    export OMP_NUM_THREADS=$THREADS
    srun bin/OMPFibonacci 47
    echo
done

for THREADS in 1 2 4 8 16 28 ; do
    srun bin/pthreadFibonacci 47 $THREADS
    echo
done
