#include "../src/matrix_matrix.hpp"
#include <gtest/gtest.h>
#include <stdbool.h>


// TODO: Free matrix rows and data

struct Matrix* mallocMatrixData(int rows, int cols);
struct Matrix* buildMatrix(int rows, int cols, double offset, bool isResult);
void populateMatrix(Matrix* matrix, int rows, int cols, double offset, bool isResult);

/***
  Naming Conventions For Matrix Variables:
    -"a" and "b" are the matrices that will be multiplied together.
    -"c" is the calculated matrix.
    -"d" is the matrix with the known correct answer.
***/

TEST(MatrixMatrixTest, one_by_one_zeros){
  const int square = 1;
  struct Matrix *a = buildMatrix(square, square, 0, false);
  struct Matrix *c = matrix_matrix_mult(a, a, 0);
  
  ASSERT_FALSE(c==NULL);
  EXPECT_DOUBLE_EQ(c->rows, a->cols);
  EXPECT_DOUBLE_EQ(c->cols, a->rows);
  EXPECT_DOUBLE_EQ(c->data[0][0], 0);
  
  free(a);
  free(c);
}

TEST(MatrixMatrixTest,twobytwo_times_twobytwo){
  const int square = 2;
  struct Matrix *a = buildMatrix(square, square, 0, false);
  struct Matrix *b = buildMatrix(square, square, 0, false);
  struct Matrix *d = buildMatrix(square, square, 0, true);
  
  d->data[0][0] = 2;
  d->data[0][1] = 3;
  d->data[1][0] = 6;
  d->data[1][1] = 11;
  
  struct Matrix *c = matrix_matrix_mult(a, b, 0);
  ASSERT_FALSE(c==NULL);
  EXPECT_DOUBLE_EQ(c->rows, a->rows);
  EXPECT_DOUBLE_EQ(c->cols, b->cols);
  
  int i, j;
  for(i=0;i<d->rows;i++){
    for(j=0;j<d->cols;j++){
      EXPECT_DOUBLE_EQ(c->data[i][j], d->data[i][j]);
    }
  }
  
  free(a);
  free(b);
  free(c);
  free(d);
}

TEST(MatrixMatrixTest,threebythee_times_threebythree){
  const int square = 3;
  struct Matrix *a = buildMatrix(square, square, 0, false);
  struct Matrix *b = buildMatrix(square, square, 0, false);
  struct Matrix *d = buildMatrix(square, square, 0, true);

  d->data[0][0] = 15;
  d->data[0][1] = 18;
  d->data[0][2] = 21;
  d->data[1][0] = 42;
  d->data[1][1] = 54;
  d->data[1][2] = 66;
  d->data[2][0] = 69;
  d->data[2][1] = 90;
  d->data[2][2] = 111;

  struct Matrix *c = matrix_matrix_mult(a, b, 0);
  ASSERT_FALSE(c==NULL);
  EXPECT_DOUBLE_EQ(c->rows, a->rows);
  EXPECT_DOUBLE_EQ(c->cols, b->cols);
  
  int i, j;
  for(i=0;i<d->rows;i++){
    for(j=0;j<d->cols;j++){
      EXPECT_DOUBLE_EQ(c->data[i][j], d->data[i][j]);
    }
  }
  
  free(a);
  free(b);
  free(c);
  free(d);
}

TEST(MatrixMatrixTest,fourbyfour_times_fourbyfour){
  const int square = 4;
  struct Matrix *a = buildMatrix(square, square, 0, false);
  struct Matrix *b = buildMatrix(square, square, 0, false);
  struct Matrix *d = buildMatrix(square, square, 0, true);

  d->data[0][0] = 56;
  d->data[0][1] = 62;
  d->data[0][2] = 68;
  d->data[0][3] = 74;
  d->data[1][0] = 152;
  d->data[1][1] = 174;
  d->data[1][2] = 196;
  d->data[1][3] = 218;
  d->data[2][0] = 248;
  d->data[2][1] = 286;
  d->data[2][2] = 324;
  d->data[2][3] = 362;
  d->data[3][0] = 344;
  d->data[3][1] = 398;
  d->data[3][2] = 452;
  d->data[3][3] = 506;

  struct Matrix *c = matrix_matrix_mult(a, b, 0);
  ASSERT_FALSE(c==NULL);
  EXPECT_DOUBLE_EQ(c->rows, a->rows);
  EXPECT_DOUBLE_EQ(c->cols, b->cols);
  
  int i,j;
  for(i=0;i<d->rows;i++){
    for(j=0;j<d->cols;j++){
      EXPECT_DOUBLE_EQ(c->data[i][j], d->data[i][j]);
    }
  }
  
  free(a);
  free(b);
  free(c);
  free(d);
}

