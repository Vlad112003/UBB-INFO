#include "IteratorColectie.h"
#include "Colectie.h"
#include <iostream>
using namespace std;


IteratorColectie::IteratorColectie(const Colectie& c): col(c) {
	curent = 0;
}

void IteratorColectie::prim() {
	curent = 0;
}


void IteratorColectie::urmator() {
//	if (curent <col.dim())
        curent++;
}

int IteratorColectie::return_curent(){
    return curent;
}


bool IteratorColectie::valid() const {
	return curent < col.dim();
}

void IteratorColectie::print() {
    for(int i = 0; i< col.dim(); i++)
        cout << col.e[i].lmn << endl;
}

TElem IteratorColectie::element() const {


    return col.e[curent].lmn;
}
