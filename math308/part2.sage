#!/usr/bin/sage
# vi:syntax=python
load('ecclib.sage')

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



