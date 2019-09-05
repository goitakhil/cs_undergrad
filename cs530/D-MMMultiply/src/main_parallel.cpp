#include "matrix_matrix.hpp"
#include "Configuration.hpp"
#include <stdio.h>
#include <stdlib.h>

// TODO: Add cmdline arg checks for file names
// TODO: Add command arg/flag for thread limit

void freeMatrix(Matrix* matrix);

int main(int argc, char** argv){
  int debugMode = 0;
  fprintf(stderr,"Running matrix_matrix main\n");

  // Configuration and cmdline parser is from office hours meeting
  /*Configuration config;
  config.addParamString("filename1",'a',NULL,"-a [string] the first matrix");
  config.addParamString("filename2",'b',NULL,"-b [string] the second matrix");

  config.parse(argc,argv);

  printf("I am planning to read: %s and %s\n",
         config.getString("filename1").c_str(),
         config.getString("filename2").c_str());*/

  if(argc != 4){
    fprintf(stderr,"ERROR: You had %d command arguments.\n", argc);
    fprintf(stderr,"USAGE: ./matrix_matrix <matrixA.txt> <matrixB.txt> <num of threads>\n");
    return 0;
  }
  
  printf("arg: %s\n", argv[1]);
  struct Matrix* a = read_matrix_file(argv[1]);
  struct Matrix* b = read_matrix_file(argv[2]);
  struct Matrix* c = matrix_matrix_mult(a, b, debugMode, atoi(argv[3]));
  write_matrix_file(c);

  freeMatrix(a);
  freeMatrix(b);
  freeMatrix(c);
  
  return 0;
}

void freeMatrix(Matrix* matrix) {
  int i;
  for (i=0; i<matrix->rows; i++) {
    free(matrix->data[i]);
  }
  free(matrix->data);
  free(matrix);
}
