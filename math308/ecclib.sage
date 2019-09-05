#!/usr/bin/sage
# vi:syntax=python
import numpy
import itertools
import math
import sympy.ntheory

############################################################################################################################
# For all these procedures we are working with elliptic curve (EC) groups over Z_p where p is a prime number larger than 3.#
# An Elliptic Curve group will be represented by a triple [A,B,p] where                                                    #
#      (1) p is a prime number larger than 3                                                                               #
#      (2) A is the coefficient of x and                                                                                   #
#      (3) B is the constant term in  y^2 = x^3 + A*x + B mod p.                                                           #
#                                                                                                                          #
# A point in the group will be represented as a pair [x,y] where                                                           #
#      (1)  x is the x-coordinate of a solution                                                                            #
#      (2)  y is the y-coordinate of a solution                                                                            #
#      (3)  The identity will be represented by []                                                                         #
############################################################################################################################


def TonSh (a, Prime):
    if kronecker(a, Prime) == -1:
        pass
        print "{0} does not have a square root mod {1}".format(a, Prime)
        return None
    elif Prime % 4 == 3:
        x = power_mod(ZZ(a), ZZ((Prime+1)/4),ZZ(Prime))
        return(x)
    else:
        e = ZZ(0)
        q = ZZ(Prime - 1)
        for j in itertools.count(1):
            if q % 2 == 0:
                e = ZZ(e + 1)
                q = ZZ(q / 2)
            else:
                break
        for i in range(1, 101):
            n = i
            if kronecker(n, Prime) == -1:
                break
        z = power_mod(ZZ(n),ZZ(q),ZZ(Prime))
        y = ZZ(z)
        r = ZZ(e)
        x = power_mod(ZZ(a),ZZ( (q-1)/2),ZZ( Prime))
        b = (a * x ** 2) % Prime
        x = (a * x) % Prime
        for i in itertools.count(1):
            if b == 1:
                break
            else:
                for m in itertools.count(1):
                    t = power_mod(ZZ(b), ZZ(2^m) ,  ZZ(Prime))
                    if t == 1:
                        mm = m
                        break
                t = power_mod(ZZ(y), ZZ(2^(r - mm - 1)),ZZ(Prime))
                y = power_mod(ZZ(t), ZZ(2),ZZ(Prime))
                r = mm
                x = (x * t) % Prime
                b = (b * y) % Prime
        return(x)

def ECSearch (lowerbound, upperbound, Group):
    p = Group[2]
    a = Group[0]
    b = Group[1]
    if (4 * a ** 3 + 27 * b ** 2) % p == 0:
        print "This is not an elliptic curve. "
    else:
        for j in itertools.count(lowerbound):
            if j==lowerbound-1 or j>upperbound or j>upperbound:
                return "not found"
            j=j%p
            if kronecker(j ** 3 + a * j + b, p) == 1:
                x = (j ** 3 + a * j + b) % p
                var('z')
                L=TonSh(x,p)
                y = L
                return([j,y])

def FermatGreat (N):
    if sympy.ntheory.isprime(N):
        if N % 4 == 1:
            Z = two_squares(N)
            #print(Z)
            return(Z)
        else:
            print "{0} is prime, but {1} mod 4 = 3".format(N, N)
    else:
        print "{0} is not prime\n".format(N)

def ECAdd (Point1, Point2, Group):
    a = Group[0]
    b = Group[1]
    p = Group[2]
    if(Point1!=[]):
        x1 = Point1[0]
        y1 = Point1[1]
    if(Point2!=[]):
        x2 = Point2[0]
        y2 = Point2[1]
    if (4 * a ** 3 + 27 * b ** 2) % p == 0:
        print "This is not an elliptic curve. "
    elif Point1 != [] and y1 ** 2 % p != (x1 ** 3 + a * x1 + b) % p:
        print "Point 1 is not on the elliptic curve. \n"
    elif Point2 != [] and y2 ** 2 % p != (x2 ** 3 + a * x2 + b) % p:
        print "Point 2 is not on the elliptic curve. \n"
    else:
        if Point1 == []:
            R = Point2
        elif Point2 == []:
            R = Point1
        else:
            if x1 == x2 and 0 == (y1 + y2) % p:
                R = []
            elif x1 == x2 and y1 == y2:
                R = ECDouble(Point1, Group)
            else:
                s = ((y1 - y2) / (x1 - x2)) % p
                x = (s ** 2 - x1 - x2) % p
                y = (s * (x1 - x) - y1) % p
                R = [x,y]
    return(R)

