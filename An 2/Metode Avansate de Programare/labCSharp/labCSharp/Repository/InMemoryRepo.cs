using System.Collections.ObjectModel;
using System.Security.Claims;
using labCSharp.Domain;

namespace labCSharp.Repository;

public class InMemoryRepo<TId, TE> : IRepository<TId, TE> where TE : Entity<TId>
{
    private readonly Collection<TE> _collection = new();
    
    public TE? FindOne(TId id)
    {
        return _collection.FirstOrDefault(entity => entity.Id.Equals(id));
    }
    
    public IEnumerable<TE> FindAll()
    {
        return _collection;
    }
    
    public virtual TE? Save(TE entity)
    {
        ArgumentNullException.ThrowIfNull(entity);
        if (_collection.Contains(entity))
            return null;
        _collection.Add(entity);
        return entity;
    }
    
    public virtual TE? Update(TE entity)
    {
        ArgumentNullException.ThrowIfNull(entity);
        if (!_collection.Contains(entity))
            return null;
        _collection.Remove(entity);
        _collection.Add(entity);
        return entity;
    }
    
    public virtual TE? Delete(TId id)
    {
        var entity = FindOne(id);
        if (entity == null)
            return null;
        _collection.Remove(entity);
        return entity;
    }
    
}