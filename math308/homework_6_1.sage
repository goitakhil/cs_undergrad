#!/usr/bin/sage
# vi:syntax=python
load('ecclib.sage')

def main():
    # Save the public parameters of the key:
    a = 32423423
    b = 0
    p = 111111756372541867431527
    G = [a, b, p]
    g = [133213, 42904742913851518927444]
    b_alice = [77012625572753817011635, 107177820655281658062820]
    b_bob = [63567155336615021877739, 36007178347937208049611]
    b_carol = [104225718616102818923406, 48710727653703914714853]
    # Private keys x is unknown.
    tolerance = 45

    # Factor the order of the base point g:
    print(factor(findptOrder(g, G)))

    c = [64822910729849959453845, 54324913307643112006572]
    y = [40965317570056334177487, 76589794174623293315486]
    multnum = 2 * 13 * 17 * 89 * 211 * 839 * 466637
    gg = ECTimes(g, multnum, G)
    ggord = findptOrder(gg, G)
    bb_alice = ECTimes(b_alice, multnum, G)
    x_alice = RhoOnEC(bb_alice, gg, G, ggord)
    print(x_alice)
    bb_bob = ECTimes(b_bob, multnum, G)
    x_bob = RhoOnEC(bb_bob, gg, G, ggord)
    print(x_bob)
    bb_carol = ECTimes(b_carol, multnum, G)
    x_carol = RhoOnEC(bb_carol, gg, G, ggord)
    print(x_carol)

    # Use HPS attack on random number:
    r = HPSonEC(y, g, G, [[2, 1], [13, 1], [17, 1], [89, 1],
                          [211, 1], [839, 1], [466637, 1], [949777, 1]])
    print(r)

    # Compute decoding point:
    key = ECTimes(b_alice, r, G)

    # Decode message with generated key:
    message = ECUnembed([ECAdd(c, ECInverse(key, G), G)], tolerance)
    print(message)

main()

