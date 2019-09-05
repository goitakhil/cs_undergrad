#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>
#include <pthread.h>

#define MAX_ITER 3037012561

long long stamp();

struct threadopts{
    unsigned long long iters;
    int tid;
    int nthreads;
    long double* result;
};

void* do_work(void* opts)
{
    struct threadopts* options = (struct threadopts*) opts;
    long double curr = 0.0;
    for (unsigned long long i = options->tid; i < options->iters; i += options->nthreads){
        curr += pow(-1.0, (double)i)/((2.0*i)+1.0);
    }
    *options->result = curr;
    return NULL;
}

long double calculate_pi(unsigned long long iterations, int precision, int nthreads)
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

    iters--;
    pthread_t pool[nthreads];
    long double results[nthreads];
    struct threadopts options[nthreads];
    clock_t start = stamp();
    for (int i = 0; i < nthreads; i++) {
        options[i].iters = iters;
        options[i].tid = i;
        options[i].nthreads = nthreads;
        options[i].result = &results[i];
        pthread_create(&pool[i], NULL, do_work, &options[i]); 
    }
    clock_t finish = stamp();
    long long diff = finish - start;
    fprintf(stderr, "spawned threads %lli\n", diff);

    for (int i = 0; i < nthreads; i++){
        void *status;
        pthread_join(pool[i], &status);
    }
    finish = stamp();
    diff = finish - start;
    fprintf(stderr, "joined %lli\n", diff);

    long double sum = 0;
    for (int i = 0; i < nthreads; i++){
        sum += results[i];
    }
    finish = stamp();
    diff = finish - start;
    fprintf(stderr, "%i, %lli\n", nthreads, diff);
    return sum*4;
}

// via @eric-wang on stackoverflow
long long stamp() {
    struct timeval te; 
    gettimeofday(&te, NULL);
    long long milliseconds = te.tv_sec*1000LL + te.tv_usec/1000;
    return milliseconds;
}
