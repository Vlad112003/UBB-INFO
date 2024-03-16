using labCSharp.Domain;
using labCSharp.Repository;

namespace labCSharp.Service;

public class CrudService<TId, TE> where TE : Entity<TId>
{
    protected IRepository<TId, TE> Repo;
    
    public CrudService(IRepository<TId, TE> x)
    {
        Repo = x;
    }
    
    public TE? FindOne(TId id)
    {
        var rez = Repo.FindAll();
        foreach(var e in rez)
            if (e.Id.Equals(id))
                return e;
        return null;
    }
    
    public IEnumerable<TE> FindAll()
    {
        return Repo.FindAll();
    }
}