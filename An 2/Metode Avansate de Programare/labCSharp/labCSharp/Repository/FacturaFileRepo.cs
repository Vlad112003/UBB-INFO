using labCSharp.Domain;

namespace labCSharp.Repository;

public class FacturaFileRepo:FileRepository<string, Factura>
{
    public FacturaFileRepo(string filename) : base(filename, EntityFileMapping.LineToFactura, EntityFileMapping.FacturaToLine)
    {
    }
}