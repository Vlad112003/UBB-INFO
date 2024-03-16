%returneaza true daca elementul apare in lista

%apare(L-list,E-element)
%(i,i)
apare([E|_],E).
apare([H|T],E):-
    H=\=E,
    apare(T,E).

%face suma elementelor din lista

%suma(L-list,S-suma)
%(i,o)
%(i,i)
suma([],0).
suma([H|T],S):-
    suma(T,S1),
    S is S1+H.


%fact(N, F)

fact(0,1).
fact(N, F):- N>0,
            N1 is N - 1,
            fact(N1, F1),
            F is N * F1.

%parimpar(L - lista, R - rezultat)

parimpar([], 0).
parimpar([H|T], R1):-
         H mod 2 =:= 0,
         !,
         parimpar(T,R),
         R1 is R + H.

parimpar([H|T], R1):-
         parimpar(T,R),
         R1 is R - H.


%difmult(L1 - lista, L2 - lista, R - rezz)
%model matematic
% difmult(l1..ln, L1..Lm) - {
%                             [] daca n = 0
%                             l1..ln daca m = 0
%                             l1 (+) difmult(l2..ln, L1..Lm) daca !apartine(L1..Lm, l1)
%                             difmult(l2..ln, L1..Lm), altfel
%

%apartine(L1 - lista, E - element)
%model matematic
%apartine(l1..ln, E) = {
%                       true daca l1 = E,
%                       false daca n = 0
%                       apartine(l2..ln, E), altfel
%}

apartine([H|_], H):-!.
apartine([_|T], E):-
        apartine(T,E).

difmult([],_, []).
difmult([H|T], [], [H|T]).

difmult([H1|T1], L, [H1|Rez]):-
    \+ apartine(L, H1),
    !,
    difmult(T1, L, Rez).
difmult([_|T1], L, Rez):-
%    apartine(L, H1),
    difmult(T1, L, Rez).


    %face diferenta dintre doua multimi (elementele din a doua, sa nu mai apara in prima)


%adaugapar(L1 - lista, R - rezultat).
%model matematic
%adaugapar(l1..ln){
%                       [], daca n = 0
%                       l1 (+) 1 (+) adaugapar(l2..ln) daca l1 % 2 = 0
%                        adaugapar(l2..ln), altfel

adaugapar([], []).
adaugapar([H|T], [H|R]):-
        H mod 2 =\= 0,
        !,
        adaugapar(T, R).

adaugapar([H|T], [H, 1 | Rez]):-
        adaugapar(T,Rez).


%cmmmc(L - lista, R - rezultat)
%model matematic
%cmmmc(l1..ln) = {
%       l1 daca n = 1
%       l1 * cmmmc(l2..ln) / cmmdc (l1, cmmmc(l2..ln))
%

%cmmcd(a - integer, b - integer)
%model matematic
%cmmdc(a,b) = {
%    a, daca b = 0
%    b, daca a = 0
%    cmmdc(a-b, b), daca a>b
%    cmmdc(a, b-a), altfel

cmmdc(A, 0, A).
cmmdc(0, B, B).
cmmdc(A, B, R):-
       A > B,
       !,
       A1 is A - B,
       cmmdc(A1, B, R).
cmmdc(A, B, R):-
        B1 is B - A,
        cmmdc(A, B1, R).

cmmmc([H], H).
cmmmc([H|T], R):-
        cmmmc(T, R1),
        cmmdc(H, R1, R2),
        R is H * R1 / R2.

%insertposRapper(L - lista, V - integer, R - rezultat)
%model matematic
%insertposRapper(l1..ln, V) = insertpos(l1..ln, V, 1, 1)
%
%
%
%insertpos(l1..ln, V, P, I) = {
%               [], n = 0;
%               l1 (+) v (+) insertpos(l2..ln, V, P*2, I+1), daca P = I.
%               l1 (+) insertpos(l2..ln, V, P, I+1), altfel
%    }

insertpos([], _, _, _, []).
insertpos([H|T], V, P, I, [H,V|Rez]):-
        P =:= I,
        !,
        P1 is P * 2,
        I1 is I + 1,
        insertpos(T, V, P1, I1, Rez).

 insertpos([H|T], V, P, I, [H|Rez]):-
         I1 is I + 1,
         insertpos(T, V, P, I1, Rez).

insertposRapper(L, V, Rez):-
    insertpos(L, V, 1, 1, Rez),
    write(Rez).


%listToMult(L - lista, C - lista colectoare R - rezultat)
%model matematic
%listToMult(l1..ln, C) = {
%                   C, n = 0.
%                   listToMult(l2..ln, C+l1), daca !apartine(C, l1).
%                   listToMult(l2..ln, C), altfel
%}

listToMult([], C, C).
listToMult([H|T], C, R):-
        \+ apartine(C, H),
        !,
        listToMult(T, [H|C], R).
listToMult([_|T], C, R):-
        listToMult(T, C, R).






