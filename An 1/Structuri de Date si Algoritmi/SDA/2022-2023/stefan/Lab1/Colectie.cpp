#include "Colectie.h"
#include "IteratorColectie.h"
#include <exception>
#include <iostream>

using namespace std;

Colectie::Colectie() {
	this->dimensiune = 0;
	this->capacitateVector = 0;
	this->nrElemente = 0;
}


void Colectie::adauga(TElem elem) {
	// Verificam daca elementul exista deja in colectie:
	for (int i = 0; i < this->dimensiune; i++)
	{
		if (this->vectorElemente[i] == elem)
		{
			// Adaugam elementul si iesim din functie
			this->nrElemente++;
			this->vectorFrecvente[i]++;
			return;
		}
	}

	// Daca elementul este unul nou:
	if (this->dimensiune >= this->capacitateVector)
	// Este necesara o redimensionare a colectiei:
	{
		if (this->dimensiune > this->capacitateVector)
			throw ("eroare! capacitatea anterioara a fost depasita.");

		// Alocare spatiu pentru vectori noi mai mari:
		if (this->capacitateVector == 0)
			this->capacitateVector += 2;
		else
			this->capacitateVector *= 2;
		TElem* tempVectorElemente = new TElem[capacitateVector];
		int* tempVectorFrecvente = new int[capacitateVector];

		// Copiere valori anterioare:
		for (int i = 0; i < this->dimensiune; i++)
		{
			tempVectorElemente[i] = this->vectorElemente[i];
			tempVectorFrecvente[i] = this->vectorFrecvente[i];
		}

		// Dealocare vectori anteriori si actualizare:
		delete[] this->vectorElemente;
		delete[] this->vectorFrecvente;

		this->vectorElemente = tempVectorElemente;
		this->vectorFrecvente = tempVectorFrecvente;
	}
		
	// Adaugare propriu-zisa a unui element nou:
	this->nrElemente++;
	this->vectorElemente[this->dimensiune] = elem;
	this->vectorFrecvente[this->dimensiune] = 1;
	this->dimensiune++;
}


bool Colectie::sterge(TElem elem) {
	
	// Cautam elementul:
	for (int i = 0; i < this->dimensiune; i++)
	{
		if (this->vectorElemente[i] == elem)
		// Elementul a fost gasit
		{
			// Decrementan frecventa:
			this->nrElemente--;
			this->vectorFrecvente[i]--;

			// Daca noua frecventa este 0, scoatem elementul din colectie:
			if (this->vectorFrecvente[i] == 0)
			{
				for (int j = i; j < this->dimensiune - 1; j++)
				{
					this->vectorElemente[j] = this->vectorElemente[j + 1];
					this->vectorFrecvente[j] = this->vectorFrecvente[j + 1];
				}
				this->dimensiune--;
			}
			
			return true;
		}
	}

	// Daca elementul nu a fost gasit sau daca colectia este goala:
	return false;
}


bool Colectie::cauta(TElem elem) const {
	// Cautam perechea (elem, frecv) a carui elem este egal cu cel cautat:
	for (int i = 0; i < this->dimensiune; i++)
		if (this->vectorElemente[i] == elem)
			// Am gasit elementul:
			return true;

	return false;
}

int Colectie::nrAparitii(TElem elem) const {
	// Cautam elementul cu valoarea ceruta
	for (int i = 0; i < this->dimensiune; i++)
		if (this->vectorElemente[i] == elem)
			// Daca gasim elementul, returnam frecventa lui:
			return this->vectorFrecvente[i];

	// Daca elementul nu a fost gasit, atunci are frecventa 0:
	return 0;
}


int Colectie::dim() const {
	// Daca lista nu este goala:
	if (this->dimensiune > 0)
		return this->nrElemente;

	return 0;
}


bool Colectie::vida() const {
	// Daca dimensiunea este 0, atunci colectia este vida, altfel nu:
	return this->dimensiune == 0;
}

IteratorColectie Colectie::iterator() const {
	return  IteratorColectie(*this);
}


Colectie::~Colectie() {
	// Dealocam cei 2 vectori:
	delete[] this->vectorElemente;
	delete[] this->vectorFrecvente;
}


int Colectie::nrElementeUnice() const {
	int count = 0;

	// Parcurgem toate perechile de (element, frecventa):
	for (int i = 0; i < this->dimensiune; i++)
		// Daca frecventa unui element este 1, atunci el este unic:
		if (this->vectorFrecvente[i] == 1)
			count++;

	return count;
}
