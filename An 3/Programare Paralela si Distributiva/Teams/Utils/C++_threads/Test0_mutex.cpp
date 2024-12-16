

#include "Test0_mutex.h"
#include <iostream>
#include <thread>
#include<mutex>


using namespace std;


void f0_b_pointer(int a, mutex * m){//mutex * pointer to the mutex is passed
    auto id = this_thread::get_id();
    uint64_t*  p_id =  (uint64_t*) &id;
     this_thread::sleep_for(std::chrono::seconds(25));
    m->lock();
    cout << "a=" << a << "I am thread pointer!"<<" ID= "<<this_thread::get_id()<<" pointer to id "<<*p_id  << endl;
    m->unlock();

}



void f0_b_ref(int a, mutex & m){//mutex & reference to the mutex is passed
    auto id = this_thread::get_id();
    uint64_t*  p_id =  (uint64_t*) &id;
//cout<<id<<endl;
    m.lock();
    cout <<"a="<<a << "I am thread ref!" << " ID= " << this_thread::get_id() << " pointer to id " << *p_id << endl;
    m.unlock();
    // this_thread::sleep_for(std::chrono::seconds(25));
}

//void f0_b_val(int a, mutex  m){//mutex & !!!
//    auto id = this_thread::get_id();
//    uint64_t*  p_id =  (uint64_t*) &id;
////cout<<id<<endl;
//    m.lock();
//    cout << "I am thread ref!"<<a<<" ID= "<<this_thread::get_id()<<" pointer to id "<<*p_id  << endl;
//    m.unlock();
//    // this_thread::sleep_for(std::chrono::seconds(25));
//}
int test0_mutex(){

    mutex m;
    int i =1; //global variable passed to the threads

    thread t1(f0_b_pointer, ref(i), &m);//ref(i) i sent by reference
//    t1.detach();
    auto id1 = t1.get_id();

    i++;
   // thread t2(f0_b_pointer,ref(i), &m);i sent by reference
    thread t2(f0_b_pointer, i, &m); //i sent by value
//    t2.detach();
    auto id2 = t2.get_id();
    this_thread::sleep_for(std::chrono::seconds(10));
    i++;
    //thread t3(f0_b_ref, ref(i), ref(m)); //i sent by reference
    thread t3(f0_b_ref, i, ref(m)); //i sent by value
//    t3.detach();

    /*The arguments to the thread function are moved or copied by value.
     * If a reference argument needs to be passed to the thread function,
     * it has to be wrapped (e.g. with std::ref or std::cref).
     * */
    auto id3 = t2.get_id();

    m.lock();
    cout<<"i="<<i << "main thread id is " << this_thread::get_id() << "   thread id1 " << id1 << endl;
    m.unlock();
    this_thread::sleep_for(std::chrono::seconds(5));
    m.lock();
    cout<<"i="<<i<<"main thread id is "<<this_thread::get_id()<<"   thread id2 "<<id2<<endl;
    m.unlock();

    t1.join();
    t2.join();
    this_thread::sleep_for(std::chrono::seconds(5));
    t3.join();// if threads are not joined and  - error code 6
//    t3.detach();
    cout<<"i="<<i<<endl;
    return 0;
}

