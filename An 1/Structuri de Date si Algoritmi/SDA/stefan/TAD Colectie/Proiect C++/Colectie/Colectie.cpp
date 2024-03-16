#include "Colectie.h"
#include "IteratorColectie.h"
#include <exception>
#include <iostream>

using namespace std;


Colectie::Colectie() {
	this->cp = cp;
    e = new TElem[cp];
    this->n = 0;

}

void Colectie::redim() {
    TElem *eNou = new int[2*cp];

    for (int i = 0; i<n; i++)
        eNou[i] = e[i];

    cp = 2*cp;

    delete[] e;

    e = eNou;


}

void Colectie::adauga(TElem elem) {
	if (n == cp)
        redim();

    this->e[n++] = elem;
}


bool Colectie::sterge(TElem elem) {
	/* de adaugat */
	return false;
}


bool Colectie::cauta(TElem elem) const {
	/* de adaugat */
	return false;
}

int Colectie::nrAparitii(TElem elem) const {
	/* de adaugat */
	return 0;
}


int Colectie::dim() const {
	return n;
}


bool Colectie::vida() const {
	return n==0;
}

IteratorColectie Colectie::iterator() const {
	return  IteratorColectie(*this);
}


Colectie::~Colectie() {
	delete[] e;
}


