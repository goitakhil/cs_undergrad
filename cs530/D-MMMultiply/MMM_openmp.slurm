#!/bin/bash
#SBATCH -J MMM-openmp
#SBATCH -o log/openmp/log_MMM_openmp.log
#SBATCH -n 1
#SBATCH --cpus-per-task=28
#SBATCH -p shortq
#SBATCH -t 00:05:00

module purge
module load slurm

echo "OMP with 1 thread"
srun bin/matrix_matrix_openmp etc/thread1_n25.txt etc/thread1_n25.txt 1
srun bin/matrix_matrix_openmp etc/thread1_n73.txt etc/thread1_n73.txt 1
srun bin/matrix_matrix_openmp etc/thread1_n853.txt etc/thread1_n853.txt 1

echo "OMP with 14 threads"
srun bin/matrix_matrix_openmp etc/thread14_n96.txt etc/thread14_n96.txt 14
srun bin/matrix_matrix_openmp etc/thread14_n273.txt etc/thread14_n273.txt 14
srun bin/matrix_matrix_openmp etc/thread14_n853.txt etc/thread14_n853.txt 14

echo "OMP with 28 threads"
srun bin/matrix_matrix_openmp etc/thread28_n136.txt etc/thread28_n136.txt 28
srun bin/matrix_matrix_openmp etc/thread28_n386.txt etc/thread28_n386.txt 28
srun bin/matrix_matrix_openmp etc/thread28_n1207.txt etc/thread28_n1207.txt 28
