//
// Created by vladb on 6/9/2023.
//

#include "MyTestuletz.h"

void testutz(){
    Relatie cresc = [](TCheie c1, TCheie c2) { return c1 <= c2; };
    MDO dictOrd = MDO(cresc);
    assert(dictOrd.dim() == 0);
    assert(dictOrd.vid());
    dictOrd.adauga(1, 2);
    dictOrd.adauga(1, 3);
    assert(dictOrd.dim() == 2);
    assert(!dictOrd.vid());

    vector<TValoare> v = dictOrd.cauta(1);
    assert(v.size() == 2);
    v = dictOrd.cauta(3);
    assert(v.size() == 0);
    IteratorMDO it = dictOrd.iterator();
    it.prim();
    while (it.valid()) {
        TElem e = it.element();
        it.urmator();
    }
    assert(dictOrd.sterge(1, 2) == true);
    assert(dictOrd.sterge(1, 3) == true);
    assert(dictOrd.sterge(2, 1) == false);
    assert(dictOrd.vid());

    dictOrd.adauga(1,4);
    dictOrd.adauga(1,3);
    dictOrd.adauga(2,5);
    dictOrd.adauga(3,6);
    for(auto i = dictOrd.iterator(); i.valid(); i.urmator())
        cout<<i.element().first<<" "<<i.element().second<<endl;


}
