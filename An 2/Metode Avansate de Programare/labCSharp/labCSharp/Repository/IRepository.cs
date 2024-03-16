using labCSharp.Domain;

namespace labCSharp.Repository;

public interface IRepository<Id, E> where E : Entity<Id>
{
    public IEnumerable<E> FindAll();
}