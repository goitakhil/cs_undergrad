#!/bin/bash
#SBATCH --job-name=MMM_MPI
#SBATCH --output=log/mpi/log_mmm_mpi.log
#SBATCH --ntasks=64
#SBATCH --partition=shortq
#SBATCH --time=00:05:00

module purge
module load slurm
module load openmpi/gcc


echo "2x2 matrix with ntasks = 4, without debug"
mpirun -n 4 bin/matrix_matrix_mpi etc/MatrixB.txt etc/MatrixB.txt

echo "4x4 matrix with ntasks = 16, without debug"
mpirun -n 16 bin/matrix_matrix_mpi etc/MatrixD.txt etc/MatrixD.txt

echo "8x8 matrix with ntasks = 64, without debug"
mpirun -n 64 bin/matrix_matrix_mpi etc/MatrixE.txt etc/MatrixE.txt

# Will not work on R2, not enough slots available.
#echo "16x16 matrix with ntasks = 256, without debug"
#mpirun -n 256 bin/matrix_matrix_mpi etc/MatrixF.txt etc/MatrixF.txt

#echo "1024 x 1024 matrix with ntasks = 4"
#mpirun -n 4 bin/matrix_matrix_mpi etc/MatrixI.txt etc/MatrixI.txt

#echo "1024 x 1024 matrix with ntasks = 16"
#mpirun -n 16 bin/matrix_matrix_mpi etc/MatrixI.txt etc/MatrixI.txt

#echo "1024 x 1024 matrix with ntasks = 64"
#mpirun -n 64 bin/matrix_matrix_mpi etc/MatrixI.txt etc/MatrixI.txt

<<comment
echo "1024 x 1024 matrix with ntasks = 256"
mpirun -n 25 bin/matrix_matrix_mpi etc/MatrixI.txt etc/MatrixI.txt
comment
