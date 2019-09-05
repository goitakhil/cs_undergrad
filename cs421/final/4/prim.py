#!/usr/bin/env python3

def prim(V, S):
    key = {}
    parent = {}
    for city in V:
        key[city] = float('inf')
        parent[city] = ''
    key[S] = 0
    city = S
    while V:
        for other in iter(V[city]):
            if other in V:
                if key[city] + V[city][other] < key[other]:
                    key[other] = key[city] + V[city][other]
                    parent[other] = city
        del V[city]
        best = float('inf')
        for name in iter(key):
            if name in V and key[name] != float('inf'):
                if key[name] < best:
                    city = name
                    best = key[name]
    return (key, parent)


if __name__ == '__main__':
    KEY, PARENT = prim({'Constantinople': {'Rayy': 1200, 'Antioch':1500},
                   'Rayy': {'Constantinople': 1200, 'Antioch':1650, 'Samarkand': 500, 'Patalene':1700},
                   'Antioch': {'Rayy': 1650, 'Constantinople':1500},
                   'Samarkand': {'Rayy': 500, 'Kashgar':900, 'Lop Nor': 1400, 'Taxila':1300},
                   'Patalene': {'Rayy': 1700, 'Taxila':950},
                   'Kashgar': {'Samarkand': 900, 'Lop Nor':850},
                   'Taxila': {'Patalene': 950, 'Samarkand':1300, 'Pataliputra': 1150},
                   'Lop Nor': {'Kashgar': 850, 'Samarkand':1400, 'Xian': 1050},
                   'Lhasa': {'Pataliputra': 300, 'Xian':700},
                   'Pataliputra': {'Taxila': 1150, 'Lhasa':300, 'Xian':1250},
                   'Xian': {'Lop Nor': 1050, 'Lhasa':700, 'Pataliputra':1250, 'Guangzhou':1400, 'Hangzhou':1000},
                   'Guangzhou': {'Xian': 1400},
                   'Hangzhou': {'Xian': 1000}}, 'Hangzhou')
    print(KEY)
    print(PARENT)


######## OUTPUT ########
#
#  {'Constantinople': 5150, 'Rayy': 3950, 'Antioch': 5600, 'Samarkand': 3450, 'Patalene': 4100, 'Kashgar': 2900, 
#   'Taxila': 3150, 'Lop Nor': 2050, 'Lhasa': 1700, 'Pataliputra': 2000, 'Xian': 1000, 'Guangzhou': 2400, 'Hangzhou': 0}
#
#  {'Constantinople': 'Rayy', 'Rayy': 'Samarkand', 'Antioch': 'Rayy', 'Samarkand': 'Lop Nor', 'Patalene': 'Taxila', 
#   'Kashgar': 'Lop Nor', 'Taxila': 'Pataliputra', 'Lop Nor': 'Xian', 'Lhasa': 'Xian', 'Pataliputra': 'Lhasa', 
#   'Xian': 'Hangzhou', 'Guangzhou': 'Xian', 'Hangzhou': ''}
#
