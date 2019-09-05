#!/bin/bash
#SBATCH -J MVM
#SBATCH -o log_mvm_omp_145x145.log
#SBATCH -n 1
#SBATCH --cpus-per-task=28
#SBATCH -p shortq
#SBATCH -t 00:50:00

module purge
module load slurm

srun bin/omp_mvmult test/L2_145x145_1core.txt 1
srun bin/omp_mvmult test/L2_145x145_1core.txt 14 
srun bin/omp_mvmult test/L2_145x145_1core.txt 28

