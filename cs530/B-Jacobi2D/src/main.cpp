#include <stddef.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include <jacobi2d.h>
//#include <time.h>
#include <sys/time.h>
#include <iostream>

const int MAX_LENGTH = 1024;
const int MAX_TOKENS = 10000;

long long stamp() {
    struct timeval te; 
    gettimeofday(&te, NULL);
    long long milliseconds = te.tv_sec*1000LL + te.tv_usec/1000;
    return milliseconds;
}

//Credit: CS253-resourses / Stolen from MVMultiply.  THANKS ANNE!
char **parseInput(char *s, const char *delimiter, int *numTokens){
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
    
    if (argc < 2) {
        perror("Not enough arguments. Type -h for help.\n");
        exit(errno);
    }

    const char* input_file = "";
    input_file = argv[1];

    if(strcmp(argv[1], "-h") == 0) {
        printf("Required arguments: <String: filename> <Float: delta value>\n");
        exit(0);
    }

    FILE *file = fopen(input_file, "r");
    if(!file){
        perror("Could not open file. Make sure it exists.\n");
        exit(errno);
    }

    if (argc < 3) {

        perror("Please provide a delta value, type -h for help.");
        exit(0);
    }

    double delta = atof(argv[2]);
    if (!delta) {
        perror("Invalid delta value. Should be some digit or float value.\n");
        exit(errno);
    }

    

    double* data = (double*)malloc(sizeof(double) * 62000000);
    int numTokens = 0;
    const char *delim = " \n";
    char buffer[20000];
    char **token;
    int loop = 0;

    while(fgets(buffer, sizeof(buffer), file)!=NULL){
		token = parseInput(buffer, delim, &numTokens);
		int i;
		for(i=0; i<numTokens; i++){
	    	data[i+loop]=strtod(token[i], NULL);
		}
		loop+=numTokens;       
    }

    //jacobi2d
    int m = (int) data[0];
    int n = (int) data[1];
    double* matrix = (double*)malloc(sizeof(double) * (m * n));

    
    for(int i = 0; i < (m * n); i++){
		matrix[i] = data[i+2];
    }

    //const clock_t begin_time = clock();
    clock_t start = stamp();
    double* result = jacobi2d(matrix, m, n, delta);
    clock_t finish = stamp();
    long long diff = finish - start;
    std::cout << (n * m);
    std::cout << " ";
    std::cout << diff; 
    std::cout << "\n";
    

    //write file
    FILE *fout = fopen("result.txt", "w");
    for(int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            int pos = (i * n) + j;
	        fprintf(fout, "%f ", result[pos]);
        }
        fprintf(fout, "\n");
    }
    fclose(fout);
    free(data);
    free(matrix);
    free(result);
    return 0;
}
