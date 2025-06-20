#pragma once
#include "Matrice.h"
#include <utility>

class IteratorMatrice {
private:
    const Matrice& matrice;
    Node* currentNode;
    int currentColumn;

public:
    IteratorMatrice(const Matrice& m);

    void prim();
    void urmator();
    bool valid() const;
    TElem element() const;
    std::pair<int, int> pozitie() const;
};