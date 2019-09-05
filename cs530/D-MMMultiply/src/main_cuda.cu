#include "matrix_matrix.hpp"
#include "Configuration.hpp"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// TODO: Add cmdline arg checks for file names
// TODO: Add command arg/flag for thread limit

void freeMatrix(CudaMatrix* matrix);

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

  
  struct CudaMatrix* a = read_cuda_matrix_file(argv[1]);
  struct CudaMatrix* b = read_cuda_matrix_file(argv[2]);

  // Cuda, grid dimension from Nvidia docs. Link in README.
  dim3 dimBlock(a->rows/2, b->cols/2); // Using max/2 so that it's not overkill 
  dim3 dimGrid(b->cols / dimBlock.x, a->rows / dimBlock.y); // From Nvidia docs
  
  // Cuda
  //int blockSize = 2; // TODO: What size to use?
  //int numBlocks = (a->rows*b->cols + blockSize - 1) / blockSize; // TODO: Determine if this is the right calculation to use

  //printf("numBlocks: %d\n", numBlocks);

  // Allocing the c matrix with cuda
  struct CudaMatrix *c;
  cudaMallocManaged(&c, sizeof(struct Matrix));
  cudaMallocManaged(&c->data, a->rows*b->cols*sizeof(double));
  c->rows = a->rows;
  c->cols = b->cols;

  //matrix_matrix_mult<<<numBlocks, blockSize>>>(a, b, c, debugMode);
  matrix_matrix_mult<<<dimGrid, dimBlock>>>(a, b, c, debugMode); // Using 1,1 until cuda works
  
  // Wait for GPU to finish before accessing on host
  cudaDeviceSynchronize();

  if (c == NULL) return 0;
  //printf("Got back to main\n");
  //printf("[%lf, %lf, %lf, %lf]\n", c->data[0][0], c->data[0][1], c->data[1][0], c->data[1][1]);
  
  write_cuda_matrix_file(c);

  freeMatrix(a);
  freeMatrix(b);
  freeMatrix(c);
  
  return 0;
}

void freeMatrix(CudaMatrix* matrix) {
  
  //free(matrix->data);
  //free(matrix);
  cudaFree(matrix->data);
  cudaFree(matrix);
}
