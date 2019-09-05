#include "matrix_matrix.hpp"
#include "Configuration.hpp"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// TODO: Add cmdline arg checks for file names
// TODO: Add command arg/flag for thread limit

void freeMatrix(Matrix* matrix);

int main(int argc, char** argv){
  //fprintf(stderr,"Running matrix_matrix main\n");
  int debugMode = 0;

  // Configuration and cmdline parser is from office hours meeting
  /*Configuration config;
  config.addParamString("filename1",'a',NULL,"-a [string] the first matrix");
  config.addParamString("filename2",'b',NULL,"-b [string] the second matrix");

  config.parse(argc,argv);

  printf("I am planning to read: %s and %s\n",
         config.getString("filename1").c_str(),
         config.getString("filename2").c_str());*/
	
  if (argc == 4 && strcmp(argv[3], "-d") == 0) {
    // Turn on debug mode
    printf("Turning on debug mode with %s\n", argv[3]);
    debugMode = 1;
  }
  else if(argc != 3){
    fprintf(stderr,"ERROR: You had %d command arguments.\n", argc);
    fprintf(stderr,"USAGE: ./matrix_matrix <matrixA.txt> <matrixB.txt>\n");
    return 0;
  }
  
  //printf("arg: %s\n", argv[1]);
  struct Matrix* a = read_matrix_file(argv[1]);
  struct Matrix* b = read_matrix_file(argv[2]);
  struct Matrix* c = matrix_matrix_mult(a, b, debugMode);
  if (c == NULL) return 0;
  //printf("Got back to main\n");
  //printf("[%lf, %lf, %lf, %lf]\n", c->data[0][0], c->data[0][1], c->data[1][0], c->data[1][1]);
  
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
