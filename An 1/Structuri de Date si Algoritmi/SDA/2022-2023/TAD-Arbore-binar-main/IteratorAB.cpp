#include "AB.h"
#include "IteratorAB.h"

/// caz favoranil : Teta(1)
/// caz defavorabil : Teta(n)
/// caz mediu : Teta(n)
/// overall case : O(n)
IteratorInordine::IteratorInordine(const AB& _ab):ab(_ab) {
	actual = NULL;
	actual = _ab.radacina;
	prim();
}

/// caz favoranil : Teta(1)
/// caz defavorabil : Teta(n)
/// caz mediu : Teta(n)
/// overall case : O(n)
void IteratorInordine::prim(){
	while (!s.empty())
		s.pop();
	actual = ab.radacina;
	
	while (actual != NULL)
	{
		//se adauga in stiva ramura stanga a elementului curent
		s.push(actual);
		actual = actual->st;
	}
	//se acceseaza nodul din varful stivei
	if(!s.empty())
		actual = s.top();
}

// Teta(1)
bool IteratorInordine::valid(){
	return actual != NULL;
}

// Teta(1)
TElem IteratorInordine::element() {
	if (!valid())
		throw(exception());
	return actual->element;
}

// Teta(1)
void IteratorInordine::urmator(){
	if (!valid())
		throw(exception());
	PNod aux = s.top();
	s.pop();
	if (actual->dr != NULL)
	{
		actual = actual->dr;
		while (actual != NULL)
		{
			//se adauga in stiva ramura stanga a elementului curent
			s.push(actual);
			actual = actual->st;
		}
	}
	if (!s.empty())
	{
		// se acceseaza nodul din varful stivei
		actual = s.top();
	}
	else
	{
		actual = NULL;
	}
}

// Teta(1)
IteratorPreordine::IteratorPreordine(const AB& _ab):ab(_ab){
	actual = NULL;
	prim();
}

/// caz favoranil : Teta(1)
/// caz defavorabil : Teta(n)
/// caz mediu : Teta(n)
/// overall case : O(n)
void IteratorPreordine::prim(){
	while (!s.empty())
		s.pop();
	
	actual = ab.radacina;
	s.push(ab.radacina);
}

// Teta(1)
bool IteratorPreordine::valid(){
	return actual != NULL;
}

// Teta(1)
TElem IteratorPreordine::element(){
	if (!valid())
		throw(exception());

	return actual->element;
}

// Teta(1)
void IteratorPreordine::urmator(){
	if (!valid())
		throw(exception());

	PNod aux = s.top();
	s.pop();

	if (aux->dr != NULL)
		s.push(aux->dr);
	if (aux->st != NULL)
		s.push(aux->st);

	if (!s.empty())
		actual = s.top();
	else
		actual = NULL;
}

// Teta(1)
IteratorPostordine::IteratorPostordine(const AB& _ab):ab(_ab){
	actual.p = _ab.radacina;
}

/// caz favoranil : Teta(1)
/// caz defavorabil : Teta(n)
/// caz mediu : Teta(n)
/// overall case : O(n)
void IteratorPostordine::prim(){ ///!
	while (!s.empty())
		s.pop();
	actual.k = 0;
	actual.p = ab.radacina;
	s.push(actual);
	while (actual.p != NULL)
	{
		el x;
		x.k = 0;
		x.p = actual.p;
		s.push(x);
		actual.p = actual.p->st;
	}
}

// Teta(1)
bool IteratorPostordine::valid(){
	return actual.p != NULL && !s.empty();
}

// Teta(1)
TElem IteratorPostordine::element(){
	if (!valid())
		throw(exception());
	return actual.p->element;
}

/// caz favoranil : Teta(1)
/// caz defavorabil : Teta(n)
/// caz mediu : Teta(n)
/// overall case : O(n)
void IteratorPostordine::urmator(){
	if (!valid())
		throw(exception());
	while (actual.p != NULL)
	{
		el x;
		x.k = 0;
		x.p = actual.p;
		s.push(x);
		actual.p = actual.p->st;
	}

	el x = s.top();
	s.pop();
	if (x.k == 0)
	{
		// nu s-a traversat subarborele drept al lui p
		x.k = 1;
		s.push(x);
		actual.p = x.p->dr;
	}
	else
	{
		//actual.p = NULL;
	}
}

// Teta(1)
IteratorLatime::IteratorLatime(const AB& _ab):ab(_ab){
	actual = NULL;
	prim();
}

/// caz favoranil : Teta(1)
/// caz defavorabil : Teta(n)
/// caz mediu : Teta(n)
/// overall case : O(n)
void IteratorLatime::prim(){
	while (!q.empty())
		q.pop();
	actual = ab.radacina;
	q.push(ab.radacina);
}

// Teta(1)
bool IteratorLatime::valid(){
	return actual != NULL;
}

// Teta(1)
TElem IteratorLatime::element(){
	if (!valid())
		throw(exception());
	return actual->element;
}

// Teta(1)
void IteratorLatime::urmator(){
	if (!valid())
		throw(exception());

	PNod aux = q.front();
	q.pop();
	if (aux->st != NULL)
		q.push(aux->st);
	if (aux->dr != NULL)
		q.push(aux->dr);

	if (!q.empty())
		actual = q.front();
	else
		actual = NULL;
}
