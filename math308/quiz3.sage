#!/usr/bin/sage
# vi:syntax=python

load('ecclib.sage')

R1 = 6613123044387128229269740942041896655251189533369656026527218371215360219713313693043486241626211327457111212031888550460493
p1 = 81321110693270343633073697475490711454793026610211314645065327
q1 = R1 / p1
n1 = 223
print("p")
print(p1)
print("q")
print(q1)
print(p1*q1 == R1)
pout = lcm(p1-1,p1+1)
qout = lcm(q1-1,q1+1)
gR1 = lcm(pout,qout)
print("Gamma R1")
print(gR1)
m = 1/n1 % gR1
print("m")
print(m)

R2 = 289471428810261456415567094628397551571673398895561257713310531281407326469689662778847142566513008960155999085339
n2 = 59346851610269420023834991268641570299723512284162764985361262149325314812259463687678059995151464103080478058424538431694525990568315236552209429689359101472530047366195607396823297823407339960579448001571739031899866980143105

X = 213257531956542360141062305335790608142092140111307845000411750782450225221733798886377128955023795430916978922167797987070


E1 = (X^2-4) % R1
pointdecryption1 = [X,1]
lucgpdecryption1 = [E1,R1]
decryptedPoint1 = LUCexp(pointdecryption1,m,lucgpdecryption1)
C = decryptedPoint1[0]
print(C)