TEST(MatrixMatrixTest,fourbyfour_times_fourbyfour_with_offset){
  const int square = 4;
  struct Matrix *a = buildMatrix(square, square, 0.5, false);
  struct Matrix *b = buildMatrix(square, square, 0.5, false);
  struct Matrix *d = buildMatrix(square, square, 0, true);

  d->data[0][0] = 72;
  d->data[0][1] = 80;
  d->data[0][2] = 88;
  d->data[0][3] = 96;
  d->data[1][0] = 176;
  d->data[1][1] = 200;
  d->data[1][2] = 224;
  d->data[1][3] = 248;
  d->data[2][0] = 280;
  d->data[2][1] = 320;
  d->data[2][2] = 360;
  d->data[2][3] = 400;
  d->data[3][0] = 384;
  d->data[3][1] = 440;
  d->data[3][2] = 496;
  d->data[3][3] = 552;

  struct Matrix *c = matrix_matrix_mult(a, b, 0);
  ASSERT_FALSE(c==NULL);
  EXPECT_DOUBLE_EQ(c->rows, a->rows);
  EXPECT_DOUBLE_EQ(c->cols, b->cols);
  
  int i,j;
  for(i=0;i<d->rows;i++){
    for(j=0;j<d->cols;j++){
      EXPECT_DOUBLE_EQ(c->data[i][j], d->data[i][j]);
    }
  }
  
  free(a);
  free(b);
  free(c);
  free(d);
}

TEST(MatrixMatrixTest, fourbyfive_times_fivebythree){
  struct Matrix *a = buildMatrix(4, 5, 0, false);
  struct Matrix *b = buildMatrix(5, 3, 0, false);
  struct Matrix *d = buildMatrix(4, 3, 0, true);
  
  d->data[0][0] = 90;
  d->data[0][1] = 100;
  d->data[0][2] = 110;
  d->data[1][0] = 240;
  d->data[1][1] = 275;
  d->data[1][2] = 310;
  d->data[2][0] = 390;
  d->data[2][1] = 450;
  d->data[2][2] = 510;
  d->data[3][0] = 540;
  d->data[3][1] = 625;
  d->data[3][2] = 710;

  struct Matrix *c = matrix_matrix_mult(a, b, 0);
  ASSERT_FALSE(c==NULL);
  EXPECT_DOUBLE_EQ(c->rows, a->rows);
  EXPECT_DOUBLE_EQ(c->cols, b->cols);
  
  int i,j;
  for(i=0;i<d->rows;i++){
    for(j=0;j<d->cols;j++){
      EXPECT_DOUBLE_EQ(c->data[i][j], d->data[i][j]);
    }
  }
  
  free(a);
  free(b);
  free(c);
  free(d);
}

TEST(MatrixMatrixTest, fourbyfive_times_fivebythree_with_offset){  
  struct Matrix *a = buildMatrix(4, 5, 20, false);
  struct Matrix *b = buildMatrix(5, 3, -7, false);
  struct Matrix *d = buildMatrix(4, 3, 0, true);
  
  d->data[0][0] = -80;
  d->data[0][1] = 30;
  d->data[0][2] = 140;
  d->data[1][0] = -105;
  d->data[1][1] = 30;
  d->data[1][2] = 165;
  d->data[2][0] = -130;
  d->data[2][1] = 30;
  d->data[2][2] = 190;
  d->data[3][0] = -155;
  d->data[3][1] = 30;
  d->data[3][2] = 215;

  struct Matrix *c = matrix_matrix_mult(a, b, 0);
  ASSERT_FALSE(c==NULL);
  EXPECT_DOUBLE_EQ(c->rows, a->rows);
  EXPECT_DOUBLE_EQ(c->cols, b->cols);
  
  int i,j;
  for(i=0;i<d->rows;i++){
    for(j=0;j<d->cols;j++){
      EXPECT_DOUBLE_EQ(c->data[i][j], d->data[i][j]);
    }
  }
  
  free(a);
  free(b);
  free(c);
  free(d);
}

