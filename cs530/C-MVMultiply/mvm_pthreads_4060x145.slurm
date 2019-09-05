#!/bin/bash
#SBATCH -J MVM
#SBATCH -o log_mvm_pthreads_4060x145.log
#SBATCH -n 1
#SBATCH --cpus-per-task=28
#SBATCH -p shortq
#SBATCH -t 00:50:00

module purge
module load slurm

srun bin/pthreads_mvmult test/L2_4060x145_28core.txt 1
srun bin/pthreads_mvmult test/L2_4060x145_28core.txt 14
srun bin/pthreads_mvmult test/L2_4060x145_28core.txt 28
