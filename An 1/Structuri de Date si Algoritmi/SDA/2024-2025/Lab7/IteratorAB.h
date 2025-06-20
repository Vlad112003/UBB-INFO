#pragma once
#include "AB.h"
#include <stack>
#include <queue>

class IteratorAB {
public:
    friend class AB;

    /// \fn virtual void IteratorAB::prim()
    /// \brief Reseteaza iteratorul la inceputul containerului
    virtual void prim()=0;

    /// \fn virtual void IteratorAB::urmator()
    /// \brief Muta iteratorul la urmatorul element
    /// \throws std::exception daca iteratorul nu e valid
    virtual void urmator()=0;

    /// \fn virtual TElem IteratorAB::element()
    /// \brief Returneaza valoarea elementului curent
    /// \throws std::exception daca iteratorul nu e valid
    /// \return valoarea elementului curent
    virtual TElem element() = 0;

    /// \fn virtual bool IteratorAB::valid()
    /// \brief Verifica daca iteratorul este valid
    /// \return true daca iteratorul este valid, altfel false
    virtual bool valid() = 0;
};

class IteratorPreordine: public IteratorAB{
    friend class AB;
private:
    IteratorPreordine(const AB& _ab);

    const AB& ab;
    std::stack<Nod*> stiva;
    Nod* curent;

public:
    /// \fn void IteratorPreordine::prim()
    /// \brief Reseteaza iteratorul la inceputul parcurgerii preordine
    void prim() override;

    /// \fn void IteratorPreordine::urmator()
    /// \brief Muta iteratorul la urmatorul element in preordine
    /// \throws std::exception daca iteratorul nu e valid
    void urmator() override;

    /// \fn bool IteratorPreordine::valid()
    /// \brief Verifica daca iteratorul este valid
    /// \return true daca iteratorul este valid, altfel false
    bool valid() override;

    /// \fn TElem IteratorPreordine::element()
    /// \brief Returneaza valoarea elementului curent
    /// \throws std::exception daca iteratorul nu e valid
    /// \return valoarea elementului curent
    TElem element() override;
};

class IteratorInordine: public IteratorAB{
   friend class AB;
private:
    IteratorInordine(const AB& _ab);

    const AB& ab;
    std::stack<Nod*> stiva;
    Nod* curent;

public:
    /// \fn void IteratorInordine::prim()
    /// \brief Reseteaza iteratorul la inceputul parcurgerii inordine
    void prim() override;

    /// \fn void IteratorInordine::urmator()
    /// \brief Muta iteratorul la urmatorul element in inordine
    /// \throws std::exception daca iteratorul nu e valid
    void urmator() override;

    /// \fn bool IteratorInordine::valid()
    /// \brief Verifica daca iteratorul este valid
    /// \return true daca iteratorul este valid, altfel false
    bool valid() override;

    /// \fn TElem IteratorInordine::element()
    /// \brief Returneaza valoarea elementului curent
    /// \throws std::exception daca iteratorul nu e valid
    /// \return valoarea elementului curent
    TElem element() override;
};

class IteratorPostordine: public IteratorAB{
   friend class AB;
private:
    IteratorPostordine(const AB& _ab);
    const AB& ab;
    std::stack<Nod*> stiva;
    Nod* curent;
    Nod* ultim_vizitat;

public:
    /// \fn void IteratorPostordine::prim()
    /// \brief Reseteaza iteratorul la inceputul parcurgerii postordine
    void prim() override;

    /// \fn void IteratorPostordine::urmator()
    /// \brief Muta iteratorul la urmatorul element in postordine
    /// \throws std::exception daca iteratorul nu e valid
    void urmator() override;

    /// \fn bool IteratorPostordine::valid()
    /// \brief Verifica daca iteratorul este valid
    /// \return true daca iteratorul este valid, altfel false
    bool valid() override;

    /// \fn TElem IteratorPostordine::element()
    /// \brief Returneaza valoarea elementului curent
    /// \throws std::exception daca iteratorul nu e valid
    /// \return valoarea elementului curent
    TElem element() override;
};

class IteratorLatime: public IteratorAB{
   friend class AB;
private:
    IteratorLatime(const AB& _ab);
    const AB& ab;
    std::queue<Nod*> coada;

public:
    /// \fn void IteratorLatime::prim()
    /// \brief Reseteaza iteratorul la inceputul parcurgerii pe latime
    void prim() override;

    /// \fn void IteratorLatime::urmator()
    /// \brief Muta iteratorul la urmatorul element in parcurgerea pe latime
    /// \throws std::exception daca iteratorul nu e valid
    void urmator() override;

    /// \fn bool IteratorLatime::valid()
    /// \brief Verifica daca iteratorul este valid
    /// \return true daca iteratorul este valid, altfel false
    bool valid() override;

    /// \fn TElem IteratorLatime::element()
    /// \brief Returneaza valoarea elementului curent
    /// \throws std::exception daca iteratorul nu e valid
    /// \return valoarea elementului curent
    TElem element() override;
};
