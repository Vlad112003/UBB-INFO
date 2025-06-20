#include "Multime.h"
#include "IteratorMultime.h"
#include <iostream>

using namespace std;

//o posibila relatie
bool rel(TElem e1, TElem e2) {
    if (e1 <= e2) {
        return true;
    }
    else {
        return false;
    }
}

Multime::Multime() {
    this->capacitateVector = 10;

    this->vector = new TElem [this->capacitateVector];

    this->dimensiune = 0;
}

void Multime::redim()
{
    TElem *elementeNoi = new TElem[capacitateVector*2];

    for (int i = 0; i<this->dimensiune; i++)
        elementeNoi[i] = this->vector[i];

    this->capacitateVector *=2;

    delete[] this->vector;

    this->vector = elementeNoi;
}

int Multime::cautare(TElem elem) const{
    for (int i = 0; i<this->dimensiune;i++)
        if(this->vector[i]==elem)
        {
            return i;
        }
    return -1;
}

void Multime::select_sort()
{
    TElem aux;
    for(int i =0; i<this->dimensiune-1; i++)
        for (int j = i+1; j<this->dimensiune; j++)
            if(this->vector[i]>this->vector[j])
            {
                aux =  this->vector[i];
                this->vector[i] = this->vector[j];
                this->vector[j] = aux;
            }
}

void Multime::quicksort(int arr[], int left, int right) {
    int i = left, j = right;
    int pivot = arr[(left + right) / 2];

    while (i <= j) {
        while (arr[i] > pivot) {
            i++;
        }
        while (arr[j] < pivot) {
            j--;
        }
        if (i <= j) {
            swap(arr[i], arr[j]);
            i++;
            j--;
        }
    }

    if (left < j) {
        quicksort(arr, left, j);
    }
    if (i < right) {
        quicksort(arr, i, right);
    }
}

int Multime::binarySearch(int arr[], int left, int right, TElem x) const{
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (arr[mid] == x) {
            return mid;
        }
        if (arr[mid] < x) {
            left = mid + 1;
        }
        else {
            right = mid - 1;
        }
    }
    return -1;
}

bool Multime::adauga(TElem elem) {
    if (this->capacitateVector == this->dimensiune)
    {
        redim();
    }
    int pos = binarySearch(this->vector, 0, this->dimensiune, elem);
    if(pos != -1) return false;
    if(pos == -1)
    {
        this->vector[this->dimensiune] = elem;
        this->dimensiune++;
        select_sort();
        return true;
    }


}


bool Multime::sterge(TElem elem) {
    int poz = binarySearch(this->vector, 0, this->dimensiune, elem);
    if(poz==-1)
        return false;
    for( int i = poz; i<this->dimensiune-1; i++)
        this->vector[i] = this->vector[i+1];
    this->dimensiune--;
    if (cautare(elem) == -1)
        return true;
    return false;
}


bool Multime::cauta(TElem elem) const {
    if (this->binarySearch(this->vector, 0, this->dimensiune, elem) != -1)
        return true;
    return false;
}


int Multime::dim() const {
    return this->dimensiune;
}

TElem Multime::return_element(int i) const {
    return this->vector[i];
}

bool Multime::vida() const {
    if (this->dimensiune!=0) return false;
    return true;
}

IteratorMultime Multime::iterator() const {
    return IteratorMultime(*this);
}


Multime::~Multime() {
    delete[] this->vector;
}







