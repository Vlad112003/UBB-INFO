/// matrice inchidere tranzitiva, 1 daca se poate ajunge de la x la y, 0 altfel
#include <iostream>
#include <limits.h>
#include <fstream>

using namespace std;

ifstream fin("graf.in");

void afisare_inchidere_tranzitiva(int m[10][10],int n)
{   int i,j,inf=INT_MAX;
    for(i=1;i<=n;i++)
    {for(j=1;j<=n;j++)
    {   if(m[i][j] == inf) cout<<"0 ";
        else
            cout<<"1 ";
    }
    cout<<endl;
    }
}

int main()
{   int n,m[10][10]={0},i,j,k,inf=INT_MAX;

    ///CITIRE MATRICE
    fin>>n;
    while(fin>>i>>j)
       {
           m[i][j]=1;

       }

    for(i=1;i<=n;i++)
    for(j=1;j<=n;j++)
    {
        if(m[i][j]==0)
            m[i][j]=inf;
    }

    cout<<"Matrice inchidere tranzitiva:"<<endl;
    for (k =1;k<=n;k++)
    for (i=1;i<=n;i++)
    for (j=1;j<=n;j++)
    {   if(m[i][k]!=inf && m[k][j]!=inf)
        {
            if(m[i][k]+m[k][j]<m[i][j])
                m[i][j] = m[i][k] + m[k][j];
        }
    }

    afisare_inchidere_tranzitiva(m,n);
    return 0;
}
