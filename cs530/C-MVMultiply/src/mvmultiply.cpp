#include <stddef.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

double* mvMultiply(double* matrix, double* vector, int m, int n, int vecsize, int num_threads) {
    printf("Testing serial mv multiply\n");
    int i;
    int j;
    int k;
    double* result = (double*)malloc(sizeof(double)*n*1);
    if(vecsize == m){
    	for(i=0;i<n; i++){
	    result[i] = 0;
            for(j=0; j<m; j++){
	        result[i] += matrix[i*m+j] * vector[j];
             }
        }
    }else{
	result[0] = -1;
    }
    return result;
}
