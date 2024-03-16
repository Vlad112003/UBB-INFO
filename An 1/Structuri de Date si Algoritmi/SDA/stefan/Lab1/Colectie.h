#pragma once

#define NULL_TELEM -1
typedef int TElem;

class IteratorColectie;

class Colectie
{
	friend class IteratorColectie;

private:
	int dimensiune, capacitateVector, nrElemente;
	TElem *vectorElemente;
	int* vectorFrecvente;

public:
	/*
	Constructorul implicit
	
	Complexitate overall: O(1)
	*/
	Colectie();

	/*
	Adauga un element in colectie
	
	Complexitate overall : O(n)
	Complexitate overall amortizata : O(n)
	*/
	void adauga(TElem e);

	/*
	Sterge o aparitie a unui element din colectie
	Returneaza adevarat daca s-a putut sterge

	Complexitate overall : O(n)
	*/
	bool sterge(TElem e);

	/*
	Verifica daca un element se afla in colectie

	Complexitate overall : O(n)
	*/
	bool cauta(TElem elem) const;

	/*
	Returneaza numar de aparitii ale unui element in colectie

	Complexitate overall : O(n)
	*/
	int nrAparitii(TElem elem) const;

	/*
	Intoarce numarul de elemente din colectie

	Complexitate overall : theta(1)
	*/
	int dim() const;

	/*
	Verifica daca colectia e vida

	Complexitate overall : theta(1)
	*/
	bool vida() const;

	/*
	Returneaza un iterator pe colectie

	Complexitate overall : theta(1)
	*/
	IteratorColectie iterator() const;

	/*
	Destructorul colectiei

	Complexitate overall : theta(1)
	*/
	~Colectie();

	/*
	Returneaza numarul de elemente unice din colectie

	Complexitate BC : theta(1) (toate elementele sunt identice)
	Complexitate WC : theta(n) (toate elementele sunt diferite)
	Complexitate medie : theta(n)

	Complexitate overall : O(n)
	*/
	int nrElementeUnice() const;

};

