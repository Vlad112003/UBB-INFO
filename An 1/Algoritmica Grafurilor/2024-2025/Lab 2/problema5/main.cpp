#include <iostream>
#include <fstream>



//5. Pentru un graf dat să se afișeze pe ecran vârfurile descoperite de
// apelul recursiv al procedurii DFS_VISIT(G, u)
// (apadurea descoperită de DFS).


///PARCURGERE IN ADANCIME (DFS)
using namespace std;
ifstream fin("graf.in");
int a[20][20],n,vizitat[20];

///mergem pe o ruta pana la capat
void dfs(int inceput)
{   int i;

    vizitat[inceput] = 1;
    for(i=1;i<=n;i++)
    {
        if(vizitat[i]==0 && a[inceput][i]==1)
            dfs(i);
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

    dfs(inceput);
    cout<<"Nodurile descoperite de apelul dfs sunt: ";
    for(i=1;i<=n;i++)
    {
        if(vizitat[i] == 1) cout<<i<<" ";
    }


    return 0;
}