def ECDouble (Point, Group):
    a = Group[0]
    b = Group[1]
    p = Group[2]
    if Point != []:
        x1 = Point[0]
        y1 = Point[1]
        if (4 * a ** 3 + 27 * b ** 2) % p == 0:
            print "This is not an elliptic curve. "
        elif Point != [] and y1 ** 2 % p != (x1 ** 3 + a * x1 + b) % p:
            print "The point to double is not on the elliptic curve. "
        elif y1 == 0:
            R = []
        else:
            s = mod((3 * x1 ** 2 + a) / y1 / 2, p)
            x = (s ** 2 - 2 * x1) % p
            y = (s * (x1 - x) - y1) % p
            R = [x,y]
    else:
        R = []
    return(R)

def ECInverse (Point, Group):
    if Point == []:
        return(Point)
    else:
        p = Group[2]
        x = Point[0]
        y = Point[1]
        return([x,(p - y) % p])

def ECTimes (Point, scalar, Group):
    ECIdentity=[]
    if Point == ECIdentity or scalar == 0:
        return(ECIdentity)
    else:
        E=EllipticCurve(GF(Group[2]),[Group[0],Group[1]])
        EPoint = E(Point[0],Point[1])
        #print type(EPoint)
        cgret = int(scalar)*EPoint
        #print cgret[0]
        if(cgret[0]==0 and cgret[1]==1 and cgret[2]==0):
            return ECIdentity
        return([cgret[0],cgret[1]])

import multiprocessing
from multiprocessing import Process
from multiprocessing.queues import SimpleQueue
import time
def ECFactor (PointA, PointB, Group, num_threads):
    ECIdentity=[]
    if PointA == ECIdentity or PointB == ECIdentity:
        return(ECIdentity)
    else:
        E=EllipticCurve(GF(Group[2]),[Group[0],Group[1]])
        EPoint = E(PointA[0],PointA[1])
	my_queue = SimpleQueue()
        def run ( begin,EPoint,PointB, my_queue ):
            scalar = begin
            calcpoint = scalar*EPoint
            testpoint = [calcpoint[0], calcpoint[1]]
            while testpoint != PointB and my_queue.empty():
                scalar += num_threads
                calcpoint = scalar*EPoint
                testpoint = [calcpoint[0], calcpoint[1]]
            my_queue.put(scalar)
        thread_list = []
        for index in range(num_threads):
            thread_list.append(Process(target=run, args=(index+2,EPoint,PointB,my_queue)))
            thread_list[index].start()
        while my_queue.empty():
            time.sleep(1)
        retval = my_queue.get()
        return(retval)


def listproduct (primelist):
    x = primelist
    k = len(primelist)
    #print k
    result = 1
    for i in range(0, k):
        result = result * x[i][0] ^ x[i][1] #op(1, op(i, x)) ** op(2, op(i, x))
    return(result)

'''def listptorder (pt, Group, factoredGpOrder):
    ECIdentity = []
    x = factoredGpOrder
    k = len(factoredGpOrder)
    orderlist = set([])
    result = listproduct(factoredGpOrder)
    for i in range(0, k ):
        p =  x[i][0] #op(1, op(i, x))
        a =  x[i][1] #op(2, op(i, x))
        ord = 0
        for j in range(0, a ):
            result = result / p
            test = ECTimes(pt, result, Group)
            if test != ECIdentity:
                result = result * p
                ord = ord + 1
        if 0 < ord:
            orderlist = orderlist | set([[p,ord]])
    return(convert(orderlist, list))'''

def listptorder (pt, Group, factoredGpOrder):
    ECIdentity = []
    x = factoredGpOrder
    k = len(factoredGpOrder)
    orderlist = []#set([])
    result = listproduct(factoredGpOrder)
    for i in range(0, k ):
        p =  x[i][0] #op(1, op(i, x))
        a =  x[i][1] #op(2, op(i, x))
        ord = 0
        for j in range(0, a ):
            try:
                #print result / p
                result = ZZ(result / p)
                test = ECTimes(pt, result, Group)
                if test != ECIdentity:
                    result = result * p
                    ord = ord + 1
            except:
                pass
        if 0 < ord:
            orderlist.append([p,ord])
    #print orderlist
    returnlist = []
    return orderlist#(convert(orderlist, list))

#def primeHPSonEC (y,g,Group,p):
#    newg = []#ECIdentity
#    for j in range(0, p ):
#        if y == newg:
#            break
#        newg = ECAdd(newg, g, Group)
#    return(j)
#
#
#def powerHPSonEC (y,g,og,p,a,Group):
#    gp = ECTimes(g, og / p, Group)
#    newog = og
#    newy = y
#    c = 0
#    partx = 0
#    for i in range(0, a ):
#        newy = ECAdd(y, ECInverse(ECTimes(g, partx, Group), Group), Group)
#        newog = newog / p
#        ypower = ECTimes(newy, newog, Group)
#        c = primeHPSonEC(ypower, gp, Group, p)
#        partx = partx + c * p ** i
#    return(partx)

