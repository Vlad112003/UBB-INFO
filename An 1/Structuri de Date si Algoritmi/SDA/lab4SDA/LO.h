#pragma once
#include <vector>
#include <exception>
using std::vector;
class Iterator;

typedef int TComparabil;
typedef TComparabil TElement;

typedef bool (*Relatie)(TElement, TElement);

#define NULL_TELEMENT -1
#define INIT_SIZE 2

class Node{
public:
    TElement data;//elementul din nod
    Node* next;//pointer la urmatorul nod

    Node()
    {
        data = 0;///initializam elementul cu 0
        next = nullptr;///initializam pointerul la urmatorul nod cu nullptr
    }

    Node(int d)
    {
        data = d;///initializam elementul cu d
        next = nullptr;///initializam pointerul la urmatorul nod cu nullptr
    }
};

class LO {
private:
    friend class Iterator;
private:
    TElement* ls;///vectorul de elemente
    int* urm;
    int* prec;
    Node* head;///capul listei de pozitii libere
    int lenFree;
    int first;
    int cap;
    int len;
    Relatie r;


    /* aici reprezentarea */
public:
    // constructor
    LO (Relatie r);

    void redim();

    // returnare dimensiune
    int dim() const;

    // verifica daca LO e vida
    bool vida() const;

    // prima pozitie din LO
    Iterator prim() const;

    // returnare element de pe pozitia curenta
    //arunca exceptie daca poz nu e valid
    TElement element(Iterator poz) const;

    // adaugare element in LO a.i. sa se pastreze ordinea intre elemente
    void adauga(TElement e);

    // sterge element de pe o pozitie poz si returneaza elementul sters
    //dupa stergere poz e pozitionat pe elementul de dupa cel sters
    //arunca exceptie daca poz nu e valid
    TElement sterge(Iterator& poz);

    // cauta element si returneaza prima pozitie pe care apare (sau iterator invalid)
    Iterator cauta(TElement e) const;

    //Iterator ultimul();

    //destructor
    ~LO();

};
