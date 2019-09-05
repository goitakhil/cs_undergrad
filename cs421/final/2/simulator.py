#!python -x
'''Use a greedy approach to select the shortest valid configuration.'''


def schedule(alpha):
    '''Greedy search for best order.'''
    time = 1
    done = False
    while alpha:
        lowest = float('inf')
        best = None;
        for person in alpha:
            if time >= person['start'] and time < person['end']:
                best = person
                break
        if best != None:
            alpha.remove(best)
            print('From {} to {} = {}'.format(time, time+1, best['name']))
        time += 1


if __name__ == '__main__':
    schedule([{'name': 'Earhart',      'start': 1, 'end': 4},
              {'name': 'Lindbergh',    'start': 3, 'end': 5},
              {'name': 'Doolittle',    'start': 4, 'end': 6},
              {'name': 'Rickenbacker', 'start': 8, 'end': 11},
              {'name': 'Yeager',       'start': 10, 'end': 12},
              {'name': 'Richthoven',   'start': 2, 'end': 14},
              {'name': 'Fossett',      'start': 3, 'end': 7},
              {'name': 'Bleriot',      'start': 12, 'end': 15}])

