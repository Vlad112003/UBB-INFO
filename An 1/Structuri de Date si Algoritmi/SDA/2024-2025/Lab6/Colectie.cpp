#include "Colectie.h"
#include "IteratorColectie.h"
#include <exception>
#include <cmath>

/// \fn int Colectie::hash(TElem e, int i) const
/// \brief calcuelaza hash-ul pentru un element `e` la pasul `i`
/// \param e elementul pentru care se calculeaza hash-ul
/// \param i pasul de incercare pentru a evita coliziunile
/// \return returneaza pozitia in tabela de dispersie pentru elementul `e` la pasul `i`
/// \complexity theta(1)
int Colectie::hash(TElem e, int i) const {
    return (h1(e) + i * h2(e)) % capacity;
}

/// \fn int Colectie::h1(TElem e) const
/// \param e elementul pentru care se calculeaza prima parte a hash-ului
/// \return returneaza prima parte a hash-ului, folosind o functie de dispersie bazata pe valoarea absoluta a elementului
//// \complexity theta(1)
int Colectie::h1(TElem e) const {
    return abs(e) % capacity;
}

/// \fn int Colectie::h2(TElem e) const
/// \param e elementul pentru care se calculeaza a doua parte a hash-ului
/// \return returneaza a doua parte a hash-ului, folosind o functie de dispersie care asigura ca nu se va intoarce 0
/// \complexity theta(1)
int Colectie::h2(TElem e) const {
    return 1 + (abs(e) % (capacity - 1));
}

/// \fn bool Colectie::vida() const
/// \brief verifica daca colectia este vida
/// \return returneaza true daca colectia nu contine niciun element, altfel false
/// \complexity theta(1)
bool Colectie::vida() const {
    return totalSize == 0;
}

/// \fn int Colectie::dim() const
/// \brief returneaza numarul de elemente din colectie
/// \return returneaza numarul total de elemente din colectie, inclusiv cele sterse logic
/// \complexity theta(1)
int Colectie::dim() const {
    return totalSize;
}

/// \fn Colectie::~Colectie()
/// \brief destructor pentru colectie
/// Elibereaza memoria alocata pentru tabela de dispersie si vectorul de stergere logica.
Colectie::~Colectie() {
    delete[] table;
    delete[] deleted;
}

/// \fn IteratorColectie Colectie::iterator() const
/// \brief creeaza un iterator pentru colectie
/// \return returneaza un iterator pentru colectie, care permite parcurgerea elementelor
/// \complexity theta(1)
IteratorColectie Colectie::iterator() const {
    return IteratorColectie(*this);
}

/// \fn void Colectie::resize()
/// \brief redimensioneaza tabela de dispersie si vectorul de stergere logica
/// Aloca o noua tabela de dispersie cu o capacitate dubla si rehash-eaza toate elementele existente.
/// \return nu returneaza nimic
/// \complexity O(n), unde n este numarul de elemente in colectie
void Colectie::resize() {
    int oldCapacity = capacity;
    TElem* oldTable = table;
    bool* oldDeleted = deleted;
    capacity = capacity * 2;
    // capacitatea este un numar prim pentru a reduce coliziunile din tabela de dispersie
    bool isPrime = false;
    while (!isPrime) {
        isPrime = true;
        for (int i = 2; i * i <= capacity; i++) {
            if (capacity % i == 0) {
                isPrime = false;
                break;
            }
        }
        if (!isPrime) capacity++;
    }
    table = new TElem[capacity];
    deleted = new bool[capacity];
    for (int i = 0; i < capacity; i++) {
        table[i] = NULL_TELEM;
        deleted[i] = false;
    }
    totalSize = 0;
    for (int i = 0; i < oldCapacity; i++) {
        if (oldTable[i] != NULL_TELEM && !oldDeleted[i]) {
            adauga(oldTable[i]);
        }
    }
    delete[] oldTable;
    delete[] oldDeleted;
}

/// \fn Colectie::Colectie()
/// \brief constructor pentru colectie
/// Initializeaza tabela de dispersie si vectorul de stergere logica cu dimensiunea initiala.
/// \complexity theta(1)
Colectie::Colectie() {
    capacity = INIT_SIZE;
    table = new TElem[capacity];
    deleted = new bool[capacity];
    for (int i = 0; i < capacity; i++) {
        table[i] = NULL_TELEM;
        deleted[i] = false;
    }
    totalSize = 0;
}

