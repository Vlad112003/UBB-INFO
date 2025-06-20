#pragma once

#include <vector>
typedef int TCheie;
typedef int TValoare;

#include <utility>
typedef std::pair<TCheie, TValoare> TElem;

using namespace std;
#define null make_pair(-1,-1)
#define sters make_pair(-2,-2)
#define maxcap 5003
class IteratorMDO;

typedef bool(*Relatie)(TCheie, TCheie);

class MDO{
	friend class IteratorMDO;
    private:
        int m;
        int prim;
        int lg;
        int d(TCheie c, int i ) const;
        Relatie r;
    public:
    TElem e[maxcap];
	// constructorul implicit al MultiDictionarului Ordonat
	MDO(Relatie r);

	// adauga o pereche (cheie, valoare) in MDO
	void adauga(TCheie c, TValoare v);

	//cauta o cheie si returneaza vectorul de valori asociate
	vector<TValoare> cauta(TCheie c) const;

	//sterge o cheie si o valoare 
	//returneaza adevarat daca s-a gasit cheia si valoarea de sters
	bool sterge(TCheie c, TValoare v);

	//returneaza numarul de perechi (cheie, valoare) din MDO 
	int dim() const;

	//verifica daca MultiDictionarul Ordonat e vid 
	bool vid() const;

	// se returneaza iterator pe MDO
	// iteratorul va returna perechile in ordine in raport cu relatia de ordine
	IteratorMDO iterator() const;

	// destructorul 	
	~MDO();


};