def primeHPSonEC (y,g,Group,p):
    newg = []
    #for j in range(0, p ):
    for j in itertools.count(0):
        if y == newg:
            break
        #print (newg,g)
        newg = ECAdd(newg, g, Group)
    return(j)


def powerHPSonEC (y,g,og,p,a,Group):
    gp = ECTimes(g, ZZ(og / p), Group)
    newog = og
    newy = y
    c = 0
    partx = 0
    for i in range(0, a ):
        #print (y, ECInverse(ECTimes(g, partx, Group), Group))
        newy = ECAdd(y, ECInverse(ECTimes(g, partx, Group), Group), Group)
        newog = ZZ(newog / p)
        ypower = ECTimes(newy, newog, Group)
        c = primeHPSonEC(ypower, gp, Group, p)
        partx = partx + c * p ** i
    return(partx)

def HPSonEC (y,g,Group,factoredGpOrder):
    X = 0
    Ord = 1
    K = listptorder(g, Group, factoredGpOrder)
    k = len(K)
    ordg = listproduct(K)
    #print K
    for j in range(0, k):
        #print (K,j)
        p = K[j][0] #op(1, op(j, K))
        powerp = K[j][1] #op(2, op(j, K))
        #print powerp
        partx = powerHPSonEC(y, g, ordg, p, powerp, Group)
        #print partx
        if j == 0:
            X = partx
            Ord = p ** powerp
            #print "firstif"
            #print X
            #print Ord
        else:
            #print [X,partx]
            #print [Ord,p^powerp]
            X = crt([X,partx], [Ord,p ^ powerp])
            Ord = Ord * (p ^ powerp)
        #print X
    return(X)

def ASCIIPad(Mess,Mod):
    K = []
    for letter in Mess:
        K.append(ord(letter))
    L = Mod.ndigits()
    l = len(K)
    y = ZZ(math.floor(L/3))
    count = 0
    padded = []
    buffer = ""
    for numChar in K:
        numChar+=100
        buffer+=str(numChar)
        count+=1
        if count==y:
            padded.append(ZZ(buffer))
            buffer = ""
            count = 0
    if len(buffer)>0:
        padded.append(ZZ(buffer))
    return padded

                
def ASCIIDepad(Number):
    N = "";
    n = Number.ndigits() % 3;
    if (n > 0):
        pass
    else:
        L = [((Number - (Number % (1000^i)))/1000^i)%1000 - 100 for i in range(Number.ndigits()/3)];
        for i in range(Number.ndigits()/3):
            N = chr(L[i]) + N;
    return(N);

def ECEmbed (Message, gp, tolerance):
    p = ZZ(math.floor(gp[2] / (tolerance + 1)))
    M = ASCIIPad(Message, p)
    packets = len(M)
    pointlist = [0]*packets
    for j in range(0, packets ):
        N = M[j]
        pointlist[j] = ECSearch(tolerance * N, tolerance * (N + 1) - 1, gp)
    return(pointlist)

def ECUnembed (pointlist, tolerance):
    k = len(pointlist)
    paddedasciilist=[0]*k
    for j in range(0, k ):
        pointlist[j][0]=ZZ(pointlist[j][0])
        toType = ZZ(QQ((pointlist[j][0])/tolerance).floor())
        paddedasciilist[j] = ((pointlist[j][0])/tolerance).floor()
    returnStr = ""
    for paddedItem in paddedasciilist:
        buffer = ASCIIDepad(paddedItem)
        returnStr+= buffer
    return returnStr

def findptOrder(point,group):
    E = EllipticCurve(GF(group[2]),[group[0],group[1]])
    Ep = E(point[0],point[1])
    return Ep.additive_order()

def GroupOrder(Group):
    E = EllipticCurve(GF(Group[2]),[Group[0],Group[1]])
    return E.order()

