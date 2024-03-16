#include "IteratorMDO.h"
#include "MDO.h"

IteratorMDO::IteratorMDO(const MDO& d) : dict(d)
{
    this->poz=d.prim;
    int n=d.dim();
    int c=0;
	for(auto i:d.e)
    {
        if(i!=null&&i!=sters)
        {
            this->v.push_back(i);
            c++;
        }
        if(c==n) break;
    }
    //std::sort(this->v.begin(),this->v.end(),[&d](TCheie a,TCheie b ){return (bool)d.r(a,b);});
    for(int i=0;i<this->v.size()-1;i++)
        for(int j=i+1;j<this->v.size();j++)
            if(!d.r(v[i].first,v[j].first))
            {
                auto x=v[i];
                v[i]=v[j];
                v[j]=x;
            }
}

void IteratorMDO::prim()
{
	this->poz=this->dict.prim;
}

void IteratorMDO::urmator()
{
    if(!this->valid())
        throw exception();
    this->poz++;
}

bool IteratorMDO::valid() const{
	return this->poz<this->v.size()&&this->poz!=-1;
}

TElem IteratorMDO::element() const
{
    if(!this->valid())
        throw exception();
	return this->v[this->poz];
}
