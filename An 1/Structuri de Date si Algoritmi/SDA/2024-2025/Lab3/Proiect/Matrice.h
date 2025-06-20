#pragma once
#include <utility>

typedef int TElem;

#define NULL_TELEMENT 0

// Forward declarations
class IteratorMatrice;

struct Node {
    int line;
    int column;
    TElem value;
    Node* next;
};

class Matrice {
private:
    //linii
    int L;
    //coloane
    int C;
    //primul nod din lista
    Node* head;

    //returneaza nodul de pe pozitia (i,j)
    Node* find(int i, int j) const;

public:
    // Constructor
    // arunca exceptie in cazul in care nrLinii sau nrColoane <= 0
    Matrice(int nrLinii, int nrColoane);

    // Destructor
    ~Matrice();

    // returneaza elementul de pe pozitia (i,j)
    TElem element(int i, int j) const;

    // returneaza numarul de linii
    int nrLinii() const;

    // returneaza numarul de coloane
    int nrColoane() const;

    // modifica elementul de pe pozitia (i,j) si returneaza valoarea veche
    // arunca exceptie daca (i,j) nu e o pozitie valida in Matrice
    TElem modifica(int i, int j, TElem e);

    // creaza un iterator asupra elementelor nenule din Matrice
    // iteratorul va parcurge matricea pe coloane
    IteratorMatrice iterator() const;

    friend class IteratorMatrice;
};