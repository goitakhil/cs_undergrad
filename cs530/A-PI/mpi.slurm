#!/bin/bash
#SBATCH -J PI
#SBATCH -o log_pi.%j.log
#SBATCH -n 112
#SBATCH --cpus-per-task=1
#SBATCH -p shortq
#SBATCH -t 00:05:00

module purge
module load slurm
module load openmpi/gcc

mpirun -n 112 bin/pi_mpi -n 8586147681 
