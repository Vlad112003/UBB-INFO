#include "IteratorMDO.h"
#include "MDO.h"

//Complexitate O(n)
IteratorMDO::IteratorMDO(const MDO& d) : dict(d) {
    /* de adaugat */
    int nod = dict.abc.radacina;
    while (nod != null) {
        this->stiva.push(nod);
        nod = dict.abc.noduri[nod].stanga;
    }
    if (!this->stiva.empty()) {
        this->nodCurent = stiva.top();
    }
    else {
        this->nodCurent = null;
    }
}

//Complexitate O(n)
void IteratorMDO::prim() {
    /* de adaugat */
    while (!this->stiva.empty()) {
        this->stiva.pop();
    }
    int nod = dict.abc.radacina;
    while (nod != null) {
        this->stiva.push(nod);
        nod = dict.abc.noduri[nod].stanga;
    }
    if (!this->stiva.empty()) {
        this->nodCurent = this->stiva.top();
    }
    else {
        this->nodCurent = null;
    }
}

//complexitate O(n) in ordine
void IteratorMDO::urmator() {
    /* de adaugat */
    if (!valid())
        throw "iterator invalid!";
    int nod = this->stiva.top();
    this->stiva.pop();
    if (dict.abc.noduri[nod].dreapta != null) {
        nod = dict.abc.noduri[nod].dreapta;
        while (nod != null) {
            this->stiva.push(nod);
            nod = dict.abc.noduri[nod].stanga;
        }
    }
    if (!this->stiva.empty()) {
        this->nodCurent = this->stiva.top();
    }
    else {
        this->nodCurent = null;
    }
}

//complexitate theta(1)
bool IteratorMDO::valid() const {
    /* de adaugat */
    if (dict.vid())
        return false;
    return this->nodCurent != null;
}

//complexitate theta(1)
TElem IteratorMDO::element() const {
    /* de adaugat */
    if (!valid())
        throw "iterator invalid!";
    return dict.abc.noduri[this->nodCurent].info;
}