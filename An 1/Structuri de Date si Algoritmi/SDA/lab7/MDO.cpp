#include "IteratorMDO.h"
#include "MDO.h"
#include <iostream>
#include <vector>

#include <exception>
using namespace std;

//8.TAD MultiDictionar Ordonat –reprezentare sub forma unui 
// ABC reprezentat inlantuit pe tablou
//complexitate theta(capacitate)
MDO::MDO(Relatie r) : r(r) {
    /* de adaugat */
    this->abc.capacitate = cap;
    this->abc.noduri = new NodAbc[this->abc.capacitate];
    this->abc.size = 0;
    this->abc.radacina = 0;
    this->abc.primLiber = 0;
    for (int i = 0; i < cap; i++) {
        this->abc.noduri[i].info = nullValue;
        this->abc.noduri[i].stanga = null;
        this->abc.noduri[i].dreapta = null;
    }
}

//complexitate theta(1)
void MDO::actPrimLiber() {
    if (this->abc.noduri[this->abc.primLiber].info != nullValue)
        this->abc.primLiber++;

}

//complexitate theta(capacitate)
void MDO::resize() {
    NodAbc* aux = new NodAbc[this->abc.capacitate * 2];
    for (int i = 0; i < this->abc.capacitate; i++) {
        aux[i] = this->abc.noduri[i];
    }
    for (int i = this->abc.capacitate; i < this->abc.capacitate * 2; i++) {
        aux[i].info = nullValue;
        aux[i].stanga = null;
        aux[i].dreapta = null;
    }
    actPrimLiber();
    this->abc.capacitate = this->abc.capacitate * 2;
    delete[]this->abc.noduri;
    this->abc.noduri = aux;

}

//Complexitate O(n); n-inaltimea subarborelui
int MDO::adauga_rec(int nod, TElem e) {
    if (nod == null) {//arbore vid
        this->abc.noduri[this->abc.primLiber].info = e;
        int old = this->abc.primLiber;
        actPrimLiber();
        return old;
    }
    else if (!this->r(this->abc.noduri[nod].info.first, e.first)) {//nu respecta relatia
        this->abc.noduri[nod].stanga = adauga_rec(this->abc.noduri[nod].stanga, e);
        return nod;
    }
    else {
        this->abc.noduri[nod].dreapta = adauga_rec(this->abc.noduri[nod].dreapta, e);
        return nod;
    }
}

//complexitate O(n)
void MDO::adauga(TCheie c, TValoare v) {
    /* de adaugat */
    if (vid()) {
        this->abc.noduri[0].info = std::make_pair(c, v);
        this->abc.primLiber = 1;
        this->abc.size++;
        return;
    }
    if (this->abc.size == this->abc.capacitate - 1)
        resize();
    this->adauga_rec(this->abc.radacina, std::make_pair(c, v));
    this->abc.size++;

}

//complexitate O(n)
vector<TValoare> MDO::cauta(TCheie c) const {
    /* de adaugat */
    vector<TValoare>valori;
    int curent = this->abc.radacina;
    while (curent != null) {
        if (this->r(this->abc.noduri[curent].info.first, c)) {
            if (c == this->abc.noduri[curent].info.first)
                valori.push_back(this->abc.noduri[curent].info.second);
            curent = this->abc.noduri[curent].dreapta;
        }
        else {
            curent = this->abc.noduri[curent].stanga;
        }
    }
    return valori;
}

//complexitate O(n)
int MDO::minim(int nod) {//minimul din arbore
    int curent = nod;
    while (this->abc.noduri[curent].stanga != null) {
        curent = this->abc.noduri[curent].stanga;
    }
    return curent;

}

