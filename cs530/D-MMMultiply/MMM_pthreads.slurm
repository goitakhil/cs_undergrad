#!/bin/bash
#SBATCH -J MMM-pthreads
#SBATCH -o log/pthreads/log_MMM_pthreads.log
#SBATCH -n 1
#SBATCH --cpus-per-task=28
#SBATCH -p shortq
#SBATCH -t 00:05:00

module purge
module load slurm

echo "Pthreads with 1 thread"
srun bin/matrix_matrix_pthreads etc/thread1_n25.txt etc/thread1_n25.txt 1
srun bin/matrix_matrix_pthreads etc/thread1_n73.txt etc/thread1_n73.txt 1
srun bin/matrix_matrix_pthreads etc/thread1_n853.txt etc/thread1_n853.txt 1

echo "Pthreads with 14 threads"
srun bin/matrix_matrix_pthreads etc/thread14_n96.txt etc/thread14_n96.txt 14
srun bin/matrix_matrix_pthreads etc/thread14_n273.txt etc/thread14_n273.txt 14
srun bin/matrix_matrix_pthreads etc/thread14_n853.txt etc/thread14_n853.txt 14

echo "Pthreads with 28 threads"
srun bin/matrix_matrix_pthreads etc/thread28_n136.txt etc/thread28_n136.txt 28
srun bin/matrix_matrix_pthreads etc/thread28_n386.txt etc/thread28_n386.txt 28
srun bin/matrix_matrix_pthreads etc/thread28_n1207.txt etc/thread28_n1207.txt 28
