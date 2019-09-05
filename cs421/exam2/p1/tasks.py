#!python -x
'''Use a greedy approach to select the most profitable list of tasks.'''


def choose_tasks(alpha):
    '''Greedy search for best tasks.'''
    results = []
    time = 1
    done = False
    while not done:
        best = {}
        for task in alpha:
            if task["deadline"] >= time:
                if not best or task["deadline"] < best["deadline"]:
                    best = task
                elif task["deadline"] == best["deadline"]:
                    if task["value"] > best["value"]:
                        best = task
        if not best:
            done = True
        else:
            results.append(best)
            alpha.discard(best)
            time += 1
    return results


if __name__ == "__main__":
    RESULT = choose_tasks([{"name": "A", "deadline": 5, "value": 20},
                           {"name": "B", "deadline": 2, "value": 30},
                           {"name": "C", "deadline": 1, "value": 25},
                           {"name": "D", "deadline": 1, "value": 15},
                           {"name": "E", "deadline": 3, "value": 35}])
    for item in RESULT:
        print("{} ".format(item.name), end=" ")
    print("")

###########################################################################
# OUTPUT:
#
#    C B E A
#
#
