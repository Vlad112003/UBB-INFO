#include <iostream>
#include <vector>

using namespace std;

int main() {
    int n, m;
    //citim numarul de varfuri si de muchii
    cin >> n >> m;

    // citim matricea de incidență
    vector<vector<int>> inc(n, vector<int>(m));
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            cin >> inc[i][j];
        }
    }

    // construim lista de adiacență
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

    // afișăm lista de adiacență
    for (int u = 0; u < n; u++) {
        cout << u << ": ";
        for (int v : adj[u]) {
            cout << v << " ";
        }
        cout << "\n";
    }

    return 0;
}


