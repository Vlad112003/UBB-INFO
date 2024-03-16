#include <bits/stdc++.h>
#include <fstream>
#include <vector>
using namespace std;


int main()
{
    ifstream fin;
    fin.open("in.txt");
    int n = 0;
    int m;
    int i, j, u, v;
    fin >> n;
    m = (n*(n-1))/2;
    int mat_adiacenta[n+1][n+1];
    vector<int> vectoras;

    for(i=0;i<=n;i++)
        for(j=0;j<=n;j++)
        mat_adiacenta[i][j]=0;


    for (i=0;i<n;i++)
    {
        fin>>u>>v;
        mat_adiacenta[u][v]=1;
        mat_adiacenta[v][u]=1;
    }
    cout<<"Matrice de adiacenta: "<<endl;
    for(i=1;i<=n;i++)
    {
        for(j=1;j<=n;j++)
        cout<<mat_adiacenta[i][j]<<" ";
        cout<<endl;
    }
cout<<"Lista de adiacenta:";
for(i=1;i<=n;i++)
        for(j=1;j<=n;j++)
            vectoras.push_back(mat_adiacenta[i][j]);

    for (auto it = vectoras.begin(); it!=vectoras.end(); it++)
        cout<<" "<<*it;
    cout<<endl;


    fin.close();
    return 0;
}
