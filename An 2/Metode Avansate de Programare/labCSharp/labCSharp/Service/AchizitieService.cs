using labCSharp.Domain;
using labCSharp.Repository;

namespace labCSharp.Service;

public class AchizitieService:CrudService<string, Achizitie>
{
    public AchizitieService(IRepository<string, Achizitie> repo) : base(repo)
    {
    }
    
}