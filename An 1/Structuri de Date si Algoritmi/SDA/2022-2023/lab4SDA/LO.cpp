#include "Iterator.h"
#include "LO.h"

#include <iostream>
using namespace std;

#include <exception>

//worst case: Theta(n)
//best case: Theta(1)
//average case: O(n)

LO::LO(Relatie r) {
	/* de adaugat */
	cap = INIT_SIZE;///capacitatea vectorului
	lenFree = INIT_SIZE;///lungimea vectorului de pozitii libere
	first = 0;
	len = 0;
	ls = new TElement[cap];///vectorul de elemente
	urm = new int[cap];///vectorul de pozitii urmatoare
	ls[first] = NULL_TELEMENT;///primul element este NULL_TELEMENT
	urm[first] = -1;///pozitia urmatoare este -1
	this->r = r;///relatia de ordine

	Node* copy = new Node();///lista de pozitii libere
	head = new Node(first);///capul listei de pozitii libere
	copy->next = head;///capul listei de pozitii libere
	for (int i = 0; i < lenFree; i++)///adaugam in lista de pozitii libere pozitiile de la 1 la lenFree
	{
		Node* temp = new Node(i + 1);///pozitia curenta
		head->next = temp;///adaugam pozitia curenta la lista de pozitii libere
		head = head->next;///trecem la urmatoarea pozitie
	}
	head->next = nullptr;///ultima pozitie este NULL
	head = copy->next;///capul listei de pozitii libere
	delete copy;///stergem copia
}

///Worst case: Theta(n)
///Best case: Theta(1)
///Average case: O(n)

void LO::redim()
{
	cap *= 2;///dublam capacitatea
	TElement* newLs = new TElement[cap];///noul vector de elemente
	int* newUrm = new int[cap];///noul vector de pozitii urmatoare
	for (int i = 0; i < len; i++)///copiem elementele si pozitiile urmatoare
	{
		newLs[i] = ls[i];///copiem elementele
		newUrm[i] = urm[i];///copiem pozitiile urmatoare
	}
	
	delete[] ls;///stergem vechiul vector de elemente
	delete[] urm;///stergem vechiul vector de pozitii urmatoare
	ls = newLs;///noul vector de elemente
	urm = newUrm;///noul vector de pozitii urmatoare

	Node* copy = new Node();///lista de pozitii libere
	copy->next = head;///capul listei de pozitii libere
	for (int i = cap/2; i < cap; i++)///adaugam in lista de pozitii libere pozitiile de la cap/2 la cap
	{
		Node* temp = new Node(i + 1);///pozitia curenta
		head->next = temp;///adaugam pozitia curenta la lista de pozitii libere
		head = head->next;///trecem la urmatoarea pozitie
		lenFree++;///crestem lungimea listei de pozitii libere
	}
	head->next = nullptr;///ultima pozitie este NULL
	head = copy->next;///capul listei de pozitii libere
}

///Worst case: Theta(1)
///Best case: Theta(1)
///Average case: O(1)
// returnare dimensiune
int LO::dim() const {
	/* de adaugat */
	return len;
}

//Teta(1)
// verifica daca LO e vida
bool LO::vida() const {
	/* de adaugat */
	if (len == 0)
		return true;
	else
		return false;
}

//Teta(1)
// prima pozitie din LO
Iterator LO::prim() const {
	/* de adaugat */
	return Iterator(*this);
}

//Teta(1)
// returnare element de pe pozitia curenta
//arunca exceptie daca poz nu e valid
TElement LO::element(Iterator poz) const {
	/* de adaugat */
	if (poz.valid())
		return poz.el;
	throw std::exception();
}

