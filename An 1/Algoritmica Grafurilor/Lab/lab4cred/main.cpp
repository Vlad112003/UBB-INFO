#include <fstream>
#include <iostream>

using namespace std;

struct muchie {
    int x, y;
    int c;
};

int n, m, cate;
int arb[101];
muchie v[5000], sel[100];
ifstream f("3-in.txt");

void sortare() {
    for (int i = 0; i < m - 1; i++) {
        for (int j = 0; j < m - i - 1; j++) {
            if (v[j].c > v[j + 1].c) {
                swap(v[j], v[j + 1]);
            }
        }
    }
}
//
//void sortare()
//{
//    int ok;
//    do {
//        ok = 1;
//        for (int i = 1; i < m; i++)
//        {
//            if (v[i].c > v[i + 1].c)
//            {
//                swap(v[i], v[i + 1]);
//                ok = 0;
//            }
//        }
//    } while (ok == 0);
//
//}

int findRoot(int vertex) {
    while (arb[vertex] != vertex) {
        vertex = arb[vertex];
    }
    return vertex;
}

int main(int argc, char** argv) {
    // ifstream f(argv[1]);
    // ofstream g(argv[2]);
    f >> n >> m;
    for (int i = 0; i < m; i++) {
        f >> v[i].x >> v[i].y >> v[i].c;
    }
    // kruskal
    int i, k, cost, j, a1, a2;
    sortare();
    for (i = 1; i <= n; i++) {
        arb[i] = i;
    }
    k = 0;
    i = 0;
    cost = 0;
    while (k < n - 1) {
        a1 = findRoot(v[i].x);
        a2 = findRoot(v[i].y);
        if (a1 != a2) {
            sel[cate++] = v[i];
            cost = cost + v[i].c;
            arb[a1] = a2;
            k++;
        }
        i++;
    }
    cout << cost << "\n";
    cout << cate << "\n";
    for (i = 0; i < cate; i++) {
        cout << sel[i].x << " " << sel[i].y << "\n";
    }

    return 0;
}
