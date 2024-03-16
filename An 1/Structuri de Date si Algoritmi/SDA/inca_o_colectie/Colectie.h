#pragma once

#define NULL_TELEM -1
typedef int TElem;

class IteratorColectie;

class Colectie
{
	friend class IteratorColectie;

private:

    int dimensiune, capacitateVector, nrElemente;
    //vectorul de elemente
    TElem *vectorElemente;

    //vectorul de frecventa
    int *vectorFrecvente;
    //pt  redimensionare
    void redim();

    //pt cautarea elementului in vectorul de elemente
    int cautare(TElem elem) const;
	/* aici e reprezentarea */
public:
		//constructorul implicit
        //Complexitate: O(1)
		Colectie();

		//adauga un element in colectie
        //Complexitate: O(n)
		void adauga(TElem e);

		//sterge o aparitie a unui element din colectie
		//returneaza adevarat daca s-a putut sterge
        //Complexitate overall: O(n)
		bool sterge(TElem e);

		//verifica daca un element se afla in colectie
        //Complexitate overall : O(n)
		bool cauta(TElem elem) const;

		//returneaza numar de aparitii ale unui element in colectie
        //Complexitate overall : O(n)
		int nrAparitii(TElem elem) const;


		//intoarce numarul de elemente din colectie;
        //Complexitate overall : theta(1)
		int dim() const;

		//verifica daca colectia e vida;
        //Complexitate overall : theta(1)
		bool vida() const;

		//returneaza un iterator pe colectie
		IteratorColectie iterator() const;

        //returneaza elementul din vectorul de elemente
        int return_element(int i);

        //returneaza frecventa unui element din vectorul de elemente
        int return_frecventa(int i);
		// destructorul colectiei
		~Colectie();


    int nrElementeUnice() const;

    //Complexitate:
    //Best case: Elementul are frecventa mai mare decat nr dat, si atunci nu face nimic
    //Best case complexity: theta(1)
    //Worst case: Elementul apare de n ori, iar nr este n;
    //Worst case complexity: theta(n^2)
    //Average case: Elementul apare de n/2 ori, iar nr este n;
    //Average case complexity: theta(n^2)
    //Overall complexity: O(n^2);
    int stergeNrAparitii(TElem elem, int nr);
};

