#!/bin/bash
#SBATCH -J JACOBI
#SBATCH -o log_jacobi_serial_28.log
#SBATCH -n 1
#SBATCH --cpus-per-task=1
#SBATCH -p shortq
#SBATCH -t 00:10:00

delta=.001
module purge
module load slurm

<<comment
srun bin/jacobi2d inputfiles/1core_l1_44x44.txt $delta
srun bin/jacobi2d inputfiles/1core_l2_126x126.txt $delta
srun bin/jacobi2d inputfiles/1core_l3_1479x1479.txt $delta
comment

<<comment
srun bin/jacobi2d inputfiles/14core_l1_616x44.txt $delta
srun bin/jacobi2d inputfiles/14core_l2_1764x126.txt $delta
srun bin/jacobi2d inputfiles/14core_l3_20706x1479.txt $delta
comment

#<<comment
srun bin/jacobi2d inputfiles/28core_l1_1232x44.txt $delta
srun bin/jacobi2d inputfiles/28core_l2_3528x126.txt $delta
srun bin/jacobi2d inputfiles/28core_l3_41412x1479.txt $delta
#comment


