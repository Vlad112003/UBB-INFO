#include <bits/stdc++.h>
#include "grafuri.h"
using namespace std;

#define NIL 0
#define inf 0x3f3f3f3f

ifstream fin("graf.txt");

void Moore(GrafListaAdiacenta& g, int src, int* l, int* p)
{
    // initializare
    for(int i=0;i<=g.noduri;++i)
        l[i] = inf,
                p[i] = NIL;
    l[src] = 0;
    priority_queue<int, vector<int>, greater<>> q;
    q.push(src);
    // alg
    while(!q.empty())
    {
        int x = q.top();
        q.pop();
        for(int y : g[x])
            if(l[y] == inf)
                l[y] = l[x] + 1,
                        p[y] = x,
                        q.push(y);
    }
}

int main()
{
    // citire
    int n, n1, n2, src, *l, *p;
    fin>>n;
    l = new int[n+1];
    p = new int[n+1];
    GrafListaAdiacenta g(n, true);
    while(fin>>n1>>n2) g.adauga(n1, n2);
    cout<<"Varful sursa:"; cin>>src;

    // apel Moore
    Moore(g, src, l, p);
    cout<<setw(3);
    for(int i=1;i<=n;++i)
        cout<<i<<' ';
    cout<<endl<<setw(3);
    for(int i=1;i<=n;++i)
        if(l[i] != inf) cout<<l[i]<<' ';
        else cout<<"inf ";
    cout<<endl;
    for(int i=1;i<=n;++i)
        if(p[i] != NIL) cout<<p[i]<<' ';
        else cout<<"NIL ";
}