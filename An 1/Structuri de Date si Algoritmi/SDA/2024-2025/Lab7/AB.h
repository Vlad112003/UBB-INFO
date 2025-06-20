#pragma once
#include <string>
using namespace std;

typedef int TElem;

class AB;

class IteratorAB;

class Nod {
public:
    TElem e;
    Nod* st;
    Nod* dr;

    /// \fn Nod::Nod(TElem e)
    /// \brief Constructor pentru nodul arborelui
    /// \param e valoarea elementului din nod
    Nod(TElem e) : e(e), st(nullptr), dr(nullptr) {}
};

class AB {

private:
    Nod* rad;
    
    /// \fn Nod* AB::copiaza(Nod* nod) const
    /// \brief Copiaza recursiv un subarbore
    /// \param nod radacina subarborelui de copiat
    /// \return pointer catre radacina copiata
    /// \complexity O(n), n = numarul de noduri din subarbore
    Nod* copiaza(Nod* nod) const;
    
    /// \fn void AB::distruge(Nod* nod)
    /// \brief Elibereaza recursiv memoria unui subarbore
    /// \param nod radacina subarborelui de distrus
    /// \complexity O(n), n = numarul de noduri din subarbore
    void distruge(Nod* nod);

public:
    friend class IteratorAB;
    friend class IteratorPreordine;
    friend class IteratorInordine;
    friend class IteratorPostordine;
    friend class IteratorLatime;

    /// \fn AB::AB()
    /// \brief Constructor implicit (arbore vid)
    /// \complexity O(1)
    AB();

    /// \fn AB::AB(const AB& ab)
    /// \brief Constructor de copiere
    /// \param ab arborele de copiat
    /// \complexity O(n), n = numarul de noduri din ab
    AB(const AB& ab);

    /// \fn AB::AB(TElem elem)
    /// \brief Creeaza un arbore binar redus la o frunza
    /// \param elem valoarea frunzei
    /// \complexity O(1)
    AB(TElem elem);

    /// \fn AB::AB(const AB& st, TElem elem, const AB& dr)
    /// \brief Creeaza un arbore binar cu subarbore stang, element si subarbore drept
    /// \param st subarborele stang
    /// \param elem valoarea radacinii
    /// \param dr subarborele drept
    /// \complexity O(n), n = numarul total de noduri din st si dr
    AB(const AB& st, TElem elem, const AB& dr);

    /// \fn void AB::adaugaSubSt(const AB& st)
    /// \brief Adauga subarborele stang
    /// \param st subarborele de adaugat
    /// \throws std::exception daca arborele este vid
    /// \complexity O(n), n = numarul de noduri din st
    void adaugaSubSt(const AB& st);

    /// \fn void AB::adaugaSubDr(const AB& dr)
    /// \brief Adauga subarborele drept
    /// \param dr subarborele de adaugat
    /// \throws std::exception daca arborele este vid
    /// \complexity O(n), n = numarul de noduri din dr
    void adaugaSubDr(const AB& dr);

    /// \fn bool AB::vid() const
    /// \brief Verifica daca arborele este vid
    /// \return true daca arborele este vid, altfel false
    /// \complexity O(1)
    bool vid() const;

    /// \fn TElem AB::element() const
    /// \brief Acceseaza elementul din radacina
    /// \throws std::exception daca arborele este vid
    /// \return valoarea din radacina
    /// \complexity O(1)
    TElem element() const;

    /// \fn AB AB::stang() const
    /// \brief Returneaza subarborele stang
    /// \throws std::exception daca arborele este vid
    /// \return subarborele stang (copie)
    /// \complexity O(n), n = numarul de noduri din subarbore
    AB stang() const;

    /// \fn AB AB::drept() const
    /// \brief Returneaza subarborele drept
    /// \throws std::exception daca arborele este vid
    /// \return subarborele drept (copie)
    /// \complexity O(n), n = numarul de noduri din subarbore
    AB drept() const;

    /// \fn IteratorAB* AB::iterator(string ordine) const
    /// \brief Creeaza un iterator pentru arbore
    /// \param ordine tipul de parcurgere ("preordine", "inordine", "postordine", "latime")
    /// \return pointer la iteratorul creat
    /// \complexity O(h), h = inaltimea arborelui (initializare iterator)
    IteratorAB* iterator(string ordine) const;

    /// \fn AB::~AB()
    /// \brief Destructorul arborelui binar
    /// \complexity O(n), n = numarul de noduri din arbore
    ~AB();


    TElem maxim() const;
};
