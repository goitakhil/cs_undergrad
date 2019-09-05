#include <stddef.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <pthread.h>
struct mat_vec_mat {
    double *A;
    double *v;
    double *r;
    int rows;
    int i;
    int num_calc;
    int m;
};

void* mvm_serial(void *d) {
    struct mat_vec_mat *mvm = (struct mat_vec_mat*)d;

    int i = mvm->i;
    int rows = mvm->rows;
    int j;
    int num_r = mvm->num_calc;
    double* result = (double*)malloc(sizeof(double)*rows);
    int k;
    int m = mvm->m;
    for(k=i; k<i+num_r; k++){
   	for(j=0; j<m; j++){
	    mvm->r[k] += mvm->A[k*m+j] * mvm->v[j];
	}
    }
}

double* mvMultiply(double* matrix, double *vecter, int m, int n, int vecsize, int num_threads){
    double *result = (double*)malloc(sizeof(double)*n);
    int num_calc = n/num_threads;
    if(num_calc ==0){
	num_calc=1;
	num_threads=n;
    }

    if(vecsize == m){
	struct mat_vec_mat *mvm = (struct mat_vec_mat*)malloc(sizeof(struct mat_vec_mat)*n);
	pthread_t *pids = (pthread_t*)malloc(sizeof(pthread_t)*n);
	int i;
	for(i=0; i <num_threads; i++){
 	    mvm[i].A = matrix;
  	    mvm[i].v = vecter;
	    mvm[i].r = result;
	    mvm[i].rows = n;
	    mvm[i].i = i*num_calc;
	    mvm[i].num_calc=num_calc;
	    mvm[i].m = m;
	    if(i==num_threads-1){
		mvm[i].num_calc=n-(i*num_calc);
	    }
	    pthread_create(&pids[i], NULL, mvm_serial, &mvm[i]);
	}
	
	for(i = 0; i < num_threads; i++){
	    pthread_join(pids[i], NULL);
	}
	free(pids);
    }else{
	result[0] = -1;
    }
    return result;
}
