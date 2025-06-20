#include "Colectie.h"
#include "IteratorColectie.h"
#include <exception>
#include <iostream>

using namespace std;


Colectie::Colectie() {
    //capacitate
	this->cp = 10;
    //vectorul de elemente
    e = new TElem[cp];
    //numarul de elemente din vector
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
	int i = 0;
    int pos = 0;
    while (e[i] != elem)
    {
        if (i==n)
            return false;
        i++;
        pos++;

    }
    for (i=pos; i<n; i++)
        e[i] = e[i+1];
    n= n -1;
    return true;


}


bool Colectie::cauta(TElem elem) const {
	int i = 0;
    while (e[i] != elem)
    {
        if(i==n)
            return false;
        i++;
    }
    return true;

}

int Colectie::nrAparitii(TElem elem) const {
	int i;
    int nr = 0;
    for (i=0; i<=n; i++)
        if (e[i] == elem){
            nr++;
        }
    return nr;
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


