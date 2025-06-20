#pragma once

typedef int TElem;

class IteratorVectorDinamic;

class VectorDinamic {

    friend class IteratorVectorDinamic;
private:
    //capacitate vector
    int cp;

    //dimensiune vector
    int n;

    //elemente vector
    TElem *e;

	//redimensionare
	void redim();

public:
		// constructor
		//arunca exceptie in cazul in care capacitate e <=0
		explicit VectorDinamic (int capacitate);

		// returnare dimensiune
		int dim() const;

		// returnare element
		//arunca exceptie daca i nu e valid
		//indicii ii consideram de la 0
		TElem element(int i) const;

		// modifica element de pe pozitia i si returneaza vechea valoare
		//arunca exceptie daca i nu e valid
		TElem modifica(int i, TElem e);

		// adaugare element la sfarsit
		void adaugaSfarsit(TElem e);

		// adaugare element pe o pozitie i
		//arunca exceptie daca i nIteratorVectorDinamic::IteratorVectorDinamic(const VectorDinamic& _v) :u e valid
		void adauga(int i, TElem e);

		// sterge element de pe o pozitie i si returneaza elementul sters
		//arunca exceptie daca i nu e valid
		TElem sterge(int i);

        //verifica daca toate elementele din vector sunt unice
        //returneaza true daca sunt
        //returneaza false daca elementele nu sunt unice
        bool suntUnice() const;

		// returnare iterator
		IteratorVectorDinamic iterator();



    //destructor
		~VectorDinamic();

};







