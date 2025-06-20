#include <iostream>
#include <vector>
#include <unordered_map>
#include <stack>
using namespace std;
#include <fstream>
ifstream fin("5.in");

class Graph {
public:
    unordered_map<int, vector<int>> adjList;

    void addEdge(int u, int v) {
        adjList[u].push_back(v);
        adjList[v].push_back(u);
    }

    void eulerianCycle(vector<int>& cycle) {
        if (adjList.empty())
            return;

        int start = adjList.begin()->first;
        stack<int> st;
        vector<int> tempCycle;

        st.push(start);

        while (!st.empty()) {
            int u = st.top();

            if (adjList[u].empty()) {
                tempCycle.push_back(u);
                st.pop();
            } else {
                int v = adjList[u].back();
                adjList[u].pop_back();
                for (auto it = adjList[v].begin(); it != adjList[v].end(); ++it) {
                    if (*it == u) {
                        adjList[v].erase(it);
                        break;
                    }
                }
                st.push(v);
            }
        }

        for (int i = tempCycle.size() - 2; i >= 0; --i)
            cycle.push_back(tempCycle[i]);
    }
};

int main() {
    int numLandmarks, numPaths;
    fin >> numLandmarks >> numPaths;

    Graph graph;

    for (int i = 0; i < numPaths; ++i) {
        int from, to;
        fin >> from >> to;
        graph.addEdge(from, to);
    }

    vector<int> cycle;
    graph.eulerianCycle(cycle);

    if (cycle.empty()) {
        cout << "Nu s-a găsit niciun ciclu." << endl;
    } else {
        for (int landmark : cycle)
            cout << landmark << " -> ";
        cout << cycle[0] << endl;
    }

    return 0;
}
