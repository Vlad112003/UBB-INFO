#pragma once

typedef int TElem;

typedef bool(*Relatie)(TElem, TElem);

//in implementarea operatiilor se va folosi functia (relatia) rel (de ex, pentru <=)
// va fi declarata in .h si implementata in .cpp ca functie externa colectiei
bool rel(TElem, TElem);

class IteratorMultime;

class Multime {

    friend class IteratorMultime;

private:
    /* aici e reprezentarea */
    int capacitateVector, dimensiune;

    //vectorul dinamic cu elemente de tip TElem
    TElem *vector;

    //pt redimensionare
    void redim();

    //pt cautarea elementului in vector
    int cautare(TElem elem) const;

    void select_sort();

    void quicksort(int *arr, int left, int right);

    int binarySearch(int *arr, int left, int right, TElem x) const;
public:
    //constructorul implicit
    Multime();

    //adauga un element in multime
    //returneaza adevarat daca elementul a fost adaugat (nu exista deja in multime)
    bool adauga(TElem e);

    //sterge un element din multime
    //returneaza adevarat daca elementul a existat si a fost sters
    bool sterge(TElem e);

    //verifica daca un element se afla in multime
    bool cauta(TElem elem) const;

    TElem return_element(int i) const;

    //intoarce numarul de elemente din multime;
    int dim() const;

    //verifica daca multimea e vida;
    bool vida() const;

    //returneaza un iterator pe multime
    IteratorMultime iterator() const;

    // destructorul multimii
    ~Multime();


};

