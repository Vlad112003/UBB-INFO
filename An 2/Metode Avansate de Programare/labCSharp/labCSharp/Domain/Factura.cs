namespace labCSharp.Domain;

public enum Categorie
{
    Utilities,
    Groceries,
    Fashion,
    Entertainment,
    Electronics
}

public class Factura:Document
{
    public DateTime dataScadenta { get; set; }
    public List<Achizitie> achizitii { get; set; }
    public Categorie categorie { get; set; }
    
    public override string ToString()
    {
        return Id + " " + nume + " " + dataEmitere + " " + dataScadenta + " " + categorie;
    }
}