#include <jacobi2d.h>
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>


/* struct that holds data for threads */
typedef struct jacobi_data {
    int m;
    int n;
    double* matrixA;
    double* matrixB;
    double delta;
    int thread_count;
    int thread_num;
} j_data;


/* Prototypes of helper functions */
double return_max(double a, double b);
void* jacobi(void* info);


double* jacobi2d(double* A, int m, int n, double delta) {
    /*Setup threads*/
    long thread;
    pthread_t* thread_handles;
    int thread_count = atoi(getenv("OMP_NUM_THREADS"));
    thread_handles = (pthread_t*) malloc(sizeof(pthread_t) * thread_count);

    /*Copy the values of matrix A to another matrix B*/
    double* B = (double*) malloc(sizeof(double) * (n * m));
    for (int i = 0; i < (n * m); i++) {
        B[i] = A[i];
    }

    /*Set current_delta greater than delta to enter loop*/
    double current_delta = delta + 1;
    while (current_delta > delta) {

         /*Create threads that execute jacobi*/
        for (int i = 0; i < thread_count; i++) {

            /*Load jacobi2d data into struct, and copy the values of */  
            j_data* data = (j_data*) malloc(sizeof(j_data));
            data->m = m;
            data->n = n; 
            data->matrixA = A;
            data->matrixB = B; 
            data->delta = delta;
            data->thread_count = thread_count;
            data->thread_num = i;

            pthread_create(&thread_handles[i], NULL, jacobi, (void*) data);
        }

        /*Join threads to finish*/
        for (int i = 0; i < thread_count; i++) {
            pthread_join(thread_handles[i], NULL);
        }

        current_delta = check_delta(A, B, m, n);
    }

    free(thread_handles);
    return B;   
}


void* jacobi(void* info) {

    /* get data from struct */
    j_data* data = (j_data*) info;
    int m = data->m;
    int n = data->n;
    double* A = data->matrixA;
    double* B = data->matrixB;
    double delta = data->delta;
    int thread_count = data->thread_count;
    int thread_num = data->thread_num;

    /*Determine bounds*/
    int local_m = (m / thread_count);
    int my_first_row = (thread_num * local_m);
    int my_last_row = (thread_num + 1) * local_m - 1;

    if (my_first_row == 0) {
        my_first_row++;
    }

    if (my_last_row == (m - 1)) {
        my_last_row--;
    }

    for ( int i = my_first_row; i <= my_last_row; i++ ) {
        for ( int j = 1; j < n - 1; j++ ) {
            //calculate desired indices of B
            int pos = (i * n) + j;
            int left = pos - 1;
            int right = pos + 1;
            int up = pos - n;
            int down = pos + n;

            B[pos] = (A[left] + A[right] + A[down] + A[up])/4; 
        }
    }
    
    free(info);
    return NULL;
}


double check_delta(double* A, double* B, int m, int n) {

     double max_delta = 0;

    for (int i = 1; i < m - 1; i++) {
        for (int j = 1; j < n - 1; j++ ) {
            int pos = (i * n) + j;
            max_delta = return_max(*(B + pos) - *(A + pos), max_delta);
        }
    }
    
    for (int i = 0; i < n * m; i++) {
        A[i] = B[i];
    }

    return max_delta;
}


double return_max(double a, double b) {
    if (a > b) {
        return a; 
    } else if (b > a) {
        return b;
    } else {
        return a; 
    }
}
