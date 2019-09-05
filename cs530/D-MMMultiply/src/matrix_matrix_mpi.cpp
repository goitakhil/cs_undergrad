#include "matrix_matrix.hpp"
#include "timing.c"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdexcept>
#include <errno.h>
#include <mpi.h>
#include <math.h>

#define MAX_LENGTH 4048
#define MAX_TOKENS 2000

void rotate_row(double* row, int cols, int shift);
void rotate_col(double** matrix, int col, int rows, int shift);

int parseCommandLine(int argc, char** argv){
  return 0;
}

int cti(int i, int j, int N) { return (i%N)*N + (j%N); }

// Parsing code is from CS253-resources
char **parseInput(char *s, char *delimiter, int *numTokens);

// Multiplies two matrices together
struct Matrix* matrix_matrix_mult(struct Matrix* A, struct Matrix* B, int debugMode){
  if (A == NULL || B == NULL) {
    throw std::invalid_argument("Can't multiply a NULL matrix.");
  }

  if (A->cols != B->rows) {
    throw std::invalid_argument("A's rows and B's columns don't match.");
  }

  double start_time;
  double sorting_time;
  
  start_time = getMilliSeconds();

  int size;
  int rank;

  // 1) Shift rows of A to the left by i
   int q;
   for (q = 0; q < A->rows; q++) {
     rotate_row(A->data[q], A->cols, -q);
   }
   // 2) Shift columns of B up by i
   for (q = 0; q < B->cols; q ++) {
     rotate_col(B->data, q, B->rows, q);
   }

  MPI_Init(NULL, NULL);
  MPI_Comm_rank(MPI_COMM_WORLD, &rank);
  MPI_Comm_size(MPI_COMM_WORLD, &size);


  if (debugMode) {
    fprintf(stderr,"DEBUG: C range is %i\n", size);
  } 
  
  // World size must be perfect square:
  // https://www.geeksforgeeks.org/program-to-find-whether-a-no-is-power-of-two/
  if ((ceil(log2(size)) != floor(log2(size)))){
    return NULL;
  }

  // For now, input arrays must be square
  int N = sqrt(size);

  //START CANNON'S MPI IMPLEMENTATION
  // i is tile row index
  int i = rank / N;
  // is is tile row stride
  int is = A->rows / N;
  // j is tile column index
  int j = rank % N;
  // js is time column stride
  int js = is;
  
  if (debugMode) {
    fprintf(stderr, "DEBUG: process:%i  row:%i  col:%i rowstride:%i colstride:%i\n", rank, i, j, is, js);
  }
    
