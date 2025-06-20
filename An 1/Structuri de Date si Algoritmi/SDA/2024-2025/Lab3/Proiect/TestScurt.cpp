#include "TestScurt.h"
#include <cassert>
#include "Matrice.h"
#include "IteratorMatrice.h"
#include <iostream>

using namespace std;

void testAll() { //apelam fiecare functie sa vedem daca exista
	Matrice m(4,4);
	assert(m.nrLinii() == 4);
	assert(m.nrColoane() == 4);
	//adaug niste elemente
	m.modifica(1,1,5);
	assert(m.element(1,1) == 5);
	m.modifica(1,1,6);
	assert(m.element(1,2) == NULL_TELEMENT);
    cout<<"TestScurt passed!"<<endl;
}

void testIterator() {
    Matrice m(4, 4);

    // adaugam elemente in coloane diferite
    m.modifica(1, 0, 5);  // prima coloana
    m.modifica(3, 0, 9);  // prima coloana
    m.modifica(0, 1, 2);  // a doua coloana
    m.modifica(2, 2, 6);  // a treia coloana
    m.modifica(3, 3, 7);  // a patra coloana

    IteratorMatrice im = m.iterator();

    // elementele din prima coloana trebuie sa vina primele
    im.prim();
    assert(im.valid() == true);
    assert(im.element() == 5);
    assert(im.pozitie().first == 1 && im.pozitie().second == 0);

    im.urmator();
    assert(im.valid() == true);
    assert(im.element() == 9);
    assert(im.pozitie().first == 3 && im.pozitie().second == 0);

    // apoi a doua coloana
    im.urmator();
    assert(im.valid() == true);
    assert(im.element() == 2);
    assert(im.pozitie().first == 0 && im.pozitie().second == 1);

    // a treia coloana
    im.urmator();
    assert(im.valid() == true);
    assert(im.element() == 6);
    assert(im.pozitie().first == 2 && im.pozitie().second == 2);

    // a patra coloana
    im.urmator();
    assert(im.valid() == true);
    assert(im.element() == 7);
    assert(im.pozitie().first == 3 && im.pozitie().second == 3);

    // nu mai exista elemente
    im.urmator();
    assert(im.valid() == false);

    cout << "TestIterator passed!" << endl;
}
