#!/usr/bin/env python3
import sys


def gcs(alpha):
    '''Main method of search algo.'''
    alpha = ' ' + alpha  # For 1 based index
    beta = ' ' + alpha[::-1] # The string at index 0 will never be checked
    # array is AxB sized
    results = [[0 for x in range(len(beta))] for x in range(len(alpha))]
    for x in range(1, len(alpha)):
        for y in range(1, len(beta)):
            if alpha[x] == beta[y]:
                results[x][y] = results[x-1][y-1] + 1
            else:
                results[x][y] = max(results[x-1][y], results[x][y-1])
    x = y = len(alpha) - 1
    solution = ''
    while x > 0 and y > 0:
        if results[x][y] > results[x-1][y] and results[x][y] > results[x][y-1]:
            solution = alpha[x] + solution
            x = x - 1
        elif results[x-1][y] > results[x][y-1]:
            x = x - 1
        else:
            y = y - 1
    return solution


if __name__ == '__main__':
    if len(sys.argv) != 2:
        print("Usage: palindrome.py <word>")
    word = sys.argv[1]
    print(gcs(word))
