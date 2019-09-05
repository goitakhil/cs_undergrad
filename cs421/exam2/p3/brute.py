#!python3 -x
'''Brute force approach to finding the number of subsets of a given set
   that are disjoint but have the same sum.abs
'''


def brute(S):
    '''Brute force method on the power set.'''
    power = set()
    for item in combos(S, [], 0, len(S)):
        power.add(item)
    result = set()
    for item1 in power:
        for item2 in power:
            if set(item1).isdisjoint(set(item2)):
                if sum(item1) == sum(item2):
                    if (item2, item1) not in result:
                        result.add((item1, item2))
    return result


def combos(S, L, size, maxsize):
    '''Generate the power set of S recursively.'''
    result = []
    for i in S:
        if i not in L:
            newitem = L.copy()  # Not an exact copy of the algo, because python
            newitem.append(i)   # passes by container reference.
            result.append(tuple(sorted(newitem)))
            result.extend(combos(S, newitem, size+1, maxsize))
    return result


if __name__ == "__main__":
    print(brute([1, 2, 3, 4]))


################################################################
# OUTPUT:
#
# {((1, 4), (2, 3)), ((1, 3), (4,)), ((1, 2), (3,))}
#
#
