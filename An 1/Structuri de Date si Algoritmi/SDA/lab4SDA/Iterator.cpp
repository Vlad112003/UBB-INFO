#include "Iterator.h"


//Teta(1)
Iterator::Iterator(const LO& lo) : lo(lo){
	/* de adaugat */
	poz = lo.first;///pozitia curenta
	el = lo.ls[poz];///elementul curent
}

//Teta(1)
void Iterator::prim(){
	/* de adaugat */
	poz = lo.first;///pozitia curenta
	el = lo.ls[poz];///elementul curent
}

//Teta(1)
void Iterator::urmator(){
	/* de adaugat */
	if (valid())///daca pozitia curenta este valida
	{
		poz = lo.urm[poz];///pozitia curenta devine urmatoarea
		el = lo.ls[poz];///elementul curent devine urmatorul
	}
	else
		throw std::exception();
}
//Transformați iteratorul în unul bidirecțional. Adăugați operația următoare în clasa IteratorListăOrdonată.

// modifică elementul curent referit de iterator la elementul anterior, sau, în cazul în care elementul curent a fost primul, transformă iteratorul în unul nevalid

// aruncă excepție în cazul în care iteratorul nu este valid

///Worst case: Theta(n)
///Best case: Theta(1)
///Average case: O(n)
/*
 Subalgoritm anterior()
    pre: iteratorul este valid
    post: iteratorul se mută la poziția anterioară în listă

    dacă valid() este adevărat atunci
        dacă poz este egal cu lo.first atunci
            // Daca elementul curent este primul, transformă iteratorul în unul nevalid
            poz <- -1
            el <- NULL_TELEMENT
        altfel
            // Parcurgem lista de la inceput pana cand gasim pozitia care are urmatorul element ca pozitia curenta
            prev_poz <- -1
            pentru i de la lo.first până când i este diferit de poz și i este diferit de -1 executa
                prev_poz <- i
            dacă prev_poz != -1 atunci
                poz <- prev_poz
                el <- lo.ls[poz]
            altfel
                aruncă o excepție
        sfârșit dacă
    altfel
        aruncă o excepție
    sfârșit dacă
sfârșit subalgoritm anterior
 */
void Iterator::anterior() {
    if (valid()) {
        if (poz == lo.first) {
            // Daca elementul curent este primul, transformă iteratorul în unul nevalid
            poz = -1;//pozitia curenta devine -1
            el = NULL_TELEMENT;//elementul curent devine NULL_TELEMENT
        } else {
            // Parcurgem lista de la inceput pana cand gasim pozitia care are urmatorul element ca pozitia curenta
            int prev_poz = -1;//pozitia anterioara
            // Parcurgem lista de la inceput pana cand gasim pozitia care are urmatorul element ca pozitia curenta
            for (int i = lo.first; i != poz && i != -1; i = lo.urm[i]) {
                prev_poz = i;//pozitia anterioara devine i
            }
            if (prev_poz != -1) {
                poz = prev_poz;//pozitia curenta devine pozitia anterioara
                el = lo.ls[poz];//elementul curent devine elementul de pe pozitia curenta
            } else {
                throw std::exception(); // arunca exceptie daca nu s-a gasit pozitia anterioara
            }
        }
    } else {
        throw std::exception(); // arunca exceptie daca iteratorul nu este valid
    }
}


//Teta(1)
bool Iterator::valid() const{
	/* de adaugat */
	if (poz >= 0 && poz <= lo.cap && el!=NULL_TELEMENT)///daca pozitia curenta este valida
		return true;
	return false;
}

//Teta(1)
int Iterator::element() const{
	/* de adaugat */
	if (valid())///daca pozitia curenta este valida
		return el;///returneaza elementul curent
	return -1;
}



