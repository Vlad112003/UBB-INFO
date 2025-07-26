class Colectie:
    def __init__(self):
        self.__elemente = []

    def adauga(self, e):
        self.__elemente.append(e)

    def cauta(self, e):
        return e in self.__elemente

    def sterge(self, e):
        # optional, se poate returna True daca elementul a fost sters si False altfel
        # (a se vedea interfata TAD Colectie de la laborator)
        if self.cauta(e):
            self.__elemente.remove(e)

    def dim(self):
        return len(self.__elemente)

    def iterator(self):
        return Iterator(self)

    def nr_aparitii(self, e):
        """
        (Adaugat in plus fata de materialul de seminar)
        Returneaza numarul de aparitii al elementului e in colectie
        :param e: TElement
        :return: int, numarul de aparitii al elementului in colectie
        """
        n = 0
        for elem in self.__elemente:
            if elem == e:
                n = n + 1
        return n

class Iterator:
    def __init__(self,c):
        self.__c = c
        self.__curent = 0

    def valid(self):
        return self.__curent < self.__c.dim()

    def element(self):
        return self.__c._Colectie__elemente[self.__curent]

    def urmator(self):
        self.__curent = self.__curent+1

    def prim(self):
        """
        (Adaugat in plus fata de materialul de seminar; vezi utilizare in functia de tiparire)
        Iteratorul este setat astfel incat sa refere primul element din colectie
        """
        self.__curent = 0
        
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
    c = Colectie()
    populeaza_colectie_intregi(c)
    print("Dimensiunea colectiei:", c.dim())
    print("Numarul de aparitii ale elementului 2:", c.nr_aparitii(2))
    tipareste(c)

main()
