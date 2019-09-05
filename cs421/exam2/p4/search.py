#!python3 -x
'''Dynamic Programming approach to a traveling salesman problem.'''


def search(dist, start):
    '''Main function of the algorithm.'''
    # dist is the table of distances.
    # start is the start city
    # returns the list of cities visited in order
    remainder = set([x for x in range(len(dist))])  # Set of remaining cities
    next_city = start  # Initalize next city to the start
    remainder.discard(start)  # remainderemove the start from the target list
    result = [start]  # result contains start point
    while remainder:  # While items remain in set
        lowest = None
        best_dist = float("inf")
        for i in range(len(dist[next_city])):  # Find lowest distance
            if i in remainder and dist[next_city][i] < best_dist:
                lowest = i
                best_dist = dist[next_city][i]
        next_city = lowest
        result.append(next_city)  # Add to result
        remainder.discard(next_city)
    result.append(start)  # result ends with start as well
    return result


if __name__ == "__main__":
    CITIES = ["Mumbai", "Delhi", "Surat", "Kanpur", "Agra"]
    DISTANCES = [[0, 19, 29, 25, 22],
                 [19, 0, 24, 23, 26],
                 [29, 24, 0, 21, 20],
                 [25, 23, 21, 0, 25],
                 [22, 26, 20, 25, 0]]
    RESULT = search(DISTANCES, 0)
    for city in RESULT:
        print("{} ".format(CITIES[city]), end='')
    print("")


###############################################################################
# OUTPUT:
#
# Mumbai Delhi Kanpur Surat Agra Mumbai
#
#
