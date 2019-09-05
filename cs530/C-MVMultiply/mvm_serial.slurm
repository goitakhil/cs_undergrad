#!/bin/bash
#SBATCH -J MVM
#SBATCH -o log_mvm_serial_3.log
#SBATCH -n 1
#SBATCH --cpus-per-task=28
#SBATCH -p shortq
#SBATCH -t 00:10:00

module purge
module load slurm


<<comment
srun bin/serial_mvmult test/L1_45x45_1core.txt 1
srun bin/serial_mvmult test/L2_145x145_1core.txt 1
srun bin/serial_mvmult test/L3_1475x1475_1core.txt 1
comment

<<comment
srun bin/serial_mvmult test/L1_630x45_14core.txt 1
srun bin/serial_mvmult test/L2_2030x145_14core.txt 1
srun bin/serial_mvmult test/L3_20650x1475_14core.txt 1
comment

#<<comment
srun bin/serial_mvmult test/L1_1260x45_28core.txt 1
srun bin/serial_mvmult test/L2_4060x145_28core.txt 1
srun bin/serial_mvmult test/L3_41300x1475_28core.txt 1
#comment

