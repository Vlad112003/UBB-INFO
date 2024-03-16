%sa se scrie un predicat care elimina un element dintr-o lista, doar elementul de pe lista data

%eliminare(l1..ln, P) = [], n=0
%                       l1+eliminare(l2..ln, P-1), P != 1
%                       eliminare(l2..ln, P-1), altfel

%eliminare(L-lista, P-pozitie, R-rezultat)
%eliminare(i,i,o)

eliminare([],_,[]).
eliminare([H|T],P,[H|R]):-
    P =\= 1,
    P1 is P - 1,
    eliminare(T,P1,R).
eliminare([_|T],1,R):-
    eliminare(T,0,R).

%eliminare([1,2,3,4,5],3,R).
%eliminare([1,2,3,4,5],1,R).

%sa se scrie un predicat care elimina un element dintr-o lista, doar elementul de pe pozitie para

%eliminare_pare(l1..ln) = [], n=0
%                         l1+eliminare_pare(l2..ln), P-1%2=0
%                         eliminare_pare(l2..ln), P-1%2!=0

%eliminare_pare(L-lista, R-rezultat)
%eliminare_pare(i,o)

eliminare_pare([],[]).
eliminare_pare([H|T],[H|R]):-
    H mod 2 =\= 0,
    eliminare_pare(T,R).
eliminare_pare([_|T],R):-
    eliminare_pare(T,R).








