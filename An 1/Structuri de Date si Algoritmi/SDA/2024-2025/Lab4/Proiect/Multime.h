//Multime.h

#pragma once

typedef int TElem;
#define NIL -1
#define CAPACITY 10

class IteratorMultime;

class Multime {
private:
    // Reprezentarea ca lista dublu inlantuita folosind tablouri
    TElem* elements;  // vectorul de elemente
    int* next;        // vectorul de legaturi catre urmatorul element
    int* prev;        // vectorul de legaturi catre elementul anterior

    int capacity;     // capacitatea actuala a tablourilor
    int size;         // numarul de elemente din multime
    int head;         // primul element din lista
    int firstEmpty;   // primul element liber din reprezentare

    // functii pentru gestionarea alocarii
    void initialize();
    int allocate();
    void free(int position);
    void resize();

    friend class IteratorMultime;

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

    //intoarce numarul de elemente din multime;
    int dim() const;

    //verifica daca multimea e vida;
    bool vida() const;

    //returneaza un iterator pe multime
    IteratorMultime iterator() const;

    // destructorul multimii
    ~Multime();

    void reuniune(Multime& m);
};