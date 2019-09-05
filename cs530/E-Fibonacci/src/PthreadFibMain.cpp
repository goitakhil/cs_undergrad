#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/time.h>

#include <PthreadFibonacci.hpp>

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
	int threadNum;

	if(argc != 3)
	{
		printf("Usage: fibonacci n threadNum, where n = positive integer and threadNum = number of threads\n");
		return 0;
	}

	if((n = atoi(argv[1])) && (threadNum = atoi(argv[2])))
	{
		clock_t start = stamp();
		long long result = find_fib(n, threadNum);
		clock_t finish = stamp();

		long long diff = finish - start;

		printf("For %d threads, Fibonacci %d: %llu\n", threadNum, n, result);
		printf("Time taken: %llu\n", diff);
	}
	else
	{
		printf("Unable to read integer\n");
	}	return 0;
}
