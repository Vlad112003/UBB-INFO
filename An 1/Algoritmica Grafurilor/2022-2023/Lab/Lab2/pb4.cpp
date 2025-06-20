#include <iostream>
#include <queue>
#include <vector>

using namespace std;

void bfs(vector<vector<int>>& graph, int start, vector<int>& distances, vector<int>& parents) {
    int n = graph.size();
    distances.assign(n, -1); // initialize distances to -1 (not visited)
    parents.assign(n, -1); // initialize parents to -1 (not visited)

    queue<int> q;
    q.push(start);
    distances[start] = 0;
    parents[start] = start;

    while (!q.empty()) {
        int u = q.front();
        q.pop();
        for (int v = 0; v < n; ++v) {
            if (graph[u][v] && distances[v] == -1) {
                distances[v] = distances[u] + 1;
                parents[v] = u;
                q.push(v);
            }
        }
    }
}

void print_bfs(vector<int>& distances, vector<int>& parents) {
    int n = distances.size();
    cout << "Vertex\tDistance" << endl;
    for (int i = 0; i < n; ++i) {
        cout << i << "\t" << distances[i] << endl;
    }
    cout << endl << "Vertex\tParent" << endl;
    for (int i = 0; i < n; ++i) {
        cout << i << "\t" << parents[i] << endl;
    }
}

#include <fstream>

using namespace std;

int main() {
    ifstream fin("graph.txt");

    int n;
    fin >> n;
    vector<vector<int>> graph(n, vector<int>(n));
    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
            fin >> graph[i][j];
        }
    }

    int start;
    cout << "Enter the start vertex: ";
    cin >> start;

    vector<int> distances, parents;
    bfs(graph, start, distances, parents);

    print_bfs(distances, parents);

    return 0;
}
