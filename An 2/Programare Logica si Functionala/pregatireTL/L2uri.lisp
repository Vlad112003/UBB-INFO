;un arbore este reprezentat (A (B) (C (D) (E)))

;1. se da un arbore, sa se afiseze calea de la radacina pana la un nod X dat


;input: (A (B) (C (D) (E))), D
;output: A C D

(defun find-path (tree node)
  (cond
    ((null tree) nil)
    ((eq (car tree) node) (list node))
    (t (let ((left (find-path (cadr tree) node))
             (right (find-path (caddr tree) node)))
         (if left (cons (car tree) left)
           (if right (cons (car tree) right)))))))

(print (find-path '(A (B) (C (D) (E))) 'D))
(print (find-path '(A (B) (C (D) (E))) 'a))

;Sa se tipareasca lista nodurilor de pe nivelul k dintr-un arbore

;input: (A (B) (C (D) (E))), 1
;output: (B C)

(defun get-level (tree level)
  (cond
    ((null tree) nil)
    ((= level 0) (list (car tree)))
    (t (append (get-level (cadr tree) (- level 1))
               (get-level (caddr tree) (- level 1))))))
(print (get-level '(A (B) (C (D) (E))) 1))

;se da un arbore, sa se precizeze numarul de niveluri din arbore

;input: (A (B) (C (D) (E)))
;output: 3

(defun get-height (tree)
  (cond
    ((null tree) 0)
    (t (1+ (max (get-height (cadr tree))
                (get-height (caddr tree)))))))
(print (get-height '(A (B) (C (D) (E)))))

;sa se intoarca adancimea la care se afla un nod dat

;input: (A (B) (C (D) (E))), D
;output: 2

(defun get-depth (tree node)
  (cond
    ((null tree) nil)
    ((eq (car tree) node) 0)
    (t (let ((left (get-depth (cadr tree) node))
             (right (get-depth (caddr tree) node)))
         (if left (1+ left)
           (if right (1+ right)))))))

(print (get-depth '(A (B) (C (D) (E))) 'D))

;sa se construiasca lista nodurilor, parcursa in inordine

;input: (A (B) (C (D) (E)))
;output: (B A D C E)

(defun inorder (tree)
  (cond
    ((null tree) nil)
    (t (append (inorder (cadr tree))
               (list (car tree))
               (inorder (caddr tree))))))

(print (inorder '(A (B) (C (D) (E)))))

;se da un arbore, sa se precizeze nivelul pe care apare un nod dat

;input: (A (B) (C (D) (E))), D
;output: 2

(defun get-level (tree node)
  (cond
    ((null tree) nil)
    ((eq (car tree) node) 0)
    (t (let ((left (get-level (cadr tree) node))
             (right (get-level (caddr tree) node)))
         (if left (1+ left)
           (if right (1+ right)))))))

(print (get-level '(A (B) (C (D) (E))) 'D))

;sa se construiasca lista nodurilor, parcursa in preordine

;input: (A (B) (C (D) (E)))
;output: (A B C D E)

(defun preorder (tree)
  (cond
    ((null tree) nil)
    (t (append (list (car tree))
               (preorder (cadr tree))
               (preorder (caddr tree))))))
(print (preorder '(A (B) (C (D) (E)))))

;se da un arbore. sa se afiseze calea de la radacina pana la un nod x dat

;input: (A (B) (C (D) (E))), D
;output: A C D

(defun find-path (tree node)
  (cond
    ((null tree) nil)
    ((eq (car tree) node) (list node))
    (t (let ((left (find-path (cadr tree) node))
             (right (find-path (caddr tree) node)))
         (if left (cons (car tree) left)
           (if right (cons (car tree) right)))))))

(print (find-path '(A (B) (C (D) (E))) 'D))

;sa se construiasca lista nodurilor, parcursa in postordine

;input: (A (B) (C (D) (E)))
;output: (B D E C A)

(defun postorder (tree)
  (cond
    ((null tree) nil)
    (t (append (postorder (cadr tree))
               (postorder (caddr tree))
               (list (car tree))))))
(print (postorder '(A (B) (C (D) (E)))))

;sa se decina daca un arbore este echilibrat (diferenta dintre adancimile celor 2 subarbori nu este mai mare decat 1)

;input: (A (B) (C (D) (E)))
;output: T

(defun get-height (tree)
  (cond
    ((null tree) 0)
    (t (1+ (max (get-height (cadr tree))
                (get-height (caddr tree)))))))

(defun is-balanced (tree)
    (cond
        ((null tree) t)
        (t (let ((left (get-height (cadr tree)))
                 (right (get-height (caddr tree))))
             (and (<= (abs (- left right)) 1)
                (is-balanced (cadr tree))
                (is-balanced (caddr tree)))))))

(print (is-balanced '(A (B) (C (D) (E)))))
(print (is-balanced '(A (B (C)) (D (E (F))))))