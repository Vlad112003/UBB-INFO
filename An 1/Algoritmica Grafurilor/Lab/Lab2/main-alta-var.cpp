#include <iostream>
#include <fstream>
using namespace std;
ifstream f("graf.txt");

int n,m,mat[101][101],x,y;
int vizitat[101][101];

int mini = -1 ,rez[101];

void DFS(int nod,int nr)
{
    int vcount = 0;
    for(int i=0;i<=n;i++)
        if(mat[nod][i] && vizitat[nod][i] == 0)
        {
            vizitat[nod][i] = 1;
            rez[nr] = i;
            DFS(i,nr+1);
            vizitat[nod][i] = 0;
            vcount++;
        }

    if(vcount == 0 &&(mini = -1 || nr < mini))
    {
        mini = nr;
//        for(int i=0;i<mini;i++)
//        cout << rez[i] << " ";
//        cout << endl;
    }

}

int main()
{
    /// Citire din fisier
    f >> n;
    while(f >> x >> y)
    {
        mat[x][y] = 1;
    }

    /// Citim varful sursa
    int x;
    cout << "Varful sursa : ";
    cin >> x;

    int nr = 0;
    DFS(x,nr);

    cout << x << " ";
    for(int i=0;i<mini;i++)
        cout << rez[i] << " ";

    return 0;
}