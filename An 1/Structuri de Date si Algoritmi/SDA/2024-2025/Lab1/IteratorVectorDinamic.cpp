#include "IteratorVectorDinamic.h"
#include "VectorDinamic.h"
#include <stdexcept>
using namespace std;

//constructor
//Complexitate: theta(1)
IteratorVectorDinamic::IteratorVectorDinamic(const VectorDinamic& _v) : v(_v) {
    curent = 0;
}

//reseteaza pozitia iteratorului la inceputul containerului
//Complexitate: theta(1)
void IteratorVectorDinamic::prim() {
    curent = 0;
}

//muta iteratorul in container
//Complexitate: theta(1)
// arunca exceptie daca iteratorul nu e valid
bool IteratorVectorDinamic::valid() const {
    return curent < v.dim();
}

//verifica daca iteratorul e valid (indica un element al containerului)
//Complexitate: theta(1)
//arunca exceptie daca iteratorul nu e valid
TElem IteratorVectorDinamic::element() const {
    if (!valid())
        throw out_of_range("Iterator out of range");
    return v.e[curent];
}

//trece pe urmatoarea pozitie in container daca iteratorul e valid
//Complexitate: theta(1)
// arunca exceptie daca iteratorul nu e valid
void IteratorVectorDinamic::urmator() {
    if (!valid())
        throw out_of_range("Iterator out of range");
    curent++;
}