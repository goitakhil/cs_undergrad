#!/bin/bash
#SBATCH -J MVM
#SBATCH -o log_mvm_omp_45x45.log
#SBATCH -n 1
#SBATCH --cpus-per-task=28
#SBATCH -p shortq
#SBATCH -t 00:05:00

module purge
module load slurm

#<<comment
srun bin/omp_mvmult test/L1_45x45_1core.txt 1
srun bin/omp_mvmult test/L1_45x45_1core.txt 14
srun bin/omp_mvmult test/L1_45x45_1core.txt 28
