#!python3 -x
'''Main file for count script.'''
from typing import List


def count(A: List[int], x: int, l: int, u: int):
    '''Return the number of values in A that have an abs of x.'''
    if A[l] > x or A[u] < -x or u < l:
        return 0
    median = (l+u)//2
    if abs(A[median]) == x:
        if l == u:
            return 1
        else:
            return 1 + count(A, x, l, median-1) + count(A, x, median+1, u)
    else:
        return count(A, x, l, median-1) + count(A, x, median+1, u)


if __name__ == "__main__":
    print(count([-6, -5, 2, 4, 5], 5, 0, 4))

###################################################################
# OUTPUT:
#
# 2
#
#
