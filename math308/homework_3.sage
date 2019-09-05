#!/usr/bin/sage
# vi:syntax=python

load('ecclib.sage')

a = 7197467384693462796497469
b = 745697856874569824735
p = 73657856349756784653478657834659847568497356973891
G = [a,b,p]
g = [773475695697567, 52275679192332504817994914354216048998968408414764]
b = [41030834640932917983103424176233916857128023084843, 51714466241536028944899866338920447412690190650854]
# X is unknown.
tolerance = 46

# r is unknown secret key
# public point from sender g^r:
y = [21478532406087707182410446686356558800777486356489, 62168223452312923224511582969271645946692281765663]
#ciphertext:
e = [ [28175896933920284410497875497848075232743258009281, 39137020745538123348703673067447480372901135807265],
      [55340746490250151431939764254535018875852094143130, 3343168312939476923195751151237875001065778885759],
      [58643339612582841196921146819977335780715888732781, 18615594834659137697333452034629319810499644522415],
      [20507117052795475037368530115389322130668351633985, 33707222248286045948788366487715493416338754436802],
      [54346519059866548231947336760392390900580304679213, 32147152068938714415696414526167746666046404595533]
    ]

k = ECEmbed("nt message pack",G,tolerance)

x = [] #Potential keys
for packet in e:
    x.append(ECAdd(packet,ECInverse(k[0],G),G))

#key = x[0]
key = x[1]
#key = x[2]
#key = x[3]
#key = x[4]

for packet in e:
    print(ECUnembed( [ECAdd( packet,ECInverse(key,G),G )],tolerance ))
print(key)

