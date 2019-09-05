#include "matrix_matrix.hpp"
#include "timing.c"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdexcept>
#include <errno.h>

#define MAX_LENGTH 2048
#define MAX_TOKENS 1000

int parseCommandLine(int argc, char** argv){
  return 0;
}

// Parsing code is from CS253-resources
char **parseInput(char *s, char *delimiter, int *numTokens);

__global__
void matrix_matrix_mult(struct CudaMatrix* A, struct CudaMatrix* B, struct CudaMatrix* C, int debugMode){
/*  if (A == NULL || B == NULL) {
    throw std::invalid_argument("Can't multiply a NULL matrix.");
  }

  if (A->cols != B->rows) {
    throw std::invalid_argument("A's rows and B's columns don't match.");
  }
*/

  // Cuda offsets 
  /* 
  int iOffset = blockDim.x * blockIdx.x;
  int jOffset = blockDim.y * blockIdx.y;

  int i;
  for (i=iOffset; i<gridDim.x*blockDim.x; i+=iOffset) { // block/grid x dim
    int j;
    for (j=jOffset; j<gridDim.y*blockDim.y; j+=jOffset) { // block/grid y dim
      int k;
      for (k=0; k<A->cols; k++) { // cols in a
        double a = A->data[i][k];
        double b = B->data[k][j];
        C->data[i][j] += a * b;
      }
    }
  }

*/
  //int index = threadIdx.x; //index of the current thread within it's block
  //int stride = blockDim.x; //number of threads in the block.

//printf("threadIdx: %d\n", index);
//printf("stride: %d\n", stride);
 
  /*
  int i;
  for (i = index; i < A->rows; i += stride) {
    int j;
    for(j = 0; j < B->cols; j++) {
      int k;
      for(k = 0; k < A->cols; k++) {
        C->data[i * B->cols + j] += A->data[i * A->cols + k] * B->data[k * B->cols + j]; 
      }
    }  
  }*/

  // This code is from a Nvidia developer documentation. Link in README.
  int row = blockIdx.y * blockDim.y + threadIdx.y;
  int col = blockIdx.x * blockDim.x + threadIdx.x;
  int k;
  for(k = 0; k < A->cols; k++) {
    C->data[row * B->cols + col] += A->data[row * A->cols + k] * B->data[k * B->cols + col]; 
  }



  /*
  // Original serial code
  int i;
  start_time = getMilliSeconds();
  for (i=0; i<A->rows; i++) { // rows in a
    C->data[i] = (double*)malloc(B->cols*sizeof(double)); // malloc'ing the C matrix columns
    int j;
    for (j=0; j<B->cols; j++) { // cols in b
      C->data[i][j] = 0;
      int k;
      for (k=0; k<A->cols; k++) { // cols in a
        double a = A->data[i][k];
        double b = B->data[k][j];
        C->data[i][j] += a * b;
      }
    }
  }*/

  //Had to get rid to timing stuff because cuda doesn't like it. 
}

struct CudaMatrix* read_cuda_matrix_file(char* filename){
  printf("Read matrix file: %s \n", filename);
  
  FILE *file = fopen(filename, "r");
  if(!file){
    fprintf(stderr, "An error has occurred. Wrong file name?\n");
    exit(errno);
  }

  // Parse - Code is from CS253-resources and C-MVMultiply.
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

  // Mallocing matrix with cuda 
  struct CudaMatrix *matrix;
  cudaMallocManaged(&matrix, sizeof(struct Matrix));
  cudaMallocManaged(&matrix->data, rows*cols*sizeof(double));
  matrix->rows = rows;
  matrix->cols = cols;
  
  int b;
  int dataIndex = 2; // 2 to account for index 0 and 1 are taken for row and col values.
  for (b=0; b<rows*cols; b++){
      matrix->data[b] = data[dataIndex++];
  }
  
  fclose(file); // This line in mentioned in the gdb stacktrace?
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


void write_cuda_matrix_file(struct CudaMatrix* matrix){
  FILE *fout = fopen("result_cuda.txt", "w");
  int i,j;
  
  // Print matrix dimensions
  fprintf(fout, "%d\n", matrix->rows);
  fprintf(fout, "%d\n", matrix->cols);
  
  // Print matrix data
  for (i=0; i< matrix->rows; i++){
    for (j=0; j<matrix->cols; j++) {
      fprintf(fout, "%lf", matrix->data[i * matrix->cols + j]);
      if (j != matrix->cols-1) fprintf(fout, " "); // Adds spaces between data values
    }
    fprintf(fout, "\n");
  }
  fclose(fout);  
}