/// \fn void Colectie::adauga(TElem elem)
/// \brief adauga un element in colectie
/// Daca tabela de dispersie este plina, se redimensioneaza. Daca elementul este deja in colectie, nu se adauga.
/// \param elem elementul de adaugat in colectie
/// \return nu returneaza nimic
/// \complexity best case theta(1), worst case O(n), average case O(n)
void Colectie::adauga(TElem elem) {
    if (totalSize * 100 >= capacity * MAX_FACTOR) {
        resize();
    }
    int i = 0, pos, firstDeleted = -1;
    do {
        pos = hash(elem, i++);
        if (table[pos] == NULL_TELEM) {
            if (firstDeleted != -1) {
                table[firstDeleted] = elem;
                deleted[firstDeleted] = false;
            } else {
                table[pos] = elem;
                deleted[pos] = false;
            }
            totalSize++;
            return;
        }
        if (deleted[pos] && firstDeleted == -1) {
            firstDeleted = pos;
        }
    } while (i < capacity);
    if (firstDeleted != -1) {
        table[firstDeleted] = elem;
        deleted[firstDeleted] = false;
        totalSize++;
        return;
    }
    resize();
    adauga(elem);
}

/// \fn bool Colectie::sterge(TElem elem)
/// \brief Sterge un element din colectie.
/// Daca elementul este gasit, acesta este marcat ca sters logic si numarul total de elemente este decrementat.
/// \param elem elementul de sters din colectie
/// \return returneaza true daca elementul a fost sters, altfel false
/// \complexity best case theta(1), worst case O(n), average case O(n)
bool Colectie::sterge(TElem elem) {
    int i = 0, pos;
    do {
        pos = hash(elem, i++);
        if (table[pos] == elem && !deleted[pos]) {
            deleted[pos] = true;
            totalSize--;
            return true;
        }
        if (table[pos] == NULL_TELEM) {
            return false;
        }
    } while (i < capacity);
    return false;
}

/// \fn bool Colectie::cauta(TElem elem) const
/// \brief cauta un element in colectie
/// Daca elementul este gasit si nu este marcat ca sters, returneaza true.
/// \param elem elementul de cautat in colectie
/// \return returneaza true daca elementul este gasit si nu este sters, altfel false
/// \complexity O(n), unde n este numarul de elemente in colectie
bool Colectie::cauta(TElem elem) const {
    int i = 0, pos;
    do {
        pos = hash(elem, i++);
        if (table[pos] == elem && !deleted[pos]) {
            return true;
        }
        if (table[pos] == NULL_TELEM) {
            return false;
        }
    } while (i < capacity);
    return false;
}

/// \fn int Colectie::nrAparitii(TElem elem) const
/// \brief numara aparitiile unui element in colectie
/// Parcurge tabela de dispersie si numara cate aparitii ale elementului `elem` exista, ignorand elementele marcate ca sterse.
/// \param elem elementul pentru care se numara aparitiile
/// \return returneaza numarul de aparitii ale elementului `elem` in colectie
/// \complexity O(n), unde n este numarul de elemente in colectie
int Colectie::nrAparitii(TElem elem) const {
    int count = 0;
    for (int i = 0; i < capacity; i++) {
        if (table[i] == elem && !deleted[i]) {
            count++;
        }
    }
    return count;
}

/// \fn int Colectie::diferenta() const
/// \brief calculeaza diferenta dintre valoarea maxima si cea minima din colectie
/// \return returneaza max(c) - min(c) sau -1 daca colectia este vida
/// \complexity best case theta(1), worst case theta(n^2), average case O(n^2)
int Colectie::diferenta() const {
    if (vida()) return -1;
    int minVal = 0, maxVal = 0;
    bool found = false;
    for (int i = 0; i < capacity; i++) {
        if (table[i] != NULL_TELEM && !deleted[i]) {
            if (!found) {
                minVal = maxVal = table[i];
                found = true;
            } else {
                if (table[i] < minVal) minVal = table[i];
                if (table[i] > maxVal) maxVal = table[i];
            }
        }
    }
    return maxVal - minVal;
}

/* diferenta(Colectie c)
 * pre: c - o colectie
 * post: returneaza diferenta dintre valoarea maxima si minima din colectie sau -1 daca vida(c)
 * parcurge toate pozitiile si gaseste minimul si maximul element valid
 *
 * Alg diferenta(c)
 *   daca vida(c) atunci
 *     returneaza -1
 *   sfDaca
 *   gasit <- fals
 *   pentru i = 0, i < capacity(c) - 1 executa
 *     daca c.table[i] != NULL_TELEM si c.deleted[i] == fals atunci
 *       daca !gasit atunci
 *         minVal <- c.table[i]
 *         maxVal <- c.table[i]
 *         gasit <- adevarat
 *       altfel
 *         daca c.table[i] < minVal atunci
 *           minVal <- c.table[i]
 *         sfDaca
 *         daca c.table[i] > maxVal atunci
 *           maxVal <- c.table[i]
 *         sfDaca
 *       sfDaca
 *     sfDaca
 *   sfPentru
 *   returneaza maxVal - minVal
 * sfAlg
 */
