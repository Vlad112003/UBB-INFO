#include "Colectie.h"
#include "IteratorColectie.h"
#include <exception>
#include <iostream>

using namespace std;


Colectie::Colectie() {

    this->size = 0;
    this->capacity=10;
    this->items = new TElem[capacity];
    this->fr = new int[capacity];
}


void Colectie::adauga(TElem elem) {
    if(this->size==this->capacity)
    {
        this->capacity = this->capacity*2;
        TElem* new_items = new TElem[this->capacity];
        for(int i = 0; i<this->size; i++)
            new_items[i]=items[i];
        delete[] items;
        items = new_items;
    }
    items[this->size] = elem;
    this->size++;


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
    /* de adaugat */
    return this->size;
}


bool Colectie::vida() const {
    /* de adaugat */
    if (this->size == 0)
        return true;
    else return false;
}

IteratorColectie Colectie::iterator() const {
    return  IteratorColectie(*this);
}


Colectie::~Colectie() {
    delete[] this->items;
}


