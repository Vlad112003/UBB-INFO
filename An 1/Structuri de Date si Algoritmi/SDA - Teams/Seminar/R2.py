class PerecheElementFrecventa:
    
    def __init__(self, e, f):
        self.__e = e
        self.__f = f

    def set_element(self, e):
        self.__e = e

    def set_frecventa(self, f):
        self.__f = f

    def get_element(self):
        return self.__e

    def get_frecventa(self):
        return self.__f
    
    def __eq__(self, other):
        return self.__e == other.__e

class ColectieElementFrecventa:

    def __init__(self):
        self.__perechi = []

    def adauga(self, e):
        # Distingem doua cazuri: (1) elementul apare sau (2) nu apare deja in colectie
        # cazul (1): incrementam frecventa elementului
        # cazul (2): adaugam elementul nou, cu frecventa 1 
        for p in self.__perechi:
            if p.get_element() == e:
                p.set_frecventa(p.get_frecventa()+1)
                return
                
        p = PerecheElementFrecventa(e, 1)
        self.__perechi.append(p)

    def cauta(self, e):
        return p in self.__perechi

    def sterge(self, e):
        # Distingem trei cazuri:
        # (1) elementul nu apare in colectie
        # (2) elementul apare in colectie o singura data sau
        # (3) elementul apare in colectie de mai multe ori
        # cazul (1): colectia ramane nemodificata
        # cazul (2): perechea care il contine va fi stearsa
        # cazul (3): frecventa lui va fi decrementata
        # optional, se poate returna True daca elementul a fost sters si False altfel
        # (a se vedea interfata TAD Colectie de la laborator)
        for p in self.__perechi:
            if p.get_element() == e:
                if p.get_frecventa() > 1:
                    p.set_frecventa(p.get_frecventa()-1)
                else:
                    self.__perechi.remove(p)

    def dim(self):
        return sum(p.get_frecventa() for p in self.__perechi)

    def iterator(self):
        return IteratorColectieFrecventa(self)
		
    def nr_aparitii(self, e):
        """
        (Adaugat fata de materialul de seminar)
        Returneaza numarul de aparitii al elementul e in colectie
        :param e: TElement
        :return: int, numarul de aparitii al elementului in colectie
        """
        for p in self.__perechi:
            if p.get_element() == e:
                return p.get_frecventa()
        return 0


class IteratorColectieFrecventa:
    def __init__(self, c):
        self.__col = c
        self.__curent = 0
        self.__f = 1

    def valid(self):
        return self.__curent < len(self.__col._ColectieElementFrecventa__perechi)

    def element(self):
        return self.__col._ColectieElementFrecventa__perechi[self.__curent].get_element()

    def urmator(self):
        # Distingem doua cazuri: frecventa curenta a elementului curent este
        # (1) strict mai mica decat sau (2) egala cu frecventa elementului curent in colectia iterata
        # cazul (1): incrementam frecventa curenta
        # cazul (2): trecem la elementul urmator, incrementand indexul curent si reinitializand frecventa curenta cu 1 
        if self.__f < self.__col._ColectieElementFrecventa__perechi[self.__curent].get_frecventa():
           self.__f = self.__f+1
        else:
           self.__curent = self.__curent+1
           self.__f = 1
		   
    def prim(self):
        """
        (Adaugat fata de materialul de seminar; vezi utilizare in functia de tiparire)
        Iteratorul este setat astfel incat sa refere primul element din colectie
        """
        self.__curent = 0
        self.__f = 1

def populeaza_colectie_intregi(c):
    c.adauga(1)
    c.adauga(2)
    c.adauga(3)
    c.adauga(2)
    c.sterge(2)
    c.adauga(2)
    c.sterge(100)
    
def tipareste(c):
    it = c.iterator()
    while it.valid():
        print(it.element())
        it.urmator()
        
    print("Finalizare tiparire. Sa tiparim din nou continutul colectiei.")
    it.prim()
    while it.valid():
        print(it.element())
        it.urmator()
        
def main():
    c = ColectieElementFrecventa()
    populeaza_colectie_intregi(c)
    print("Dimensiunea colectiei:", c.dim())
    print("Numarul de aparitii ale elementului 2:", c.nr_aparitii(2))
    tipareste(c)

main()
