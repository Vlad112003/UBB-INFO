//IteratorMultime.h

#pragma once
#include "Multime.h"

class IteratorMultime {
private:
    //constructorul primeste o referinta catre container
    //iteratorul va referi primul element din container
    IteratorMultime(const Multime& m);

    //contine o referinta catre containerul pe care il itereaza
    const Multime& multime;

    //positia curenta in iterator
    int current;

    friend class Multime;

public:
    //reseteaza pozitia iteratorului la inceputul containerului
    void prim();

    //muta iteratorul in container
    //arunca exceptie daca iteratorul nu e valid
    void urmator();

    //verifica daca iteratorul e valid (indica un element al containerului)
    bool valid() const;

    //returneaza valoarea elementului din container referit de iterator
    //arunca exceptie daca iteratorul nu e valid
    TElem element() const;
};