  // Creating tiled copies of matrices A, B, and C
  double **a = (double**) malloc(sizeof(double*)*is);
  for (int x = 0; x < is; x++) {
    a[x] = (double *) malloc(sizeof(double) * js);
    for (int y = 0; y < js; y++) {
      a[x][y] = A->data[i+x][j+y];
    }
  }
  double **b = (double**) malloc(sizeof(double*)*is);
  for (int x = 0; x < is; x++) {
    b[x] = (double *) malloc(sizeof(double) * js);
    for (int y = 0; y < js; y++) {
      b[x][y] = B->data[i+x][j+y];
    }
  }
  double **c = (double**) malloc(sizeof(double*)*is);
  for (int x = 0; x < is; x++) {
    c[x] = (double *) malloc(sizeof(double) * js);
    for (int y = 0; y < js; y++) {
      c[x][y] = 0;
    }
  }

  
  // MPI send/receive loop
  for (int k = 0; k < N; k++) {
    for (int l = 0; l < is; l++) { // Tile of i-size
      for (int p = 0; p < js; p++) { // Tile of j-size
        c[l][p] += a[l][p] * b[p][l]; // Calculate part of the cell
      }
    }
    
    if (j == 0) { 
      for (int x = 0; x < is; x++) { // MPI Send
        if (debugMode) {
            fprintf(stderr, "DEBUG: process %i is sending to process %i\n", rank, cti(i, j+1, N));
        }
        MPI_Send(a[x], js, MPI_DOUBLE, cti(i, j+1, N), 0, MPI_COMM_WORLD);
      }
      for (int x = 0; x < is; x++) { // MPI Receive
        MPI_Recv(a[x], js, MPI_DOUBLE, cti(i, j+N-1, N), 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
      }
    } else {
      double **recv_buff = (double**) malloc(sizeof(double*)*js);
      for (int x = 0; x < is; x++) { // MPI Receive
        recv_buff[x] = (double*) malloc(sizeof(double)*is);
        MPI_Recv(recv_buff[x], js, MPI_DOUBLE, cti(i, j+N-1, N), 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
      }
      for (int x = 0; x < is; x++) { // MPI Send
        if (debugMode) {
            fprintf(stderr, "DEBUG: process %i is sending to process %i\n", rank, cti(i, j+1, N));
        }
        MPI_Send(a[x], js, MPI_DOUBLE, cti(i, j+1, N), 0, MPI_COMM_WORLD);
      }
      a = recv_buff;
    }
    if (i == 0) {
      for (int x = 0; x < js; x++) { // MPI Send
        if (debugMode) {
            fprintf(stderr, "DEBUG: process %i is sending to process %i\n", rank, cti(i+1, j, N));
        }
        MPI_Send(b[x], is, MPI_DOUBLE, cti(i+1, j, N), 0, MPI_COMM_WORLD);
      }
      for (int x = 0; x < js; x++) { // MPI Receive
        MPI_Recv(b[x], is, MPI_DOUBLE, cti(i+N-1, j, N), 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
      }
    } else {
      double **recv_buff = (double**) malloc(sizeof(double*)*js);
      for (int x = 0; x < js; x++) { // MPI Receive
        recv_buff[x] = (double*) malloc(sizeof(double)*js);
        MPI_Recv(recv_buff[x], is, MPI_DOUBLE, cti(i+N-1, j, N), 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
      }
      for (int x = 0; x < js; x++) { // MPI Send
        if (debugMode) {
            fprintf(stderr, "DEBUG: process %i is sending to process %i\n", rank, cti(i+1, j, N));
        }
        MPI_Send(b[x], is, MPI_DOUBLE, cti(i+1, j, N), 0, MPI_COMM_WORLD);
      }
      b = recv_buff;
    }
  }

  // Finall receive for process 0 to build the final C matrix.
  if (rank == 0) {
    struct Matrix *C = (struct Matrix *)malloc(sizeof(struct Matrix));
    C->data = (double **) malloc(sizeof(double*)*is*N);
    for (int x = 0; x < is*N; x++) {
      C->data[x] = (double*) malloc(sizeof(double)*js*N);
    }
    C->rows = is*N;
    C->cols = js*N;
    for (int x = 0; x < N; x++) {
      for (int y = 0; y < N; y++) {
        if (x > 0 || y > 0) {
          for (int z = 0; z < is; z++) {
            MPI_Recv(c[z], js, MPI_DOUBLE, (x*N + y), 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
          }
        }
        for (int l = 0; l < is; l++) {
          for (int p = 0; p < js; p++) {
            C->data[x*is+l][y*js+p] = c[l][p];
          }
        }
      }
    }
    sorting_time = getMilliSeconds() - start_time;
    printf("Calculating the matrix took %4.2lf seconds.\n", sorting_time/1000.0);
    printf("It took %lf milliseconds.\n", sorting_time);
    MPI_Finalize();
    return C;
  } else {
    for (int z = 0; z < is; z++) {
      if (debugMode) {
        fprintf(stderr, "DEBUG: process %i is sending to process %i\n", rank, 0);
      }
      MPI_Send(c[z], js, MPI_DOUBLE, 0, 0, MPI_COMM_WORLD);
    }
    MPI_Finalize();
    return NULL;
  }
}

// Reads a matrix from a text file.
struct Matrix* read_matrix_file(char* filename){
  //printf("Read matrix file: %s \n", filename);
  
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
  //fclose(file); // This line in mentioned in the gdb stacktrace?
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

// Writes a matrix to a text file.
void write_matrix_file(struct Matrix* matrix){
  if(matrix->data[0] == NULL) {
    printf("matrix data is null\n");
    return;
  } 

  FILE *fout = fopen("result_mpi.txt", "w");
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


/*Helper function that rotates a row left or right*/
void rotate_row(double* row, int cols, int shift) {
  double* copy = (double*)malloc(sizeof(double) * cols);

  int i;
  for (i = 0; i < cols; i++) {
    copy[i] = row[i];
  }

  for (i = 0; i < cols; i++) {
    if (i + shift < 0) {
      row[(i + shift) % cols + cols] = copy[i];
    } else {
      row[(i + shift) % cols] = copy[i];
    }
  }
  free(copy);
}


/*Helper function that rotates a column up or down*/
void rotate_col(double** matrix, int col, int rows, int shift){
  double* ptr = (double*) malloc(sizeof(double) * rows);

  //copy the column
  int i;
  for (i = 0; i < rows; i++) {
    ptr[i] = matrix[i][col];
  }

  //shift column
  for (i = 0; i < rows; i++) {
    if(i + shift < 0) {
      matrix[i][col] = ptr[(i + shift) % rows + rows];
    } else {
      matrix[i][col] = ptr[(i + shift) % rows];
    }
  }
}
// vim: softtabstop=4:shiftwidth=4:expandtab 
