#include <iostream>
#include <fstream>
#include <vector>
#include <queue>
#include <cstring>

using namespace std;

const int MAXN = 1005; // Numarul maxim de noduri
vector<int> adj[MAXN]; // Lista de adiacenta a grafului
int dist[MAXN]; // Array pentru stocarea distantei minime
bool visited[MAXN]; // Array pentru a marca nodurile vizitate

void bfs(int source) {
    queue<int> q;
    q.push(source);
    visited[source] = true;
    dist[source] = 0;

    while(!q.empty()) {
        int u = q.front();
        q.pop();

        for(int v : adj[u]) {
            if(!visited[v]) {
                visited[v] = true;
                dist[v] = dist[u] + 1;
                q.push(v);
            }
        }
    }
}

int main() {
    ifstream fin("graf.txt"); // Deschidem fisierul de intrare
    int n, m; // n este numarul de noduri, iar m este numarul de muchii din graf
    fin >> n >> m;

    // Citim muchiile grafului
    for(int i=0; i<m; i++) {
        int u, v;
        fin >> u >> v;
        adj[u].push_back(v);
    }

    int source;
    cout << "Introduceti nodul sursa: ";
    cin >> source; // Citim nodul sursa de la tastatura

    // Initializam array-urile de distanta si de vizitare a nodurilor
    memset(visited, false, sizeof(visited));
    memset(dist, -1, sizeof(dist));

    bfs(source); // Aplicam algoritmul lui Moore pentru a calcula distantele minime

    // Afisam lanțul cel mai scurt dintr-un vârf
    cout << "Lantul cel mai scurt din nodul " << source << ":\n";
    for(int i=1; i<=n; i++) {
        if(dist[i] != -1) {
            cout << i << " -> " << dist[i] << endl;
        }
        else {
            cout << i << " -> Infinit\n";
        }
    }

    fin.close(); // Inchidem fisierul de intrare
    return 0;
}
