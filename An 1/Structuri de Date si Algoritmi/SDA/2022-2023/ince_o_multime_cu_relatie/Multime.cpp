#include "Multime.h"
#include "IteratorMultime.h"
#include <iostream>

using namespace std;

//o posibila relatie
bool rel(TElem e1, TElem e2) {
	if (e1 <= e2) {
		return true;
	}
	else {
		return false;
	}
}


bool Multime::adauga(TElem elem) {
    if (cauta(elem)) {
        throw std::invalid_argument("Elementul există deja în mulțime.");
    }

    Nod* nod = new Nod;
    nod->element = elem;

    if (prim == nullptr || prim->element > elem) {
        nod->urmator = prim;
        prim = nod;
    }
    else {
        Nod* curent = prim;
        while (curent->urmator != nullptr && curent->urmator->element < elem) {
            curent = curent->urmator;
        }
        nod->urmator = curent->urmator;
        curent->urmator = nod;
    }
}


bool Multime::sterge(TElem elem) {
    if (prim == nullptr) {
        return false;
    }

    if (prim->element == elem) {
        Nod* nod = prim;
        prim = prim->urmator;
        delete nod;
        return true;
    }

    Nod* curent = prim;
    while (curent->urmator != nullptr) {
        if (curent->urmator->element == elem) {
            Nod* nod = curent->urmator;
            curent->urmator = nod->urmator;
            delete nod;
            return true;
        }
        curent = curent->urmator;
    }

    return false;

}


bool Multime::cauta(TElem elem) const {
    Nod* nod = prim;
    while (nod != nullptr) {
        if (nod->element == elem) {
            return true;
        }
        if (nod->element > elem) {
            break;
        }
        nod = nod->urmator;
    }
    return false;
}


int Multime::dim() const {
	/* de adaugat */
	return 0;
}



bool Multime::vida() const {
    return (prim == nullptr);
}

IteratorMultime Multime::iterator() const {
	return IteratorMultime(*this);
}


Multime::~Multime() {
	/* de adaugat */
    while (prim != nullptr) {
        Nod* nod = prim;
        prim = prim->urmator;
        delete nod;
    }
}






