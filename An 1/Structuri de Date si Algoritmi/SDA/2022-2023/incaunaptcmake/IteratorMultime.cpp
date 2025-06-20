#include "IteratorMultime.h"
#include "Multime.h"

IteratorMultime::IteratorMultime(const Multime& m) : multime(m) {
    /* de adaugat */
    curent = m.prim;
}


void IteratorMultime::prim() {
    /* de adaugat */
    curent = multime.prim;
}


void IteratorMultime::urmator() {
    /* de adaugat */
    if (!valid()) throw "Iterator invalid!";
    curent = curent->urm;

}


TElem IteratorMultime::element() const {
    /* de adaugat */
    if (!valid()) throw "Iterator invalid!";
    return curent->data;
}

bool IteratorMultime::valid() const {
    /* de adaugat */
    return curent != nullptr;
}
