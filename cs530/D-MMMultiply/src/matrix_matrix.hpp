struct Matrix{
    double** data;
    int rows;
    int cols;
};

struct CudaMatrix{
    double* data;
    int rows;
    int cols;
};

  struct Matrix* read_matrix_file(char* filename);

  struct CudaMatrix* read_cuda_matrix_file(char* filename);

  // Used for serial mmmm
  struct Matrix* matrix_matrix_mult(struct Matrix* A, struct Matrix* B, int debugMode);
  
  // Used for cuda mmm
  __global__ void matrix_matrix_mult(struct CudaMatrix* A, struct CudaMatrix* B, struct CudaMatrix* C, int debugMode);
  
  // Used for openmp and pthreads mmm
  struct Matrix* matrix_matrix_mult(struct Matrix* A, struct Matrix* B, int debugMode, int numOfThreads);
  
  void write_matrix_file(struct Matrix* A);

  void write_cuda_matrix_file(struct CudaMatrix* A);