TEST(MatrixMatrixTest, identity_matrix){
  const int square = 3;
  struct Matrix *a = buildMatrix(square, square, 0, false);
  struct Matrix *b = buildMatrix(square, square, 0, false);
  struct Matrix *d = buildMatrix(square, square, 0, true);
  
  // Identity matrix
  b->data[0][0] = 1;
  b->data[0][1] = 0;
  b->data[0][2] = 0;
  b->data[1][0] = 0;
  b->data[1][1] = 1;
  b->data[1][2] = 0;
  b->data[2][0] = 0;
  b->data[2][1] = 0;
  b->data[2][2] = 1;
  
  // Known result
  d->data[0][0] = 0;
  d->data[0][1] = 1;
  d->data[0][2] = 2;
  d->data[1][0] = 3;
  d->data[1][1] = 4;
  d->data[1][2] = 5;
  d->data[2][0] = 6;
  d->data[2][1] = 7;
  d->data[2][2] = 8;

  struct Matrix *c = matrix_matrix_mult(a, b, 0);
  ASSERT_FALSE(c==NULL);
  EXPECT_DOUBLE_EQ(c->rows, a->rows);
  EXPECT_DOUBLE_EQ(c->cols, b->cols);
  
  int i, j;
  for(i=0;i<d->rows;i++){
    for(j=0;j<d->cols;j++){
      EXPECT_DOUBLE_EQ(c->data[i][j], d->data[i][j]);
    }
  }
  
  free(a);
  free(b);
  free(c);
  free(d);
}

 
TEST(MatrixMatrixTest,fivebyfive_times_fivebyfive_with_offset){  
  const int square = 5;
  struct Matrix *a = buildMatrix(square, square, 0, false);
  struct Matrix *b = buildMatrix(square, square, -10.5, false);
  struct Matrix *d = buildMatrix(square, square, 0, true);

  d->data[0][0] = 45;
  d->data[0][1] = 55;
  d->data[0][2] = 65;
  d->data[0][3] = 75;
  d->data[0][4] = 85;
  d->data[1][0] = 32.5;
  d->data[1][1] = 67.5;
  d->data[1][2] = 102.5;
  d->data[1][3] = 137.5;
  d->data[1][4] = 172.5;
  d->data[2][0] = 20;
  d->data[2][1] = 80;
  d->data[2][2] = 140;
  d->data[2][3] = 200;
  d->data[2][4] = 260;
  d->data[3][0] = 7.5;
  d->data[3][1] = 92.5;
  d->data[3][2] = 177.5;
  d->data[3][3] = 262.5;
  d->data[3][4] = 347.5;
  d->data[4][0] = -5;
  d->data[4][1] = 105;
  d->data[4][2] = 215;
  d->data[4][3] = 325;
  d->data[4][4] = 435;

  struct Matrix *c = matrix_matrix_mult(a, b, 0);
  ASSERT_FALSE(c==NULL);
  EXPECT_DOUBLE_EQ(c->rows, a->rows);
  EXPECT_DOUBLE_EQ(c->cols, b->cols);
  
  int i,j;
  for(i=0;i<d->rows;i++){
    for(j=0;j<d->cols;j++){
      EXPECT_DOUBLE_EQ(c->data[i][j], d->data[i][j]);
    }
  }
  
  free(a);
  free(b);
  free(c);
  free(d);
}

