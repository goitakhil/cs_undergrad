# Report / Reflection on Fibonacci
Tyler Bevan

## Design
#### Problem Description

The Fibonacci algorithm defines a numeric series such that the first two values
are 0 and 1, and following values are the sum of the previous two. For example:
```
0, 1, 1, 2, 3, 5, 8, ... n-2, n-1, (n-2 + n-1)
```
This program takes in an index and returns the number in the series at that 
index. For index 4 the return value is 3.

#### Program Design

The approach used here is a recursive algorithm. To find a given value, you
first need to find the two previous values. The same function is used to find
those values. This recursion continues down the series until the base cases of
n=0 or n=1 are hit.
Function prototype:
```c
int find_fib(int n)
```

## Testing Plan

The function should return an integer for any input index. If the input is
negative (which we did not implement) then the function should return -1, which
indicates an invalid input.
```
 N   F
 0   0
 1   1
 2   1
 3   2
-1  -1
-2  -1
```
There should be no possibility for exceptions or faults of any kind, beyond
memory limitations on the recursion.

## Code Review

The code for this project is quite clean and simple. It checks for invalid
input, then checks the base cases, and if neither of those happens it performs
the two recursive calls and returns the sum.
There are not any comments in the code, however in this case it seems like a good
example of how if the code is simple and clean it can be understood without extra
comments cluttering the source.
My only comment on the code is that there is an additional base case for n==2 in
the code which is not needed, as the recursion on n=1 and n=0 would generate the
correct answer. This does not effect the correctness of the output, but does add 
extra cycles to the function and therefore has a performance impact.

## Reflection

This project was a good way to get acquainted with the different algorithms that
we will be working with throughout the semester. However, I must admit that the
carousel setup was pretty confusing at first. As far as the choice of algorithms
goes, the selection is good. They each require a slightly different approach to
parallelization, which will continue to be useful throughout the semester. For
example, Fibonacci is recursive, Pi is iterative, and the rest are different
types of matrices.
