#pragma once
#include <set>
#define NULL_TELEM -1
typedef int TElem;
//typedef std::multiset <TElem> colectie;

class IteratorColectie;

class Colectie
{
    friend class IteratorColectie;

private:
    /* aici e reprezentarea */
    TElem* items;
    int size;
    int capacity;
    TElem* fr;

public:



    //constructorul implicit
    Colectie();

    //adauga un element in colectie
    void adauga(TElem e);

    //sterge o aparitie a unui element din colectie
    //returneaza adevarat daca s-a putut sterge
    bool sterge(TElem e);

    //verifica daca un element se afla in colectie
    bool cauta(TElem elem) const;

    //returneaza numar de aparitii ale unui element in colectie
    int nrAparitii(TElem elem) const;


    //intoarce numarul de elemente din colectie;
    int dim() const;

    //verifica daca colectia e vida;
    bool vida() const;

    //returneaza un iterator pe colectie
    IteratorColectie iterator() const;

    // destructorul colectiei
    ~Colectie();

};

