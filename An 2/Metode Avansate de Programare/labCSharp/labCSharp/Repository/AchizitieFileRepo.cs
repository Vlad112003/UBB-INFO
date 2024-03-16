using labCSharp.Domain;

namespace labCSharp.Repository;

public class AchizitieFileRepo:FileRepository<string, Achizitie>
{
    public AchizitieFileRepo(string filename) : base(filename, EntityFileMapping.LineToAchizitie, EntityFileMapping.AchizitieToLine)
    {
    }
    
}