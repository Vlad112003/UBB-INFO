using labCSharp.Domain;

namespace labCSharp.Repository;

public class DocumentFileRepo : FileRepository<string, Document>
{
    public DocumentFileRepo(string filename) : base(filename, EntityFileMapping.LineToDocument, EntityFileMapping.DocumentToLine)
    {
    }
}