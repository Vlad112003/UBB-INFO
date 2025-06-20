#include <iostream>
#include <fstream>
#include <vector>
#include <list>
#include <limits>
#include <queue>

using namespace std;

const int INF = numeric_limits<int>::max();

void readGraphFromFile(const string& filepath, int& numarNoduri, vector<pair<int, int>>& muchii) {
    ifstream file(filepath);
    if (!file.is_open()) {
        cerr << "Eroare la deschiderea fisierului" << endl;
        return;
    }

    int u, v;
    file >> numarNoduri;

    while (file >> u >> v) {
        muchii.emplace_back(u - 1, v - 1);
    }

    file.close();
}

void createMatriceAdiacenta(int numarNoduri, const vector<pair<int, int>>& muchii, vector<vector<int>>& matriceAdiacenta) {
    matriceAdiacenta.resize(numarNoduri, vector<int>(numarNoduri, 0));
    for (const auto& muchie : muchii) {
        matriceAdiacenta[muchie.first][muchie.second] = 1;
        matriceAdiacenta[muchie.second][muchie.first] = 1;
    }
}

void createListaAdiacentaFromMatriceAdiacenta(const vector<vector<int>>& matriceAdiacenta, vector<list<int>>& listaAdiacenta) {
    listaAdiacenta.resize(matriceAdiacenta.size());
    for (size_t i = 0; i < matriceAdiacenta.size(); ++i) {
        for (size_t j = 0; j < matriceAdiacenta[i].size(); ++j) {
            if (matriceAdiacenta[i][j] == 1) {
                listaAdiacenta[i].push_back(j);
            }
        }
    }
}

void createMatriceIncidentaFromListaAdiacenta(const vector<list<int>>& listaAdiacenta, vector<vector<int>>& matriceIncidenta) {
    int numarMuchii = 0;
    for (const auto& lista : listaAdiacenta) {
        numarMuchii += lista.size();
    }
    numarMuchii /= 2;

    matriceIncidenta.resize(listaAdiacenta.size(), vector<int>(numarMuchii, 0));
    int muchieIndex = 0;
    for (size_t i = 0; i < listaAdiacenta.size(); ++i) {
        for (int nod : listaAdiacenta[i]) {
            if (i < nod) {
                matriceIncidenta[i][muchieIndex] = 1;
                matriceIncidenta[nod][muchieIndex] = 1;
                ++muchieIndex;
            }
        }
    }
}

void createListaAdiacentaFromMatriceIncidenta(const vector<vector<int>>& matriceIncidenta, vector<list<int>>& listaAdiacenta) {
    listaAdiacenta.clear();
    listaAdiacenta.resize(matriceIncidenta.size());
    for (size_t j = 0; j < matriceIncidenta[0].size(); ++j) {
        int u = -1, v = -1;
        for (size_t i = 0; i < matriceIncidenta.size(); ++i) {
            if (matriceIncidenta[i][j] == 1) {
                if (u == -1) {
                    u = i;
                } else {
                    v = i;
                    break;
                }
            }
        }
        if (u != -1 && v != -1) {
            listaAdiacenta[u].push_back(v);
            listaAdiacenta[v].push_back(u);
        }
    }
}

void createMatriceAdiacentaFromListaAdiacenta(const vector<list<int>>& listaAdiacenta, vector<vector<int>>& matriceAdiacenta) {
    matriceAdiacenta.resize(listaAdiacenta.size(), vector<int>(listaAdiacenta.size(), 0));
    for (size_t i = 0; i < listaAdiacenta.size(); ++i) {
        for (int nod : listaAdiacenta[i]) {
            matriceAdiacenta[i][nod] = 1;
        }
    }
}

void createInputFromMatriceAdiacenta(const vector<vector<int>>& matriceAdiacenta, int& numarNoduri, vector<pair<int, int>>& muchii) {
    muchii.clear();
    numarNoduri = matriceAdiacenta.size();
    for (size_t i = 0; i < matriceAdiacenta.size(); ++i) {
        for (size_t j = i + 1; j < matriceAdiacenta[i].size(); ++j) {
            if (matriceAdiacenta[i][j] == 1) {
                muchii.emplace_back(i, j);
            }
        }
    }
}

void printInput(const int& numarNoduri, const vector<pair<int, int>>& muchii) {
    cout << numarNoduri << endl;
    for (const auto& muchie : muchii) {
        cout << muchie.first + 1 << " " << muchie.second + 1 << endl;
    }
}


void printMatriceAdiacenta(const vector<vector<int>>& matriceAdiacenta) {
    for (const auto& rand : matriceAdiacenta) {
        for (int val : rand) {
            cout << val << " ";
        }
        cout << endl;
    }
}

void printListaAdiacenta(const vector<list<int>>& listaAdiacenta) {
    for (size_t i = 0; i < listaAdiacenta.size(); ++i) {
        cout << i + 1 << ": ";
        for (int val : listaAdiacenta[i]) {
            cout << val + 1 << " ";
        }
        cout << endl;
    }
}

void printMatriceIncidenta(const vector<vector<int>>& matriceIncidenta) {
    for (const auto& rand : matriceIncidenta) {
        for (int val : rand) {
            cout << val << " ";
        }
        cout << endl;
    }
}

vector<int> findNoduriIzolate(const vector<vector<int>>& matriceAdiacenta) {
    vector<int> noduriIzolate;
    for (size_t i = 0; i < matriceAdiacenta.size(); ++i) {
        bool izolat = true;
        for (size_t j = 0; j < matriceAdiacenta[i].size(); ++j) {
            if (matriceAdiacenta[i][j] == 1) {
                izolat = false;
                break;
            }
        }
        if (izolat) {
            noduriIzolate.push_back(i + 1);
        }
    }
    return noduriIzolate;
}