TEST(MatrixMatrixTest, twobyone_time_onebythree){
  struct Matrix *a = buildMatrix(2, 1, 0, false);
  struct Matrix *b = buildMatrix(1, 3, -1.25, false);
  struct Matrix *d = buildMatrix(2, 3, 0, true);
  
  d->data[0][0] = 0;
  d->data[0][1] = 0;
  d->data[0][2] = 0;
  d->data[1][0] = -1.25;
  d->data[1][1] = -0.25;
  d->data[1][2] = 0.75;

  struct Matrix *c = matrix_matrix_mult(a, b, 0);
  ASSERT_FALSE(c==NULL);
  EXPECT_DOUBLE_EQ(c->rows, a->rows);
  EXPECT_DOUBLE_EQ(c->cols, b->cols);
  
  int i, j;
  for(i=0;i<d->rows;i++){
    for(j=0;j<d->cols;j++){
      EXPECT_DOUBLE_EQ(c->data[i][j], d->data[i][j]);
    }
  }
  
  free(a);
  free(b);
  free(c);
  free(d);
}

TEST(MatrixMatrixTest, threebytwo_time_twobytwo){
  struct Matrix *a = buildMatrix(3, 2, 0, false);
  struct Matrix *b = buildMatrix(2, 2, -2, false);
  struct Matrix *d = buildMatrix(3, 2, 0, true);

  d->data[0][0] = 0;
  d->data[0][1] = 1;
  d->data[1][0] = -4;
  d->data[1][1] = 1;
  d->data[2][0] = -8;
  d->data[2][1] = 1;

  struct Matrix *c = matrix_matrix_mult(a, b, 0);
  ASSERT_FALSE(c==NULL);
  EXPECT_DOUBLE_EQ(c->rows, a->rows);
  EXPECT_DOUBLE_EQ(c->cols, b->cols);
  
  int i, j;
  for(i=0;i<d->rows;i++){
    for(j=0;j<d->cols;j++){
      EXPECT_DOUBLE_EQ(c->data[i][j], d->data[i][j]);
    }
  }
  
  free(a);
  free(b);
  free(c);
  free(d);
}

TEST(MatrixMatrixTest, twobyone_twobyone){
  struct Matrix *a = buildMatrix(2, 1, 0, false);
  struct Matrix *b = buildMatrix(2, 1, 0, false);
  struct Matrix *d = buildMatrix(2, 2, 0, true);

  EXPECT_THROW(matrix_matrix_mult(a, b, 0), std::exception);
  
  free(a);
  free(b);
  free(d);
}

TEST(MatrixMatrixTest, null_input_matrixa){
  struct Matrix *a = NULL;
  struct Matrix *b = buildMatrix(1, 1, 0, false);

  ASSERT_THROW(matrix_matrix_mult(a, b, 0), std::exception);
  
  free(b);
}

TEST(MatrixMatrixTest, null_input_matrixb){
  struct Matrix *a = buildMatrix(1, 1, 0, false);
  struct Matrix *b = NULL;
 
  ASSERT_THROW(matrix_matrix_mult(a, b, 0), std::exception);
  
  free(a);
}

// Creates and returns a malloc'd matrix with data.
struct Matrix* buildMatrix(int rows, int cols, double offset, bool isResult) {
  struct Matrix *c =  mallocMatrixData(rows, cols);
  populateMatrix(c, rows, cols, offset, isResult);
  c->rows = rows;
  c->cols = cols;
  return c;
}

// Allocates memory for a matrix with specified dimensions.
struct Matrix* mallocMatrixData(int rows, int cols) {
  struct Matrix *c = (struct Matrix *)malloc(sizeof(struct Matrix));
  c->data = (double **) malloc(rows*sizeof(double*)); // Allocating the matrix rows 
  int i;
  for (i=0; i<rows; i++) {
    c->data[i] = (double*)malloc(cols*sizeof(double)); // Allocating the matrix columns
  }
  return c;
}

// Populates a matrix with incrementing values. Offset adjusts starting point.
void populateMatrix(Matrix* matrix, int rows, int cols, double offset, bool isResult) {
  int j;
  int k;
  double element = 0;
  
  for (j=0; j<rows; j++) {
    for (k=0; k<cols; k++) {
      if (!isResult) {
        matrix->data[j][k] = (element++) + offset;
      } else { // For the result that we hardcode in tests
        matrix->data[j][k] = 0;
      }
    }
  }
}

int main(int argc, char **argv){
  ::testing::InitGoogleTest(&argc, argv);
  return RUN_ALL_TESTS();
}
