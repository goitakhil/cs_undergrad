#include "matrix_matrix.hpp"
#include "timing.c"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdexcept>
#include <errno.h>
#include <pthread.h>

#define MAX_LENGTH 2048
#define MAX_TOKENS 1000
//#define MAX_THREADS 25

int parseCommandLine(int argc, char** argv){
  return 0;
}

// Parsing code is from CS253-resources
char **parseInput(char *s, char *delimiter, int *numTokens);
void *serial_matrix_matrix_mult_ptr(void *matrices);

struct Matrices {
  struct Matrix *A;
  struct Matrix *B;
  struct Matrix *C;
  int rowLeft;
  int rowRight;
};

void *serial_matrix_matrix_mult_ptr(void *data) {
  struct Matrices *matrices = (struct Matrices *) data;

  //printf("Thread %d in the runner\n", pthread_self());
  
  
  //printf("Row range from %d to %d\n", matrices->rowLeft, matrices->rowRight);

  int i, j, k;
  for (i=matrices->rowLeft; i<=matrices->rowRight; i++) { // subsection of Matrix A's rows, inclusive of the right boundary!!
    //printf("i: %d\n", i);
    for (j=0; j<matrices->B->cols; j++) { // cols in b
      //printf("j: %d\n", j);
      matrices->C->data[i][j] = 0; // seg fault here
      int k;
      for (k=0; k<matrices->A->cols; k++) { // cols in a
        //printf("k: %d\n", k);
        double a = matrices->A->data[i][k];
        double b = matrices->B->data[k][j];
        //printf("a: %f\n", a);
        //printf("b: %f\n", b);
        matrices->C->data[i][j] += a * b;
      }
      //printf("matrices->C->data[%d][%d]: %f\n", i, j, matrices->C->data[i][j]);
    }
  }
	return NULL;
}

struct Matrix* matrix_matrix_mult(struct Matrix* A, struct Matrix* B, int debugMode, int numOfThreads){
  //printf("Running matrix_matrix_pthreads\n");
  if (A == NULL || B == NULL) {
    throw std::invalid_argument("Can't multiply a NULL matrix.");
  }

  if (A->cols != B->rows) {
    throw std::invalid_argument("A's rows and B's columns don't match.");
  }
  
  //printf("Number of threads: %d\n", numOfThreads);
  
  // Cap max threads at A's number of rows
  if (numOfThreads > A->rows) {
    numOfThreads = A->rows;
  }

  // Matrix setup
  struct Matrix *C = (struct Matrix *)malloc(sizeof(struct Matrix));
  C->data = (double **) malloc(A->rows*sizeof(double*));
  C->rows = A->rows;
  C->cols = B->cols;
  
  //printf("C->rows: %d\n", C->rows);
  //printf("C->cols: %d\n", C->cols);
  
  // Pthreads
  pthread_t tids[A->rows]; // Creates enough tids for all the rows in A
  int selectionSize = A->rows / numOfThreads;
  //printf("Selection size: %d\n", selectionSize);
  
  // Timing
  double start_time;
	double sorting_time;
  
  int i, k;
  start_time = getMilliSeconds();
  
  // Malloc'ing the columns in C
  for (i=0; i<A->rows; i++) { // rows in a
    C->data[i] = (double*)malloc(B->cols*sizeof(double)); // malloc'ing the C matrix columns
  }
  
