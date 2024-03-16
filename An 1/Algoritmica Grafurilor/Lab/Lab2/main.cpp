#include <iostream>
#include <fstream>
using namespace std;
ifstream f("input2.txt");

int n,m,mat[101][101],x,y;
int coada[1001],inc,sf;

int d[101][101];

int main()
{
    f >> n;
    while(f >> x >> y)
    {
        mat[x][y] = 1;
    }

    for(int i=1;i<=n;i++)
    {
        inc = 0;
        coada[0] = i;
        sf = 1;

        while(sf > inc)
        {
            for(int k=1;k<=n;k++)
                if(mat[coada[inc]][k])
                    coada[sf++] = k;
            inc++;
            if(inc != sf)
                d[i][coada[inc]] = 1;
        }
    }

    for(int i=1;i<=n;i++)
    {
        for(int j=1;j<=n;j++)
            cout << d[i][j] << " ";
        cout << endl;
    }

    return 0;
}