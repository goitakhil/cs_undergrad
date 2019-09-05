#!/usr/bin/env python3

def extend_sp(L, W):
    n = len(L)
    LP = [[float("inf") for x in range(len(L))] for x in range(len(L))]
    for i in range(n):
        for j in range(n):
            for k in range(n):
                LP[i][j] = min(LP[i][j], L[i][k] + W[k][j])
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
        print(W[x])

if __name__ == "__main__":
    input_array = [[0, float("inf"), 3, float("inf"), float("inf")],
                   [-4, 0, 2, float("inf"), float("inf")],
                   [float("inf"), -1, 0, 7, 5],
                   [float("inf"), 1, float("inf"), 0, 10],
                   [float("inf"), float("inf"), float("inf"), -8, 0]]
    faster_all_pairs_shortest_path(input_array)

