#include <Fibonacci.hpp>

long long find_fib(int n) {
	if(n < 0)
		return -1ull;
	if(n == 0)
		return 0ull;
	if(n == 1)
		return 1ull;
	if(n == 2)
		return 1ull;

	return find_fib(n - 1) + find_fib(n - 2);
}
