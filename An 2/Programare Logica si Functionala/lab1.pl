/* a Sa se scrie un predicat care sterge toate aparitiile unui anumit atom
 dintr-o lista.
 b Definiti un predicat care, dintr-o lista de atomi, produce o lista de
 perechi (atom n), unde atom apare in lista initiala de n ori. De ex:
 numar([1, 2, 1, 2, 1, 3, 1], X) va produce X = [[1, 4], [2, 2], [3, 1]].

    sterge(L:list, X:number, R:list)
    model de flux sterge(i, i, o)
*/

sterge([], _, []).

sterge([X|Tail], X, Result):- !, sterge(Tail, X, Result).

sterge([Head|Tail], X, Result):-  sterge(Tail, X, Result1),
                       Result = [Head|Result1].


/* numar(L:list, R:List)
   model de flux: numar(i,o)
*/
numar([], []).
numar([Head|Tail], [[Head, N] | R]) :-
    numara_aparitii(Head, [Head|Tail], N, Result),
    numar(Result, R).

/* numara_aparitii(X:number, L:list, N:number, R:list)
   model de flux: numara_aparitii(i, i, i, o).
*/
numara_aparitii(_, [], 0, []).
numara_aparitii(X, [X|Tail], N, Result) :-
    numara_aparitii(X, Tail, N1, Result),
    N is N1 + 1.
numara_aparitii(X, [Head|Tail], N, [Head|Result]) :-
    X \= Head,
    numara_aparitii(X, Tail, N, Result).


















