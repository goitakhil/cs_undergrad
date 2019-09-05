#include <jacobi2d.h>
#include <stddef.h>
#include <stdlib.h>
#include <stdbool.h>
#include <omp.h>

double return_max(double a, double b);

double* jacobi2d(double* A, int m, int n, double delta){
    
    //Calculate total length of A
    int total_length = m * n;
    
    //Allocate new matrix B
    double* B = (double*) malloc(sizeof(double) * (m * n));

    //Copy A into B;
    for (int i = 0; i < (m * n); i++) {
        B[i] = A[i];
    }

    do { 
        #pragma omp parallel for 
        for ( int i = 1; i < m - 1; i++ ) {
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
    } while (check_delta(A, B, m, n) > delta);

    return B;  
}

double check_delta(double* A, double* B, int n, int m){
    
    double max_delta = 0;

    for (int i = 1; i < m - 1; i++) {
        for (int j = 1; j < n - 1; j++ ) {
            int pos = (i * n) + j;
            max_delta = return_max(*(B + pos) - *(A + pos), max_delta);
        }
    }
    
    for (int i = 0; i < m * n; i++) {
        *(A + i) = *(B + i);
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
