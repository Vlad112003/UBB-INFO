%Sa se scrie un predicat care intoarce diferenta a doua multimi.

%diff(L-lista, M-lista, R-lista)
%(i,i,o)

diff([],_,[]).
diff([H|T],M,R):-
    member(H,M),
    diff(T,M,R).

diff([H|T],M,[H|R]):-
    \+member(H,M),
    diff(T,M,R).

%Sa se scrie un predicat care adauga intr-o lista dupa fiecare element par
%valoarea 1.

%add(L-lista, R-lista)
%(i,o)

add([],[]).
add([H|T],[H,1|R]):-
    H mod 2 =:= 0,
    add(T,R).

add([H|T],[H|R]):-
    H mod 2 =\= 0,
    add(T,R).


%Sa se scrie un predicat care determina cel mai mic multiplu comun al
%elementelor unei liste formate din numere intregi

%gcd(A-numar, B-numar, R-numar)
%(i,i,o)

gcd(A,0,A).
gcd(A,B,R):-
    B > 0,
    A1 is A mod B,
    gcd(B,A1,R).

%lcm(A-numar, B-numar, R-numar)
%(i,i,o)

lcm(A,B,R):-
    gcd(A,B,G),
    R is A*B/G.

%lcmList(L-lista, R-numar)
%(i,o)
lcmList([],1).
lcmList([H|T],R):-
    lcmList(T,R1),
    lcm(H,R1,R).

%Sa se scrie un predicat care transforma o lista intr-o multime, in
  % ordinea primei aparitii. Exemplu: [1,2,3,1,2] e transformat in [1,2,3]

%set(L-lista, R-lista)
%(i,o)

set([],[]).
set([H|T],[H|R]):-
    \+member(H,T),
    set(T,R).

set([H|T],R):-
    member(H,T),
    set(T,R).

%Sa se scrie o functie care descompune o lista de numere intr-o lista de
% forma [ lista-de-numere-pare lista-de-numere-impare] (deci lista cu doua
% elemente care sunt liste de intregi), si va intoarce si numarul
% elementelor pare si impare.

%split(L-lista, P-Lista nr pare, I-lista nr impare, N1-numar, N2-numar)
%(i,o,o)

split([],[],[],0,0).
split([H|T],[H|P],I,N1,N2):-
    H mod 2 =:= 0,
    split(T,P,I,N11,N2),
    N1 is N11 + 1.

split([H|T],P,[H|I],N1,N2):-
    H mod 2 =\= 0,
    split(T,P,I,N1,N22),
    N2 is N22 + 1.

%sa se scrie un predicat care inlocuieste intr-o lista, un element dat cu o lista data

%replace(L-lista, E-element, R-lista, R-lista)
%(i,i,i,o)

replace([],_,_,[]).
replace([E|T],E,R,[R|T]).
replace([H|T],E,R,[H|R1]):-
    H \= E,
    replace(T,E,R,R1).

%Sa se elimine elementul de pe pozitia a n-a a unei liste liniare.

%remove(L-lista, N-numar, R-lista)
%(i,i,o)

remove([],_,[]).
remove([_|T],1,T).
remove([H|T],N,[H|R]):-
    N1 is N - 1,
    remove(T,N1,R).

%sa se scrie un predicat care sterge toate aparitiile unui anumit atom  dintr-o lista.

%removeAll(L-lista, E-element, R-lista)
%(i,i,o)

removeAll([],_,[]).
removeAll([E|T],E,R):-
    removeAll(T,E,R).
removeAll([H|T],E,[H|R]):-
    H \= E,
    removeAll(T,E,R).

%Definiti un predicat care, dintr-o lista de atomi, produce o lista de perechi (atom n), unde atom apare in lista initiala de n ori. De ex: numar([1, 2, 1, 2, 1, 3, 1], X) va produce X = [[1, 4], [2, 2], [3, 1]].

%count(L-lista, E-element, N-numar)
%(i,i,o)

count([],_,0).
count([E|T],E,N):-
    count(T,E,N1),
    N is N1 + 1.
count([H|T],E,N):-
    H \= E,
    count(T,E,N).

%countAll(L-lista, R-lista)
%(i,o)

countAll([],[]).
countAll([H|T],[[H,N]|R]):-
    count([H|T],H,N),
    removeAll(T,H,R1),
    countAll(R1,R).

%Sa se scrie un predicat care elimina dintr-o lista toate elementele care  se repeta (ex: l=[1,2,1,4,1,3,4] => l=[2,3])

%removeDuplicates(L-lista, R-lista)
%(i,o)

removeDuplicates([],[]).
removeDuplicates([H|T],[H|R]):-
    removeAll(T,H,R1),
    removeDuplicates(R1,R).

%Sa se elimine toate aparitiile elementului maxim dintr-o lista de numere intregi.

%removeMax(L-lista, R-lista)
%(i,o)

max([E],E).
max([H|T],H):-
    max(T,M),
    H > M.
max([H|T],M):-
    max(T,M),
    H =< M.

removeMax([],[]).
removeMax(L,R):-
    max(L,M),
    removeAll(L,M,R).

%Sa se scrie un predicat care intoarce reuniunea a doua multimi.

%union(L-lista, M-lista, R-lista)
%(i,i,o)

union([],M,M).
union([H|T],M,R):-
    member(H,M),
    union(T,M,R).
union([H|T],M,[H|R]):-
    \+member(H,M),
    union(T,M,R).

%8. Sa se sorteze o lista cu eleiminarea dublurilor.

%sort(L-lista, R-lista)


















