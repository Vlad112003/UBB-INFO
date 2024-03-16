%apartine(L - lista, E - elem)
%model matematic
%apartine(l1..ln, E) = {
%                           true, daca l1 = E.
%                           false, daca n = 0
%                           apartine(l2..ln, E), altfel
%}

apartine([H|_], H) :- !.
apartine([_|T], E):-
        apartine(T, E).

%listMul(L - Lista, C - Lista colectoare, R - rezultat)
%model matematic
%listMul(l1..ln, C) = {
%            C, n = 0;
%            C (+) l1 + listMul(l2..ln), daca l1 !apartine(C, l1)
%            C + listMul(l2..ln), altfel
%}

listMul([], C, C).
listMul([H|T], C, R):-
    not(apartine(C,H)),
    !,
    listMul(T, [H|C], R).
listMul([_|T], C, R):-
    listMul(T, C, R).


%invertList(L - lista, Acc - lista acumulatoare, R - lista rezultat)
%model matematic
%invertList(l1..ln, Acc) = {
%                           Acc, n = 0
%                           invertList(l2..ln, l1 + Acc), altfel
%}

invertList([], Acc, Acc).
invertList([H|T], Acc, R):-
    invertList(T, [H|Acc], R).


%listMulMain(L - lista, R - lista rezultat)
%model matematic
%listMulMain(l1..ln) = {
%                       invertList(listMul(l1..ln, []), [])
%}


listMulMain([H|T], R):-
    listMul([H|T], [], R1),
    invertList(R1, [], R).



%build_multime(L - lista, R - lista rezultat)
%model matematic
%build_multime(l1..ln) = {
%                           [], n = 0
%                           build_multime(l2..ln), daca l1 apartine l2..ln
%                           l1 + build_multime(l2..ln), altfel
%}

build_multime([], []):-!.
build_multime([H|T], R):-
    apartine(T, H),
    !,
    build_multime(T, R).

build_multime([H|T], [H|R]):-
    build_multime(T, R).

%build_multime_main(L - lista, R - lista rezultat)
%model matematic
%build_multime_main(l1..ln) = {
%                               invertList(build_multime(l1..ln), [])
%}
build_multime_main([H|T], R):-
    build_multime([H|T], R1),
    invertList(R1, [], R).

%sa se scrie un program care genereaza lista aranjamentelor de k elemente dintr-o listsa de numere intregi avand o suma S data

%arange(L - lista, K - numar, S - suma, R - lista rezultat)
%model matematic
%arange(l1..ln, K, S) = {
%                           [], K = 0
%                           l1 + arange(l2..ln, K - 1, S - l1), daca l1 <= S
%                           arange(l2..ln, K, S), altfel
%}

arange(_, 0, 0, []):-!.

arange([H|T], K, S, [H|R]):-
    K1 is K - 1,
    S1 is S - H,
    H =< S,
    arange(T, K1, S1, R).

arange([_|T], K, S, R):-
    arange(T, K, S, R).

%arange_main(L - lista, K - numar, S - suma, R - lista rezultat)
%model matematic
%arange_main(l1..ln, K, S) = {
%                              findall(X, (arange(l1..ln, K, S, X1), permutation(X1, X)), R)
%}

arange_main(L, K, S, R):-
    findall(X, (arange(L, K, S, X1), permutation(X1, X)), R).


%Definiti un predicat care determina suma a doua numere scrise in
      % reprezentare de lista

%suma(L1 - lista, L2 - lista, R - lista rezultat)
%model matematic
%suma(l1..ln, L1..Ln) = {
%                           [], n = 0
%                           l1 + suma(l1..ln), L1..Ln = [],
%                           L1 + suma(L2..Ln), l1..ln = []
%                           l1 + suma(l2..ln, L2..Ln), altfel
%}

suma([], [], []):-!.

suma([H1|T1], [H2|T2], [H3|R]):-
    H3 is H1 + H2,
    suma(T1, T2, R).

suma([], [H2|T2], [H3|R]):-
    H3 is H2,
    suma([], T2, R).

suma([H1|T1], [], [H3|R]):-
    H3 is H1,
    suma(T1, [], R).

/*Se da o lista eterogena, formata din numere intregi si liste de cifre. Sa
se calculeze suma tuturor numerelor reprezentate de subliste. De ex:
[1, [2, 3], 4, 5, [6, 7, 9], 10, 11, [1, 2, 0], 6] =>
[8, 2, 2].*/

%sumaNumere(L - lista, R - lista rezultat)
%model matematic
%sumaNumere(l1..ln) = {
%                           [], n = 0
%                           suma(l1) + sumaNumere(l2..ln), daca l1 este numar
%                           sumaNumere(l2..ln), altfel
%}







