struct Matrix {
    double** data;
    int rows;
    int cols;
};
  struct Matrix *read_matrix_file(char* filename);

  struct Matrix *matrix_matrix_mult(struct Matrix *A, struct Matrix *B);

  void write_matrix_file(struct Matrix *A, char* filename);
