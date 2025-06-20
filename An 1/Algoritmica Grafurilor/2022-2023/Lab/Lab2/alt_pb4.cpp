#include <iostream>
#include <fstream>
using namespace std;
ifstream f("graf.txt");

int vizitati[101],mat[101][101],n;
int Coada[1001],inc,sf;

void BFS(int nod)
{
    inc = 0;
    Coada[sf++] = nod;
    int d = 1;
    vizitati[nod] = 1;
    while(sf != inc)
    {

        for(int i=0;i<=n;i++)
            if(mat[Coada[inc]][i] && !vizitati[i])
            {
                cout << i << " " << d << endl;
                Coada[sf++] = i;
                vizitati[i] = 1;
            }

        inc++;
        d++;
    }
}

int main()
{
    /// Citire din fisier
    int x,y;
    f >> n;
    while(f >> x >> y)
    {
        mat[x][y] = 1;
    }

    int nod;
    cout<<"Cititi nodul sursa: \n";
    cin >> nod;
    BFS(nod);

    return 0;
}