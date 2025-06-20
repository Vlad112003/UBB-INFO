#include "Colectie.h"
#include "IteratorColectie.h"
#include <exception>
#include <iostream>

using namespace std;


Colectie::Colectie() {
	//capacitatea initiala a vectorilor dinamici
    this->capacitateVector = 10;

    //vectorul dinamic care contine elementele TElem
    this->vectorElemente = new TElem[this->capacitateVector];

    //vectorul dinamic care contine frecventele
    this->vectorFrecvente = new int[this->capacitateVector];

    //nr de elemente din vector
    this->nrElemente=0;

    //dimensiunea vectorului
    this->dimensiune=0;
}

void Colectie::redim(){

    TElem *elementeNoi = new TElem[capacitateVector*2];
    int *frecventaNoua = new int[capacitateVector*2];


    for (int i = 0; i<this->dimensiune;i++){
        elementeNoi[i] = this->vectorElemente[i];
        frecventaNoua[i] = this->vectorFrecvente[i];
    }


    this->capacitateVector *=2;

    delete[] this->vectorElemente;
    delete[] this->vectorFrecvente;

    this->vectorElemente = elementeNoi;
    this->vectorFrecvente = frecventaNoua;

}

int Colectie::cautare(TElem elem) const {
    for(int i=0; i<this->dimensiune;i++)
        if(this->vectorElemente[i] == elem)
            return i;
    return -1;
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
    int pos = cautare(elem);
    if (pos==-1) return false;
    return true;
}

int Colectie::nrAparitii(TElem elem) const {
    int pos = cautare(elem);
    if (pos == -1)
        return 0;
    else return vectorFrecvente[pos];
}


int Colectie::dim() const {
//    int dim = 0;
//    for(int i=0;i<=n;i++)
//        dim = dim + frecventa[i];
//    return dim;
    if(this->dimensiune > 0)
        return this->nrElemente;
    return 0;
}


bool Colectie::vida() const {
	return (this->nrElemente==0);
}

IteratorColectie Colectie::iterator() const {
	return  IteratorColectie(*this);
}


Colectie::~Colectie() {
	delete[] vectorElemente;
    delete[] vectorFrecvente;
}

int Colectie::return_element(int i) {
    return this->vectorElemente[i];
}

int Colectie::return_frecventa(int i) {
    return this->vectorFrecvente[i];
}

//nr elemente care apar cel putin o data
int Colectie::nrElementeUnice() const {
    int count = 0;

    // Parcurgem toate perechile de (element, frecventa):
    for (int i = 0; i < this->dimensiune; i++)
        // Daca frecventa unui element este mai mare sau egal cu 1, atunci el apare cel putin o data:
        if (this->vectorFrecvente[i] >= 1)
            count++;

    return count;
}

int Colectie::stergeNrAparitii(TElem elem, int nr) {
    int nr_stergeri = 0;
    if (nr < 0)
    {
        throw "frecventa nu poate fi negativa!";
    }
    int n = nrAparitii(elem);
    if (nr> nrAparitii(elem)) {
        for (int i = 0; i < n; i++)
        {
            sterge(elem);
            nr_stergeri++;
    }
    }
    return nr_stergeri;
}

//Pseudocod pt functia stergeNrAparitii:
//
//Subalgoritm stergeNrAparitii(c, element, nr_aparitii_dat)
//    nr_stergeri <- 0
//    Daca (nr_aparitii_dat < 0) atunci
//        arunca exceptie "frecventa nu poate fi negativa!"
//    SfDaca
//    numar_aparitii <- nrAparitii(elem)
//    Daca (nr_aparitii_dat > numar_aparitii) atunci
//        Pentru i<-0, numar_aparitii, 1 executa
//            sterge(element)
//            nr_stergeri <- nr_stergeri + 1
//        SfPentru
//    returneaza nr_stergeri
//SfSubalgoritm

//preconditii: c- colectia din care stergem elementul
              //  e apartine lui TElem
            //    nr_aparitii dat este un numar natural
//postconditii: c = c' - nr_aparitii al elementului


