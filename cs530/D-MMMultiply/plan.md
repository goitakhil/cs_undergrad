# Matrix - Matrix Multiplication

## Problem Description
This problem involves the linear algebra operation of multiplying two matrices 
together. If matrix A is n by m, then matrix B must be m by p. The output 
matrix C has shape n by p.

The below diagram has A that is 4 by 2 and B is 2 by 3, thus the output is 4 
by 3. The value in each cell of the matrix C is the sum of the pairwise 
products of the values in the corresponding row of A and column of B. So the
product of the first value in the row of A with the first value in the column
of B, and so on.


![Matrix multiplication diagram 2](https://upload.wikimedia.org/wikipedia/commons/thumb/e/eb/Matrix_multiplication_diagram_2.svg/313px-Matrix_multiplication_diagram_2.svg.png)

[Matrix multiplication diagram.svg:User:Bilou](https://commons.wikimedia.org/wiki/File:Matrix_multiplication_diagram_2.svg) "Matrix multiplication diagram 2“ [CC BY-SA 3.0](https://creativecommons.org/licenses/by-sa/3.0/legalcode)

This calculation takes n*p*2m operations, which in two square matrices (the worst
case scenario) has a maximal runtime of O(n^3).

## Interface Description
The user will provide two input files. Each file contains one matrix with its
expected dimensions in floating point format. The output will be to a specified
file in the same format as the inputs.
```
3 # rows
2 # columns
1.0 2.0
3.2 2.3
4.5 6.4
```
## Code organization
```c
struct Matrix{
    double** data,
    int rows,
    int cols
};
```

```c
Matrix* matrix_matrix_mult(Matrix* A, Matrix* B);
Matrix* read_matrix_file(char* filename);
void write_matrix_file(Matrix* A, char* filename);
```
