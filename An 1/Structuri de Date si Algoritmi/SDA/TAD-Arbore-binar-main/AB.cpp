#include "AB.h"
#include "IteratorAB.h"
#include <exception>
#include <string>

// Teta(1)
AB::AB() {
	this->radacina = NULL;
}

/// caz favoranil : Teta(1)
/// caz defavorabil : Teta(n)
/// caz mediu : Teta(n)
/// overall case : O(n)
AB::AB(const AB& ab) {
	this->radacina = copiere(ab.radacina);
}

// Teta(1)
AB::AB(TElem e){
	this->radacina = new Nod(e, NULL, NULL);
}

/// caz favoranil : Teta(1)
/// caz defavorabil : Teta(n)
/// caz mediu : Teta(n)
/// overall case : O(n)
AB::AB(const AB& st, TElem e, const AB& dr){
	this->radacina = new Nod(e, copiere(st.radacina), copiere(dr.radacina));
}

/// caz favoranil : Teta(1)
/// caz defavorabil : Teta(n)
/// caz mediu : Teta(n)
/// overall case : O(n)
PNod AB::copiere(PNod p) const {
	if (p != NULL) {
		//creez radacina
		PNod pNew = new Nod(p->element, NULL, NULL);
		pNew->st = copiere(p->st);
		pNew->dr = copiere(p->dr);
		return pNew;
	}
	return NULL;
}

/// caz favoranil : Teta(1)
/// caz defavorabil : Teta(n)
/// caz mediu : Teta(n)
/// overall case : O(n)
void AB::distrugeSubarbori(PNod p) {
	if (p != NULL) {
		distruge(p->st);
		distruge(p->dr);
	}
}

/// caz favoranil : Teta(1)
/// caz defavorabil : Teta(n)
/// caz mediu : Teta(n)
/// overall case : O(n)
void AB::distruge(PNod p) {
	if (p != NULL) {
		distruge(p->st);
		distruge(p->dr);
		delete p;
	}
}

/// caz favoranil : Teta(1)
/// caz defavorabil : Teta(n)
/// caz mediu : Teta(n)
/// overall case : O(n)
void AB::adaugaSubSt(const AB& st){
	if (this->vid())
		throw(exception());
	// distrug vechii subarbori ai subarborelui stang
	distrugeSubarbori(this->radacina->st);
	//copiez noul subarbore
	this->radacina->st = copiere(st.radacina);
}

/// caz favoranil : Teta(1)
/// caz defavorabil : Teta(n)
/// caz mediu : Teta(n)
/// overall case : O(n)
void AB::adaugaSubDr(const AB& dr){
	if (this->vid())
		throw(exception());
	// distrug vechii subarbori ai subarborelui drept
	distrugeSubarbori(this->radacina->dr);
	//copiez noul subarbore
	this->radacina->dr = copiere(dr.radacina);
}

// Teta(1)
TElem AB::element() const{
	if (this->vid())
		throw(exception());
	return this->radacina->element;
}

// Teta(1)
AB AB::stang() const{
	if (this->vid())
		throw(exception());
	AB ab;
	ab.radacina = copiere(this->radacina->st);
    return ab;
}

// Teta(1)
AB AB::drept() const{
	if (this->vid())
		throw(exception());
	AB ab;
	ab.radacina = copiere(this->radacina->dr);
	return ab;
}

/// caz favoranil : Teta(1)
/// caz defavorabil : Teta(n)
/// caz mediu : Teta(n)
/// overall case : O(n)
AB::~AB() {
	distruge(this->radacina);
}

// Teta(1)
bool AB::vid() const{
	return this->radacina == NULL;
}

// Teta(1)
IteratorAB* AB::iterator(string s) const {
	if (s=="preordine")
		return new IteratorPreordine(*this);
	if (s=="inordine")
		return new IteratorInordine(*this);
	if (s=="postordine")
		return new IteratorPostordine(*this);
	if (s=="latime")
		return new IteratorLatime(*this);
	return nullptr;
};

// Complexitate :  Teta(n)
/// caz favoranil : Teta(1) daca n e 1
/// caz defavorabil : Teta(n)
/// caz mediu : Teta(n)
/// overall case : Teta(n)
int AB::dif_max_min()
{
	if (this->vid())
		throw(exception());

	// initializam min si max cu radacina
	TElem mini = radacina->element, maxi = radacina->element;
	// parcurgem arborele prim latime
	queue<PNod> q;
	// se adauga radacina in coada
	q.push(radacina); 

	// incepem bfs ul
	while (!q.empty())
	{
		PNod aux = q.front();
		q.pop();

		if (aux->st != NULL)
			q.push(aux->st);
		if (aux->dr != NULL)
			q.push(aux->dr);

		if (aux->element > maxi)
			maxi = aux->element;
		if (aux->element < mini)
			mini = aux->element;
	}

	return maxi - mini;
}