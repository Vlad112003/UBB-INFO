#pragma once
#include "VectorDinamic.h"

class IteratorVectorDinamic {

	friend class VectorDinamic;
private:

	//constructorul primeste o referinta catre Container
    const VectorDinamic &v;

	//iteratorul va referi primul element din container
    int curent;

public:

        IteratorVectorDinamic(const VectorDinamic&);
		//reseteaza pozitia iteratorului la inceputul containerului
		void prim();

		//muta iteratorul in container
		// arunca exceptie daca iteratorul nu e valid
		void urmator();

		//verifica daca iteratorul e valid (indica un element al containerului)
		bool valid() const;

		//returneaza valoarea elementului din container referit de iterator
		//arunca exceptie daca iteratorul nu e valid
		TElem element() const;

};