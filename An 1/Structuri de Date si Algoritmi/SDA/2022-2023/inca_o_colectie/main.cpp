#include <iostream>
#include <cassert>
#include "TestExtins.h"
#include "TestScurt.h"
#include "Colectie.h"
#include "IteratorColectie.h"

using namespace std;


int main() {
	testAll();
    testAllExtins();
    auto c = Colectie();
//    int n;
    TElem e;
//    cout<<"introduceti nr de numere de adaugat in colectie: \n";
//    cin>>n;
//    int nr = n;
//    for(int i=0;i<nr;i++)
//    {
//        cout<<"Introduceti elementul "<<i<<": \n";
//        cin>>e;
//        c.adauga(e);
//    }
//    cout<<"Reprezentarea colectiei este: \n";
//    for(int i = 0; i<c.nrElementeUnice(); i++)
//        cout<<"("<<c.return_element(i)<<", "<<c.return_frecventa(i)<<")\n";
    cout<<"Introduceti elemente in colectie: \n(Introduceti x pentru finish)\n";
    while(cin>>e)
    {
        c.adauga(e);
    }
        cout<<"Reprezentarea colectiei este: \n";
    cout<<"[";
    for(int i = 0; i<c.nrElementeUnice(); i++)
        cout<<"("<<c.return_element(i)<<","<<c.return_frecventa(i)<<")";
    cout<<"]";

	//cout<<"End";
}
