namespace labCSharp.Domain;

public class Document:Entity<string>
{
    public string nume { get; set; }
    
    public DateTime dataEmitere { get; set; }

    public override string ToString()
    {
        return nume + " " + dataEmitere;
    }
}