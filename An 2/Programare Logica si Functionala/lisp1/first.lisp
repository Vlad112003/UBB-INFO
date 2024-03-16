;10.
;a) Sa se construiasca o functie care intoarce produsul atomilor numerici
;dintr-o lista, de la nivelul superficial.
;b) Sa se scrie o functie care, primind o lista, intoarce multimea tuturor
;perechilor din lista. De exemplu: (a b c d) --> ((a b) (a c) (a d)(b c) (b
;d) (c d))
;c) Sa se determine rezultatul unei expresii aritmetice memorate in preordine
;pe o stiva. Exemple:
;(+ 1 3) ==> 4 (1 + 3)
;(+ * 2 4 3) ==> 11 ((2 * 4) + 3)
;(+ * 2 4 - 5 * 2 2) ==> 9 ((2 * 4) + (5 - (2 * 2))


;1.a)

;produs(l1..ln) = 1, n = 0
;                 l1 * produs(l2..ln), l1 numar
;                 produs(l2..ln), altfel


(defun produs (lst)
    (cond
        ((null lst) 1) ;daca lista e vida, produsul e 1
        ((numberp (car lst)) (* (car lst) (produs (cdr lst)))) ;daca primul element e numar, inmultim cu produsul celorlalte elemente
        (t (produs (cdr lst))) ;altfel, ignoram primul element si calculam produsul celorlalte
    )
)

(print(produs '(1 2 3 4)))

;1.b)
;perechi(l1..ln) = nil, n = 0
;                  perechi-helper(l1, l2..ln) + perechi(l2..ln), altfel


(defun perechi (lst)
  (cond
    ((null lst) nil)                                ; daca lista e vida, returnam nil.
    (t (append (perechi-helper (car lst) (cdr lst)) ; folosim functia helper pentru a crea perechi cu primul element si restul listei.
               (perechi (cdr lst))))                 ; concatenam rezultatul cu rezultatul obtinut pentru restul listei.
  )
)

;perechi-helper(first-elem, l1..ln) = nil, n = 0
;                                     (first-elem, l1) + perechi-helper(first-elem, l2..ln), altfel


(defun perechi-helper (first-elem rest-list)
  (cond
    ((null rest-list) nil)                          ; daca lista e vida, returnam nil.
    (t (cons (list first-elem (car rest-list))     ; altfel, cream o pereche cu primul element si primul element din restul listei.
             (perechi-helper first-elem (cdr rest-list)))) ; apelam recursiv functia pentru restul listei.
  )
)

(print(perechi '(a b c d)))
(print(perechi '(1 2 3 4)))

;1.c)

;_expression(op, a, b) = a + b, op = +
;                        a - b, op = -
;                        a * b, op = *
;                        a / b, op = /

(defun _expression (op a b)
	(cond
		((string= op "+") (+ a b)) ;daca operatorul e +, adunam a si b
		((string= op "-") (- a b)) ;daca operatorul e -, scadem b din a
		((string= op "*") (* a b)) ;daca operatorul e *, inmultim a cu b
		((string= op "/") (floor a b)) ;daca operatorul e /, impartim a la b
	)
)

;expression(l1..ln) = nil, n = 0
;                     _expression(l1, l2, l3) + expression(l4..ln), l1 operator, l2 si l3 numere
;                     l1 + expression(l2..ln), l1 numar
;                     l1, altfel
;


(defun expression (l)
    (cond
        ((null l) nil) ;daca lista e vida, returnam nil
        ((and (and (numberp (cadr l)) (numberp (caddr l))) (atom (car l))) (cons (_expression (car l) (cadr l) (caddr l)) (expression (cdddr l)))) ;daca primul element e operator si urmatoarele doua sunt numere,
      ;calculam expresia si apelam recursiv functia pentru restul listei
        (t (cons (car l) (expression (cdr l)))) ;altfel, adaugam primul element in lista rezultat si apelam recursiv functia pentru restul listei
    )
)


;solve(l1..ln) = l1, n = 1
;                solve(expression(l1..ln)), altfel

(defun solve (l)
    (cond
        ((null (cdr l)) (car l)) ;daca lista are un singur element, returnam acel element
        (t (solve (expression l))) ;altfel, calculam expresia si apelam recursiv functia pentru lista rezultat
    )
)

(print(solve '(+ 1 3)))
(print(solve '(+ * 2 4 3)))
(print(solve '(+ * 2 4 - 5 * 2 2)))

;d) Definiti o functie care, dintr-o lista de atomi, produce o lista de
;perechi (atom n), unde atom apare in lista initiala de n ori. De ex:
;(A B A B A C A) --> ((A 4) (B 2) (C 1)).

;_count(l1..ln, x) = 0, n = 0
;                    1 + _count(l2..ln, x), l1 = x
;                    _count(l2..ln, x), altfel

(defun _count (l x)
    (cond
        ((null l) 0) ;daca lista e vida, returnam 0
        ((eq (car l) x) (+ 1 (_count (cdr l) x))) ;daca primul element e egal cu x, adunam 1 la rezultatul obtinut pentru restul listei
        (t (_count (cdr l) x)) ;altfel, ignoram primul element si apelam recursiv functia pentru restul listei
    )
)

;_remove(l1..ln, x) = nil, n = 0
;                     l1 + _remove(l2..ln, x), l1 != x
;                     _remove(l2..ln, x), altfel

(defun _remove (l x)
    (cond
        ((null l) nil) ;daca lista e vida, returnam nil
        ((eq (car l) x) (_remove (cdr l) x)) ;daca primul element e egal cu x, ignoram primul element si apelam recursiv functia pentru restul listei
        (t (cons (car l) (_remove (cdr l) x))) ;altfel, adaugam primul element in lista rezultat si apelam recursiv functia pentru restul listei
    )
)

;counter(l1..ln) = nil, n = 0
;                (l1, _count(l1..ln, l1)) + counter(l2..ln), altfel

(defun counter (l)
    (cond
        ((null l) nil) ;daca lista e vida, returnam nil
        (t (cons (list (car l) (_count l (car l))) (counter (_remove l (car l))))) ;altfel, adaugam in lista rezultat o pereche formata din primul element si numarul de aparitii ale acestuia in lista initiala
      ;si apelam recursiv functia pentru restul listei, dupa ce eliminam toate aparitiile primului element din lista
    )
)


(print(counter '(a b a b a c a)))
