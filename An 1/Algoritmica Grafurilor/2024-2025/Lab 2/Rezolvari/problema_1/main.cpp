#include <iostream>
#include <queue>
#include <fstream>
#define inf 10000
using namespace std;
///Mergem pe ideea de creare a unei matrice de distante, cel mai scurt drum va fi creat folosind un vector de parinti.
///Daca exista o muchie de la x la y, x va fi parintele lui y
///Practic este o parcurgere ca de arbore.


ifstream fin("graf.in");

int a[20][20],distanta[20],radacina[20],n,vizitat[20];
queue<int>coada; ///Coada e folosita pentru a tine nodul curent si nodurile adiacente lui, pentru a sti cu ce nod continuam cautarea.

void bfs(int inceput){
    int crt,i;
    vizitat[inceput] = 1;
    coada.push(inceput);

    ///Momentan consideram ca nu exista drum intre oricare 2 noduri, deci le initializam cu infinit, avand radacina -1.
    for (i=1;i<=n;i++)
    {
        distanta[i] = inf;
        radacina[i] = -1;
    }

    ///Consideram radacina nodului de la care pornim ca fiind 0, pt a sti unde ne oprim cand reconstruim drumul minim.
    distanta[inceput] = 0;
    radacina[inceput] = 0;

    while(coada.empty() != 1) ///Cat timp mai exista noduri legate direct sau indirect de inceput
    {
        ///Luam primul nod din coada si lucram cu el(il eliminam si din coada)
        crt = coada.front();
        coada.pop();
        for(i=1;i<=n;i++)
        {
            if(a[crt][i]==1 && !vizitat[i]) ///daca nodul curent e legat de nodul i si acesta n-a mai fost vizitat
            {
                vizitat[i] = 1;
                distanta[i] = distanta[crt] + 1;
                radacina[i] = crt;
                coada.push(i); ///adaugam nodul i in coada pt a sti ca trebuie sa lucram cu el ulterior
            }
        }

    }

}

int main()
{
    int i,j,inceput,sfarsit;
    ///se creaza matricea de adiacenta
    fin>>n; ///nr de noduri
    while(fin>>i>>j)
    {
        a[i][j] = 1;
    }
    cout<<"Introduceti nodul de inceput si nodul de sfarsit: ";
    cin>>inceput>>sfarsit;
    bfs(inceput);
    ///refacem drumul de la sfarsit spre inceput folosind vectorul radacina, daca exista un drum de la inceput la sfarsit (distanta[sfarsit] != inf)
    if(distanta[sfarsit] == inf)
        cout<<"Nu exista un drum intre aceste noduri.";
    else
    {
        cout<<sfarsit<<" ";
        while(radacina[sfarsit] != 0)
        {
            sfarsit = radacina[sfarsit];
            cout<<sfarsit<< " ";
        }
    }

    return 0;
}
