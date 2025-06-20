#include "Matrice.h"
#include <exception>
#include "IteratorMatrice.h"

using namespace std;

// Constructorul initializeaza matricea cu dimensiunile date
// arunca exceptie in cazul in care nrLinii sau nrColoane <= 0
// complexitate theta(1)
Matrice::Matrice(int m, int n) {
    // daca m <= 0 sau n <= 0 aruncam exceptie
    if (m <= 0 || n <= 0) {
        throw exception();
    }
    // initializam dimensiunile matricei
    this->L = m;
    this->C = n;
    // initializam lista cu nullptr
    this->head = nullptr;
}

// destructorul elibereaza memoria alocata pentru lista
// complexitate O(n)
Matrice::~Matrice() {
    Node* current = head;
    while (current != nullptr) {
        Node* toDelete = current;
        current = current->next;
        delete toDelete;
    }
}

// complexitate theta(1)
int Matrice::nrLinii() const {
    return L;
}

// complexitate theta(1)
int Matrice::nrColoane() const {
    return C;
}

// complexitate O(n)
// arunca exceptie daca (i,j) nu e o pozitie valida in matrice
TElem Matrice::element(int i, int j) const {
    if (i < 0 || i >= L || j < 0 || j >= C) {
        throw exception();
    }
    Node* node = find(i, j);
    if (node != nullptr) {
        return node->value;
    }
    return NULL_TELEMENT;
}

// complexitate O(n)
// arunca exceptie daca (i,j) nu e o pozitie valida in matrice
// modifica elementul de pe pozitia (i,j) si returneaza valoarea veche
TElem Matrice::modifica(int i, int j, TElem e) {
    if (i < 0 || i >= L || j < 0 || j >= C) {
        throw exception();
    }
    Node* node = find(i, j);
    if (node != nullptr) {
        TElem oldValue = node->value;
        if (e == NULL_TELEMENT) {
            // stergem nodul
            if (head == node) {
                head = node->next;
            } else {
                Node* prev = head;
                while (prev->next != node) {
                    prev = prev->next;
                }
                prev->next = node->next;
            }
            delete node;
        } else {
            node->value = e;
        }
        return oldValue;
    } else {
        if (e != NULL_TELEMENT) {
            // inseram un nou nod
            Node* newNode = new Node{i, j, e, nullptr};
            // daca lista e goala sau nodul trebuie adaugat la inceput
            if (head == nullptr || (head->line > i || (head->line == i && head->column > j))) {
                newNode->next = head;
                head = newNode;
            } else {
                // daca lista nu e goala, cautam pozitia de inserare
                Node* prev = head;
                while (prev->next != nullptr && (prev->next->line < i || (prev->next->line == i && prev->next->column < j))) {
                    prev = prev->next;
                }
                newNode->next = prev->next;
                prev->next = newNode;
            }
        }
        return NULL_TELEMENT;
    }
}

// cauta un nod in lista cu coordonatele (i,j)
// complexitate O(n)
Node* Matrice::find(int i, int j) const {
    Node *current = head;
    while (current != nullptr) {
        if (current->line == i && current->column == j) {
            return current;
        }
        current = current->next;
    }
    return nullptr;
}

// creeaza un iterator pe matrice
// complexitate theta(1)
IteratorMatrice Matrice::iterator() const {
    return IteratorMatrice(*this);
}



