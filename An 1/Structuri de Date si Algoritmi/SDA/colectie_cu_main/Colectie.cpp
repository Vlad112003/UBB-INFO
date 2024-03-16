#include "Colectie.h"
#include "IteratorColectie.h"
#include <exception>
#include <iostream>

using namespace std;


Colectie::Colectie() {
    //capacitate
	this->cp = 10;
    //vectorul de elemente
    e = new telemen[cp];
    //numarul de elemente din vector
    this->n = 0;

}

void Colectie::redim() {
    telemen *eNou = new telemen[2*cp];

    for (int i = 0; i<n; i++)
        eNou[i] = e[i];

    cp = 2*cp;

    delete[] e;

    e = eNou;


}

int Colectie::cautare(TElem elem) const {
    for (int i = 0; i<n; i++)
        if(e[i].lmn == elem)
            return i;
    return -1;
}


void Colectie::adauga(TElem elem) {
	if (n == cp)
        redim();
    int pos = cautare(elem);
    if(pos == -1)
    {
        e[n].lmn = elem;
        e[n].frec = 1;
        n++;
    }
    else {
        e[pos].frec++;
    }
}


bool Colectie::sterge(TElem elem) {
	int pos = cautare(elem);
    if (pos<0) return false;
    if(e[pos].frec > 1)
    { e[pos].frec--;
        return true;}
    else
    {
        for(int i = pos; i<n-1;i++)
            e[i] = e[i+1];
        n--;
        return true;
    }


}

bool Colectie::cauta(TElem elem) const {
	int pos = cautare(elem);
    if (pos == -1)
        return false;
    else return true;

}

int Colectie::nrAparitii(TElem elem) const {
	int pos = cautare(elem);
    if(pos == -1) return 0;
    return e[pos].frec;
}


int Colectie::dim() const {
    int dim = 0;
	for (int i=0; i<n; i++)
    {
        dim = dim + e[i].frec;
    }
    return dim;
};


bool Colectie::vida() const {
	return n==0;
}

IteratorColectie Colectie::iterator() const {
	return  IteratorColectie(*this);
}


Colectie::~Colectie() {
	//delete[] e;
};



