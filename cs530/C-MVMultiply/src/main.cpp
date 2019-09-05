#include <stddef.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "../include/mvmultiply.hpp"
#include <errno.h>
#include <sys/time.h>
#include <iostream>
#include "timing.c"

const int MAX_LENGTH = 1024;
const int MAX_TOKENS = 1000;

//from jacobi
long long stamp() {
    struct timeval te;
    gettimeofday(&te, NULL);
    long long milliseconds = te.tv_sec*1000LL + te.tv_usec/1000;
    return milliseconds;
}


//Credit: CS253-resourses
char **parseInput(char *s, char *delimiter, int *numTokens){
    char *nextToken;
    char **token = (char **) malloc (sizeof(char *) * MAX_TOKENS);

    /* tokenize the string s */
    nextToken = strtok(s, delimiter);
    *numTokens = 0;
    while (nextToken != NULL) {
        token[*numTokens] = (char *) malloc(sizeof(char) * (strlen(nextToken)+1));
        strcpy(token[*numTokens], nextToken);
        (*numTokens)++;
        nextToken = strtok(NULL, delimiter);
    }
    return token;
}

int main(int argc, char** argv){
    if(argc != 3){
	printf("To run: _mvmult(execution file) <input_file>\n");    
        return 0;
    }
    
    if(strcmp(argv[1], "-h") == 0){
        printf("To run: _mvmult <input_file> <number of threads>\n");
    	return 0;
    }
    FILE *file = fopen(argv[1], "r");
    if(!file){
        perror(argv[0]);
        exit(errno);
    }
    int num_threads = atoi(argv[2]);
    double* data = (double*)malloc(sizeof(double)*1000);
    int numTokens = 0;
    char *delim = " ";
    char buffer[2048];
    char **token;
    int loop,m = 0;
    while(fgets(buffer, sizeof(buffer), file)!=NULL){
	token = parseInput(buffer, delim, &numTokens);
	int i=0;
	m=numTokens;
	while(i<numTokens && i <m){
	    data[i+loop]=strtod(token[i], NULL);
	    if(loop==1){
		m=data[loop+i];
	    }
	    i++;
	}
	loop+=(numTokens);       
    }
    
    //multiply
    int n =(int) data[0];
    double* matrix = (double*)malloc(sizeof(double)*m*n);
    double* vector = (double*)malloc(sizeof(double)*n);
    int vecsize = m;
    int c,b;
    for(c=0; c<m*n; c++){
	matrix[c]=data[c+2];
    }
    for(b=0;b<m;b++){
	vector[b]=data[b+n*m];
    }
    //timer code from jacobi
    //clock_t start = stamp();
    double start = getMilliSeconds();
    double* res = mvMultiply(matrix, vector, m,n,vecsize, num_threads);  
    res = mvMultiply(matrix, vector, m,n,vecsize, num_threads);
    res = mvMultiply(matrix, vector, m,n,vecsize, num_threads);
    res = mvMultiply(matrix, vector, m,n,vecsize, num_threads);
    res = mvMultiply(matrix, vector, m,n,vecsize, num_threads);
    double finish = getTimeMilliSeconds();
    double diff = finish - start;
    fprintf(stderr, "%d, %lf\n", num_threads, diff);

    //write file
    FILE *fout = fopen("result.txt", "w");
    int a;
    for(a=0; a<n;a++){
	fprintf(fout, "%lf\n", res[a]);
    }
    
    return 0;
}
