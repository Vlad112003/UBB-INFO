#include <iostream>
#include <fstream>
#include <iomanip>
#include <limits>
#include <vector>

using namespace std;

const int INF = numeric_limits<int>::max() / 2;

int main() {
    ifstream in("inputfile.txt");
    if (!in) {
        cout << "Eroare la deschiderea fisierului de intrare" << endl;
        return 1;
    }

    int n, m; //n - nr huburi, m - nr conexiuni
    in >> n >> m;

    // initializam matricea de distante cu INF
    vector<vector<int>> dist(n + 1, vector<int>(n + 1, INF));

    //distanta de la un nod la el insusi este 0
    for (int i = 1; i<=n; i++){
        dist[i][i]=0;
    }

    //citim muchiile si costurile
    for (int i = 0; i < m; ++i) {
        int from, to, cost;
        in >> from >> to >> cost;
        dist[from][to] = cost;
    }

    // alg floyd-warshall - parcurge distanta de la fiecare nod la fiecare si determina costurile minime intre fiecare
    // fw poate parcurge drumuri cu costuri negative si poate detecta cicluri negative
    for (int k = 1; k <= n; ++k) {
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= n; ++j) {
                if (dist[i][k] != INF && dist[k][j] != INF &&
                    dist[i][k] + dist[k][j] < dist[i][j]) {
                    dist[i][j] = dist[i][k] + dist[k][j];
                }
            }
        }
    }

    //verificam daca avem cicluri negative
    bool hasNegativeCycles = false;
    for (int i=1; i<=n; i++){
        if (dist[i][i] < 0) {
            hasNegativeCycles = true;
            break;
        }
    }

    //afisam rezultatele
    if(hasNegativeCycles)
        cout<<"Graful contine un ciclu negativ!\n";
    else{
        cout<<"Costurile minime intre toate huburile sunt: \n";
        for(int i = 1; i<=n; i++){
            for(int j = 1; j<=n; j++){
                if(dist[i][j] == INF)
                {
                    cout << setw(5) << "INF";
                }
                else
                    cout << setw(5) << dist[i][j];
            }
            cout<<endl;
        }

    }
    return 0;
}
