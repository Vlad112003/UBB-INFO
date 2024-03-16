#include "TesteleMele.h"
#include <assert.h>
#include "Colectie.h"
#include "IteratorColectie.h"

#define _CRTDBG_MAP_ALLOC
#include <stdlib.h>

void testAllMele()
{
	Colectie c;
	IteratorColectie i = c.iterator();

	// Test 1:
	int lista[] = { 1, 1, 10, 9, 5, 5, 5, 5, 6, 6, 7, 3, 4, 4, 2 };

	for (int i = 0; i < sizeof(lista) / sizeof(int); i++)
		c.adauga(lista[i]);

	int j = 0;
	for (i.prim(); i.valid(); i.urmator(), j++)
		assert(i.element() == lista[j]);

	// Test 2:
	int lista2[] = { 10, 9, 5, 5, 5, 5, 6, 6, 7, 3, 4, 4, 2 };

	c.sterge(1);
	c.sterge(1);

	j = 0;
	for (i.prim(); i.valid(); i.urmator(), j++)
		assert(i.element() == lista2[j]);

	// Test 3:
	int lista3[] = { 10, 5, 5, 5, 6, 6, 3, 4, 4, 2 };

	c.sterge(9);
	c.sterge(5);
	c.sterge(7);

	j = 0;
	for (i.prim(); i.valid(); i.urmator(), j++)
		assert(i.element() == lista3[j]);

	// Test 4:
	int lista4[] = { 10, 5, 5, 5, 5, 6, 6, 3, 4, 4, 2, 8, 8, 8};

	c.adauga(8);
	c.adauga(8);
	c.adauga(5);
	c.adauga(8);

	j = 0;
	for (i.prim(); i.valid(); i.urmator(), j++)
		assert(i.element() == lista4[j]);

	//c.~Colectie();

}

void testeNrElementeUnice()
{
	// Test 0:
	Colectie c0;

	assert(c0.nrElementeUnice() == 0);

	//c0.~Colectie();

	// Test 1:
	Colectie c1;

	int lista1[] = { 1 };
	c1.adauga(lista1[0]);

	assert(c1.nrElementeUnice() == 1);

	//c1.~Colectie();

	// Test 2:
	Colectie c2;

	int lista2[] = { 1, 1, 1 };
	for (int i = 0; i < 3; i++)
		c2.adauga(lista2[i]);

	assert(c2.nrElementeUnice() == 0);

	//c2.~Colectie();

	// Test 3:
	Colectie c3;

	int lista3[] = { 1, 2, 3 };
	for (int i = 0; i < 3; i++)
		c3.adauga(lista3[i]);

	assert(c3.nrElementeUnice() == 3);

	//c3.~Colectie();

	// Test 4:
	Colectie c4;

	int lista4[] = { 1, 3, 3, 3, 2, 1};
	for (int i = 0; i < 6; i++)
		c4.adauga(lista4[i]);

	assert(c4.nrElementeUnice() == 1);

	//c4.~Colectie();

	// Test 5:
	Colectie c5;

	int lista5[] = { 1, 1, 10, 9, 5, 5, 5, 5, 6, 6, 7, 3, 4, 4, 2 };
	for (int i = 0; i < sizeof(lista5) / sizeof(int); i++)
		c5.adauga(lista5[i]);

	assert (c5.nrElementeUnice() == 5);

	//c5.~Colectie();

	// Test 6:
	Colectie c6;

	int lista6[] = { 6, 5, 4, 3, 2, 1, 1, 2, 3, 4, 5, 6};
	for (int i = 0; i < sizeof(lista6) / sizeof(int); i++)
		c6.adauga(lista6[i]);

	assert(c6.nrElementeUnice() == 0);

	//c6.~Colectie();
}