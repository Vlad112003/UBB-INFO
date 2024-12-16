//
// Created by Virginia Niculescu on 03/10/16.
// ~ a small initial example with C++ threads
//

#include "Test0.h"
#include <iostream>
#include <thread>
#include <vector>
#include <functional>

using namespace std;

///////////////////////////////////////////////////////////////////////////////////////////////////////
void f0(int a){

    auto id = this_thread::get_id(); 
    uint64_t  p_id =  (uint64_t) &id;
    cout << "I am thread no. "<<a<<" ID= "<<this_thread::get_id()<<"******   thread id *******"<<p_id  << endl;
    this_thread::sleep_for(std::chrono::seconds(2));

    if (a<10)  // what if this condition is excluded?
    {
        thread t(f0, a + 10);
        t.join(); //t.detach();
    }
}
///////////////////////////////////////////////////////////////////////////////////////////////////////
void f1(int a, int& b, std::function<int(int, int)> f) {

// a parameter of function type
    auto id = this_thread::get_id(); 
    uint64_t  p_id =  (uint64_t) &id;
    cout << "I am thread with ID= "<<this_thread::get_id()<<"******   thread id *******"<<p_id  << endl;
    cout<<"my computation result is ="<<f(a,b)<<endl;
}
///////////////////////////////////////////////////////////////////////////////////////////////////////
int test0(){
    // is here in this example a race condition? if yes explain and solve it!
    // how many threads are executed?

       
    auto t_start = chrono::steady_clock::now();

    thread t1(f0,1);

    
    auto id1 = t1.get_id();

    int x=2, y=3;
    // function type parameter could be initialized with a lambda expression

    thread t2(f1, x, std::ref(y), [=](int xx,int yy){return xx+yy;});

    auto id2 = t2.get_id();

    cout<<"main thread id is "<<this_thread::get_id()<<"******   thread id1 is *******"<<id1<<endl;

    this_thread::sleep_for(std::chrono::seconds(3));

    cout<<"main thread id is "<<this_thread::get_id()<<"******   thread id2 is *******"<<id2<<endl;

    t1.join();
// a child thread should be joined or detached!
    t2.join();
      //t2.detach(); // the object thread is detached from the execution thread that is still executed by the OS

    auto t_final =chrono::steady_clock::now();
    auto diff =t_final-t_start;
    cout<<"computation time of the main thread = "<<chrono::duration <double, milli> (diff).count() << " ms" <<endl;

    return 0;
}
///////////////////////////////////////////////////////////////////////////////////////////////////////