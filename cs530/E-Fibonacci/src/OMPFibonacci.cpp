#include <omp.h>
#include <Fibonacci.hpp>
#include <OMPFibonacci.hpp>


long long find_fib(int n) {
	long long result;

	#pragma omp parallel
	{
		#pragma omp single
		result = omp_fib(n);
	}

	return result;
}

long long omp_fib(int n) {
	long long i, j;

	if(n < 0)
		return -1ull;
	if(n == 0)
		return 0ull;
	if(n == 1)
		return 1ull;
	if(n == 2)
		return 1ull;

	#pragma omp task shared(i)
	i = omp_fib(n - 1);
	j = omp_fib(n - 2);
	
	#pragma omp taskwait
	return i + j;
}
