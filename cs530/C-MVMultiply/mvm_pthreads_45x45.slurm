#!/bin/bash
#SBATCH -J MVM
#SBATCH -o log_mvm_pthreads_45x45.log
#SBATCH -n 1
#SBATCH --cpus-per-task=28
#SBATCH -p shortq
#SBATCH -t 00:05:00

module purge
module load slurm

#<<comment
srun bin/pthreads_mvmult test/L1_45x45_1core.txt 1
srun bin/pthreads_mvmult test/L1_45x45_1core.txt 14
srun bin/pthreads_mvmult test/L1_45x45_1core.txt 28
