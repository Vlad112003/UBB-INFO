#pragma once
#include "Colectie.h"

class Colectie;
typedef int TElem;

class IteratorColectie
{
    friend class Colectie;
private:
    /*
    Constructorul primeste o referinta catre Container
    Iteratorul va referi primul element din container

    Complexitate overall: theta(1)
    */
    IteratorColectie(const Colectie& c);

    // Contine o referinta catre containerul pe care il itereaza
    const Colectie& col;
    int indexElement, indexFrecventa;


public:
    /*
    Reseteaza pozitia iteratorului la inceputul containerului

    Complexitate overall: theta(1)
    */
    void prim();

    /*
    Muta iteratorul in container
    Arunca exceptie daca iteratorul nu e valid

    Complexitate overall: theta(1)
    */
    void urmator();

    /*
    Verifica daca iteratorul e valid (indica un element al containerului)

    Complexitate overall: theta(1)
    */
    bool valid() const;

    /*
    Returneaza valoarea elementului din container referit de iterator
    Arunca exceptie daca iteratorul nu e valid

    Complexitate overall: theta(1)
    */
    TElem element() const;
};

