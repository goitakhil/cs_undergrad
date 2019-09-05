#!/bin/bash
#SBATCH -J MVM
#SBATCH -o log_mvm_pthreads_630x45.log
#SBATCH -n 1
#SBATCH --cpus-per-task=28
#SBATCH -p shortq
#SBATCH -t 00:05:00

module purge
module load slurm

#<<comment

srun bin/pthreads_mvmult test/L1_630x45_14core.txt 1
srun bin/pthreads_mvmult test/L1_630x45_14core.txt 14
srun bin/pthreads_mvmult test/L1_630x45_14core.txt 28

