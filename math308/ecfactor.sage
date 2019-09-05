#!/usr/bin/sage
# vi:syntax=python

load('ecclib.sage')

a = 7197467384693462796497469
b = 745697856874569824735
p = 73657856349756784653478657834659847568497356973891
G = [a,b,p]
g = [773475695697567, 52275679192332504817994914354216048998968408414764]
#b = [41030834640932917983103424176233916857128023084843, 51714466241536028944899866338920447412690190650854]
x = 229691
b = ECTimes(g,x,G)

#Group = G
#PointA = g
#PointB = b
#E=EllipticCurve(GF(Group[2]),[Group[0],Group[1]])
#EPoint = E(PointA[0],PointA[1])
#scalar = 123
#result = scalar*EPoint
#testresult = [result[0], result[1]]
#print(result)
#print(testresult)
#print(PointB)
#print(testresult == PointB)

result = ECFactor(g,b,G,4)
print('Result: {:d}'.format(result))