///Worst case: Theta(n)
///Best case: Theta(1)
///Average case: O(n)
//sterge element de pe o pozitie poz si returneaza elementul sters
//dupa stergere poz e pozitionat pe elementul de dupa cel sters
//arunca exceptie daca poz nu e valid
TElement LO::sterge(Iterator& poz) {
	/* de adaugat */
	if (poz.valid())///daca pozitia e valida
	{
		TElement temp = poz.el;///elementul de pe pozitia curenta, care va fi sters, il retinem, pentru a-l returna, in temp
		//delete &ls[poz.poz];
		if (poz.poz != first)///daca nu e primul element, atunci trebuie sa modificam urmatorul element al elementului precedent
		{
			Iterator prev = Iterator(*this);///iteratorul prev va fi pozitionat pe elementul precedent, pentru a-i modifica urmatorul, dupa stergere
			Iterator ps = cauta(poz.el);///iteratorul ps va fi pozitionat pe elementul care trebuie sters, pentru a-i modifica urmatorul, dupa stergere
			while (prev.valid() && urm[prev.poz] != ps.poz)///prev va fi pozitionat pe elementul precedent,
				prev.urmator();///care va fi gasit cautand elementul curent in lista
			urm[prev.poz] = urm[ps.poz];///modificam urmatorul elementului precedent
		}
		else
		{
			poz.urmator();///daca e primul element, atunci dupa stergere, pozitia curenta va fi pozitionata pe elementul urmator
			first = poz.poz;///modificam primul element
		}

		len--;///scadem lungimea listei
		lenFree++;///crestem lungimea listei de elemente libere
		Node* newHead = new Node(poz.poz);///adaugam pozitia curenta in lista de elemente libere
		newHead->next = head;///modificam head-ul listei de elemente libere
		head = newHead;///modificam head-ul listei de elemente libere
		return temp;///returnam elementul sters
	}
	else
		throw std::exception();///daca pozitia nu e valida, aruncam exceptie
}

///Worst case: Theta(n)
///Best case: Theta(1)
///Average case: O(n)
// cauta element si returneaza prima pozitie pe care apare (sau iterator invalid)
Iterator LO::cauta(TElement e) const{
	/* de adaugat */
	Iterator it = Iterator(*this);
	while (it.valid() && it.el != e)
		it.urmator();
	if (it.el != e)
		it.poz = -1;
	return it;
	//search can stop after relation is no longer verified
}

///Worst case: Theta(n)
///Best case: Theta(1)
///Average case: O(n)
// adaugare element in LO
void LO::adauga(TElement e) {
	/* de adaugat */
	if (len == cap)///daca e plin
	{
		redim();///redimensionare
	}

	if (len == 0)
	{
		ls[head->data] = e;///adaugare element
		urm[head->data] = -1;///urmatorul e -1, adica nu exista

	}
	else
	{
		Iterator it = prim();///iteratorul pleaca de la inceput
		if (!r(it.el, e))///daca e mai mic decat primul element
		{
			ls[head->data] = e;///adaugare element
			urm[head->data] = first;///urmatorul e primul element
			first = head->data;///primul element e noul element
		}
		else
		{
			Iterator prev = Iterator(*this);///iteratorul pleaca de la inceput
				while (it.valid() && r(it.el, e))///cat timp e mai mic decat elementul curent
				{
					it.urmator();///se trece la urmatorul element
				}

			while (prev.valid() && urm[prev.poz] != it.poz)///cat timp nu s-a ajuns la elementul anterior celui curent
				prev.urmator();///se trece la urmatorul element

			/*ls[head->data] = e;
			urm[it.poz] = head->data;*/

			ls[head->data] = e;///adaugare element
			urm[head->data] = it.poz;///urmatorul e elementul curent, adica it
			urm[prev.poz] = head->data;///elementul anterior e noul element, adica head
		}

	}
	head = head->next;///se trece la urmatorul element
	len++;///se creste lungimea
	lenFree--;///scade numarul de elemente libere
}

//Complexitate:
//Best case: O(1)
//Worst case: O(n)
//Average case: O(n)
/*
 * Preconditii: k > 0
 * Postconditii: se elimina din lista elementele de pe pozitiile k, 2k, 3k, ...
 * Returneaza numarul de elemente eliminate
 * Daca k > n, se va arunca exceptie
 *
 * Pseudocod:
 * Subprogram EliminaDinKInK(k)
 *      daca k <= 0 atunci
 *          arunca exceptie
 *      sfarsit daca
 *      it <- prim()
 * 	    cnt <- 0
 * 	    i <- 0
 * 	    daca len>=k atunci
 * 	        cat timp valid(i) executa
 * 	            daca i % k = 0 atunci
 * 	                sterge(i)
 * 	                cnt <- cnt + 1
 * 	                i <- i + 1
 * 	            altfel
 * 	                i <- i + 1
 * 	                urmator(i)
 * 	            sfarsit daca
 * 	        sfarsit cat timp
 * 	    returneaza cnt
 *
 */

//Iterator LO::ultimul() {
//    Iterator it = Iterator(*this);
//    while (it.valid()) {
//        it.urmator();
//    }
//    it.anterior();
//    return it;
//}
//destructor
LO::~LO() {
	/* de adaugat */
	delete[] urm;///destructor
	delete[] ls;///destructor

	while (head->next != nullptr)///
	{
		Node* n = head->next;
		delete head;
		head = n;
	}

}
