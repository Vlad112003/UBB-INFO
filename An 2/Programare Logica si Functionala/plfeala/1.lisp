;(defun interval(a b)
;   (if(> a b)
;        nil
;        (cons a (interval (+ a 1) b))
;  )
;)
;
;(print (interval 1 7))
;
;
;(defun suma(l)
;  (cond
;    ((null l) 0)
;    ((numberp (car l)) (+ (car l) (suma (cdr l))))
;    (t (suma (cdr l)))
;    )
;  )
;
;(print (suma '(2 3 4 5)))
;
;(defun sumaNonlin(l)
;  (cond
;    ((null l) 0)
;    ((numberp (car l)) (+ (car l) (sumaNonlin(cdr l))))
;    ((atom (car l)) (sumaNonlin (cdr l)))
;    (t (+ (sumaNonlin (car l)) (sumaNonlin(cdr l))))
;    )
;  )
;
;
;(print(sumaNonlin '(1 (2 a (3 4) b 5) c 1)))
;
;
;
;;insertEven(l - lista, e - integer, poz - pozitia curenta, p - pozitia para)
;;model matematic
;;sa se insereze intr-o lista liniara un atom a dat dupa al 2-lea, al 4-lea,
;;al 6-lea,....element.
;;insertEven: list, integer, integer, integer -> list
;;insertEven(l1...ln, e, poz, p) =
;;	[], n = 0
;;	l1 U insertEven(l2...ln, e, poz + 1, p), poz = p
;;	l1 U e U insertEven(l2...ln, e, poz + 1, p), poz != p
;;insertEven(L - lista, E - integer, P - pozitia para, Poz - pozitia curenta)
;(defun insertEven(L E P Poz)
;    (cond
;      ((null L) nil)
;      ((= Poz P) (cons E (cons (car L) (insertEven (cdr L) E (+ P 2) (+ Poz 1)))))
;      (t (cons (car L) (insertEven(cdr L) E P (+ Poz 1)))
;    )
;)
;  )
;
;;insertEvenWrapper(L - lista, E - element)
;;model matematic
;;insertEvenWrapper: list, integer -> list
;;insertEvenWrapper(L, E) = insertEven(L, E, 2, 0)
;(defun insertEvenWrapper(L E)
;    (insertEven L E 2 0)
;)
;
;(print(insertEvenWrapper '(1 2 3 4 5 6 7 8 9) 0))

;listMult(L - lista. C - lista colectoare)
;model matematic
;listMult(l1..ln, c1..cn) = [], n = 0
;                       l1 U listMult(l1..ln, c1..cn). daca apartine(C, l1)
;                       listMult(l1..ln, c1..cn) altfel

;apartine(L - lista, E - element)
;model matematic
;apartine(l1..ln, E), false daca n = 0,
;                      true daca l1 == E
;                      apartine(l2..ln, E), altfel

(defun apartine(L E)
  (cond
    ((null L) nil)
    ((= (car L) E) t)
    (t (apartine (cdr L) E))
  )
)

;(print(apartine '(2 3 4) 3))

(defun listMul(L C)
  (cond
    ((null L) nil)
    ((apartine C (car L)) (listMul (cdr L) C))
    (t (cons (car L) (listMul (cdr L) (cons (car L) C))))
  )
)

;(print (listMul '(1 2 2 3 4) '()))


;invertList(L-lista)
;model matematic
;invertList(l1..ln) = [], n = 0
;                     invertList(l2..ln) U l1, altfel

(defun invertList(L)
  (cond
    ((null L) nil)
    (t (append (invertList (cdr L)) (list (car L))))
  )
)

;(print(invertList '(2 3 4)))


;listMulWrapper(L - lista)
;model matematic
;listMulWrapper(l1..ln) = invertList(listMul(l1..ln, []))

(defun listMulWrapper(L)
  (invertList (listMul L '()))
  )

;(print (listMulWrapper '(1 2 2 3 4)))

;prim(N - numar)
;model matematic
;prim(n) = true daca n e prim
;            nil, altfel

;prim_aux(N, D)
;model matematic
;prim_aux(N, D) = true, daca n == d (n este prim)
;                  false daca n%d = 0

(defun prim_aux(N D)
  (cond
    ((= N D) t)
    ((= 0 (mod N D)) nil)
    (t (prim_aux N (+ D 1)))
  )
)

(defun prim(N)
  (prim_aux N 2)
)

(print (prim 6))


(defun primList(L)
  (mapcar 'prim L)
)

;(print (primList '(2 3 4 5 6 7)))


(defun dublu(e)
  (* 2 e)
  )

;(print (dublu 2))

(defun dubluLista(L)
  (mapcan (lambda (x) (if (listp x) (dubluLista x) (if (numberp x) (list (dublu x))))) L)
)

(print (dubluLista '((2 3) 4 5)))


