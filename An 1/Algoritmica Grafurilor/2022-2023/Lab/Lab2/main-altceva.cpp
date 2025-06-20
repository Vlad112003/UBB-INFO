#include<bits/stdc++.h>
using namespace std;

ifstream fin("graf.txt");

const int MAXN = 1e5 + 5; // Maximum number of nodes in the graph
vector<int> adj[MAXN]; // adjacency list of the graph
int dist[MAXN]; // array to store the distance of each node from the source
bool visited[MAXN]; // array to mark visited nodes

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
    int n, m; // n is the number of nodes and m is the number of edges in the graph
    fin >> n >> m;

    for(int i=0; i<m; i++) {
        int u, v;
        fin >> u >> v;
        adj[u].push_back(v);
        adj[v].push_back(u);
    }

    int source;
    cout<<"Alegeti nodul sursa: \n";
    cin >> source; // source node from where the shortest paths will be calculated

    memset(visited, false, sizeof(visited));
    memset(dist, -1, sizeof(dist));

    bfs(source);

    // Printing the shortest distances of all nodes from the source node
    for(int i=1; i<=n; i++) {
        cout << "Shortest distance of node " << i << " from the source " << source << " is: " << dist[i] << endl;
    }

    return 0;
}
