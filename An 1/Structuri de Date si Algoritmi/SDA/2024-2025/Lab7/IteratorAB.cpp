#include "AB.h"
#include "IteratorAB.h"
#include <exception>

// IteratorInordine

IteratorInordine::IteratorInordine(const AB& _ab):ab(_ab) {
    prim();
}

void IteratorInordine::prim() {
    // Golim stiva în caz că iteratorul este reutilizat
    while (!stiva.empty()) {
        stiva.pop();
    }
    
    curent = ab.rad;
    
    // Adăugăm toți nodii din stânga în stivă
    while (curent != nullptr) {
        stiva.push(curent);
        curent = curent->st;
    }
    
    // Dacă există elemente în stivă, setăm curent la vârful stivei
    if (!stiva.empty()) {
        curent = stiva.top();
    } else {
        curent = nullptr;
    }
}

bool IteratorInordine::valid() {
    return curent != nullptr;
}

TElem IteratorInordine::element() {
    if (!valid()) {
        throw std::exception();
    }
    return curent->e;
}

void IteratorInordine::urmator() {
    if (!valid()) {
        throw std::exception();
    }
    
    // Extragem nodul curent din stivă
    Nod* nod = stiva.top();
    stiva.pop();
    
    // Dacă nodul are un subarbore drept, trebuie să adăugăm toți
    // nodii din stânga acestuia în stivă
    if (nod->dr != nullptr) {
        Nod* aux = nod->dr;
        while (aux != nullptr) {
            stiva.push(aux);
            aux = aux->st;
        }
    }
    
    // Dacă există elemente în stivă, setăm curent la vârful stivei
    if (!stiva.empty()) {
        curent = stiva.top();
    } else {
        curent = nullptr;
    }
}

// IteratorPreordine

IteratorPreordine::IteratorPreordine(const AB& _ab):ab(_ab) {
    prim();
}

void IteratorPreordine::prim() {
    // Golim stiva în caz că iteratorul este reutilizat
    while (!stiva.empty()) {
        stiva.pop();
    }
    
    if (ab.rad != nullptr) {
        stiva.push(ab.rad);
        curent = ab.rad;
    } else {
        curent = nullptr;
    }
}

bool IteratorPreordine::valid() {
    return curent != nullptr;
}

TElem IteratorPreordine::element() {
    if (!valid()) {
        throw std::exception();
    }
    return curent->e;
}

void IteratorPreordine::urmator() {
    if (!valid()) {
        throw std::exception();
    }
    
    // Extragem nodul curent din stivă
    Nod* nod = stiva.top();
    stiva.pop();
    
    // Adăugăm copiii în ordine inversă (întâi drept, apoi stâng)
    // pentru ca în stivă să fie procesați în ordinea corectă (stâng, apoi drept)
    if (nod->dr != nullptr) {
        stiva.push(nod->dr);
    }
    if (nod->st != nullptr) {
        stiva.push(nod->st);
    }
    
    // Dacă există elemente în stivă, setăm curent la vârful stivei
    if (!stiva.empty()) {
        curent = stiva.top();
    } else {
        curent = nullptr;
    }
}

// IteratorPostordine

IteratorPostordine::IteratorPostordine(const AB& _ab):ab(_ab) {
    prim();
}

void IteratorPostordine::prim() {
    // Golim stiva în caz că iteratorul este reutilizat
    while (!stiva.empty()) {
        stiva.pop();
    }
    
    ultim_vizitat = nullptr;
    
    // Adăugăm calea până la primul nod în parcurgerea postordine (cel mai din stânga-jos)
    Nod* nod = ab.rad;
    while (nod != nullptr) {
        stiva.push(nod);
        if (nod->st != nullptr) {
            nod = nod->st;
        } else {
            nod = nod->dr;
        }
    }
    
    // Setăm nodul curent dacă există elemente în stivă
    if (!stiva.empty()) {
        curent = stiva.top();
    } else {
        curent = nullptr;
    }
}

bool IteratorPostordine::valid() {
    return curent != nullptr && !stiva.empty();
}

TElem IteratorPostordine::element() {
    if (!valid()) {
        throw std::exception();
    }
    return curent->e;
}

void IteratorPostordine::urmator() {
    if (!valid()) {
        throw std::exception();
    }
    
    // Marcăm nodul curent ca fiind vizitat
    ultim_vizitat = stiva.top();
    stiva.pop();
    
    // Dacă stiva este goală, am terminat
    if (stiva.empty()) {
        curent = nullptr;
        return;
    }
    
    // Verificăm dacă trebuie să mergem în dreapta
    Nod* varf = stiva.top();
    if (varf->dr != nullptr && varf->dr != ultim_vizitat) {
        // Mergem în subarborele drept și adăugăm calea la cel mai din stânga nod
        Nod* nod = varf->dr;
        while (nod != nullptr) {
            stiva.push(nod);
            if (nod->st != nullptr) {
                nod = nod->st;
            } else {
                nod = nod->dr;
            }
        }
    }
    
    // Setăm nodul curent la vârful stivei
    curent = stiva.top();
}

// IteratorLatime

IteratorLatime::IteratorLatime(const AB& _ab):ab(_ab) {
    prim();
}

void IteratorLatime::prim() {
    // Golim coada în caz că iteratorul este reutilizat
    while (!coada.empty()) {
        coada.pop();
    }
    
    // Adăugăm rădăcina în coadă dacă există
    if (ab.rad != nullptr) {
        coada.push(ab.rad);
    }
}

bool IteratorLatime::valid() {
    return !coada.empty();
}

TElem IteratorLatime::element() {
    if (!valid()) {
        throw std::exception();
    }
    return coada.front()->e;
}

void IteratorLatime::urmator() {
    if (!valid()) {
        throw std::exception();
    }
    
    // Extragem nodul curent din coadă
    Nod* nod = coada.front();
    coada.pop();
    
    // Adăugăm copiii nodului în coadă
    if (nod->st != nullptr) {
        coada.push(nod->st);
    }
    if (nod->dr != nullptr) {
        coada.push(nod->dr);
    }
}
