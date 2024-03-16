%Sa se sorteze o lista cu eliminarea dublurilor.

%eliminare_dubluri(l1..ln) = [], n = 0
%                           eliminare_dubluri(l2..ln), l1 apartine l2..ln
%                           l1 U eliminare_dubluri(l2..ln), altfel

%eliminare dubluri
%eliminare_dubluri(L-lista, R-rezultat)
%(i,o)
eliminare_dubluri([],[]).
eliminare_dubluri([H|T],R):-
    member(H,T),
    eliminare_dubluri(T,R).
eliminare_dubluri([H|T],[H|R]):-
    not(member(H,T)),
    eliminare_dubluri(T,R).

%sortare(l1..ln) = [], n = 0
%                  inserare(l1, sortare(l2..ln)), altfel

%sortare(L-lista, R-rezultat)
%(i,o)
sortare([],[]).
sortare([H|T],R):-
    sortare(T,R1),
    inserare(H,R1,R).

%inserare(E, l1..ln) = [E], n = 0
%                      E (+) l1..ln, E =< l1
%                      l1 (+) inserare(E,l2..ln), altfel

%inserare(E-element, L-lista, R-rezultat)
%(i,i,o)
inserare(E,[],[E]).
inserare(E,[H|T],[E,H|T]):-
    E=<H.
inserare(E,[H|T],[H|R]):-
    E>H,
    inserare(E,T,R).

%sortare cu eliminare dubluri(l1..ln) = [], n = 0
%                                      sortare_dubluri(l2..ln), l1 apartine l2..ln
%                                      l1 (+) sortare_dubluri(l2..ln), altfel

%sortare_dubluri(L-lista, R-rezultat)
%(i,o)
sortare_dubluri([],[]).
sortare_dubluri(L,R):-
    eliminare_dubluri(L,R1),
    sortare(R1,R).