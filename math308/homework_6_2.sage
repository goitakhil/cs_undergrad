#!/usr/bin/sage
# vi:syntax=python
load('ecclib.sage')

G = [0, 98766778, 12357627687623542975694261]
g = [2539662136869871783297850, 1414894921445613162605806]
gord = 620176035713316419537

message = "Tokyo"
p = 111111756372541867431527
pm = ZZ(math.floor(p / 46))
m1 = ASCIIPad(message, gord)
m = m1[0]


b_alice = [5246859784562186496635824, 5457612259949488195005309]
b_bob = [5278780115068892215096216, 6857888556017625502158912]
b_carol = [11500587084934239979269346, 4630177618473717713077113]
x_alice =  HPSonEC(b_alice,g,G, [[gord,1]])
x_bob = HPSonEC(b_bob,g,G, [[gord,1]])
x_carol = HPSonEC(b_carol,g,G, [[gord,1]])
print (x_alice)
print(x_bob)
print(x_carol)

s = 496418275430782450147
X1 = 522228913141586484647
R = ECTimes(g, x_carol, G)
y1 = mod(R[0],gord)
r = mod(((m - x_carol*y1) / s),gord)
print(r)
S = mod(((m - x_carol*y1)/r),gord)
print(s)
print(S)
verify = s == S
print(verify)

