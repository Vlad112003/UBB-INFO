#include "VectorDinamic.h"
#include "IteratorVectorDinamic.h"
#include <stdexcept>

using namespace std;


//daca vectorul este plin, dubleaza capacitatea si copiaza elementele
//Complexitate: theta(n);
void VectorDinamic::redim() {
    TElem *eNou = new int[2 * cp];
    for (int i = 0; i < n; i++)
        eNou[i] = e[i];
    cp = 2 * cp;
    delete[] e;
    e = eNou;
}

//constructor
//Complexitate: theta(1)
VectorDinamic::VectorDinamic(int cp) {
    this->cp = cp;
    e = new TElem[cp];
    this->n = 0;
}

//destructor
//Complexitate: theta(1)
VectorDinamic::~VectorDinamic() {
    delete[] e;
}

//returneaza dimensiunea vectorului
//Complexitate: theta(1)
int VectorDinamic::dim() const {
    return n;
}

//returneaza elementul de pe pozitia i
//Complexitate: theta(1)
TElem VectorDinamic::element(int i) const {
    if (i < 0 || i >= n)
        throw out_of_range("Index out of range");
    return e[i];
}

//adauga un element la sfarsitul vectorului
//Complexitate: theta(1);
void VectorDinamic::adaugaSfarsit(TElem e) {
    if (n == cp)
        redim();
    this->e[n++] = e;
}

//adauga un element pe pozitia i
//Complexitate: O(n)
void VectorDinamic::adauga(int i, TElem e) {
    if (i < 0 || i > n)
        throw out_of_range("Index out of range");
    if (n == cp)
        redim();
    for (int j = n; j > i; j--)
        this->e[j] = this->e[j - 1];
    this->e[i] = e;
    n++;
}

//modifica elementul de pe pozitia i si returneaza vechea valoare
//Complexitate: theta(1)
TElem VectorDinamic::modifica(int i, TElem e) {
    if (i < 0 || i >= n)
        throw out_of_range("Index out of range");
    TElem old = this->e[i];
    this->e[i] = e;
    return old;
}

//sterge elementul de pe pozitia i si returneaza elementul sters
//Complexitate: O(n)
TElem VectorDinamic::sterge(int i) {
    if (i < 0 || i >= n)
        throw out_of_range("Index out of range");
    TElem removed = this->e[i];
    for (int j = i; j < n - 1; j++)
        this->e[j] = this->e[j + 1];
    n--;
    return removed;
}

//verifica daca toate elementele din vector sunt unice
//returneaza true daca sunt
//returneaza false daca elementele nu sunt unice
//daca primele 2 elemente nu sunt unice, nu se parcurge tot vectorul
//Complexitate best case: theta(1)
//Complexitate worst case: theta(n^2)
//Complexitate average case: theta(n^2)
//Complexitate generala: O(n^2)
bool VectorDinamic::suntUnice() const {
    for ( int i = 0; i < this-> n - 2; i ++ ) //i parcurge vectorul pana la penultimul element, Complexitate: theta(n)

        for ( int j = i + 1; j < this-> n - 1; j ++) //j parcurge vectorul de la elementul urmator lui i pana la ultimul element, Complexitate: theta(n)

            if (element(i) == element(j))   //daca elementele de pe pozitiile i si j sunt egale, Complexitate: theta(1)

                return false; //returneaza false

    return true; //daca nu s-a gasit nicio pereche de elemente egale, returneaza true
}

/*suntUnice(vectorDinamic v)
 * pre: v - vectorDinamic
 * post: true daca toate elementele sunt unice, false altfel
 * daca v este vid, returneaza true
 * daca v are un singur element, returneaza true
 * daca v are 2 elemente, returneaza true daca sunt diferite, false altfel
 * daca v are mai mult de 2 elemente, returneaza true daca toate elementele sunt unice, false altfel.
 *
 * Alg suntUnice(vectorDinamic v)
 *   pentru i <- 0, v.n - 2 executa
 *     pentru j <- i + 1, v.n - 1 executa
 *        daca v.e(i) == v.e(j) atunci
 *           return false
 *        sfDaca
 *     sfPentru
 *   sfPentru
 *   return true
 *sfAlg
 */


//returneaza un iterator pentru vector
//Complexitate: theta(1)
IteratorVectorDinamic VectorDinamic::iterator() {
    return IteratorVectorDinamic(*this);
}