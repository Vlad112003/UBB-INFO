#include <iostream>
#include <fstream>
#include <vector>
#include <queue>
#define INF 0x3f3f3f3f

using namespace std;

int n,m, s;
vector< pair<int, int> >graf[50005];
vector <int> v;
queue <int> coada;
int d[50005];
int viz[50005];
int esteincoada[50005];

// algoritmul bellman-ford, care functioneaza si pe grafuri cu costuri negative
bool bellmanford(int s)
{
    // initializam vectorul de distante cu INF pt toate nodurile
    for(int i=0;i<n;i++)
    {
        viz[i]=0;
        esteincoada[i]=0;
        d[i]=INF;
    }
    // initializam distanta de la sursa la sursa cu 0
    d[s] = 0;
    coada.push(s);
    esteincoada[s] = 1;

    // parcurgem nodurile din coada
    while(!coada.empty())
    {
        // scoatem nodul curent din coada
        int nodcurent = coada.front();
        viz[nodcurent]++;
        if(viz[nodcurent] >= n)
            return false;
        coada.pop();
        esteincoada[nodcurent] = 0;

        // parcurgem vecinii nodului curent
        for(size_t i=0;i<graf[nodcurent].size();i++)
        {
            int vecin = graf[nodcurent][i].first;
            int cost = graf[nodcurent][i].second;
            // daca distanta de la sursa la vecin este mai mare decat distanta de la sursa la nodul curent + costul
            if(d[nodcurent]+cost < d[vecin])
            {
                // actualizam distanta de la sursa la vecin
                d[vecin] = d[nodcurent]+cost;
                if(!esteincoada[vecin])
                    coada.push(vecin);
            }
        }
    }
    return true;
}

int main(int argc, char* argv[])
{
    if (argc != 3)
    {
        cout << "Usage: " << argv[0] << " <input_file> <output_file>" << endl;
        return 1;
    }

    ifstream in(argv[1]);
    ofstream out(argv[2]);
//    ifstream in("2-in.txt");
//    ofstream out("2-out.txt");

    in>>n>>m>>s;
    //cout << n << " " << m << " " << s << '\n';
    for(int i=1;i<=m;i++)
    {
        int x,y,c;
        in>>x>>y>>c;
        graf[x].emplace_back(y,c);
    }

    if(bellmanford(s))
    {
        for(int i=0;i<n;i++)
            if(d[i]==INF)
                out << "INF ";
            else
                out<<d[i]<<" ";
    }
    else
      out<<"INF";


    in.close();
    out.close();
    cout<<"Done!"<<endl;
    return 0;
}
