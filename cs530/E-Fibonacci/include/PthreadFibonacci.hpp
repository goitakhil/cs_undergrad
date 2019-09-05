#ifndef PTHREADFIBONACCI_H
#define PTHREADFIBONACCI_H

long long find_fib(int n, int threadNum);
long long serial_find_fib(int n);
void * thread_fib(void* arguments);
#endif
