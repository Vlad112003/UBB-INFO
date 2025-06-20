#include <bits/stdc++.h>
#define INF 0x3f3f3f3f

using namespace std;

const int CMAX = 5e4 + 15;

struct triplet {
    int x, y, c;
};

int muchie, cost, n, m;
int h[CMAX];
int D[CMAX];
int matrice_D[CMAX / 5][CMAX / 5];
int viz[CMAX];
const long long int infinite = 1000 * 50000;
vector<pair<int, int>> graf[CMAX];
vector<triplet> muchii;

queue<int> coada;
priority_queue<int, vector<int>, greater<int>> Q;

struct comp {
    bool operator()(int x, int y) {
        return D[x] > D[y];
    }
};

void citire(ifstream &fin) {
    int x, y, c;
    fin >> n >> m;
    for (int i = 1; i <= m; i++) {
        fin >> x >> y >> c;
        muchii.push_back({x, y, c});
        graf[x].push_back({y, c});
    }
}

void afisare(ofstream &fout, vector<pair<int, int>> graf[CMAX]) {
    for (int i = 0; i <= n; i++) {
//        fout << "Nodul: " << i << " : ";
        for (auto &p : graf[i]) {
            fout << i << " " << p.first << " " << p.second << '\n';
            cout << i << " " << p.first << " " << p.second << '\n';
        }
    }
}

bool BellmanFord(vector<pair<int, int>> graf[CMAX], int start) {
    for (int i = 0; i <= n; i++) {
        viz[i] = 0;
        D[i] = infinite;
    }
    D[start] = 0;
    coada.push(start);
    while (!coada.empty()) {
        int nodcurent = coada.front();
        viz[nodcurent]++;
        coada.pop();
        if (viz[nodcurent] >= (n + 1)) {
            return false;
        }
        for (auto &p : graf[nodcurent]) {
            int vecin = p.first;
            int cost = p.second;
            if (D[nodcurent] + cost < D[vecin]) {
                D[vecin] = D[nodcurent] + cost;
                coada.push(vecin);
            }
        }
    }
    return true;
}

void Dijkstra(vector<pair<int, int>> graf[CMAX], int start) {
    for (int i = 0; i <= n; i++)
        D[i] = infinite;
    D[start] = 0;
    Q.push(start);
    while (!Q.empty()) {
        int nodcurent = Q.top();
        Q.pop();
        viz[nodcurent] = 0;
        for (auto &p : graf[nodcurent]) {
            int vecin = p.first;
            int cost = p.second;
            if (D[nodcurent] + cost < D[vecin]) {
                D[vecin] = D[nodcurent] + cost;
                if (viz[vecin] == 0) {
                    Q.push(vecin);
                    viz[vecin] = 1;
                }
            }
        }
    }
}

int reponderare(int drum, int val_a, int val_b) {
    return drum + val_a - val_b;
}

int reponderare_inversa(int drum, int val_a, int val_b) {
    return drum - val_a + val_b;
}

bool Johnson2(ofstream &fout) {
    vector<pair<int, int>> graf_modif[CMAX];
    int h[CMAX];
    for (int i = 0; i < n; i++) {
        graf[n].push_back({i, 0});
    }
    if (!BellmanFord(graf, n)) {
        return false;
    } else {
        for (int i = 0; i < n; i++) {
            h[i] = D[i];
        }
        for (auto &e : muchii) {
            graf_modif[e.x].push_back({e.y, reponderare(e.c, D[e.x], D[e.y])});
        }

        for (int j = 0; j < n; j++) {
            memset(viz, 0, sizeof(viz));
            Dijkstra(graf_modif, j);
            for (int z = 0; z < n; z++) {
                matrice_D[j][z] = reponderare_inversa(D[z], h[j], h[z]);
            }
        }

//        fout << "Graful modificat:\n";
        afisare(fout, graf_modif);
    }
    return true;
}

int main(int argc, char *argv[]) {
    if (argc != 3) {
        cout << "Usage: " << argv[0] << " <input_file> <output_file>\n";
        return 1;
    }

    string intrare = argv[1];
    string iesire = argv[2];

    ifstream fin(intrare);
    ofstream fout(iesire);

    if (!fin.is_open()) {
        cerr << "Failed to open input file.\n";
        return 1;
    }

    if (!fout.is_open()) {
        cerr << "Failed to open output file.\n";
        return 1;
    }

    citire(fin);

    if (!Johnson2(fout)) {
        fout << -1;
    } else {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrice_D[i][j] >= infinite) {
                    fout << "INF ";
                    cout <<"INF ";
                } else {
                    fout << matrice_D[i][j] << " ";
                    cout << matrice_D[i][j] << " ";
                }
            }
            fout << '\n';
            cout   <<'\n';
        }
    }

    cout << "Done!\n";
    return 0;
}
