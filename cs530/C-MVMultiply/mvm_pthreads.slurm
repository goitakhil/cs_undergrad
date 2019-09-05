#!/bin/bash
#SBATCH -J MVM
#SBATCH -o log_mvm_pthreads_1.log
#SBATCH -n 1
#SBATCH --cpus-per-task=1
#SBATCH -p shortq
#SBATCH -t 00:10:00

module purge
module load slurm

#<<comment
srun bin/pthreads_mvmultiply test/L1_45x45_1core.txt 1
srun bin/pthreads_mvmultiply test/L1_45x45_1core.txt 14
srun bin/pthreads_mvmultiply test/L1_45x45_1core.txt 28

srun bin/pthreads_mvmultiply test/L2_145x145_1core.txt 1
srun bin/pthreads_mvmultiply test/L2_145x145_1core.txt 14 
srun bin/pthreads_mvmultiply test/L2_145x145_1core.txt 28

srun bin/pthreads_mvmultiply test/L3_1475x1475_1core.txt 1
srun bin/pthreads_mvmultiply test/L3_1475x1475_1core.txt 14
srun bin/pthreads_mvmultiply test/L3_1475x1475_1core.txt 28

srun bin/pthreads_mvmultiply test/L1_45x45x14_14cores.txt 1
srun bin/pthreads_mvmultiply test/L1_45x45x14_14cores.txt 14
srun bin/pthreads_mvmultiply test/L1_45x45x14_14cores.txt 28

srun bin/pthreads_mvmultiply test/L2_145x145x14_14cores.txt 1
srun bin/pthreads_mvmultiply test/L2_145x145x14_14cores.txt 14
srun bin/pthreads_mvmultiply test/L2_145x145x14_14cores.txt 28

srun bin/pthreads_mvmultiply test/L3_1475x1475x14_14cores.txt 1
srun bin/pthreads_mvmultiply test/L3_1475x1475x14_14cores.txt 14
srun bin/pthreads_mvmultiply test/L3_1475x1475x14_14cores.txt 28

srun bin/pthreads_mvmultiply test/L1_45x45x28_28cores.txt 1
srun bin/pthreads_mvmultiply test/L1_45x45x28_28cores.txt 14
srun bin/pthreads_mvmultiply test/L1_45x45x28_28cores.txt 28

srun bin/pthreads_mvmultiply test/L2_145x145x28_28cores.txt 1
srun bin/pthreads_mvmultiply test/L2_145x145x28_28cores.txt 14
srun bin/pthreads_mvmultiply test/L2_145x145x28_28cores.txt 28

srun bin/pthreads_mvmultiply test/L3_1475x1475x28_28cores.txt 1
srun bin/pthreads_mvmultiply test/L3_1475x1475x28_28cores.txt 14
srun bin/pthreads_mvmultiply test/L3_1475x1475x28_28cores.txt 28