  // Spinning off threads
  int n = A->rows;
  int nextIndex = 0;
  for (k=0; k<numOfThreads; k++) { // Create each thread
    int endIndex = nextIndex + selectionSize - 1;
    
    // Did not divide evenly, last thread will have the extra indices
    if (selectionSize*numOfThreads != n && k == numOfThreads-1) { // (A->rows - selectionSize) < (A->rows - endIndex)
			endIndex = n-1;
		}
   
    // If a selection is only one row
    if (endIndex == nextIndex && endIndex < A->rows) { // and it's not a thread picking up the leftovers, just a single row...
      //matrices->rowRight++; // ???? Is this the right thing to do?
    }
    
    // Threads (calculations per row of A)
    struct Matrices *matrices = (struct Matrices *) malloc(sizeof(struct Matrices));
    matrices->A = A;
    matrices->B = B;
    matrices->C = C;
    matrices->rowLeft = nextIndex;
    matrices->rowRight = endIndex;

    //printf("Creating thread %d\n", k);
    //printf("nextIndex: %d, endIndex: %d\n", matrices->rowLeft, matrices->rowRight);
    pthread_create(&tids[k], NULL, serial_matrix_matrix_mult_ptr, matrices);
    nextIndex = nextIndex + selectionSize;
  }
  
  //printf("About to join threads...\n);
  int j;
  for (j=0; j<numOfThreads; j++) {
    pthread_join(tids[j], NULL);
  }
  sorting_time = getMilliSeconds() - start_time;
  printf("Calculating the matrix took %4.2lf seconds.\n", sorting_time/1000.0);
  printf("It took %lf milliseconds.\n", sorting_time);
  return C;
}

struct Matrix* read_matrix_file(char* filename){
  printf("Read matrix file: %s \n", filename);
  
  FILE *file = fopen(filename, "r");
  if(!file){
    fprintf(stderr, "An error has occurred. Wrong file name?\n");
    exit(errno);
  }

  // Parse - Code is from CS253-resources and C-MVMultiply.
  // TODO: Would freeing data affect the matrix that is built from this data?
  double* data = (double*)malloc(sizeof(double)*MAX_TOKENS); 
  int numTokens = 0;
  char *delim = strdup(" ");
  char buffer[MAX_LENGTH];
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
    
  // Build matrix with data
  int rows = (int) data[0];
  int cols = (int) data[1];
  
  struct Matrix *matrix = (struct Matrix *)malloc(sizeof(struct Matrix));
  matrix->data = (double **) malloc(rows*sizeof(double*)); // Allocating the matrix rows 
  int i;
  for (i=0; i<rows; i++) {
    matrix->data[i] = (double*)malloc(cols*sizeof(double)); // Allocating the matrix columns
  }
  
  matrix->rows = rows;
  matrix->cols = cols;
  
  int b,c;
  int dataIndex = 2; // 2 to account for index 0 and 1 are taken for row and col values.
  for (b=0; b<rows; b++){
    for (c=0; c<cols; c++) {
   	  matrix->data[b][c] = data[dataIndex++];
    }
  }
  //fclose(file); // Causes memory issue?
  return matrix;
}


// The parseInput() function is from CS253-resources.
char **parseInput(char *s, char *delimiter, int *numTokens) {
  char *nextToken;
  char **token = (char **) malloc (sizeof(char *) * MAX_TOKENS);

  // Tokenize the string s 
  nextToken = strtok(s, delimiter);
  *numTokens = 0;
  while (nextToken != NULL) { // && *numTokens < MAX_TOKENS
    token[*numTokens] = (char *) malloc(sizeof(char) * (strlen(nextToken)+1));
    strcpy(token[*numTokens], nextToken);
    (*numTokens)++;
    nextToken = strtok(NULL, delimiter);
  }
  // Now the tokens are copied into token[0..numTokens-1]; 
  return token;
}


void write_matrix_file(struct Matrix* matrix){
  FILE *fout = fopen("result_pthreads.txt", "w");
  int i,j;
  
  // Print matrix dimensions
  fprintf(fout, "%d\n", matrix->rows);
  fprintf(fout, "%d\n", matrix->cols);
  
  // Print matrix data
  for (i=0; i<matrix->rows; i++){
    for (j=0; j<matrix->cols; j++) {
      fprintf(fout, "%lf", matrix->data[i][j]);
      if (j != matrix->cols-1) fprintf(fout, " "); // Prevents extra space at end of line.
    }
    if (i != matrix->rows-1) fprintf(fout, "\n"); // Prvents an empty line at end of the file.
  }
}