def BSGS (pt, Qt, gp):
    E = EllipticCurve(GF(gp[2]),[gp[0],gp[1]])
    N = E.order()
    m = sqrt(N).round() + 1
    BSL = [0]*(m+1)
    GSL = [0]*(m+1)
    BSL[0] = pt
    for j in range(1, m+1):
        BSL[j] = ECTimes(pt, j, gp)
        #print BSL[j]
    pt2 = BSL[m]
    GSL[0] = pt2
    for j in range(1, m+1):
        #print pt2
        #print j
        #print gp
        #print ECTimes(pt2, j, gp)
        GSL[j] = ECAdd(Qt, ECInverse(ECTimes(pt2, j, gp), gp), gp)
    for i in range(1, m+1 ):
        for j in range(1, m+1):
            if BSL[i] == GSL[j]:
                #print (i,BSL)
                #print (j,GSL)
                DL = (i + j * m) % N
                print "Discrete log is {0}".format(DL)
                return DL

def dconstruct43(q):
    counter = 1
    while True:
        p=4*counter*q-1
        if is_prime(p):
            return p
        counter=counter+1

def dconstruct32(q):
    counter = 1
    while True:
        p=6*counter*q-1
        if is_prime(p):
            return p
        counter=counter+1


def Rhof(point,g,y,Group,j):
    if point!=[]:
        pointMod3 = ZZ(mod(point[0],3))#point[0] % 3
        if pointMod3 == 0:
#            if j<15:
#                print "in mod3==0"
            pt = ECAdd(point,g,Group)
        elif pointMod3 == 1:
#            if j<15:
#                print "in mod3==1"
            pt = ECDouble(point,Group)
        else:
#            if j<15:
#                print "in mod3==2"
            pt = ECAdd(point,y,Group)
#        if j < 15:
#            print "this is the point {0}".format(pt)
        return pt
    return None

def Rhofcoef(a,b,point,gorder):
    a1 = a
    b1 = b
#    print (a,b,point,gorder)
    if point!=[]:
        pointMod3 = ZZ(mod(point[0],3))#point[0] % 3
#        print pointMod3
        if pointMod3 == 0:
#            print "Rhofcoef 0"
            a1 = ZZ(mod(a1+1,gorder))
#            a1=a1+1 % gorder
        elif pointMod3 == 1:
#            print "Rhofcoef 1"
            a1=(2*a1) % gorder
            b1=(2*b1) % gorder
        else:
#            print "Rhofcoef 2"
            b1 = (b1+1) %gorder
        return [a1,b1]

def RhoOnEC(y,g,Group,gorder):
    debug = False
    # To solve y = x*g in Group
    # Set initial z and t values
    z=g
    t=g
    # initial values of a and b
    # and A and B
    ab = [1,0]
    AB = [1,0]
    #This is floyd's cycle-finding method
    foundOne = False
    for j in itertools.count(1):
        if j>2425 and debug:
        #if j>2525 and debug:
            print "top of the loop {0}".format(j)
            print ab
            print AB
        ab = Rhofcoef(ab[0],ab[1],z,gorder)
        z = Rhof(z,g,y,Group,j)
        #if j<15 and debug:
        #if j>2525 and debug:
        #    print "top ab z {0},{1}".format(ab,z)
        if z==[]:
            d = gcd(ab[1],gorder)
            x = ZZ(mod(ab[0]/(-ab[1]), (gorder/d)))
            return x
        AB = Rhofcoef(AB[0],AB[1],t,gorder)
        t = Rhof(t,g,y,Group,j)
        #if j<15 and debug:
        #if j>2525 and debug:
        #    print "middle AB t {0},{1}".format(AB,t)
        if t==[]:
            d = gcd(AB[1],gorder)
            x = ZZ(mod(AB[0]/(-AB[1]), (gorder/d)))
            return x
        AB = Rhofcoef(AB[0],AB[1],t,gorder)
        t = Rhof(t,g,y,Group,j)
        #if j<15 and debug:
        #if j>2525 and debug:
        #    print "bottom AB t {0},{1}".format(AB,t)
        if j==2458 and debug:
            print t
            print z
            print z==t
        if t==[]:
            d = gcd(AB[1],gorder)
            x = ZZ(mod(AB[0]/(-AB[1]) , (gorder/d)))
            return x
        elif z==t:
            d = gcd(AB[1]-ab[1],gorder)
            x =ZZ(mod((ab[0]-AB[0])/(AB[1]-ab[1]),(gorder/d)))
            #print "returned from elif {0}".format(j)
            #print t
            #print z
            #print j
            #print AB
            #print ab
            #print d
            #print (ab[0]-AB[0])/(AB[1]-ab[1])
            #print (gorder/d)
            return x
def IsIn(pt, lucgp):
    if(((pt[0]^2-lucgp[0]*(pt[1]^2)) % lucgp[1]) == (4 % lucgp[1])):
        return True
    else:
        return False
    
def LUCIdentity():
    return [2,0]

