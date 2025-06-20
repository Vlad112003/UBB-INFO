#include "IteratorMatrice.h"
#include <exception>
using namespace std;

// constructor pentru iterator
// complexitate o(1)
IteratorMatrice::IteratorMatrice(const Matrice& m) : matrice(m) {
    prim();
}

// reseteaza iteratorul la primul element
// complexitate best case: theta(1)
// complexitate worst case: theta(n)
// complexitate average case: theta(n)
// complexitate generale: o(n)

void IteratorMatrice::prim() {
    currentColumn = 0;

    // gasim primul element nenul din prima coloana
    while (currentColumn < matrice.nrColoane()) {
        // cautam cel mai mic nod (dupa indice de linie) de pe coloana curenta
        currentNode = nullptr;
        Node* node = matrice.head;

        while (node != nullptr) {
            if (node->column == currentColumn) {
                if (currentNode == nullptr || node->line < currentNode->line) {
                    currentNode = node;
                }
            }
            node = node->next;
        }

        // daca am gasit un nod in coloana curenta, am terminat
        if (currentNode != nullptr) {
            break;
        }

        // altfel, trecem la urmatoarea coloana
        currentColumn++;
    }
}

/*prim()
 * pre: iteratorul este initializat
 * post: iteratorul este pozitionat pe primul element
 *
 * Alg prim()
 *   currentColumn <- 0
 *
 *   cat timp currentColumn < matrice.nrColoane() executa
 *     currentNode <- nullptr
 *     node <- matrice.head
 *
 *     cat timp node != nullptr executa
 *       daca node->column == currentColumn atunci
 *         daca currentNode == nullptr sau node->line < currentNode->line atunci
 *           currentNode <- node
 *         sfDaca
 *       sfDaca
 *       node <- node->next
 *     sfCatTimp
 *
 *     daca currentNode != nullptr atunci
 *       break // am gasit un nod in coloana curenta
 *     sfDaca
 *
 *     currentColumn <- currentColumn + 1 // trecem la urmatoarea coloana
 *   sfCatTimp
 * sfAlg
 */

// trece la urmatorul element
// complexitate best case: theta(1)
// complexitate worst case: theta(n)
// complexitate average case: theta(n)
// complexitate generale: o(n)
// arunca exceptie daca iteratorul nu e valid
void IteratorMatrice::urmator() {
    if (!valid()) {
        throw exception();
    }

    int currentLine = currentNode->line;

    // cautam urmatorul nod in aceeasi coloana cu linie mai mare
    currentNode = nullptr;
    Node* node = matrice.head;

    while (node != nullptr) {
        if (node->column == currentColumn && node->line > currentLine) {
            if (currentNode == nullptr || node->line < currentNode->line) {
                currentNode = node;
            }
        }
        node = node->next;
    }

    // daca nu am gasit un nod in coloana curenta, trecem la urmatoarea coloana
    if (currentNode == nullptr) {
        currentColumn++;

        while (currentColumn < matrice.nrColoane() && currentNode == nullptr) {
            node = matrice.head;

            while (node != nullptr) {
                if (node->column == currentColumn) {
                    if (currentNode == nullptr || node->line < currentNode->line) {
                        currentNode = node;
                    }
                }
                node = node->next;
            }

            if (currentNode == nullptr) {
                currentColumn++;
            }
        }
    }
}

/*urmator()
 * pre: iteratorul este valid
 * post: iteratorul este avansat la urmatorul element
 * arunca exceptie daca iteratorul nu e valid
 *
 * Alg urmator()
 *   daca !valid() atunci
 *     arunca exceptie
 *   sfDaca
 *
 *   currentLine <- currentNode.line
 *
 *   // cautam urmatorul nod in aceeasi coloana cu linie mai mare
 *   currentNode <- nullptr
 *   node <- matrice.head
 *
 *   cat timp node != nullptr executa
 *     daca node.column == currentColumn si node.line > currentLine atunci
 *       daca currentNode == nullptr sau node.line < currentNode.line atunci
 *         currentNode <- node
 *       sfDaca
 *     sfDaca
 *     node <- node.next
 *   sfCatTimp
 *
 *   // daca nu am gasit un nod in coloana curenta, trecem la urmatoarea coloana
 *   daca currentNode == nullptr atunci
 *     currentColumn <- currentColumn + 1
 *
 *     cat timp currentColumn < matrice.nrColoane() si currentNode == nullptr executa
 *       node <- matrice.head
 *
 *       cat timp node != nullptr executa
 *         daca node.column == currentColumn atunci
 *           daca currentNode == nullptr sau node.line < currentNode.line atunci
 *             currentNode <- node
 *           sfDaca
 *         sfDaca
 *         node <- node.next
 *       sfCatTimp
 *
 *       daca currentNode == nullptr atunci
 *         currentColumn <- currentColumn + 1
 *       sfDaca
 *     sfCatTimp
 *   sfDaca
 * sfAlg
 */

// verifica daca iteratorul e valid
// complexitate best case: theta(1)
// complexitate worst case: theta(1)
// complexitate average case: theta(1)
// complexitate generale: theta(1)

bool IteratorMatrice::valid() const {
    return currentNode != nullptr;
}

/*valid()
 * pre: iteratorul este initializat
 * post: returneaza true daca iteratorul indica un element valid, false altfel
 *
 * Alg valid()
 *   return currentNode != nullptr
 * sfAlg
 */

// returneaza valoarea elementului curent
// complexitate theta(1)
TElem IteratorMatrice::element() const {
    if (!valid()) {
        throw exception();
    }
    return currentNode->value;
}


/*element()
 * pre: iteratorul este valid
 * post: returneaza valoarea elementului curent
 * arunca exceptie daca iteratorul nu e valid
 *
 * Alg element()
 *   daca !valid() atunci
 *     arunca exceptie
 *   sfDaca
 *   return currentNode.value
 * sfAlg
 */

// returneaza pozitia (linie, coloana) a elementului curent
// complexitate theta(1)
pair<int, int> IteratorMatrice::pozitie() const {
    if (!valid()) {
        throw exception();
    }
    return make_pair(currentNode->line, currentNode->column);
}

/*pozitie()
 * pre: iteratorul este valid
 * post: returneaza perechea (linie, coloana) a elementului curent
 * arunca exceptie daca iteratorul nu e valid
 *
 * Alg pozitie()
 *   daca !valid() atunci
 *     arunca exceptie
 *   sfDaca
 *   return (currentNode->line, currentNode->column)
 * sfAlg
 */





