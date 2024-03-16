namespace labCSharp.Domain;

public class Achizitie:Entity<string>
{
    public string produs { get; set; }
    public int cantitate { get; set; }
    public double pretProdus { get; set; }
    public string facturaId { get; set; }

    public override string ToString()
    {
        return Id + " " + produs + " " + cantitate + " " + pretProdus + " " + facturaId;
    }
}