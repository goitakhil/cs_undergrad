#!/usr/bin/env python3


def extend_sp(L, W):
    n = len(L)
    LP = [[float("inf") for x in range(len(L))] for x in range(len(L))]
    for i in range(n):
        for j in range(n):
            for k in range(n):
                if LP[i][j] > L[i][k] + W[k][j]:
                    LP[i][j] = L[i][k] + W[k][j]
    return LP


def faster_all_pairs_shortest_path(W):
    printarray(W)
    print()
    n = len(W)
    L = W.copy()
    m = 1
    while m < n:
        L = extend_sp(L, L)
        printarray(L)
        print()
        m = 2*m
    return L


def printarray(W):
    for x in range(len(W)):
        for y in range(len(W[x])):
            print("%.1f" % W[x][y], end='\t')
        print()


if __name__ == "__main__":
    input_array = [[0, 4.6, float('inf'), float('inf'), float('inf'), 1.2, float('inf'), float('inf'), float('inf'), float('inf'), float('inf')],
                   [4.6, 0, 1.4, float('inf'), float('inf'), float('inf'), float('inf'), 7.1, float('inf'), float('inf'), float('inf')],
                   [float('inf'), 1.4, 0, 2.4, float('inf'), float('inf'), float('inf'), float('inf'), 4.7, float('inf'), float('inf')],
                   [float('inf'), float('inf'), 2.4, 0, 1.3, float('inf'), float('inf'), float('inf'), float('inf'), 7.5, 5.3],
                   [float('inf'), float('inf'), float('inf'), 1.3, 0, float('inf'), float('inf'), float('inf'), float('inf'), float('inf'), 3.3],
                   [1.2, float('inf'), float('inf'), float('inf'), float('inf'), 0, 2.3, float('inf'), float('inf'), float('inf'), float('inf')],
                   [float('inf'), float('inf'), float('inf'), float('inf'), float('inf'), 2.3, 0, 5, float('inf'), float('inf'), float('inf')],
                   [float('inf'), 7.1, float('inf'), float('inf'), float('inf'), float('inf'), 5, 0, float('inf'), float('inf'), float('inf')],
                   [float('inf'), 5.2, 4.7, float('inf'), float('inf'), float('inf'), float('inf'), float('inf'), 0, 3.4, float('inf')],
                   [float('inf'), float('inf'), float('inf'), 7.5, float('inf'), float('inf'), float('inf'), float('inf'), 3.4, 0, float('inf')],
                   [float('inf'), float('inf'), float('inf'), 5.3, 3.3, float('inf'), float('inf'), float('inf'), float('inf'), float('inf'), 0]]
    faster_all_pairs_shortest_path(input_array)

