
#include <stdio.h>
#include <stdlib.h>
#include <mergesort.h>


// function prototypes
int check_if_sorted(int A[], int n);
void generate_random_array(int A[], int n, int seed);

// prototype from timing.c
double getMilliSeconds();


/*
 * generate_random_array(int A[], int n, int seed):
 *
 * description: Generate random integers in the range [0,RANGE]
 *              and store in A[1..n]
 */

#define RANGE 1000000

void generate_random_array(int A[], int n, int seed)
{
    int i;

    srandom(seed);
    for (i=1; i<=n; i++)
        A[i] = random()%RANGE;
}


/*
 * check_if_sorted(int A[], int n):
 *
 * description: returns TRUE if A[1..n] are sorted in nondecreasing order
 *              otherwise returns FALSE
 */     

int check_if_sorted(int A[], int n) 
{
    int i=0;

    for (i=1; i<n; i++) {
        if (A[i] > A[i+1]) {
            return FALSE;
        }
    }
    return TRUE;
}




int main(int argc, char **argv) {

    if (argc < 3) { // there must be at least two command-line arguments
        fprintf(stderr, "Usage: %s <input size> <num threads> [<seed>] \n", argv[0]);
        exit(1);
    }

    int n = atoi(argv[1]);
    int threads = atoi(argv[2]);
    int seed = 1;
    if (argc == 4)
        seed = atoi(argv[3]);

    int *A = (int *) malloc(sizeof(int) * (n+1)); // n+1 since we are using A[1]..A[n]

    // generate random input

    generate_random_array(A,n, seed);

    double start_time;
    double sorting_time;

    // sort the input (and time it)
    start_time = getMilliSeconds();
    parallel_mergesort(A,1,n,threads);
    sorting_time = getMilliSeconds() - start_time;

    // print results if correctly sorted otherwise cry foul and exit
    if (check_if_sorted(A,n)) {
        printf("Sorting %d elements took %4.2lf seconds.\n", n,  sorting_time/1000.0);
    } else { 
        printf("%s: sorting failed!!!!\n", argv[0]);
        exit(EXIT_FAILURE);
    }

    exit(EXIT_SUCCESS); 
} 
