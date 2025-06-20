#pragma once
#include <string>
#include <queue>
using namespace std;

typedef int TElem;

class AB;

class IteratorAB;

class Nod {
public:
	TElem element;
	Nod* st;
	Nod* dr;
	Nod()
	{
		element = 0;
		st = NULL;
		dr = NULL;
	}
	Nod(TElem e, Nod* st1, Nod* dr1)
	{
		element = e;
		st = st1;
		dr = dr1;
	}
};

//se defineste tipul PNod ca fiind adresa unui Nod
typedef Nod* PNod;

class AB {

private:
	/* aici e reprezentarea */

	Nod* radacina;

	//functia recursiva pentru distrugerea arborelui
	void distruge(PNod);
	//functia recursiva pentru distrugerea subarborilor unui arbore
	void distrugeSubarbori(PNod);
	//copiere subarbore
	PNod copiere(PNod) const;

public:
	friend class IteratorAB;
	friend class IteratorPreordine;
	friend class IteratorInordine;
	friend class IteratorPostordine;
	friend class IteratorLatime;

		//constructorul implicit
		AB();

		//contructor de copiere
		AB(const AB& ab);

		//creeaza un arbore binar care se reduce la o frunza
		AB(TElem elem);

		//constructor arbore binar
		AB(const AB& st, TElem elem, const AB& dr);

		//adauga unui arbore binar subarborele stang
		//arunca exceptie daca arborele este vid
		void adaugaSubSt(const AB& st);

		//adauga unui arbore binar subarborele drept
		//arunca exceptie daca arborele este vid
		void adaugaSubDr(const AB& dr);

		//verifica daca arborele este vid
   		 bool vid() const;

		//accesare element din radacina
		//arunca exceptie daca arborele este vid
		TElem element() const;

		//accesare subarbore stang
		//arunca exceptie daca arborele este vid
		AB stang() const;

		//accesare subarbore drept
		//arunca exceptie daca arborele este vid
		AB drept() const;

		//iteratori pe arborele binar (ordine="preordine", "postordine", "inordine", "latime")
		IteratorAB* iterator(string ordine) const;

		// destructorul arborelui binar
		~AB();

		/// gaseste dif deintre val min si val max
		int dif_max_min();
};

