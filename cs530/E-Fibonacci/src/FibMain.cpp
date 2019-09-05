#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/time.h>
#include <Fibonacci.hpp>

//Borrowed from B-Jacobi2D main
long long stamp() {
    struct timeval te; 
    gettimeofday(&te, NULL);
    long long milliseconds = te.tv_sec*1000LL + te.tv_usec/1000;
    return milliseconds;
}

int main(int argc, char** argv)
{
	int n;

	if(argc != 2 || strcmp(argv[1], "-h") == 0)
	{
		printf("Usage: fibonacci n, where n = positive integer\n");
		return 0;
	}

	if(n = atoi(argv[1]))
	{
		clock_t start = stamp();
		long long result = find_fib(n);
		clock_t finish = stamp();

		long long diff = finish - start;

		printf("Fibonacci %d: %llu\n", n, result);
		printf("Time taken: %llu\n", diff);
	}
	else
	{
		printf("Unable to read integer\n");
	}

	return 0;
}