def LUCInverse(point, lucasgroup):
    return([point[0],lucasgroup[1]-point[1]])

def LUCTimes(pt1, pt2, lucgp):
    if(not IsIn(pt1,lucgp)):
        print "Point 1 is not in the group"
        raise Exception("Point is not in group")
    if(not IsIn(pt2,lucgp)):
        print "Point 2 is not in the group"
        raise Exception("Point is not in group")
    else:
        luctimesx=((lucgp[1]+1)/2)*(pt1[0]*pt2[0]+lucgp[0]*pt1[1]*pt2[1]) % lucgp[1];
        luctimesy=((lucgp[1]+1)/2)*(pt1[0]*pt2[1]+pt1[1]*pt2[0]) % lucgp[1];
        return([luctimesx,luctimesy])

def LUCSquare(pt,lucgp):
    lucsqx=(pt[0]^2-2) % lucgp[1];
    lucsqy=(pt[0]*pt[1]) % lucgp[1];
    lucsqresult=[lucsqx,lucsqy]
    return lucsqresult

def LUCexp(Point,scalar,Group):
    if Point == LUCIdentity() or scalar == 0:
        return LUCIdentity()
    else:
        m = scalar
        pt = Point
        x = LUCIdentity()
        for j in itertools.count(1):
            if m%2==0:
                m = m/2
            else:
                m=(m-1)/2
                x = LUCTimes(x,pt,Group)
            if m==0:
                return x
            else:
                pt = LUCSquare(pt, Group)

def LUCSearch(lower,higher,lucgp):
    ##for j in xrange(lower,higher+1):
    for j in itertools.count(lower):
        if j >= higher:
            print "No point found in range"
            break
        luctester = (( j ^ 2 - 4 ) / lucgp[0]) % lucgp[1]
        #print luctester
        if(kronecker(luctester,lucgp[1])==1):
            foundx=j
            foundy=TonSh(luctester,lucgp[1])
            return [foundx,foundy]
        
def GroupOrder(group):
    d = group[0]
    N = group[1]
    return(N-kronecker(d,N))

def LUCNumberembed(xvalue,Gp,tolerance):
    if ((xvalue+1)*tolerance-1 < Gp[1]):
        pt=LUCSearch(xvalue*tolerance, (xvalue+1)*tolerance-1,Gp)
        #print pt
        return pt
    else:
        print "The embedding interval is too large for the group"

def LUCEmbed(Message,Gp,tolerance):
    AMessage = ASCIIPad(Message,QQ(Gp[1]/(tolerance+1)).floor())
    #print AMessage
    numPackets = len(AMessage)
    pt = [0]*numPackets
    for j in xrange(numPackets):
        N =AMessage[j]
        pt[j]=LUCNumberembed(N,Gp,tolerance)
    return pt
def LUCUnembed(ptlist,tolerance):
    k = len(ptlist)
    X=[0]*k
    for j in xrange(k):
        X[j]=QQ(ptlist[j][0]/tolerance).floor()
    #print X
    outStr=""
    for numInX in X:
        #print numInX
        outStr=outStr+ASCIIDepad(numInX)
    return outStr


def pminone(R,a,rounds):
    b = a - 1
    #debug = 1
    for j in xrange(1,rounds+1):
        b= (ZZ(power_mod(b+1,j,R))-1) %R
        p = gcd(b,R)
        if p==R:
            print "Pollards p-1 attack fails"
            return None
        elif p>1:
            print "Discovered on round {0}".format(j)
            return p
        #if debug<100:
        #    debug+=1
        #    print b
    print "Not found in {0}".format(rounds)
    return None

def isqrt(n):
    return int(floor(sqrt(n)))

def usqrt (n):
    ur = isqrt(n)
    if ur ** 2 < n:
        ur = ur + 1
    return(ur)

def FermatAttack (n, rounds):
    st = usqrt(n)
    x = st
    while x < st + rounds + 1:
        sq = x ** 2 - n
        y = isqrt(sq)
        if y ** 2 == sq:
            print "Factor found in round {0}".format(x - st +1)
            return ZZ(x+y)
        x += 1
    print "No factor found in {0} rounds".format(rounds)


def ModSolver(a,b,n):
    testxgcd = xgcd(a,n)
    if testxgcd[0]!=1:
        print "a and n are not coprime"
        return None
    ainv = testxgcd[1]
    return (ainv*b)%n

def ISAttack (R):
    n = R.ndigits()
    #n = len(R)
    for j in range(1, n + 1):
        x=(R-(R % 10^j))/10^j
        p = gcd(x, R)
        if ((1 < p)and (p<R)):
            return(p)
    print "nonefound"

