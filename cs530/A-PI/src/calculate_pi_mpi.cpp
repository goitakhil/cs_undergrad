#include <limits.h>
#include <math.h>
#include <mpi.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>

long long stamp();

typedef unsigned long long ull;

/*
 * Estimates the value for pi 
 * using the monte carlo method.
 *
 * parameters: ull samples, int seed - random number seed,
 * bool timing - value to determine if timing is needed.
 */
long double calculate_pi(ull samples, int seed, bool timing)
{
    if (samples <= 0 || seed < -1) {
        return 0;
    }
    if (samples == ULLONG_MAX) samples--;
    int num_procs;
    MPI_Comm_size(MPI_COMM_WORLD, &num_procs);
    int proc_id;
    MPI_Comm_rank(MPI_COMM_WORLD, &proc_id);

    ull inside = 0.0;
    long r = (long)RAND_MAX * (long)RAND_MAX;
    if (seed != -1) {
        srand(seed);
    } else {
        srand((int)stamp()+(100*proc_id));
    }
    int x;
    int y;
    long long start = stamp();
    for (ull i = proc_id;i < samples; i+=num_procs) {
        x = rand();
        y = rand();
        if (((long)x*x + (long)y*y) <= r) inside++;
    }

    long long finish = stamp();
    ull *results = (ull *) malloc(sizeof(ull)*num_procs);
    MPI_Gather(&inside, 1, MPI_UNSIGNED_LONG_LONG, results, 1, MPI_UNSIGNED_LONG_LONG, 0, MPI_COMM_WORLD);
    ull total = 0;

    if (proc_id == 0) {
        long long diff = finish - start;
        if (timing) {
            fprintf(stderr, "%i, %lli\n", num_procs, diff);
        }
        for (int i = 0; i < num_procs; i++) {
            total += results[i];
        }
    }
    long double prob = total / (long double)samples;
    free(results);
    return prob*4;
}

// via @eric-wang on stackoverflow
long long stamp() {
    struct timeval te;
    gettimeofday(&te, NULL);
    long long milliseconds = te.tv_sec*1000LL + te.tv_usec/1000;
    return milliseconds;
}

