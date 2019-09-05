#!/bin/bash
#SBATCH -J JACOBI
#SBATCH -o log_jacobi_pthreads_28.log
#SBATCH -n 1
#SBATCH --cpus-per-task=28
#SBATCH -p shortq
#SBATCH -t 00:07:00

delta=.001
module purge
module load slurm

<<comment
export OMP_NUM_THREADS=1
srun bin/jacobi2d_pthreads inputfiles/1core_l1_44x44.txt $delta
srun bin/jacobi2d_pthreads inputfiles/1core_l2_126x126.txt $delta
srun bin/jacobi2d_pthreads inputfiles/1core_l3_1479x1479.txt $delta
comment

<<comment
export OMP_NUM_THREADS=14
srun bin/jacobi2d_pthreads inputfiles/14core_l1_616x44.txt $delta
srun bin/jacobi2d_pthreads inputfiles/14core_l2_1764x126.txt $delta
srun bin/jacobi2d_pthreads inputfiles/14core_l3_20706x1479.txt $delta
comment

#<<comment
export OMP_NUM_THREADS=28
srun bin/jacobi2d_pthreads inputfiles/28core_l1_1232x44.txt $delta
srun bin/jacobi2d_pthreads inputfiles/28core_l2_3528x126.txt $delta
srun bin/jacobi2d_pthreads inputfiles/28core_l3_41412x1479.txt $delta
#comment



<<comment
export OMP_NUM_THREADS=1
echo Threads: 1
srun bin/jacobi2d_pthreads anne-inputfiles/31x31_input.txt $delta
srun bin/jacobi2d_pthreads anne-inputfiles/90x90_input.txt $delta 
srun bin/jacobi2d_pthreads anne-inputfiles/119x119_input.txt $delta
srun bin/jacobi2d_pthreads anne-inputfiles/169x169_input.txt $delta
srun bin/jacobi2d_pthreads anne-inputfiles/338x338_input.txt $delta
srun bin/jacobi2d_pthreads anne-inputfiles/478x478_input.txt $delta
srun bin/jacobi2d_pthreads anne-inputfiles/1514x1514_input.txt $delta
srun bin/jacobi2d_pthreads anne-inputfiles/1810x1810_input.txt $delta
srun bin/jacobi2d_pthreads anne-inputfiles/2560x2560_input.txt $delta
export OMP_NUM_THREADS=14
echo Threads: 14
srun bin/jacobi2d_pthreads anne-inputfiles/31x31_input.txt $delta
srun bin/jacobi2d_pthreads anne-inputfiles/90x90_input.txt $delta 
srun bin/jacobi2d_pthreads anne-inputfiles/119x119_input.txt $delta
srun bin/jacobi2d_pthreads anne-inputfiles/169x169_input.txt $delta
srun bin/jacobi2d_pthreads anne-inputfiles/338x338_input.txt $delta
srun bin/jacobi2d_pthreads anne-inputfiles/478x478_input.txt $delta
srun bin/jacobi2d_pthreads anne-inputfiles/1514x1514_input.txt $delta
srun bin/jacobi2d_pthreads anne-inputfiles/1810x1810_input.txt $delta
srun bin/jacobi2d_pthreads anne-inputfiles/2560x2560_input.txt $delta
export OMP_NUM_THREADS=28
echo Threads: 28
srun bin/jacobi2d_pthreads anne-inputfiles/31x31_input.txt $delta
srun bin/jacobi2d_pthreads anne-inputfiles/90x90_input.txt $delta 
srun bin/jacobi2d_pthreads anne-inputfiles/119x119_input.txt $delta
srun bin/jacobi2d_pthreads anne-inputfiles/169x169_input.txt $delta
srun bin/jacobi2d_pthreads anne-inputfiles/338x338_input.txt $delta
srun bin/jacobi2d_pthreads anne-inputfiles/478x478_input.txt $delta
srun bin/jacobi2d_pthreads anne-inputfiles/1514x1514_input.txt $delta
srun bin/jacobi2d_pthreads anne-inputfiles/1810x1810_input.txt $delta
srun bin/jacobi2d_pthreads anne-inputfiles/2560x2560_input.txt $delta


<<comment
for THREADS in 1 2 4 7 14 28 ; do
    export OMP_NUM_THREADS=$THREADS
    srun bin/pi_omp -n 1000000000 > /dev/null
done

for THREADS in 1 2 4 7 14 28 ; do
    srun bin/pi_pthreads -p 1000000000 -t $THREADS
done
comment
