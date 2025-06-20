#pragma once
#include "Colectie.h"

class IteratorColectie {
    friend class Colectie;
private:
    const Colectie& col;
    int currentPosition;

    IteratorColectie(const Colectie& c);

public:
    void prim();
    void urmator();
    bool valid() const;
    TElem element() const;
};
