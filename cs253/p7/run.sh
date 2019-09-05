#!/bin/bash
#Setup our library path so the grader will run 
export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:./lib
#Execute the grader under valgrind
valgrind --quiet --leak-check=full ./grader 4 10000
env time ./grader 8 125000
env time ./grader 4 250000
env time ./grader 2 500000
env time ./grader 1 1000000