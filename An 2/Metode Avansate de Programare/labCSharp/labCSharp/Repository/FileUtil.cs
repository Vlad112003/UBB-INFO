namespace labCSharp.Repository;

public class FileUtil
{
    public delegate TE LineToEntity<TE>(string line);
    
    public delegate string EntityToLine<TE>(TE entity);
    
    public static List<TE> ReadAll<TE>(string filename,LineToEntity<TE> lineToEntity)
    {
        using (var r = new StreamReader(filename))
        {
            List<TE> answer = new List<TE>();
            string line;
            while ((line = r.ReadLine()) != null)
            {
                var entity = lineToEntity(line);
                answer.Add(entity);
            }
            return answer;
        }
    }
}