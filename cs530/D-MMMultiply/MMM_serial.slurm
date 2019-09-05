#!/bin/bash
#SBATCH -J MMM-serial
#SBATCH -o log/serial/log_MMM_serial.log
#SBATCH -n 1
#SBATCH --cpus-per-task=28
#SBATCH -p shortq
#SBATCH -t 00:05:00

module purge
module load slurm

srun bin/matrix_matrix etc/thread1_n25.txt etc/thread1_n25.txt
srun bin/matrix_matrix etc/thread1_n73.txt etc/thread1_n73.txt
srun bin/matrix_matrix etc/thread1_n853.txt etc/thread1_n853.txt

srun bin/matrix_matrix etc/thread14_n96.txt etc/thread14_n96.txt
srun bin/matrix_matrix etc/thread14_n273.txt etc/thread14_n273.txt
srun bin/matrix_matrix etc/thread14_n853.txt etc/thread14_n853.txt

srun bin/matrix_matrix etc/thread28_n136.txt etc/thread28_n136.txt
srun bin/matrix_matrix etc/thread28_n386.txt etc/thread28_n386.txt
srun bin/matrix_matrix etc/thread28_n1207.txt etc/thread28_n1207.txt
