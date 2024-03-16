using labCSharp.Domain;
using labCSharp.Repository;

namespace labCSharp.Service;

public class DocumentService:CrudService<string, Document>
{
    public DocumentService(IRepository<string, Document> x) : base(x)
    {
    }
    
    
    
}