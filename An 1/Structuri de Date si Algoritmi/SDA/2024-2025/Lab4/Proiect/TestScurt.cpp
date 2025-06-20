#include "TestScurt.h"
#include <assert.h>
#include <iostream>
#include "Multime.h"
#include "IteratorMultime.h"

void testAll() { //apelam fiecare functie sa vedem daca exista
	Multime m;
	assert(m.vida() == true);
	assert(m.dim() == 0); //adaug niste elemente
	assert(m.adauga(5)==true);
	assert(m.adauga(1)==true);
	assert(m.adauga(10)==true);
	assert(m.adauga(7)==true);
	assert(m.adauga(1)==false);
	assert(m.adauga(10)==false);
	assert(m.adauga(-3)==true);
	assert(m.dim() == 5);
	assert(m.cauta(10) == true);
	assert(m.cauta(16) == false);
	assert(m.sterge(1) == true);
	assert(m.sterge(6) == false);
	assert(m.dim() == 4);


	IteratorMultime im = m.iterator();
	im.prim();
	int s = 0;
	while (im.valid()) {
		TElem e = im.element();
		s += e;
		im.urmator();
	}
	assert(s == 19);
    std::cout<<"TestScurt passed!\n";
}

void testReuniune(){
    Multime m1;
    Multime m2;
    m1.adauga(1);
    m1.adauga(2);
    m2.adauga(3);
    m2.adauga(4);
    m2.adauga(1); // 1 e deja in m1, dim ar trebui sa fie tot 4 dupa reuniune
    m1.reuniune(m2);
    assert(m1.dim() == 4);
    assert(m1.cauta(1) == true);
    assert(m1.cauta(2) == true);
    assert(m1.cauta(3) == true);
    assert(m1.cauta(4) == true);
    assert(m1.cauta(5) == false);
    std::cout << "TestReuniune passed!\n";
}
