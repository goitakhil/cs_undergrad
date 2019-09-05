#!/bin/bash
#SBATCH -J PI
#SBATCH -o log_pi.%j.log
#SBATCH -n 1
#SBATCH --cpus-per-task=28
#SBATCH -p shortq
#SBATCH -t 00:05:00

module purge
module load slurm


echo 1000000000 serial
srun bin/pi -n 1000000000 > /dev/null
#echo 1000000000 omp
#for THREADS in 1 14 28 ; do
#    export OMP_NUM_THREADS=$THREADS
#    srun bin/pi_omp -n 1000000000 > /dev/null
#done
echo 1000000000 pthreads
for THREADS in 1 14 28 ; do
    srun bin/pi_pthreads -n 1000000000 -t $THREADS > /dev/null
done

<<'comment'
echo 5000000000 serial
srun bin/pi -n 5000000000 > /dev/null
echo 5000000000 omp
for THREADS in 1 14 28 ; do
    export OMP_NUM_THREADS=$THREADS
    srun bin/pi_omp -n 5000000000 > /dev/null
done
echo 5000000000 pthreads
for THREADS in 1 14 28 ; do
    srun bin/pi_pthreads -n 5000000000 -t $THREADS > /dev/null
done
echo 100000000000 serial
srun bin/pi -n 100000000000 > /dev/null
echo 100000000000 omp
for THREADS in 1 14 28 ; do
    export OMP_NUM_THREADS=$THREADS
    srun bin/pi_omp -n 100000000000 > /dev/null
done
echo 100000000000 pthreads
for THREADS in 1 14 28 ; do
    srun bin/pi_pthreads -n 100000000000 -t $THREADS > /dev/null
done
comment
