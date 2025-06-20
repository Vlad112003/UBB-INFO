#include <iostream>
#include <limits>
#include <fstream>
#include <queue>

using namespace std;

const int INF = numeric_limits<int>::max();

//1. Implementați algoritmul lui Moore pentru un graf orientat neponderat
// (algoritm bazat pe Breath-first search, vezi cursul
// 2). Datele sunt citete din fisierul graf.txt. Primul rând din graf.txt conține numărul vârfurilor,
// iar următoarele rânduri conțin muchiile grafului. Programul trebuie să afiseze lanțul cel mai scurt dintr-un vârf
// (vârful sursa poate fi citit de la tastatura).


ifstream fin("graf.in");

int a[20][20],distanta[20],radacina[20],n,vizitat[20];
queue<int>coada; //folosim queue pentru a tine nodul curent si nodurile adiacente lui.

//algoritmul lui moore
void bfs(int inceput){
    int crt, i;
    vizitat[inceput] = 1;
    coada.push(inceput);

    //consideram ca nu exista drum intre oricare 2 noduri => le initializam cu inf
    for(i = 1; i <= n; i++){
        distanta[i] = INF;
        radacina[i] = -1;
    }

    //radacina = 0 pt a sti unde ne oprim cand reconstruim drumul minim
    distanta[inceput] = 0;
    radacina[inceput] = 0;

    while(coada.empty() != 1){
        crt = coada.front(); //luam primul nod din coada si lucram cu el
        coada.pop(); //il eliminam din coada
        for(i = 1; i <= n; i++){
            //verificam daca nodul este vecin cu nodul curent si daca nu este vizitat
            if(a[crt][i] == 1 && !vizitat[i]){
                vizitat[i] = 1;
                distanta[i] = distanta[crt] + 1;
                radacina[i] = crt;
                //adaugam nodul inapoi in coada pt a lucra cu el ulterior
                coada.push(i);
            }
        }
    }
}

int main(){
    int i, j, inceput, sfarsit;
    //citim nr de noduri
    fin >> n;
    //citim muchiile grafului
    while(fin >> i >> j){
        a[i][j] = 1;
    }
    cout<<"Introduceti nodul de inceput si nodul de sfarsit: ";
    cin >> inceput >> sfarsit;
    if(inceput > n || sfarsit > n){
        cout << "Nodul de inceput sau nodul de sfarsit nu exista in graf" << endl;
        return -1;
    }
    bfs(inceput);
    if(distanta[sfarsit] == INF){
        cout << "Nu exista drum de la nodul " << inceput << " la nodul " << sfarsit << endl;
    } else {
        cout << "Distanta minima de la nodul " << inceput << " la nodul " << sfarsit << " este: " << distanta[sfarsit] << endl;
        cout << "Drumul minim este: ";
        int nod = sfarsit;
        while(nod != inceput){
            cout << nod << " ";
            nod = radacina[nod];
        }
        cout << inceput << endl;
    }
    return 0;
}