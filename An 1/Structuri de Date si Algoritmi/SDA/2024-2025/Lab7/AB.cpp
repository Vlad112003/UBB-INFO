#include "AB.h"
#include "IteratorAB.h"
#include <exception>
#include <string>


Nod* AB::copiaza(Nod* nod) const {
    if (nod == nullptr) {
        return nullptr;
    }
    Nod* nou = new Nod(nod->e);
    nou->st = copiaza(nod->st);
    nou->dr = copiaza(nod->dr);
    return nou;
}

// metoda utilitara pentru a elibera memoria pentru un nod si toti descendentii sai
void AB::distruge(Nod* nod) {
    if (nod != nullptr) {
        distruge(nod->st);
        distruge(nod->dr);
        delete nod;
    }
}

AB::AB() {
    rad = nullptr;
}

AB::AB(const AB& ab) {
    rad = copiaza(ab.rad);
}

AB::AB(TElem e) {
    rad = new Nod(e);
}

AB::AB(const AB& st, TElem e, const AB& dr) {
    rad = new Nod(e);
    rad->st = copiaza(st.rad);
    rad->dr = copiaza(dr.rad);
}

void AB::adaugaSubSt(const AB& st) {
    if (vid()) {
        throw std::exception();
    }
    
    // eliberam memoria subarborelui stang existent daca exista
    distruge(rad->st);
    
    // adaugam noul subarbore stang
    rad->st = copiaza(st.rad);
}

void AB::adaugaSubDr(const AB& dr) {
    if (vid()) {
        throw std::exception();
    }
    
    // eliberam memoria subarborelui drept existent daca exista
    distruge(rad->dr);
    
    // adaugam noul subarbore drept
    rad->dr = copiaza(dr.rad);
}

TElem AB::element() const {
    if (vid()) {
        throw std::exception();
    }
    return rad->e;
}

AB AB::stang() const {
    if (vid()) {
        throw std::exception();
    }
    
    AB stanga;
    if (rad->st != nullptr) {
        stanga.rad = copiaza(rad->st);
    }
    
    return stanga;
}

AB AB::drept() const {
    if (vid()) {
        throw std::exception();
    }
    
    AB dreapta;
    if (rad->dr != nullptr) {
        dreapta.rad = copiaza(rad->dr);
    }
    
    return dreapta;
}

AB::~AB() {
    distruge(rad);
    rad = nullptr;
}

bool AB::vid() const {
    return rad == nullptr;
}



IteratorAB* AB::iterator(string s) const {
    if (s=="preordine")
        return new IteratorPreordine(*this);
    if (s=="inordine")
        return new IteratorInordine(*this);
    if (s=="postordine")
        return new IteratorPostordine(*this);
    if (s=="latime")
        return new IteratorLatime(*this);
    return nullptr;
}


/// \fn AB::maxim()
/// \brief Returneaza valoarea maxima din arbore
/// \return valoarea maxima din arbore sau -1 daca arborele este vid
/// \complexity best case: theta(1), worst case: O(n), average case: O(n), unde n este numarul de noduri din arbore
TElem AB::maxim() const {
    if (vid()) {
        return -1; // sau arunca o exceptie, in functie de cerinte
    }

    TElem maxVal = rad->e;
    std::stack<Nod*> stiva;
    stiva.push(rad);

    while (!stiva.empty()) {
        Nod* nod = stiva.top();
        stiva.pop();

        if (nod->e > maxVal) {
            maxVal = nod->e;
        }

        if (nod->dr != nullptr) {
            stiva.push(nod->dr);
        }
        if (nod->st != nullptr) {
            stiva.push(nod->st);
        }
    }

    return maxVal;
}

/* maxim(AB ab)
 * pre: ab - un arbore binar
 * post: returneaza valoarea maxima din arbore sau -1 daca vid(ab)
 * parcurge toate nodurile si returneaza maximul gasit
 *
 * Alg maxim(ab)
 *   daca vid(ab) atunci
 *     returneaza -1
 *   sfDaca
 *   maxVal <- ab.rad.e
 *   stiva <- stiva vida
 *   push(stiva, ab.rad)
 *   cat timp !vid(stiva) executa
 *     nod <- top(stiva)
 *     pop(stiva)
 *     daca nod.e > maxVal atunci
 *       maxVal <- nod.e
 *     sfDaca
 *     daca nod.dr != null atunci
 *       push(stiva, nod.dr)
 *     sfDaca
 *     daca nod.st != null atunci
 *       push(stiva, nod.st)
 *     sfDaca
 *   sfCatTimp
 *   returneaza maxVal
 * sfAlg
 */