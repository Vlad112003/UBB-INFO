;Sa se construiasca o functie care intoarce suma atomilor numerici
; dintr-o lista, de la orice nivel folosind functii map.
;ex lista: [1,2,3,[4,a,6],7,[8,[9,b]]]
;ex rezultat: 40 (ignora a si b din lista)


;model matematic
;sum-numeric(l1,l2,...,ln) = 0, daca n = 0
;                           is-numeric(l1) + sum-numeric(l2,...,ln), daca l1 este numar
;                           sum-numeric(l1) + sum-numeric(l2,...,ln), daca l1 este lista


(defun is-numeric (x)
  (cond ((numberp x) x)
        ((listp x) (sum-numeric x))
        (t 0)))

;model matematic
;sum-numeric(l1,l2,...,ln) = 0, daca n = 0
;                           is-numeric(l1) + sum-numeric(l2,...,ln), daca l1 este numar
;                           sum-numeric(l1) + sum-numeric(l2,...,ln), daca l1 este lista

(defun sum-numeric (l)
    (apply '+ (mapcar 'is-numeric l)))

(print(sum-numeric '(1 2 3 (4 a 6) 7 (8 (9 b)))))
(print(sum-numeric '(a)))
(print(sum-numeric '()))
(print(sum-numeric '(1)))





