#include <iostream>
#include <vector>
#include <fstream>
using namespace std;
int main()
{
 int n, m;
 ifstream fin ("input.txt");
 //citim numarul de noduri si de muchii
 fin>>n>>m;

 //citim matricea de incidenta
 vector<vector<int>> inc(n, vector<int>(m));
 for(int i =0; i < n; i++)
     for (int j = 0; j<m; j++)
         fin>>inc[i][j];


 //cream lista de adiacenta
    vector<vector<int>> adj(n);
    for (int j = 0; j < m; j++) {
        int u = -1, v = -1;
        for (int i = 0; i < n; i++) {
            if (inc[i][j] == 1) {
                if (u == -1) {
                    u = i;
                } else {
                    v = i;
                }
            }
        }
        if (v != -1) {
            adj[u].push_back(v);
            adj[v].push_back(u);
        }
    }

    //afisam lista de adiacenta

    for (int u = 0; u < n; u++)
    {
        cout <<u<< ": ";
        for (int v: adj[u])
        {
            cout<< v<< " ";
        }
        cout<<'\n';
    }
    return 0;



}