// un graf este regulat daca toate nodurile au acelasi grad
bool isRegular(const vector<vector<int>>& matriceAdiacenta) {
    int grad = -1;
    for (const auto& rand : matriceAdiacenta) {
        int gradCurent = 0;
        for (int val : rand) {
            if (val == 1) {
                ++gradCurent;
            }
        }
        if (grad == -1) {
            grad = gradCurent;
        } else if (grad != gradCurent) {
            return false;
        }
    }
    return true;
}

vector<vector<int>> calcDistMat(const vector<vector<int>>& matriceAdiacenta) {
    int n = matriceAdiacenta.size();
    vector<vector<int>> matriceDistante(n, vector<int>(n, numeric_limits<int>::max()));

    for (int i = 0; i < n; ++i) {
        matriceDistante[i][i] = 0;
        for (int j = 0; j < n; ++j) {
            if (matriceAdiacenta[i][j] == 1) {
                matriceDistante[i][j] = 1;
            }
        }
    }

    for (int k = 0; k < n; ++k) {
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (matriceDistante[i][k] != INF && matriceDistante[k][j] != INF) {
                    matriceDistante[i][j] = min(matriceDistante[i][j], matriceDistante[i][k] + matriceDistante[k][j]);
                }
            }
        }
    }

    return matriceDistante;
}


// un graf este conex daca exista un drum intre oricare doua noduri
bool isConex(const vector<vector<int>>& matriceAdiacenta) {
    int n = matriceAdiacenta.size();
    vector<bool> vizitat(n, false);
    queue<int> q;

    q.push(0);
    vizitat[0] = true;

    while (!q.empty()) {
        int nod = q.front();
        q.pop();

        for (int i = 0; i < n; ++i) {
            if (matriceAdiacenta[nod][i] == 1 && !vizitat[i]) {
                vizitat[i] = true;
                q.push(i);
            }
        }
    }

    for (bool v : vizitat) {
        if (!v) {
            return false;
        }
    }
    return true;
}


void pb1(){
    int numarNoduri;
    vector<pair<int, int>> muchii;
    vector<vector<int>> matriceAdiacenta;
    vector<list<int>> listaAdiacenta;
    vector<vector<int>> matriceIncidenta;

    readGraphFromFile("in.txt", numarNoduri, muchii);

    createMatriceAdiacenta(numarNoduri, muchii, matriceAdiacenta);
    cout << "Matricea de adiacenta:" << endl;
    printMatriceAdiacenta(matriceAdiacenta);
    cout<<endl;

    createListaAdiacentaFromMatriceAdiacenta(matriceAdiacenta, listaAdiacenta);
    cout << "Lista de adiacenta din Matricea de adiacenta:" << endl;
    printListaAdiacenta(listaAdiacenta);
    cout<<endl;

    createMatriceIncidentaFromListaAdiacenta(listaAdiacenta, matriceIncidenta);
    cout << "Matricea de incidenta din Lista de adiacenta:" << endl;
    printMatriceIncidenta(matriceIncidenta);
    cout<<endl;

    createListaAdiacentaFromMatriceIncidenta(matriceIncidenta, listaAdiacenta);
    cout << "Lista de adiacenta din matricea de incidenta:" << endl;
    printListaAdiacenta(listaAdiacenta);
    cout<<endl;

    createMatriceAdiacentaFromListaAdiacenta(listaAdiacenta, matriceAdiacenta);
    cout << "Matricea de adiacenta din lista de adiacenta:" << endl;
    printMatriceAdiacenta(matriceAdiacenta);
    cout<<endl;

    createInputFromMatriceAdiacenta(matriceAdiacenta, numarNoduri, muchii);
    cout << "Inputul din matricea de adiacenta:" << endl;
    printInput(numarNoduri, muchii);
    cout<<endl;

}

void pb2() {
    int numarNoduri;
    vector<pair<int, int>> muchii;
    vector<vector<int>> matriceAdiacenta;

    readGraphFromFile("in2.txt", numarNoduri, muchii);
    createMatriceAdiacenta(numarNoduri, muchii, matriceAdiacenta);

    // a. Determină nodurile izolate
    vector<int> noduriIzolate = findNoduriIzolate(matriceAdiacenta);
    cout << "Noduri izolate: ";
    for (int nod : noduriIzolate) {
        cout << nod << " ";
    }
    if (noduriIzolate.empty())
        cout << "Nu exista noduri izolate";
    cout << endl;
    cout << endl;

    // b. Verifică dacă graful este regular
    bool regular = isRegular(matriceAdiacenta);
    cout << "Graful este " << (regular ? "regular" : "neregulat") << endl;
    cout<<endl;

    // c. Calculează matricea distanțelor
    vector<vector<int>> matriceDistante = calcDistMat(matriceAdiacenta);
    cout << "Matricea distantelor:" << endl;
    for (const auto& rand : matriceDistante) {
        for (int val : rand) {
            if (val == numeric_limits<int>::max()) {
                cout << "INF ";
            } else {
                cout << val << " ";
            }
        }
        cout << endl;
    }
    cout<<endl;

    // d. Verifică dacă graful este conex
    bool conex = isConex(matriceAdiacenta);
    cout << "Graful este " << (conex ? "conex" : "neconex") << endl;
}


int main() {
    cout<<"Problema 1: \n";
    pb1();
    cout<<"-----------------------------\n";
    cout<<"Problema 2: \n";
    pb2();

    return 0;

}