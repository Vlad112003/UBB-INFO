#include "IteratorColectie.h"
#include "Colectie.h"
#include <exception>

/*
Complexitate overall: theta(1)
*/
IteratorColectie::IteratorColectie(const Colectie& c): col(c) {
    // Setam pozitia iteratorului in raport cu frecventa elementului la 0,
    // care indica prima aparitie a elementului cu o anumita frecventa
    this->indexFrecventa = 0;

    // Setam indexul elementului tot la 0:
    this->indexElement = 0;
}

void IteratorColectie::prim() {
    // Setam pozitia iteratorului in raport cu frecventa elementului la 0,
    // care indica prima aparitie a elementului cu o anumita frecventa
    this->indexFrecventa = 0;

    // Setam indexul elementului tot la 0:
    this->indexElement = 0;
}


void IteratorColectie::urmator() {
    // Preconditie: verificam daca iteratorul este invalid:
    if (!this->valid())
        throw ("eroare! iteratorul este invalid\n");

    // Crestem pozitia iteratorului in cadrul elementului curent (care are o anumita frecventa)
    this->indexFrecventa++;

    // Daca pozitia iteratorului este egala cu frecventa elem, inseamna ca am depasit elementul
    // si trecem la urmatorul element:
    if (this->indexFrecventa == col.vectorFrecvente[this->indexElement])
    {
        this->indexFrecventa = 0;
        this->indexElement++;
    }
}


bool IteratorColectie::valid() const {
    // Verificam daca indicele se afla in intervalul [0, col.size]:
    if (0 <= this->indexElement && this->indexElement < col.dimensiune)
        return true;

    return false;
}



TElem IteratorColectie::element() const {
    // Verificam daca indicele se afla in intervalul [0, col.size]:
    if (this->valid())
        return col.vectorElemente[this->indexElement];

    throw ("eroare! iterator invalid\n");
}
