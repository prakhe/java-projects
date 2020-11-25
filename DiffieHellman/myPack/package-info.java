package myPack;

/*

Alice and Bob publicly agree to use a modulus p = 23 and base g = 5 (which is a primitive root modulo 23).
Alice chooses a secret integer a = 4, then sends Bob A = ga mod p
    A = 54 mod 23 = 4
Bob chooses a secret integer b = 3, then sends Alice B = gb mod p
    B = 53 mod 23 = 10
Alice computes s = Ba mod p
    s = 104 mod 23 = 18
Bob computes s = Ab mod p
    s = 43 mod 23 = 18
Alice and Bob now share a secret (the number 18).

Both Alice and Bob have arrived at the same value s, because, under mod p,

A b mod p = g a b mod p = g b a mod p = B a mod p {\displaystyle {\color {Blue}A}^{\color {Red}b}{\bmod {\color {Blue}p}}={\color {Blue}g}^{\color {Red}ab}{\bmod {\color {Blue}p}}={\color {Blue}g}^{\color {Red}ba}{\bmod {\color {Blue}p}}={\color {Blue}B}^{\color {Red}a}{\bmod {\color {Blue}p}}} {\displaystyle {\color {Blue}A}^{\color {Red}b}{\bmod {\color {Blue}p}}={\color {Blue}g}^{\color {Red}ab}{\bmod {\color {Blue}p}}={\color {Blue}g}^{\color {Red}ba}{\bmod {\color {Blue}p}}={\color {Blue}B}^{\color {Red}a}{\bmod {\color {Blue}p}}}

More specifically,

( g a mod p ) b mod p = ( g b mod p ) a mod p {\displaystyle ({\color {Blue}g}^{\color {Red}a}{\bmod {\color {Blue}p}})^{\color {Red}b}{\bmod {\color {Blue}p}}=({\color {Blue}g}^{\color {Red}b}{\bmod {\color {Blue}p}})^{\color {Red}a}{\bmod {\color {Blue}p}}} {\displaystyle ({\color {Blue}g}^{\color {Red}a}{\bmod {\color {Blue}p}})^{\color {Red}b}{\bmod {\color {Blue}p}}=({\color {Blue}g}^{\color {Red}b}{\bmod {\color {Blue}p}})^{\color {Red}a}{\bmod {\color {Blue}p}}}

Note that only a, b, and (gab mod p = gba mod p) are kept secret. All the other values – p, g, ga mod p, and gb mod p – are sent in the clear. Once Alice and Bob compute the shared secret they can use it as an encryption key, known only to them, for sending messages across the same open communications channel.import

*/