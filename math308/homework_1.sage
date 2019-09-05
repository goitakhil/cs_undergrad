#!/usr/bin/sage
# vi:syntax=python

load('ecclib.sage')


Source = [83,62]
Target = [20,8]
k = 0
while not Source == Target:
    Result = ECTimes(Source, k, [-1, 17233, 101])
    if Result == Target:
        break
    else:
        k = k+1
print(k)

ECTimes([83,62], 17, [-1, 17233, 101])

Curve = [-1,17233,6245249]
A = [706,1640118]
B = [456049,6134143]
C = [2645655,3090987]
m = 0
n = 0

while A != B:
    Result = ECTimes(A, m, Curve)
    if Result == B:
        break
    else:
        m = m+1
print(m)
ECTimes(A, m, Curve)
while A != C:
    Result = ECTimes(A, n, Curve)
    if Result == C:
        break
    else:
        n = n+1
print(n)
ECTimes(A, n, Curve)



