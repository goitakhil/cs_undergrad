#!/bin/bash
#SBATCH -J MVM
#SBATCH -o log_mvm_omp_2030x145.log
#SBATCH -n 1
#SBATCH --cpus-per-task=28
#SBATCH -p shortq
#SBATCH -t 00:05:00

module purge
module load slurm

#<<comment
srun bin/omp_mvmult test/L2_2030x145_14core.txt 1
srun bin/omp_mvmult test/L2_2030x145_14core.txt 14
srun bin/omp_mvmult test/L2_2030x145_14core.txt 28

