#ifndef _JACOBI2D_H
#define _JACOBI2D_H

#include <stdbool.h>

double* jacobi2d(double* A, int n, int m, double delta);
double check_delta(double* A, double* B, int n, int m);

#endif // _JACOBI2D_H
