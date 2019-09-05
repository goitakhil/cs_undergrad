#!/usr/bin/sage
# vi:syntax=python 

load('ecclib.sage')

#G = [389274768686887, 0,next_prime(8233978240328740928149873298479238749764872364786237842368)]
#a = 389274768686887 
#b = 0
#p = next_prime(8233978240328740928149873298479238749764872364786237842368); 
#g = ECSearch(3762, 3892479238, G)
#
#x = 2955242456917393069396242159271417318326709398811666998321
#b = ECTimes(g, x, G)
#print (HPSonEC(b, g, G, [[17, 1], [101, 1], [113, 1], [396049377410916615953448084370921542977, 1], [15462217, 1], [3465053, 1]]) )
#

a = 11
b = 0
p = 9391546732542682343517982574452432123375423148831231833
G = [a,b,p]
g = [6, 4068173955386537864636777717511564212686626732453583757]
b = [4738707718120884309934578832298692960185917019491370431, 8153844566970069025957602614920356671822028131466836715]
# X is unknown.
tolerance = 49

print ( factor(findptOrder(g, G)) ) 
x = HPSonEC( b, g, G, [[2,1],[3,1],[5,1],[95413,1],[3102289,1],[78157181,1],[46026701026207183804925356300601,1]] )
print(x)



# 2 * 3 * 5 * 95413 * 3102289 * 78157181 * 46026701026207183804925356300601
# 22969884943457897133733574583698098235436938923227263


# Order of G must have large prime factor

# Supersingular:
# y^2=x^3 + B mod P
# if order of group = P+1
# y^2=x^3 + Ax mod P
# if P mod 4 = 3



# 1) Choose large prime q then ,using the Dirichlet theorem for u=2q, v=-1
#     2q-1 , 2(2q)-) , 3(2q)-1
#     Contains infinitely many primes
# 2) Search for a prime of the form
#     P = n(2q)-1
#     and either P mod 3 = 2 OR P mod 4 = 3
# 3) if P mod 3 = 2 then:
#        Curve = y^2=x^3 + B mod P
#    if P mod 4 = 3 then:
#        Curve = y^2=x^3 + Ax mod P
# 4) Pick a point g on the curve
#        such that order of g = q
# 5) Choose a private key using Chinese remainder theorem
#        C0 = largest factor of Curve, which is q
#        C1 = 0 or any number less than one of the "small" primes in order of curve
#        And so on, depending on the number of prime factors in (P+1)
#      x = crt([Ci,Ci-1,...,C0], [prime 1, prime 2, ... , q])   #( i is number of primes in P+1)


