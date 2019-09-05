#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>

#define MAX_ITER 3037012561

long long stamp();

long double calculate_pi(unsigned long long iterations, int precision)
{
    unsigned long long iters = iterations;
    if (iterations < 0 || precision < 0) return 0;
    if (precision == 0 && iters == 0) {
        iters = MAX_ITER;
    } else if (iterations == 0) {
        iters = 5;
        for (int i = 1; i <= precision; i++){
            iters *= 10;
        }
    }
    long double sum = 0.0;
    long long start = stamp();
    iters--;
    for (unsigned long long i = 0;i < iters; i++) {
        sum += pow(-1.0, (double)i)/((2.0*i)+1.0);
    }
    long long finish = stamp();
    long long diff = finish - start;
    fprintf(stderr, "%s, %lli\n", getenv("OMP_NUM_THREADS"), diff);
    return sum*4;

}

// via @eric-wang on stackoverflow
long long stamp() {
    struct timeval te;
    gettimeofday(&te, NULL);
    long long milliseconds = te.tv_sec*1000LL + te.tv_usec/1000;
    return milliseconds;
}

