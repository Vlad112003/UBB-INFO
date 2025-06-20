#include "IteratorMDO.h"
#include "MDO.h"
#include <vector>
#include <cmath>
#include <exception>

using namespace std;

MDO::MDO(Relatie r)
{
	this->m=maxcap;
    this->r=r;
    this->lg=0;
    this->prim=-1;
    int i;
    for(i=0;i<this->m;i++)
         this->e[i]=null;
}

int MDO::d(TCheie c, int i) const
{
    int rez=c%this->m + 0*i+pow(-1,i)*i*i;
    return rez%this->m;
}

void MDO::adauga(TCheie c, TValoare v)
{
    int i=0;
    bool done=false;
    TElem x= make_pair(c,v);
    do
    {
       int j=this->d(c,i);
       if((this->e[j]==null||this->e[j]==sters)&&j>=0)
       {
            this->e[j]=x;
            if(this->vid())
                this->prim=j;
            this->lg++;
            done=true;
       }
       else
           i++;
    }while(i<this->m&&!done);
}

vector<TValoare> MDO::cauta(TCheie c) const
{
	vector<TValoare> v;
    if(c>=0)
    {
        int i=0,j;
        do
        {
            j=this->d(c,i);
            if(this->e[j].first==c&&j>=0)
                v.push_back(this->e[j].second);
            i++;
        }while(i<this->m&&this->e[j]!=null);
    }
    return v;
}

bool MDO::sterge(TCheie c, TValoare v)
{
    TElem x=make_pair(c,v);
    int i=0;
    while(i<this->m)
    {
        if(this->e[i]==x)
        {
            this->e[i]=sters;
            this->lg--;
            return true;
        }
        else i++;
    }
    return false;
}

int MDO::dim() const
{
	return this->lg;
}

bool MDO::vid() const
{
	return this->lg==0;
}

IteratorMDO MDO::iterator() const
{
	return IteratorMDO(*this);
}

MDO::~MDO() {
	/* de adaugat */
}
