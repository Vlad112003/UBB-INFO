% 10
% a) Se da o lista de numere intregi. Se cere sa se adauge in lista dupa
% 1-ul element, al 3-lea element, al 7-lea elemen, al 15-lea element … o
% valoare data e. b) Se da o lista eterogena, formata din numere intregi
% si liste de numere intregi. Lista incepe cu un numar si nu sunt 2
% elemente consecutive care sunt liste. Se cere ca in fiecare sublista sa
% se adauge dupa 1-ul, al 3- lea, al 7-lea… element valoarea care se
% gaseste inainte de sublista in lista eterogena. De ex: [1, [2, 3], 7,
% [4, 1, 4], 3, 6, [7, 5, 1, 3, 9, 8, 2, 7], 5] => [1, [2, 1, 3], 7, [4,
% 7, 1, 4, 7], 3, 6, [7, 6, 5, 1, 6, 3, 9, 8, 2, 6, 7], 5].
%
%
% a)
%
%
% Model matematic:
% insert_list(l1..ln, e, pos) =
%        [], n = 0
%        l1 + e + insert_pos(l2..ln, pos + 1), daca pos % 2 = 1
%        l1 + insert_pos(l2..ln, e, pos + 1), altfel
%
% insert_list(L:list, E:number, Pos:number, R:list)
% model de flux: insert_list(i, i, i, o)

insert_list([], _, _, []).
insert_list([H|T], E, Pos, [H,E|R]) :- Pos mod 2 =:= 1, !,
            NPos is Pos + 1,
            insert_list(T, E, NPos, R).
insert_list([H|T], E, Pos, [H|R]) :-
    NPos is Pos + 1,
    insert_list(T, E, NPos, R).

% insertNb(L:list, E:number, R:list)
% model de flux: insertNb(i, i, o)

insertNb(L, E, R) :- insert_list(L, E, 1, R).


% b)
%
% Model matematic:
% heterList(l1..ln, list) =
%          [], n = 0
%          l1 + insertNb(l2, l1) + heterList(l2..ln), daca is_list(l2)
%          == true and number(l1)
%          l1 + heterlist(l2..ln), altfel
%
% heterList(L:list, R:list)
% model de flux: heterList(i, o)


heterList([], []).
heterList([E], [E]).
heterList([H1, H2|T], [H1, HR|R]) :- is_list(H2), number(H1), !,
    insertNb(H2, H1, HR),
    heterList(T,R).
heterList([H|T], [H|R]) :-
    heterList(T,R).
