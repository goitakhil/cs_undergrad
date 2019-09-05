

#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#include <pthread.h>
#include <mergesort.h>



/*
 * insertion_sort(int A[], int p, int r):
 *
 * description: Sort the section of the array A[p..r].
 */
void insertion_sort(int A[], int p, int r) 
{
    int j;

    for (j=p+1; j<=r; j++) {
        int key = A[j];
        int i = j-1;
        while ((i > p-1) && (A[i] > key)) {	
            A[i+1] = A[i];
            i--;
        }
        A[i+1] = key;
    }
}

/*
 * parallel_mergesort(int A[], int p, int r):
 *
 * description: Sort the section of the array A[p..r]
 * in parallel.
 */		
void parallel_mergesort(int A[], int p, int r, int num_threads){
    struct arg_set args = {A, p, r, num_threads};
    parallel_sort_thread((void *) &args);
}

/*
 * parallel_sort_thread(void* args)
 * 
 * description: Thread process
 */
void *parallel_sort_thread(void* args){
    struct arg_set local_args = *(struct arg_set*) args;
    if (local_args.threads <= 1) {
        serial_mergesort(local_args.A,local_args.p,local_args.r);
        return NULL;
    } else if (local_args.threads >= 2){
        int q = (local_args.p+local_args.r)/2;
        int x = local_args.threads / 2;
        int y = local_args.threads - x;
        pthread_t t[2];
        pthread_attr_t a;
        pthread_attr_init(&a);
        struct arg_set args1 = {local_args.A, local_args.p, q, x};
        pthread_create(&t[0],&a,parallel_sort_thread, (void *) &args1);
        pthread_attr_init(&a);
        struct arg_set args2 = {local_args.A, q+1, local_args.r, y};
        pthread_create(&t[1],&a,parallel_sort_thread, (void *) &args2);
        int i;
        for(i = 0; i < 2; i++) pthread_join(t[i], NULL);
        merge(local_args.A, local_args.p, q, local_args.r);
        return NULL;
    }
    return NULL;
}



/*
 * serial_mergesort(int A[], int p, int r):
 *
 * description: Sort the section of the array A[p..r].
 */
void serial_mergesort(int A[], int p, int r) 
{
    if (r-p+1 <= INSERTION_SORT_THRESHOLD)  {
        insertion_sort(A,p,r);
    } else {
        int q = (p+r)/2;
        serial_mergesort(A, p, q);
        serial_mergesort(A, q+1, r);
        merge(A, p, q, r);
    }
}



/*
 * merge(int A[], int p, int q, int r):
 *
 * description: Merge two sorted sequences A[p..q] and A[q+1..r] 
 *              and place merged output back in array A. Uses extra
 *              space proportional to A[p..r].
 */     
void merge(int A[], int p, int q, int r) 
{
    int *B = (int *) malloc(sizeof(int) * (r-p+1));

    int i = p;
    int j = q+1;
    int k = 0;
    int l;

    // as long as both lists have unexamined elements
    // this loop keeps executing.
    while ((i <= q) && (j <= r)) {
        if (A[i] < A[j]) {
            B[k] = A[i];
            i++;
        } else {
            B[k] = A[j];
            j++;
        }
        k++;
    }

    // now only at most one list has unprocessed elements.

    if (i <= q) { 
        // copy remaining elements from the first list
        for (l=i; l<=q; l++) {
            B[k] = A[l];
            k++;
        }
    } else {
        // copy remaining elements from the second list
        for (l=j; l<=r; l++) {
            B[k] = A[l];
            k++;
        }
    }

    // copy merged output from array B back to array A
    k=0;
    for (l=p; l<=r; l++) {
        A[l] = B[k];
        k++;
    }

    free(B);
}

