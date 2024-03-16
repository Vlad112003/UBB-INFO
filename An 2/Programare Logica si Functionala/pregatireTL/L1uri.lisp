;sa se insereze intr-o lista liniara un atom dat dupa elementele din lista aflate pe pozitii pare

(defun insert-atom-after-every-second (atom list)
  (cond ((null list) nil)
        ((null (cdr list)) (list (car list)))
        (t (cons (car list) (cons (cadr list) (cons atom (insert-atom-after-every-second atom (cddr list))))))))

(print (insert-atom-after-every-second 'a '(1 2 3 4 5 6 7 8 9 10)))

;definiti o functie care obtine dint-o lista data
;lista tuturor atomilor care apar pe orice nivel, dar in ordine inversa
;de exemplu : (((A B) C) (D E)) => (E D C B A)

(defun get-all-atoms (list)
  (cond ((null list) nil)
        ((atom list) (list list))
        (t (append (get-all-atoms (car list)) (get-all-atoms (cdr list))))))
(defun reverse-list (list)
    (cond ((null list) nil)
            ((atom list) list)
            (t (append (reverse-list (cdr list)) (list (car list))))))
(defun get-all-atoms-reversed (list)
    (reverse-list (get-all-atoms list)))

(print (get-all-atoms-reversed '(((A B) C) (D E))))

;definiti o functie care intoarce cel mai mare divizor comun al numerelor dintr-o lista neliniara

(defun _gcd (a b)
  (cond ((= b 0) a)
        (t (_gcd b (mod a b)))))
(defun get-gcd (list)
    (cond ((null list) 0)
            ((atom list) list)
            (t (_gcd (get-gcd (car list)) (get-gcd (cdr list))))))
(print (get-gcd '(15 25)))

;sa se scrie o functie care determina numarul de aparitii ale unui atom dat intr-o lista neliniara

(defun count-occurences (atom list)
  (cond ((null list) 0)
        ((atom list) (if (eq atom list) 1 0))
        (t (+ (count-occurences atom (car list)) (count-occurences atom (cdr list))))))
(print (count-occurences 'a '(a b c a d e a)))