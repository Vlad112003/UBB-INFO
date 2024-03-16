using System.Collections.ObjectModel;
using labCSharp.Domain;
using labCSharp.Repository;

namespace labCSharp.Service;

public class FacturaService:CrudService<string, Factura>
{
    public FacturaService(IRepository<string, Factura> x) : base(x)
    {
    }
    
    
}