#!/bin/gprolog --consult-file

:- include('data.pl').
:- include('uniq.pl').

people([ann,bob,carla]).

eq(A,B,  Y,Z) :- A =:= Y, B =:= Z.
lt(A,B,  Y,Z) :- A < Y ; A =:= Y, B < Z .
gt(A,B,  Y,Z) :- A > Y ; A =:= Y, B > Z .


overlaps(A,B,C,D,  W,X,Y,Z) :- eq(A,B,W,X), eq(C,D,Y,Z).
overlaps(A,B,C,D,  W,X,Y,Z) :- lt(A,B,W,X), eq(C,D,Y,Z).
overlaps(A,B,C,D,  W,X,Y,Z) :- eq(A,B,W,X), gt(C,D,Y,Z).
overlaps(A,B,C,D,  W,X,Y,Z) :- lt(A,B,W,X), gt(C,D,Y,Z).

possibletime(TargetHour, TargetMinute) :- free(_, slot(time(TargetHour, TargetMinute),_)).
possibletime(TargetHour, TargetMinute) :- free(_, slot(_,time(TargetHour, TargetMinute))).

possibleslot((StartHour,StartMinute, EndHour, EndMinute)) :- possibletime(StartHour,StartMinute), possibletime(EndHour,EndMinute), lt(StartHour,StartMinute,EndHour,EndMinute).

canmeet((A,B,C,D), [Head|Tail]) :- free(Head, slot(time(FSHour,FSMin),time(FEHour,FEMin))), overlaps(FSHour,FSMin,FEHour,FEMin,  A,B,C,D) , canmeet((A,B,C,D), Tail) .
canmeet(_, []).

meet(Slot) :- people(LIST), possibleslot(Slot), canmeet(Slot, LIST).

main :- findall(Slot, meet(Slot), Slots),
        uniq(Slots, Uniq),
        write(Uniq), nl, halt.

:- initialization(main).
