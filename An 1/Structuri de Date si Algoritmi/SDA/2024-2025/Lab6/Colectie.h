#pragma once
typedef int TElem;
#define NULL_TELEM 0x3f3f3f3f
#define INIT_SIZE 13
#define MAX_FACTOR 70

// Nu mai folosim Slot, ci doar vector de TElem
// struct Slot { ... } // eliminat

class IteratorColectie;

class Colectie {
    friend class IteratorColectie;
private:
    TElem* table;      // vector cu toate elementele
    bool* deleted;     // vector pentru marcarea ștergerilor logice
    int capacity;
    int totalSize;

    int h1(TElem e) const;
    int h2(TElem e) const;
    int hash(TElem e, int i) const;
    void resize();

public:
    Colectie();
    ~Colectie();

    void adauga(TElem elem);
    bool sterge(TElem elem);
    bool cauta(TElem elem) const;
    int nrAparitii(TElem elem) const;
    int dim() const;
    bool vida() const;
    IteratorColectie iterator() const;
    int diferenta() const;
};
