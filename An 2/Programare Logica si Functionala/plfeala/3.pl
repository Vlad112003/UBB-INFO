apartine([H|_], H):-!.
apartine([_|T], E):-
        apartine(T, E).

