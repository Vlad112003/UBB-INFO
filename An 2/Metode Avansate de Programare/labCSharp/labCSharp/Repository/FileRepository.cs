using labCSharp.Domain;

namespace labCSharp.Repository;

public class FileRepository<TId, TE>:InMemoryRepo<TId, TE> where TE : Entity<TId>
{
    private readonly string _filename;
    private FileUtil.LineToEntity<TE> _lineToEntity;
    private FileUtil.EntityToLine<TE> _entityToLine;
    
    public FileRepository(string filename, FileUtil.LineToEntity<TE> lineToEntity, FileUtil.EntityToLine<TE>entityToLine)
    {
        _filename = filename;
        _lineToEntity = lineToEntity;
        _entityToLine = entityToLine;
        ReadAll();
    }
    
    private void ReadAll()
    {
        List<TE> x = FileUtil.ReadAll(_filename,_lineToEntity);
        x.ForEach(o=>base.Save(o));
    }
    
    private void WriteAll()
    {
        using (var w = new StreamWriter(_filename))
        {
            foreach (var entity in FindAll()) w.WriteLine(_entityToLine(entity));
        }
    }
    
    public override TE? Save(TE entity)
    {
        var rez = base.Save(entity);
        if (rez != null)
            WriteAll();
        return rez;
    }
    
    public override TE? Delete(TId id)
    {
        var rez = base.Delete(id);
        if (rez != null)
            WriteAll();
        return rez;
    }
    
    public override TE? Update(TE entity)
    {
        var rez = base.Update(entity);
        if (rez != null)
            WriteAll();
        return rez;
    }
}