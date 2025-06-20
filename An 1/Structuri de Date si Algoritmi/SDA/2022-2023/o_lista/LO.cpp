#include "Iterator.h"
#include "LO.h"

#include <iostream>
using namespace std;


#include <exception>


//24. TAD Lista Ordonată (interfața cu TPozitie = Iterator) – reprezentare sub forma unei LDI cu
//elementele ordonate în raport cu o relație de ordine .

LO::LO(Relatie r) {
    this->capacitate = 10;
    this->vector = new TElement[this->capacitate];
    this->dimensiune = 0;
}

// returnare dimensiune
int LO::dim() const {
	return this->dimensiune;
}

// verifica daca LO e vida
bool LO::vida() const {
	if (this->dimensiune==0)
        return true;
    return false;
}

// prima pozitie din LO
Iterator LO::prim() const {
	/* de adaugat */
	return Iterator(*this);
}

// returnare element de pe pozitia curenta
//arunca exceptie daca poz nu e valid
TElement LO::element(Iterator poz) const {
	/* de adaugat */
	return -1;
}

//sterge element de pe o pozitie poz si returneaza elementul sters
//dupa stergere poz e pozitionat pe elementul de dupa cel sters
//arunca exceptie daca poz nu e valid
TElement LO::sterge(Iterator& poz) {
	/* de adaugat */
	return -1;
}


// cauta element si returneaza prima pozitie pe care apare (sau iterator invalid)
Iterator LO::cauta(TElement e) const{
	/* de adaugat */
	return Iterator(*this);
}

// adaugare element in LO
void LO::adauga(TElement e) {
	/* de adaugat */
}


//destructor
LO::~LO() {
	/* de adaugat */
}
