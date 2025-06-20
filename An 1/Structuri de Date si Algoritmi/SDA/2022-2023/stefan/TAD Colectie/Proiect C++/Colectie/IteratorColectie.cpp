#include <windows.h>
#include "IteratorColectie.h"
#include "Colectie.h"


IteratorColectie::IteratorColectie(const Colectie& c): col(c) {
	curent = 0;
}

void IteratorColectie::prim() {
	curent = 0;
}


void IteratorColectie::urmator() {
	curent = curent+1;
}


bool IteratorColectie::valid() const {
	return curent < col.dim();
}



TElem IteratorColectie::element() const {
	return col.e[curent];
}
