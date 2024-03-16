#include <iostream>
#include <fstream>
using namespace std;
int mat[1001][1001] = {0};
bool sptSet[1001];
int dist[1001];

int inf = 999999999;

int v, e, s;

int minDistance()
{
    int min = inf;
    int min_index = 0;

    for (int i = 0; i<v; i++) {
        if(!sptSet[i] && dist[i]<min)
        {
            min=dist[i];
            min_index = i;
        }

    }
    return min_index;
};


void dijkstra(int src)
{
    for(int i=0;i<v;i++)
        dist[i] = inf;
    dist[src] = 0;

    for(int i = 0;i < v-1;i++)
    {
        int u = minDistance();

        sptSet[u] = true;
        for(int n = 0;n<v;n++)
            if(!sptSet[n] && mat[u][n] && dist[u] != inf && dist[u] + mat[u][n] < dist[n])
                dist[n] = dist[u] + mat[u][n];
    }

}



int main(int argc, char** argv) {
    ifstream fin(argv[1]);
    ofstream fout(argv[2]);

    //nr varfuri
    fin>>v;
    //nr arce
    fin>>e;
    //nodul sursa
    fin>>s;


    // x - nodul sursa al arcului, y - nodul destinatie al arcului, w - ponderea
    int x, y, w;
    while (fin>>x>>y>>w)
    {
        mat[x][y]=w;
    }

    dijkstra(s);

    for(int i=0;i<v;i++)
    {
        if(dist[i]==inf) {
            cout <<"inf"<<" ";
            fout<<"inf"<<" ";
        }
        else {
            cout << dist[i] << " ";

            fout << dist[i] << " ";
        }
    }
    cout << endl;

    fin.close();
    fout.close();
    return 0;
}
