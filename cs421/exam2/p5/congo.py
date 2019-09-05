#!python3 -x
'''Use a GCS algo to solve the congo problem.'''


def search(alpha, beta):
    '''Main method of search algo.'''
    alpha.insert(0, "")  # For 1 based index
    beta.insert(0, "")  # The string at index 0 will never be checked
    # array is AxB sized
    results = [[0 for x in range(len(beta))] for x in range(len(alpha))]
    for x in range(1, len(alpha)):
        for y in range(1, len(beta)):
            if alpha[x] == beta[y]:
                results[x][y] = results[x-1][y-1] + 1
            else:
                results[x][y] = max(results[x-1][y], results[x][y-1])
    return results[len(alpha)-1][len(beta)-1]

if __name__ == "__main__":
    ALPHA = ["M'Bochi", "Teke", "Kongo", "Ngala", "Kota", "Mongo"]
    BETA = ["Kongo", "Kota", "M'Bochi", "Mongo", "Ngala", "Teke"]
    print(search(ALPHA, BETA))

######################################################################
# OUTPUT:
#
# 3
#
#
