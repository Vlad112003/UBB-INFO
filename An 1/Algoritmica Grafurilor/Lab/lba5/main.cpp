#include <iostream>
#include <fstream>
using namespace std;

ifstream fin("1 -in.txt");
ofstream fout("1-out.txt");

int V, E, p;

void Euler(int k, int ** a, int* L){
    for(int i = 0; i<V; i++){
        if(a[k][i] == 1){
            a[k][i] = a[i][k] = 0;
            Euler(i, a, L);
        }
    }
    L[++p] = k;
}

int main(){
    fin>>V>>E;
    int* L = new int[E+1];
    int** a = new int*[V];
    for (int i = 0; i<V; i++){
        a[i] = new int[V];
        for(int j = 0; j<V; j++){
            a[i][j]=0;
        }
    }
    for (int i = 0; i < E; i++){
        int x,y;
        fin>>x>>y;
        a[x][y] = a[y][x]=1;
    }
    p=-1;
    Euler(1,a,L);
    for(int i = 0; i<E+1; i++){
        cout<<L[i]<<" ";
    }
    fin.close();
    fout.close();
    return 0;
}
