#!/usr/bin/sage
# vi:syntax=python

load('ecclib.sage')

R1 = 6613123044387128229269740942041896655251189533369656026527218371215360219713313693043486241626211327457111212031888550460493
n1 = 223

R2 = 289471428810261456415567094628397551571673398895561257713310531281407326469689662778847142566513008960155999085339
n2 = 59346851610269420023834991268641570299723512284162764985361262149325314812259463687678059995151464103080478058424538431694525990568315236552209429689359101472530047366195607396823297823407339960579448001571739031899866980143105

def isqrt(n):
    return int(floor(sqrt(n)))

def usqrt (n):
    ur = isqrt(n)
    if ur ** 2 < n:
        ur = ur + 1
    return(ur)

def FermatAttack (n, rounds):
    st = usqrt(n)
    for x in range(st, st + rounds + 1):
        #print (x-st)
        sq = x ** 2 - n
        y = isqrt(sq)
        if y ** 2 == sq:
            print "Factor found in round {0}".format(x-st+1)
            return(x + y)
    print "No factor found in {0} rounds".format(rounds)
print(FermatAttack(R1,100000000))
