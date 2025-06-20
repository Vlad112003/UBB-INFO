#include "IteratorMultime.h"
#include "Multime.h"
#include <exception>

// constructor
// complexitate theta(1)
IteratorMultime::IteratorMultime(const Multime& m) : multime(m) {
    this->prim();
}

// reseteaza iteratorul la inceputul containerului
// complexitate theta(1)
void IteratorMultime::prim() {
    this->current = multime.head;
}

// muta iteratorul la urmatorul element
// complexitate theta(1)
// arunca exceptie daca iteratorul nu este valid
void IteratorMultime::urmator() {
    if (!this->valid()) {
        throw std::exception();
    }

    this->current = multime.next[this->current];
}

//returneaza elementul curent
// complexitate theta(1)
// arunca exceptie daca iteratorul nu este valid
TElem IteratorMultime::element() const {
    if (!this->valid()) {
        throw std::exception();
    }

    return multime.elements[this->current];
}

// verifica daca iteratorul este valid
// complexitate theta(1)
bool IteratorMultime::valid() const {
    return this->current != NIL;
}