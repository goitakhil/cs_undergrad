#!/bin/bash
#SBATCH -J MVM
#SBATCH -o log_mvm_pthreads_20650x1475.log
#SBATCH -n 1
#SBATCH --cpus-per-task=28
#SBATCH -p shortq
#SBATCH -t 00:05:00

module purge
module load slurm

srun bin/pthreads_mvmult test/L3_20650x1475_14core.txt 1
srun bin/pthreads_mvmult test/L3_20650x1475_14core.txt 14
srun bin/pthreads_mvmult test/L3_20650x1475_14core.txt 28
