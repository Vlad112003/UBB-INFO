#include "IteratorMultime.h"
#include "Multime.h"


IteratorMultime::IteratorMultime(const Multime& m): mult(m) {
    this->indexElement = 0;
}

TElem IteratorMultime::element() const {
    TElem elem = mult.return_element(this->indexElement);
    return elem;
}

bool IteratorMultime::valid() const {
    if(this->indexElement<mult.dim())
        return true;
    return false;
}

void IteratorMultime::urmator() {
//    if (!this->valid())
//        throw ("iterator invalid!\n");
    this->indexElement++;

}

void IteratorMultime::prim() {
    this->indexElement = 0;
}

