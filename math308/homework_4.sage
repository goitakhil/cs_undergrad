#!/usr/bin/sage
# vi:syntax=python

load('ecclib.sage')

# Save the public parameters of the key:
a = 11
b = 0
p = 9391546732542682343517982574452432123375423148831231833
G = [a,b,p]
g = [6, 4068173955386537864636777717511564212686626732453583757]
b = [4738707718120884309934578832298692960185917019491370431, 8153844566970069025957602614920356671822028131466836715]
# Private key x is unknown.
tolerance = 49

# Factor the order of the base point g:
print ( factor(findptOrder(g, G)) )

# Use HPS attack on private key:
x = HPSonEC( b, g, G, [[2,1],[3,1],[5,1],[95413,1],[3102289,1],[78157181,1],  [46026701026207183804925356300601,1]] )
print(x)

# Message Parts:
e = [8727765826926616629903551867363928947229953111805478622, 9332940635772386573797521463619147639103658358606390644]
c = [8389806687819893018721601307473411624042849667898952265, 2995295352806551346623756890208740012459432734816854674]

# Compute decoding point:
key = ECTimes(c,x,G)

# Decode message with generated key:
message = ECUnembed( [ECAdd( e,ECInverse(key,G),G )],tolerance )
print(message)
