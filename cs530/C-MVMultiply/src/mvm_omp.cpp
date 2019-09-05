#include <stddef.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdexcept>
#include <omp.h>

double* mvMultiply(double* matrix, double* vector, int m, int n, int vecsize, int num_threads) {
    int i;
    int j;
    int k;
    double* result = (double*)malloc(sizeof(double)*n*1);
     if(vecsize == m){
	#   pragma omp parallel for num_threads(num_threads) shared(result) private(j)
	for(i=0;i<n; i++){
            result[i] = 0;

	    for(j=0; j<m; j++){
		int n = omp_get_num_threads();
		result[i] += matrix[i*m+j] * vector[j];
             }
        }

    }else{
        result[0] = -1;
    }
    return result;
} 
