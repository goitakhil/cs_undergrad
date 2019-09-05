#!/bin/gprolog --consult-file

:- include('data.pl').
:- include('uniq.pl').
    
eq(A,B,  Y,Z) :- A =:= Y, B =:= Z.
lt(A,B,  Y,Z) :- A < Y ; A =:= Y, B < Z .
gt(A,B,  Y,Z) :- A > Y ; A =:= Y, B > Z .


overlaps(A,B,C,D,  W,X,Y,Z) :- eq(A,B,W,X), eq(C,D,Y,Z).
overlaps(A,B,C,D,  W,X,Y,Z) :- lt(A,B,W,X), eq(C,D,Y,Z).
overlaps(A,B,C,D,  W,X,Y,Z) :- eq(A,B,W,X), gt(C,D,Y,Z).
overlaps(A,B,C,D,  W,X,Y,Z) :- lt(A,B,W,X), gt(C,D,Y,Z).

meetone(Person, slot(time(W,X),time(Y,Z))) :- free(Person, slot(time(A,B),time(C,D))), overlaps(A,B,C,D,  W,X,Y,Z) .

    
main :- findall(Person, meetone(Person,slot(time(8,30),time(8,45))), People),
	uniq(People,UNIQ), write(UNIQ), nl, halt.

:- initialization(main).
