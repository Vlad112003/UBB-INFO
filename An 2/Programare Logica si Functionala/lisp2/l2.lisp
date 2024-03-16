

;Sa se construiasca lista nodurilor unui arbore de tipul (A (B) (C (D) (E))) parcurs in inordine
;Exemplu: (A (B) (C (D) (E))) => (B A D C E)

; model matematic
; inordine(arbore) = {
;		[], daca arbore = nil
;		[arbore], daca arbore este atom
;		inordine(cadr(arbore)) + [car(arbore)] + inordine(caddr(arbore)), altfel
;		}


(defun inordine (arbore)
  (cond ((null arbore) nil) ;daca arborele e gol, returneaza nil
    ((atom arbore) (list arbore)) ;daca arborele e atom, returneaza lista cu acel atom
    (t (append (inordine (cadr arbore)) (list (car arbore)) (inordine (caddr arbore)))))) ;altfel, returneaza lista cu nodul din radacina,
;concatenata cu lista nodurilor din subarborele stang si cu lista nodurilor din subarborele drept
;cadr si caddr returneaza subarborele stang, respectiv drept, iar car este radacina

(print "Start")

(print (inordine '(A (B) (C (D) (E)))))

(print (inordine '(a (b()(f))(d(e)))))

(print (inordine '(a)))

(print (inordine '()))

(print "tabla:")

(print (inordine '(a(b(h()(i(j))))(c(d(f(k))(g))e))))





