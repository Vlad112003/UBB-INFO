namespace Model
{
    public class Entity<TId>
    {
        public TId Id { get; set; }
        
        public override string ToString()
        {
            return Id.ToString();
        }
        
    }
}