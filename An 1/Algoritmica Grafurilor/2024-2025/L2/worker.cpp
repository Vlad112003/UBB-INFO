#include "worker.h"
void probleme::citire_orientat_mtr(char* file,int** a,int &n)
{
    ifstream f(file);
    int i,x,y,j;
    for(i=0; i<dim; i++)
        for(j=0; j<dim; j++)
            a[i][j]=0;
    f>>n;
    while(!f.eof())
    {
        f>>x>>y;
        a[x][y]=1;
    }
    f.close();
}
void probleme::citire_orientat_lst(char* file,vector<int> E[], int &n)
{
    int x,y;
    ifstream f(file);
    f>>n;
    while(!f.eof())
    {
        f>>x>>y;
        E[x].push_back(y);
    }
    f.close();
}
void probleme::citire_orientat_char(char* file, char** a, int &n)
{
    ifstream f(file);
    n=0;
    while(!f.eof())
    {
        f.getline(a[n],dim);
        n++;
    }
    f.close();
}
void probleme::afisare_char(char** a,int n)
{
    int i;
    for(i=0; i<n; i++)
        cout<<a[i]<<endl;

}
void probleme::afisare_mtr(int** a,int n)
{
    int i,j;
    for(i=1; i<=n; i++)
    {
        for(j=1; j<=n; j++)
            cout<<a[i][j]<<" ";
        cout<<endl;
    }
}
void probleme::afisare_lst(vector<int> E[], int n)
{
    int i,j;
    for(i=1; i<=n; i++)
    {
        cout<<i<<": ";
        for(j=0; j<E[i].size(); j++)
            cout<<E[i][j]<<" ";
        cout<<endl;
    }
}
void probleme::p1(int p, int v)
{
    int n,i,d[dim+1],par[dim+1]= {0},rez[dim+1],x;
    const int inf=999999;
    queue<int> Q;
    vector<int> E[dim+1];
    (*this).citire_orientat_lst("graf.txt",E,n);
    for(int i=1; i<=n; i++)
        if(i!=p) d[i]=inf;
    Q.push(p);
    while(!Q.empty())
    {
        x=Q.front();
        //cout<<x<<" ";
        Q.pop();
        for(i=0; i<E[x].size(); i++)
            if(d[E[x][i]]==inf)
            {
                par[E[x][i]]=x;
                d[E[x][i]]=d[x]+1;
                Q.push(E[x][i]);
            }
    }
    int k=d[v], k2=d[v];
    rez[k]=v;
    while(k)
    {
        rez[k-1]=par[rez[k]];
        k--;
    }
    cout<<p<<" ";
    for(i=1; i<=k2; i++)
        cout<<rez[i]<<" ";
}
void probleme::p2()
{
    int i,j,k,n;
    int** a=new int*[dim];
    for(i=0; i<dim; i++)
        a[i]=new int[dim];
    this->citire_orientat_mtr("graf2.txt",a,n);
    for(k=1; k<=n; k++)
        for(i=1; i<=n; i++)
            for(j=1; j<=n; j++)
                if(a[i][j]==0)
                    a[i][j]=(a[i][k]&&a[k][j]);
    this->afisare_mtr(a,n);
    for(i=0; i<dim; i++)
        delete[] a[i];
    delete[] a;
}
void probleme::p3()
{
    int n,m,i,j,lb[dim][dim]= {0},k;
    int sx,sy,fx,fy;
    const int di[]= {-1,0,1,0},dj[]= {0,1,0,-1};
    char** a=new char*[dim];
    for(i=0; i<dim; i++)
        a[i]=new char[dim];
    queue<pair<int,int>>q;
    this->citire_orientat_char("labirint.txt",a,n);
    n--;
    m=strlen(a[0]);
    for(i=0; i<n; i++)
        for(j=0; j<strlen(a[i]); j++)
        {
            if(a[i][j]=='S')
                sx=i,sy=j;
            if(a[i][j]=='F')
                fx=i,fy=j;
        }
    for(i=0; i<n; i++)
        for(j=0; j<strlen(a[i]); j++)
            if(a[i][j]=='1')
                lb[i][j]=-1;
    q.push(make_pair(sx,sy));
    lb[sx][sy]=1;
    while(!q.empty())
    {
        for(k=0; k<4; k++)
        {
            int x = q.front().first, y = q.front().second;
            int iv=x+di[k],jv=y+dj[k];
            if(iv>=0&&iv<n&&jv>=0&&jv<=m&&lb[iv][jv]==0)
            {
                lb[iv][jv] = lb[x][y] + 1;
                q.push(make_pair(iv, jv));
            }
        }
        q.pop();
    }
    for(i=0; i<n; i++)
    {
        for(j=0; j<m; j++)
            if(lb[i][j]==-1)
                cout<<"0";
            else
                cout<<"x";
        cout<<endl;
    }
    for(i=0; i<dim; i++)
        delete[] a[i];
    delete[] a;
}
void probleme::p4(int v)
{
    int** a=new int*[dim],n,n1;
    const int inf=999999;
    int i,x[dim+1],k,st,dr,vazut[dim+1]={0};
    vector<int>E[dim];
    for(i=0; i<dim; i++)
        a[i]=new int[dim];
    this->citire_orientat_mtr("graf2.txt",a,n1);
    this->citire_orientat_lst("graf2.txt",E,n);
    int d[dim+1],prezent[dim+1] = {0};
    for (i=0; i<=n; i++)
    {
        d[i]=inf;
    }
    d[v] = 0;
    queue<int> q;
    q.push(v);
    prezent[v] =1;

    while (!q.empty())
    {
        int u=q.front();
        q.pop();
        prezent[u]=0;
        for (i=0; i<E[u].size(); i++)
        {
            int v = E[u][i];
            if (d[v] > d[u] + 1)
            {
                d[v] = d[u] + 1;
                if (!prezent[v])
                {
                    q.push(v);
                    prezent[v]=1;
                }
            }
        }
    }
    st=dr=1;
    x[1]=v;
    vazut[v]=1;
    while(st<=dr)
    {
        k=x[st];
        for(i=1; i<=n; i++)
            if(vazut[i]==0 && a[k][i]==1)
            {
                vazut[i]=1;
                x[++dr]=i;
            }
        st++;
    }
    for(i=1; i<=n; i++)
       if(vazut[i])
          cout<<i<<" ";
    cout<<endl;
    for(i=1; i<=n; i++)
        cout<<i<<" "<<d[i]<<endl;
    for(i=0; i<dim; i++)
        delete[] a[i];
    delete[] a;
}
void probleme::p5()
{

}
void probleme::meniu()
{
    cout<<"1. problema 1  "<<endl;
    cout<<"2. problema 2  "<<endl;
    cout<<"3. problema 3  x"<<endl;
    cout<<"4. problema 4  "<<endl;
    cout<<"100. iesire"<<endl;
}
void probleme::start()
{
    int opt,s,f,v;
    vector<int> G[dim+1];
    while(true)
    {
        cout<<endl<<"dati optiunea: ";
        cin>>opt;
        switch(opt)
        {
        case 1:
            cin>>s>>f;
            this->p1(s,f);
            break;
        case 2:
            this->p2();
            break;
        case 3:
            this->p3();
            break;
        case 4:
            cin>>v;
            this->p4(v);
            break;
        case 100:
            exit(0);
        default:
            cout<<"optiune invalida";
        }
    }
}
