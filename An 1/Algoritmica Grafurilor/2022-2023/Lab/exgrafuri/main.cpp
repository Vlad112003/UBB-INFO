#include <iostream>
#include <fstream>
#include <vector>
using namespace std;

int main() {
    int num_edges, u, v;
    ifstream fin("input.txt"); // deschidem fisierul de intrare
    ofstream fout ("output.txt");
    fin >> num_edges;

    // Inițializare listă de adiacență cu liste vidă pentru fiecare nod
    int num_nodes = 0;
    vector<vector<int>> adj_list;
    for (int i = 0; i < num_edges; i++) {
        fin >> u >> v;
        num_nodes = max(num_nodes, max(u, v));
        adj_list.resize(num_nodes + 1);
        adj_list[u].push_back(v);
        adj_list[v].push_back(u);
    }
    fin.close(); // închidem fisierul de intrare

    // Afisare lista de adiacență
    for (int i = 1; i < adj_list.size(); i++) {
        fout << "Nodul " << i << ": ";
        for (int j = 0; j < adj_list[i].size(); j++) {
            fout << adj_list[i][j] << " ";
        }
        fout << "\n";
    }
    return 0;
}