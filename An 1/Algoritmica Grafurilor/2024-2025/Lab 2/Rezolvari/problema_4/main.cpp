#include <iostream>
#include <queue>
#include <fstream>
#define inf 10000
using namespace std;
ifstream fin("graf.in");

///Am lucrat cu algoritmul de la 1 (Moore)

int a[20][20],distanta[20],radacina[20],n,vizitat[20];
queue<int>coada;
void bfs(int inceput){
    int crt,i;
    vizitat[inceput] = 1;
    coada.push(inceput);

    for (i=1;i<=n;i++)
    {
        distanta[i] = inf;
        radacina[i] = -1;
    }

    distanta[inceput] = 0;
    radacina[inceput] = 0;

    while(coada.empty() != 1)
    {
        crt = coada.front();
        coada.pop();
        for(i=1;i<=n;i++)
        {
            if(a[crt][i]==1 && !vizitat[i])
            {
                vizitat[i] = 1;
                distanta[i] = distanta[crt] + 1;
                radacina[i] = crt;
                coada.push(i);
            }
        }

    }

}

int main()
{
    int i,j,inceput;
    ///se creaza matricea de adiacenta
    fin>>n; ///nr de noduri
    while(fin>>i>>j)
    {
        a[i][j] = 1;
    }
    cout<<"Introduceti nodul de inceput: ";
    cin>>inceput;
    bfs(inceput);

    ///afisam vectorul pentru distante
    cout<<"Distantele intre nodul de inceput si celelalte noduri sunt:"<<endl;
    for(i=1;i<=n;i++)
    {
        if(distanta[i] == inf) cout<<"- ";
        else cout<< distanta[i]<<" ";
    }

    return 0;
}
