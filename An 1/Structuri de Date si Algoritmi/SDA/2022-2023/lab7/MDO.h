#pragma once

#include <vector>
#define cap 10
#define null -9999999
#define nullValue std::make_pair(-9999999,-9999999)
#define NULL_TCHEIE 0

typedef int TCheie;
typedef int TValoare;

#include <utility>
typedef std::pair<TCheie, TValoare> TElem;

using namespace std;

class IteratorMDO;

typedef bool(*Relatie)(TCheie, TCheie);

typedef struct NodAbc {
    TElem info;
    int stanga, dreapta;
};

typedef struct ABC {
    NodAbc* noduri;
    int capacitate, radacina, size, primLiber;
};

class MDO {
    friend class IteratorMDO;
private:
    /* aici e reprezentarea */
    Relatie r;
    ABC abc;
    void resize();
    void actPrimLiber();
    int adauga_rec(int nod, TElem e);
    int minim(int node);
    int sterge_rec(int node, TElem, bool& gasit);
public:

    // constructorul implicit al MultiDictionarului Ordonat
    MDO(Relatie r);

    // adauga o pereche (cheie, valoare) in MDO
    void adauga(TCheie c, TValoare v);

    //cauta o cheie si returneaza vectorul de valori asociate
    vector<TValoare> cauta(TCheie c) const;

    //sterge o cheie si o valoare
    //returneaza adevarat daca s-a gasit cheia si valoarea de sters
    bool sterge(TCheie c, TValoare v);

    //returneaza numarul de perechi (cheie, valoare) din MDO
    int dim() const;

    //verifica daca MultiDictionarul Ordonat e vid
    bool vid() const;

    // se returneaza iterator pe MDO
    // iteratorul va returna perechile in ordine in raport cu relatia de ordine
    IteratorMDO iterator() const;

    // destructorul
    ~MDO();
    // cheie maxima
    TCheie cheieMaxima() const;

    vector<TValoare> stergeValoriPentruCheie(TCheie cheie);
};