//complexitate O(n)
int MDO::sterge_rec(int nod, TElem e, bool& found) {
    if (nod == null) {
        return null;
    }
    if (this->abc.noduri[nod].info == e) {
        found = true;
        if (this->abc.noduri[nod].stanga == null && this->abc.noduri[nod].dreapta == null)
            return null;

        else if (this->abc.noduri[nod].stanga == null) {
            return this->abc.noduri[nod].dreapta;
        }
        else if (this->abc.noduri[nod].dreapta == null) {
            return this->abc.noduri[nod].stanga;
        }
        else {
            int min = minim(this->abc.noduri[nod].dreapta);
            this->abc.noduri[nod].info = this->abc.noduri[min].info;
            this->abc.noduri[nod].dreapta = sterge_rec(this->abc.noduri[nod].dreapta,this->abc.noduri[min].info, found);
            return nod;
        }
    }
    else if (!r(this->abc.noduri[nod].info.first, e.first)) {
        this->abc.noduri[nod].stanga = sterge_rec(this->abc.noduri[nod].stanga, e, found);
        return nod;
    }
    else {
        this->abc.noduri[nod].dreapta = sterge_rec(this->abc.noduri[nod].dreapta, e, found);
        return nod;
    }

}

//complexitate O(n), n-inaltimea
bool MDO::sterge(TCheie c, TValoare v) {
    /* de adaugat */
    bool found = false;
    sterge_rec(this->abc.radacina, std::make_pair(c, v), found);
    if (!found)
        return false;
    else {
        this->abc.size--;
        return true;
    }
}

//complexitate theta(1)
int MDO::dim() const {
    /* de adaugat */
    return this->abc.size;
}

//O(n^2) n de la cautare pt fiecare nod
TCheie MDO::cheieMaxima() const
{
    int max = 0;
    TCheie c;
    for (int i = 0; i < abc.size; i++)
    {
        vector<TValoare>values = cauta(abc.noduri[i].info.first);
        if (values.size() == 0) return NULL_TCHEIE;
        if (values.size() > max) {
            max = values.size();
            c = abc.noduri[i].info.first;
        }
    }
    return c;


}


//complexitate theta(1)
bool MDO::vid() const {
    /* de adaugat */
    return this->abc.size == 0;
}

//complexitate O(n)
IteratorMDO MDO::iterator() const {
    return IteratorMDO(*this);
}






//complexitate O(n)
vector<TValoare> MDO::stergeValoriPentruCheie(TCheie cheie) {
    vector<TValoare> valoriEliminate;

    int nodCurent = abc.radacina;
    int nodParinte = -1;
    bool gasit = false;

    while (nodCurent != null && !gasit) {
        if (cheie == abc.noduri[nodCurent].info.first) {
            gasit = true;
        } else if (r(cheie, abc.noduri[nodCurent].info.first)) {
            nodParinte = nodCurent;
            nodCurent = abc.noduri[nodCurent].stanga;
        } else {
            nodParinte = nodCurent;
            nodCurent = abc.noduri[nodCurent].dreapta;
        }
    }

    if (!gasit) {
        return valoriEliminate;
    }

    while (nodCurent != null && abc.noduri[nodCurent].info.first == cheie) {
        valoriEliminate.push_back(abc.noduri[nodCurent].info.second);

        if (abc.noduri[nodCurent].stanga != null) {
            int maximStanga = minim(abc.noduri[nodCurent].dreapta);
            abc.noduri[nodCurent].info = abc.noduri[maximStanga].info;
            nodCurent = maximStanga;
            nodParinte = abc.noduri[nodCurent].dreapta;
        } else {
            if (nodParinte == -1) {
                abc.radacina = abc.noduri[nodCurent].dreapta;
            } else if (abc.noduri[nodCurent].info == abc.noduri[nodParinte].info) {
                abc.noduri[nodParinte].stanga = abc.noduri[nodCurent].dreapta;
            } else {
                abc.noduri[nodParinte].dreapta = abc.noduri[nodCurent].dreapta;
            }

            int nodEliminat = nodCurent;
            nodCurent = abc.noduri[nodCurent].dreapta;

            if (nodEliminat == abc.primLiber - 1) {
                abc.primLiber--;
            } else {
                abc.noduri[nodEliminat] = abc.noduri[abc.primLiber - 1];
                abc.primLiber--;
            }
        }
    }

    abc.size -= valoriEliminate.size();

    return valoriEliminate;
}




//complexitate theta(1)
MDO::~MDO() {
    /* de adaugat */
    delete[] this->abc.noduri;
}