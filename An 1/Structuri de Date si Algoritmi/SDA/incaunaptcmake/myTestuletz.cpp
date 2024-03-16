//
// Created by vladb on 4/19/2023.
//
#include <iostream>
#include "myTestuletz.h"
#include "Multime.h"
#include "IteratorMultime.h"
#include <assert.h>

void run_my_teste()
{
    Multime m;
    IteratorMultime it = m.iterator();
    assert(m.vida());
    TElem e = 2;
    m.adauga(2);
    assert (m.dim()==1);
    m.adauga(3);
    assert(m.dim()==2);
    m.adauga(4);
    assert(m.dim()==3);
    m.adauga(4);
    m.adauga(5);
    m.sterge(3);
    assert(m.dim()==3);



    it.prim();
    std::cout<<it.element()<<"\n";
    it.urmator();
    std::cout<<it.element()<<"\n";
    it.urmator();
    std::cout<<it.element()<<"\n";
//    it.prim();
//    it.urmator();
//    it.urmator();
//    bool ok = rel(e, it.element());
//    std::cout<<ok<<"\n";

}