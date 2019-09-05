#!/bin/bash
#SBATCH -J GPU_JOB
#SBATCH -o log/cuda/log_MMM_cuda.log
#SBATCH -n 1
#SBATCH --cpus-per-task=14
#SBATCH -p shortq
#SBATCH --gres=gpu:1
#SBATCH -t 00:05:00

ulimit -v unlimited
ulimit -s unlimited
ulimit -u 10000

module purge
module load slurm
module load cuda91/toolkit

srun -n 1 nvprof ./bin/matrix_matrix_cuda etc/MatrixC.txt etc/MatrixC.txt
