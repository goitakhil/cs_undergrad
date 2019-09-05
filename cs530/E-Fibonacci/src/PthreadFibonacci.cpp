#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <math.h>
#include <PthreadFibonacci.hpp>

struct args_struct {
	int n;
	long long partialSum;
	int maxLevel;
};

/* defaults to 8 threads with one argument */
int find_fib(int n) {
	return find_fib(n, 8);
}

/* parallel version */
long long find_fib(int n, int threadNum) {
	int log2Threads, s;
	long long sum;
	pthread_t thread_handle;
	args_struct args;

	/* calculate the number of times the threads should split in two */
	log2Threads = -1;
	while(threadNum > 0)
	{
		threadNum = threadNum >> 1;
		log2Threads++;
	}
	
	if(threadNum <= 1 || n < log2Threads)
		return serial_find_fib(n);

	/*create the initial thread*/
	args.n = n;
	args.partialSum = 0ull;
	args.maxLevel = log2Threads;
	
	s = pthread_create(&thread_handle, NULL, thread_fib, &args);
	
	/* error checking */
	if (s != 0) 
	{
		fprintf(stderr, "pthread_create error");
		return -1;
	}
	
	pthread_join(thread_handle, NULL);

	return args.partialSum;
}


long long serial_find_fib(int n) {
	if(n < 0)
		return -1ull;
	if(n == 0)
		return 0ull;
	if(n == 1)
		return 1ull;
	if(n == 2)
		return 1ull;

	return serial_find_fib(n - 1) + serial_find_fib(n - 2);
}

void * thread_fib(void* arguments) 
{
	struct args_struct *args = (struct args_struct *)arguments;

	if(args->n <= 2)
	{
		args->partialSum = 1ull;
	}
	else
	{
		struct args_struct leftArgs, rightArgs;
		leftArgs.n = args->n - 1;
		rightArgs.n = args->n - 2;

		//if we can create a new thread
		if(args->maxLevel > 0)
		{
			int s;
			pthread_t left_handle;
	
			leftArgs.maxLevel = args->maxLevel - 1;
			rightArgs.maxLevel = args->maxLevel - 1;
		
			pthread_create(&left_handle, NULL, thread_fib, &leftArgs); //create a new thread to do half the work

			thread_fib(&rightArgs); //let the current thread continue with the other half of the calculations

			pthread_join(left_handle, NULL); //wait til the thread finishes before adding the results together
		}	
		else
		{
			thread_fib(&leftArgs);
			thread_fib(&rightArgs);
		}
	
		args->partialSum = leftArgs.partialSum + rightArgs.partialSum;
	}
	
	return NULL;
}
