//
// Created by Virginia Niculescu on 12/10/16.
//

#include "Test8_false_sharing.h"

#include <iostream>
#include <chrono>
#include <thread>
#include <vector>

using namespace std;

struct alignas (64) Element {// cache line usually is 64b
    uint64_t val;
};


void aduna(Element * el, uint64_t iterations)
{
    for(uint64_t i=0 ; i<iterations ; ++i) {
        el->val +=  i;
    }
}

int test_cache()
{
    auto t_start =chrono::steady_clock::now();
    unsigned const num_T = 4;
    Element*tablou = new Element[num_T];

    uint64_t const num_iter = 0xfffffffu; //40000000000;//
    std::vector<std::thread> threads(num_T);

    for(unsigned i=0 ; i < num_T; ++i){
        tablou[i].val = 0;
    }
    for(unsigned i=0 ; i < num_T; ++i){
        threads[i]=thread(aduna, &tablou[i], num_iter);
    }
    for(unsigned i=0 ; i < num_T; ++i){
        threads[i].join();
    }
    auto t_final = chrono::steady_clock::now();
    auto diff =t_final-t_start;

    cout<<"computation time "<<chrono::duration <double, milli > (diff).count() << " ms" << endl;

    uint64_t e = num_iter * (num_iter - 1)/2;
    cout<<"result="<<e<<endl;
    bool ok = std::all_of(tablou, tablou + num_T,
                          [e](Element const& c)->bool {return (e == c.val);} );
    cout<< (ok ? "corect" : "incorect")<<endl;
     return 0;
}