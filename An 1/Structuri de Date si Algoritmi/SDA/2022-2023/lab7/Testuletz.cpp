//
// Created by vladb on 6/9/2023.
//

#include <cassert>
#include "Testuletz.h"
#include "MDO.h"
#include "IteratorMDO.h"
#include <iostream>
using namespace std;
//bool cresc(TCheie c1, TCheie c2) {
//    if (c1 <= c2) {
//        return true;
//    }
//    else {
//        return false;
//    }
//}


void myTestuletz(){
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

    dictOrd.adauga(1, 3);
    dictOrd.adauga(1, 6);
    dictOrd.adauga(2, 5);
    TCheie ch = dictOrd.cheieMaxima();
    cout<<"Before StergeValoriPtCheie"<<endl;
    for(auto it = dictOrd.iterator(); it.valid(); it.urmator())
        cout<<it.element().first<<" "<<it.element().second<<endl;
    auto vect_nou = dictOrd.stergeValoriPentruCheie(1);
    cout<<"After StergeValoriPtCheie"<<endl;
    for(auto it = dictOrd.iterator(); it.valid(); it.urmator())
        cout<<it.element().first<<" "<<it.element().second<<endl;

    cout<<"Valorile sterse din MDO dupa StergeValoriPtCheie:\n";
    for (auto it : vect_nou){
        cout<<it<<" ";
    }
    cout<<"\n";
    assert(ch == 1);
    cout<<"Testuletz passed!"<<endl;
}