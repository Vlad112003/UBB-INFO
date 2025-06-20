#include <assert.h>
#include <iostream>
#include "LO.h"
#include "Iterator.h"

#include <exception>
using namespace std;

bool relatie1(TElement cheie1, TElement cheie2) {
	if (cheie1 <= cheie2) {
		return true;
	}
	else {
		return false;
	}
}

 void test_anterior() {
    // Test 1: Verifica daca anterior() merge corect inapoi in lista
    LO listaOrd1(relatie1);
    listaOrd1.adauga(5);
    listaOrd1.adauga(3);
    listaOrd1.adauga(8);
    listaOrd1.adauga(1);

    Iterator it1 = listaOrd1.prim();
    it1.urmator(); // 3
    it1.urmator(); // 5
    it1.anterior(); // 3
    if (it1.element() != 3) {
        std::cout << "Test 1 esuat." << std::endl;
    }}
void testAll(){
	LO lo = LO(relatie1);
	assert(lo.dim() == 0);
	assert(lo.vida());
    lo.adauga(1);
    assert(lo.dim() == 1);
    assert(!lo.vida());
    Iterator itCauta = lo.cauta(1);
    assert(itCauta.valid());
    assert(itCauta.element() == 1);
    itCauta.urmator();
    assert(!itCauta.valid());
    itCauta.prim();
    assert(itCauta.valid());
    Iterator itPrim = lo.prim();
    assert(itPrim.valid());
    assert(itPrim.element() == 1);
    assert(lo.sterge(itCauta) == 1);
    assert(lo.dim() == 0);
    assert(lo.vida());
}

