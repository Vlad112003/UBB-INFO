#include<iostream>
#include<limits.h>
using namespace std;



int distante_minime(int distanta[], bool vizitat[]) ///determina nodul pana la care distanta e minima (vecin) si care nu e vizitat
///incercam sa evitam sa actualizam nodurile deja vizitate
{
    int minim=INT_MAX,ind;

    for(int i=0;i<6;i++)
    {
        if(vizitat[i]==false && distanta[i]<=minim)
        {
            minim=distanta[i];
            ind=i;
        }
    }
    return ind;
}

void dijkstra(int graf[6][6],int radacina)
{
    int distanta[6],i; ///Aici se vor retine distantele de la radacina pana la fiecare nod din graf
    bool vizitat[6];


    for(int k = 0; k<6; k++)
    {
        distanta[k] = INT_MAX; ///Initial toate distantele sunt infinit
        vizitat[k] = false; ///Si niciun nod nu e vizitat
    }

    distanta[radacina] = 0;   ///Setam distanta de la radacina la radacina ca fiind 0

    ///Luam radacina ca nod curent
    ///Vedem care sunt distantele pana la fiecare vecin
    ///Trebuie so sa comparam variantele alternative, daca gasim una mai buna o inlocuim, altfel o ignoram

    for(i = 0; i<6; i++)
    {
        int m=distante_minime(distanta,vizitat);

        vizitat[m]=true;
        for(int k = 0; k<6; k++)
        {
            if(!vizitat[k] && graf[m][k] && distanta[m]!=INT_MAX && distanta[m]+graf[m][k]<distanta[k]) ///Verificam si sa nu fie INT_MAX sa nu dea overflow
                distanta[k]=distanta[m]+graf[m][k];
        }
    }
    for(i = 0; i<6; i++)
    {
        cout<<char('A'+i)<<"   "<<distanta[i]<<endl; ///Notam nodurile cu litere (pt claritate) si afisam reziltatul obtinut in vectorul de distante
    }
}

int main()
{
    //graf = { {0, 1, 2, 0, 0, 2},
    //         {1, 0, 0, 5, 1, 0},
    //         {0, 0, 0, 0, 2, 0},
    //         {0, 0, 0, 0, 0, 0},
    //         {0, 0, 0, 6, 0, 0},
    //         {1, 0, 0, 0, 4, 0} };
    ///Graful de mai sus este reprezentat printr-o matrice de adiacenta
    int graf[6][6]=
    {{0, 1, 2, 0, 0, 2},
    {1, 0, 0, 5, 1, 0},
    {0, 0, 0, 0, 2, 0},
    {0, 0, 0, 0, 0, 0},
    {0, 0, 0, 6, 0, 0},
    {1, 0, 0, 0, 4, 0}};
    dijkstra(graf,0);
    return 0;
}
