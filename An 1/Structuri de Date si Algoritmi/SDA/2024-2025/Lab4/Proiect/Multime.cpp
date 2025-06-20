#include "Multime.h"
#include "IteratorMultime.h"
#include <exception>

// constructor
// complexitate theta(1)
Multime::Multime() {
    this->capacity = CAPACITY;
    this->size = 0;
    this->head = NIL;

    // aloca memorie pentru tablouri
    this->elements = new TElem[this->capacity];
    this->next = new int[this->capacity];
    this->prev = new int[this->capacity];

    // initializam lista inlantuita
    this->initialize();
}


// initializeaza tablourile
// complexitate O(n)
void Multime::initialize() {
    // creeaza o lista inlantuita de pozitii libere
    for (int i = 0; i < this->capacity - 1; i++) {
        this->next[i] = i + 1;
        this->prev[i] = NIL;
    }

    // seteaza next-ul ultimului element la NIL
    this->next[this->capacity - 1] = NIL;
    this->prev[this->capacity - 1] = NIL;

    // seteaza firstEmpty la inceputul tabloului
    this->firstEmpty = 0;
}

// aloca o pozitie libera
// complexitate theta(1)
int Multime::allocate() {
    // verifica daca exista spatiu disponibil
    if (this->firstEmpty == NIL) {
        this->resize();
    }

    // obtine pozitia de alocat
    int position = this->firstEmpty;

    // actualizeaza firstEmpty la urmatoarea pozitie libera
    this->firstEmpty = this->next[position];

    // returneaza pozitia alocata
    return position;
}

// elibereaza o pozitie
// complexitate theta(1)
void Multime::free(int position) {
    // adauga pozitia la lista de pozitii libere
    this->next[position] = this->firstEmpty;
    this->prev[position] = NIL;
    this->firstEmpty = position;
}

// redimensioneaza tablourile
// complexitate O(n)
void Multime::resize() {
    // dubleaza capacitatea
    int newCapacity = this->capacity * 2;

    // creeaza noi tablouri
    TElem* newElements = new TElem[newCapacity];
    int* newNext = new int[newCapacity];
    int* newPrev = new int[newCapacity];

    // copiaza datele existente
    for (int i = 0; i < this->capacity; i++) {
        newElements[i] = this->elements[i];
        newNext[i] = this->next[i];
        newPrev[i] = this->prev[i];
    }

    // initializeaza noile pozitii libere
    for (int i = this->capacity; i < newCapacity - 1; i++) {
        newNext[i] = i + 1;
        newPrev[i] = NIL;
    }

    // seteaza next-ul ultimului element la NIL
    newNext[newCapacity - 1] = NIL;
    newPrev[newCapacity - 1] = NIL;

    // actualizeaza firstEmpty daca era NIL
    if (this->firstEmpty == NIL) {
        this->firstEmpty = this->capacity;
    }

    // sterge vechile tablouri si actualizeaza referintele
    delete[] this->elements;
    delete[] this->next;
    delete[] this->prev;

    this->elements = newElements;
    this->next = newNext;
    this->prev = newPrev;
    this->capacity = newCapacity;
}

// adauga un element in multime
// returneaza adevarat daca elementul a fost adaugat (nu exista deja in multime)
// complexitate O(n)
bool Multime::adauga(TElem elem) {
    // daca elementul exista deja, nu il adauga
    if (this->cauta(elem)) {
        return false;
    }

    // aloca o pozitie noua
    int newPos = this->allocate();

    // stocheaza elementul
    this->elements[newPos] = elem;

    // insereaza la inceputul listei
    if (this->head == NIL) {
        // primul element din lista
        this->head = newPos;
        this->next[newPos] = NIL;
        this->prev[newPos] = NIL;
    } else {
        // adauga in fata
        this->next[newPos] = this->head;
        this->prev[newPos] = NIL;
        this->prev[this->head] = newPos;
        this->head = newPos;
    }

    this->size++;
    return true;
}

bool Multime::sterge(TElem elem) {
    // daca elementul nu exista, nu poate fi sters
    if (!this->cauta(elem)) {
        return false;
    }

    // gaseste pozitia elementului
    int current = this->head;
    while (current != NIL && this->elements[current] != elem) {
        current = this->next[current];
    }

    // elimina elementul
    if (current == this->head) {
        // eliminam capul listei
        this->head = this->next[current];
        if (this->head != NIL) {
            this->prev[this->head] = NIL;
        }
    } else {
        // conecteaza elementele anterioare si urmatoare
        this->next[this->prev[current]] = this->next[current];
        if (this->next[current] != NIL) {
            this->prev[this->next[current]] = this->prev[current];
        }
    }

    // elibereaza pozitia
    this->free(current);

    this->size--;
    return true;
}

bool Multime::cauta(TElem elem) const {
    int current = this->head;

    // parcurge lista
    while (current != NIL) {
        if (this->elements[current] == elem) {
            return true;
        }
        current = this->next[current];
    }

    return false;
}

// intoarce numarul de elemente din multime
// complexitate theta(1)
int Multime::dim() const {
    return this->size;
}

// verifica daca multimea e vida
// complexitate theta(1)
bool Multime::vida() const {
    return this->size == 0;
}

// destructorul multimii
// complexitate theta(1)
Multime::~Multime() {
    delete[] this->elements;
    delete[] this->next;
    delete[] this->prev;
}

IteratorMultime Multime::iterator() const {
    return IteratorMultime(*this);
}

// adauga elemente dintr-o alta multime
// complexitate best case theta(1)
// complexitate worst case theta(n)
// complexitate average case O(n)
// complexitate generale O(n)
void Multime::reuniune(Multime& m) {
    IteratorMultime it = m.iterator();
    while (it.valid()) {
        this->adauga(it.element());
        it.urmator();
    }
}

/*reuniune(Multime a,b)
 * pre: a,b - o multime
 * post:a' = a U b
 * adauga toate elementele multimii b in multimea curenta (fara a introduce duplicate)
 *
 * Alg reuniune(Multime b)
 *   it <- iterator pentru multimea b
 *   it.prim()
 *   cat timp it.valid() executa
 *     element <- it.element()
 *     adauga(element) // metoda adauga doar daca elementul nu exista deja
 *     it.urmator()
 *   sfCatTimp
 * sfAlg
 */