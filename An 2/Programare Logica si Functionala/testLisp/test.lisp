;definiti o functie care intoarce cel mai mic multiplu comun al atomilor numerici dintr-o lista neliniara
;de exemplu: (12 2 4 6 8) -> 24

;
;model matematic
;is_number(x) = {
;                x, daca x este numar
;                nil, altfel
;}

;input: 2 -> 2
;input: [2] -> 2
;input: [] -> NIL

(defun is_number (x)
    (cond
        ((null x) nil)
        ((numberp x) x)
        ((listp x) (is_number (car x)))
        (t nil)
    )
)


;(print '(2))
;(print (is_number '(5)))
;(print (is_number '((5))))
;(print (is_number '(b)))
;(print (is_number '()))





;model matematic
;_lcm(list) = {
;                nil, daca list = []
;                x, daca list = [x]
;                cmmmc(lista), altfel
;}

;input (12 2 4 6 8) -> 24
;input (12 (2) (4 6) 8) -> 24

(defun _lcm (list)
    (cond
        ((null list) nil)
        ((null (cdr list)) (is_number (car list)))
        ((is_number (car list)) (lcm (car list) (_lcm (cdr list))))
        (t (_lcm (cdr list)))
    )
)





(print (_lcm '(12 2 4 6 8)))
;(print (_lcm '(4 10 (5 6) 2)))
;(print (_lcm '(1 A (2 3 B 6)5)))
;(print (_lcm '(1(2(3(4(F)5)6)1)1)))

;(print (_lcm '(12 2 (4) 6 8)))