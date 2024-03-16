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

Multime::Multime() {
    /* de adaugat */
    prim = nullptr;
    ultim = nullptr;
    size = 0;
}//O(1)


bool Multime::adauga(TElem elem) {
    /* de adaugat */
    Nod* newNod = new Nod;
    newNod->urm = nullptr;
    newNod->data = elem;

    // Dacă lista este goală
    if (prim == nullptr) {
        prim = newNod;
        size++;
        return true;
    }

    // Verificăm dacă elementul există deja în mulțime
    if (cauta(elem))
        return false;

    // Adăugăm elementul la sfârșitul listei
    Nod* nodCurent = prim;
    while (nodCurent->urm != nullptr) {
        nodCurent = nodCurent->urm;
    }
    nodCurent->urm = newNod;
    size++;
    return true;
}//O(n)

bool Multime::sterge(TElem elem) {
    /* de adaugat */
    // Dacă elementul nu există în listă, nu îl putem șterge
    if (!cauta(elem))
        return false;

    // Elementul există, putem să îl ștergem
    Nod* p = prim;
    Nod* anterior = nullptr;
    while (p != nullptr) {
        if (p->data == elem)
            break;
        anterior = p;
        p = p->urm;
    }
    // În p avem adresa nodului care trebuie șters

    // Caz 1: lista are un singur element
    if (prim == ultim && prim == p) {
        delete p;
        prim = ultim = nullptr;
        size--;
        return true;
    }

    // Elementul este primul element
    if (p == prim) {
        prim = prim->urm;
        delete p;
        size--;
        return true;
    }

    // Elementul este ultimul
    if (p == ultim) {
        ultim = anterior;
        ultim->urm = nullptr;
        delete p;
        size--;
        return true;
    }

    // Elementul nu este nici primul, nici ultimul
    anterior->urm = p->urm;
    delete p;
    size--;
    return true;
}//O(n) n=size


bool Multime::cauta(TElem elem) const {
    /* de adaugat */
    //daca lista e vida atunci sigur nu se afla in ea  0(1)
    if (size == 0)
        return false;
    //lista nu e vida => o parcurgem si cand dam de element returnam true 0(n)

    Nod* p = prim;
    while (p != nullptr)
    {
        if (p->data == elem)
            return true;
        p = p->urm;
    }
    //daca iese din while => nu a gasit elementul
    return false;
}//O(n) n=size;


int Multime::dim() const {
    return size;
}//O(1)



bool Multime::vida() const {
    /* de adaugat */
    if (size == 0)
        return true;
    else
        return false;
}//0(1)

IteratorMultime Multime::iterator() const {
    return IteratorMultime(*this);
}


Multime::~Multime() {
    /* de adaugat */
    Nod* curent;
    curent = prim;
    while (curent != nullptr)
    {
        Nod* next = curent->urm;
        delete curent;
        curent = next;
    }
    size = 0;
    prim = ultim = nullptr;
}//0(1)






