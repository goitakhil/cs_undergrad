
#Introduction

This folder contains a threaded implementation of mergesort.

#Manifest

Makefile         Build file.

mergesort.c      The source file that contains the mergesort and related functions.

mergesortTest.c  The driver that generates random data, runs and times mergesort.

timing.c         The timing functions.

test1.sh         Try different values of insertion sort threshold to find best 
                 value of threshold to switch to insertion sort.

test2.sh         Time mergesort for different values of n.


#Building and running

To build, use:

`make`

Run as follows:


`mergesort <input size> <num threads> [<seed>]`

Benchmarking done on 2-core 4-thread Intel i7-7500U @ 3.5GHz
Baseline:
100000000 elements - 9.60 seconds
Multithreaded implementation:
100000000 elements - 1 thread  - 9.6 seconds 1x
100000000 elements - 2 threads - 5.02 seconds 1.91x
100000000 elements - 3 threads - 4.93 seconds 1.95x
100000000 elements - 4 threads - 3.63 seconds 2.64x


