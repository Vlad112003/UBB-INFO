#include <iostream>
#include <fstream>
#include <vector>
#include <limits>
#include <iomanip>
using namespace std;

const int INF = numeric_limits<int>::max() / 2;

int main() {
    ifstream in("inputfile.txt");
    if (!in) {
        cerr << "Eroare la deschiderea fisierului de intrare" << endl;
        return 1;
    }

    int n, m; // n = numarul de noduri, m = numarul de muchii
    in >> n >> m;

    // initializam matricea de distante cu INF
    vector<vector<int>> dist(n + 1, vector<int>(n + 1, INF));

    // distanta de la nod la el insusi este 0
    for (int i = 1; i <= n; ++i) {
        dist[i][i] = 0;
    }

    //citim muchiile si costurile
    for (int i = 0; i < m; ++i) {
        int from, to, cost;
        in >> from >> to >> cost;
        dist[from][to] = cost;
    }

    // alg floyd-warshall
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

    // verificam daca avem cicluri negative
    bool hasNegativeCycle = false;
    for (int i = 1; i <= n; ++i) {
        if (dist[i][i] < 0) {
            hasNegativeCycle = true;
            break;
        }
    }

    // afisam rezultatele
    if (hasNegativeCycle) {
        cout << "Graful contine un ciclu negativ" << endl;
    } else {
        cout << "Costurile minime intre toate perechile de noduri:" << endl;
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= n; ++j) {
                if (dist[i][j] == INF) {
                    cout << setw(5) << "INF";
                } else {
                    cout << setw(5) << dist[i][j];
                }
            }
            cout << endl;
        }
    }

    return 0;
}