#include "IteratorColectie.h"
#include <exception>
using namespace std;

/// \fn IteratorColectie::IteratorColectie(const Colectie& c)
/// \brief constructor pentru iteratorul colectiei
/// Initializeaza iteratorul pentru colectia `c` si seteaza pozitia curenta la primul element valid.
/// \param c colectia pentru care se creeaza iteratorul
/// \complexity theta(1)
IteratorColectie::IteratorColectie(const Colectie& c) : col(c) {
    prim();
}

/// \fn void IteratorColectie::prim()
/// \brief seteaza iteratorul la primul element valid din colectie
/// Cauta primul element valid in tabela de dispersie, ignorand elementele sterse logic.
/// \complexity O(n), unde n este capacitatea tabelei de dispersie
void IteratorColectie::prim() {
    currentPosition = 0;
    while (currentPosition < col.capacity &&
           (col.table[currentPosition] == NULL_TELEM ||
            col.deleted[currentPosition])) {
        currentPosition++;
    }
}

/// \fn void IteratorColectie::urmator()
/// \brief avanseaza iteratorul la urmatorul element valid din colectie
/// Daca iteratorul nu este valid, arunca o exceptie. Cauta urmatorul element valid in tabela de dispersie.
/// \complexity O(n), unde n este capacitatea tabelei de dispersie
void IteratorColectie::urmator() {
    if (!valid()) throw exception();
    currentPosition++;
    while (currentPosition < col.capacity &&
           (col.table[currentPosition] == NULL_TELEM ||
            col.deleted[currentPosition])) {
        currentPosition++;
    }
}

/// \fn bool IteratorColectie::valid() const
/// \brief verifica daca iteratorul este valid
/// Verifica daca pozitia curenta este in limitele tabelei de dispersie si daca elementul de la
/// pozitia curenta nu este NULL_TELEM si nu este marcat ca sters.
/// \return returneaza true daca iteratorul este valid, altfel false
/// \complexity theta(1)
bool IteratorColectie::valid() const {
    return currentPosition < col.capacity &&
           col.table[currentPosition] != NULL_TELEM &&
           !col.deleted[currentPosition];
}

/// \fn TElem IteratorColectie::element() const
/// \brief returneaza elementul curent al iteratorului
/// Daca iteratorul nu este valid, arunca o exceptie. Returneaza elementul de la pozitia curenta din tabela de dispersie.
/// \return returneaza elementul curent al iteratorului
/// \complexity theta(1)
TElem IteratorColectie::element() const {
    if (!valid()) throw exception();
    return col.table[currentPosition];
}
