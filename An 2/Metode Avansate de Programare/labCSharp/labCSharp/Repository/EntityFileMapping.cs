using labCSharp.Domain;

namespace labCSharp.Repository;

public class EntityFileMapping
{
    public static Document LineToDocument(string line)
    {
        var field = line.Split(',');
        if (line != "")
        {
            var document = new Document
            {
                Id = field[0],
                nume = field[1],
                dataEmitere = DateTime.Parse(field[2])
            };
            return document;
        }
        else
        {
            return null;
        }
    }
    
    public static string DocumentToLine(Document entity)
    {
        return entity.Id + "," + entity.nume + "," + entity.dataEmitere;
    }
    
    public static Factura LineToFactura(string line)
    {
        var field = line.Split(',');
        var factura = new Factura
        {
            Id = field[0],
            dataScadenta = DateTime.Parse(field[1]),
            categorie = (Categorie)Enum.Parse(typeof(Categorie), field[2])
            
        };
        return factura;
    }
    
    public static string FacturaToLine(Factura entity)
    {
        return entity.Id + "," + entity.dataScadenta + "," + entity.categorie;
    }

    public static Achizitie LineToAchizitie(string line)
    {
        var field = line.Split(',');
        var achizitie = new Achizitie
        {
            Id = field[0],
            produs = field[1],
            cantitate = Int32.Parse(field[2]),
            pretProdus = double.Parse(field[3]),
            facturaId = field[4]
                
        };
        return achizitie;
    }
    
    public static string AchizitieToLine(Achizitie entity)
    {
        return entity.Id + "," + entity.produs + "," + entity.cantitate + "," + entity.pretProdus + "," + entity.facturaId;
    }
}