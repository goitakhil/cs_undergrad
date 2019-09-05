#!/usr/bin/sage
# vi:syntax=python

load('ecclib.sage')

def decrypt(X, R, m):
    E = (X^2-4) % R
    pointdecryption = [X,1]
    lucgpdecryption = [E,R]
    decryptedPoint = LUCexp(pointdecryption,m,lucgpdecryption)
    MTest = decryptedPoint[0]
    return(MTest)

def generate_key(p, q, n):
    pout = lcm(p-1,p+1)
    qout = lcm(q-1,q+1)
    gR = lcm(pout,qout)
    m = (1/n) % gR
    return(m)

def pminone(R,a,rounds):
    b = a - 1
    for j in xrange(1,rounds+1):
        b= (ZZ(power_mod(b+1,j,R))-1) %R
        p = gcd(b,R)
        if p==R:
            print "Pollards p-1 attack fails"
            return None
        elif p>1:
            print "Discovered on round {0}".format(j)
            return p
    print "Not found in {0}".format(rounds)
    return None

def pplusone(R,a,rounds):
    b = a - 1
    for j in xrange(1,rounds+1):
        b= (ZZ(power_mod(b+1,j,R))+1) %R
        p = gcd(b,R)
        if p==R:
            print "Pollards p+1 attack fails"
            return None
        elif p>1:
            print "Discovered on round {0}".format(j)
            return p
    print "Not found in {0}".format(rounds)
    return None

R = range(8)
R[0] = 727468521380627075421655946146400319649932050690940643626462979690283308382068774868599846250076243494218960006026353918445607
R[1] = 1776222827980492993771334381150032087090733524260653865593742223755886420566626642407849784330860137239174236264426548811
R[2] = 353493176119259912349429246864456983991021758039484719285373960426349880403004239603710849591435484879173730493070091401900539
R[3] = 89051143763130391327502113892284802685583148099839639981122345341003774404719972877138555357067737261264657451226133284482282041335838611491477
R[4] = 10776368768076264861189964106719638304647912315507826914514211010954490565895707255589798224186078607204076155433576119712793
R[5] = 694477005501016310693522229469437361549333719801739705949921462978584498363044118734974059491690066863782712281519
R[6] = 1154895606746144265857222382219264208677618063898424979601990128905549462742808166688705181714496450887978280748587383679818162413151
R[7] = 298753458799982729386889912919210697976187529120331896122328861485879697810094178540964351702486961399026742618287754170831

M = range(8)
M[0] = 78894567358732952989400006836398809039013289969467066351658936577491214598980207070365609592196682316322212670101379049322388762309633776774672
M[1] = 9860916289735534852529079684998686872815138545062309551709505569843095081843139482462763405126885924883991229310020154401091235529112195964751
M[2] = 910214053038303699411295500302494903410324063977945239304737405206080502260853189982054592157223882089865898812659054395762
M[3] = 448504219812150295698661810346856374917764674606615078845748829236481183842816869577634130887363258150592601453499718107283551
M[4] = 309001085102179097478460508319636689098713698099575980558556548126054530638993166470078246850798144002707926399463985474664587
M[5] = 699969889056971099397738781537078828670644159384068292410235979698809992602376568241706161762255483250067396100835462051558254158966
M[6] = 39156953506270645709234420855601419836179468365715295604667944750438031050456818696667825166518855026962710243735864566274
M[7] = 892746144557148500028590351598713700334497786778201375481251813921546033507710388880773056433279017005443899196758288724279770681194 

n = range(8)
n[0] = 797
n[1] = 809
n[2] = 811
n[3] = 821
n[4] = 1
n[5] = 12609106174370160498410220267363743604503045621810923607795149264369376683139675328652901636215450415571901766038056937462454071057291956284012113470791721742511545670265580779338080102851927066027359362364494034153259318572033
n[6] = 823
n[7] = 827

p = range(8)
q = range(8)
for i in R:
    for j in R:
        value = gcd(i,j)
        if value != 1 and i != j:
            print(i)
            print(j)
            print(value)
## The following were found using the above gcd attack.
p[0] = 670169458329325078914859772750118944981043771828312321897737
q[0] = R[0] / p[0]
p[3] = 1723288926990495594896199222260134510602978304026522284948965006459779623
q[3] = R[3] / p[3]
p[6] = 670169458329325078914859772750118944981043771828312321897737
q[6] = 1723288926990495594896199222260134510602978304026522284948965006459779623

#ISAttack(R[4])
p[4] = 89060898909721197199917058733220151278081919962874602599291
q[4] = R[4]/p[4]
print("Q")
print(q[4])

#FermatAttack(R[2],100000000)
#Factor found in round 321
p[2] = 594552921209928891515242515621559395947147422118742093609216121
q[2] = R[2] / p[2]

m = range(8)
m[0] = generate_key(p[0],q[0],n[0])
m[3] = generate_key(p[3],q[3],n[3])
m[6] = generate_key(p[6],q[6],n[6])
m[2] = generate_key(p[2],q[2],n[2])

#pminone(R[1],int(sqrt(R[1])),10000)
p[1] = 1367332591261460588099491685271967649476841926660556554408753
q[1] = R[1] / p[1]
m[1] = generate_key(p[1],q[1],n[1])
#pminone(R[7],int(sqrt(R[7])),10000)
p[7] = 333937381564236852747214692518378396874614455982294658806962501
q[7] = R[7] / p[7]
m[7] = generate_key(p[7],q[7],n[7])

#Iteration attack on R[5]
TESTM = ASCIIPad("THISISATEST",R[5])[0]
G = [(TESTM^2 - 4)%R[5], R[5]]
E = [TESTM,1]
k = 1
Ek = E
while True:
    Ek = LUCexp(Ek,n[5],G)
    k += 1
    if Ek == E:
        break
print(k)

for N in range(1):
    try:
        m[4] = generate_key(p[4],q[4],(N+1))
        print(N + 1)
        for i in range(8):
            for j in range(8):
                try:
                    thestring = ASCIIDepad(decrypt(decrypt(M[i], R[j], m[j]), R[4], m[4]))
                    if thestring != '':
                        print(i, j, (N+1), thestring)
                except:
                    pass
                try:
                    thestring = ASCIIDepad(decrypt(decrypt(M[i], R[4], m[4]), R[j], m[j]))
                    if thestring != '':
                        print(i, j, (N+1), thestring)
                except:
                    pass
    except:
        pass


print("keys")
for key in m:
    print(key)
    print(" ")



for i in range(8):
    for j in range(8):
        for k in range(8):
            try:
                thestring = ASCIIDepad(decrypt(decrypt(M[i], R[j], m[j]), R[k], m[k]))
                if thestring != "" and thestring != None:
                    print(i, j, k, thestring)
            except:
                pass

for i in range(8):
    for j in range(8):
        for k in range(50):
            try:
                E = (M[i]^2-4) % R[5]
                lucgpdecryption = [E,R[5]]
                E = LUCExp([M[i],0],n[5]^k,lucgpdecryption)
                thestring = ASCIIDepad(decrypt(E, R[j], m[j]))
                if thestring != "" and thestring != None:
                    print(i, j, k, thestring)
            except:
                pass

for i in range(8):
    for j in range(8):
        for k in range(50):
            try:
                MM = decrypt(M[i],R[j],m[j])
                E = (MM^2-4) % R[5]
                lucgpdecryption = [E,R[5]]
                E = LUCExp([MM,0],n[5]^k,lucgpdecryption)
                thestring = ASCIIDepad(E[0])
                if thestring != "" and thestring != None:
                    print(i, j, k, thestring)
            except:
                pass



