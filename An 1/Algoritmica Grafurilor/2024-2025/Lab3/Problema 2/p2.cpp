#include <iostream>
#include <fstream>
#include <vector>
#include <queue>
#define INF 0x3f3f3f3f

using namespace std;

const int CMAX = 50015;
const int oo = 1<<31-1;

int n , m , D[CMAX] , x , y , z;
bool viz[CMAX];

struct comp{
    bool operator()(int x ,int y)
    {
        return D[x] > D[y];
    }
};

vector < pair < int , int > > v[CMAX];
priority_queue < int , vector < int > , comp > Q;

int n,m, s;
vector< pair<int, int> >graf[50005];
vector <int> v;
queue <int> coada;
int d[50005];
int viz[50005];
int esteincoada[50005];

bool bellmanford(int s)
{
    for(int i=0;i<n;i++)
    {
        viz[i]=0;
        //esteincoada[i]=0;
        d[i]=INF;
    }
    d[s] = 0;
    coada.push(s);
    //esteincoada[s] = 1;

    while(!coada.empty())
    {
        int nodcurent = coada.front();
        viz[nodcurent]++;
        if(viz[nodcurent] >= n)
            return false;
        coada.pop();
        //esteincoada[nodcurent] = 0;

        for(size_t i=0;i<graf[nodcurent].size();i++)
        {
            int vecin = graf[nodcurent][i].first;
            int cost = graf[nodcurent][i].second;
            if(d[nodcurent]+cost < d[vecin])
            {
                d[vecin] = d[nodcurent]+cost;
                //if(!esteincoada[vecin])
                    coada.push(vecin);
            }
        }
    }
    return true;
}

void dijkstra(int nod)
{
    viz[nod] = 1;
    D[nod] = 0;
    Q.push(nod);

    while(!Q.empty())
    {
        int nodcurent;
        nodcurent = Q.top();
        viz[nodcurent] = 0;
        for(int i=0;i<v[nodcurent].size();i++)
        {
            int cost , vecin;
            vecin = v[nodcurent][i].first;
            cost = v[nodcurent][i].second;
            if(D[nodcurent]+cost<D[vecin])
            {
                D[vecin] = D[nodcurent] + cost;
                if(viz[vecin]==0)
                {
                    Q.push(vecin);
                    viz[vecin] = 1;
                }
            }
        }
        Q.pop();
    }
}

int main(int argc, char* argv[])
{
    ifstream in(argv[1]);
    ofstream out(argv[2]);

    in>>n>>m>>s;
    //cout << n << " " << m << " " << s << '\n';
    for(int i=1;i<=m;i++)
    {
        int x,y,c;
        in>>x>>y>>c;
        graf[x].push_back(make_pair(y,c));
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
    return 0;
}
