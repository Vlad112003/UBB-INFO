using System;

namespace Model
{
    public class Race : Entity<Guid>
    {
        public int engineCapacity { get; set; }
        
        public Race (int engineCapacity)
        {
            this.engineCapacity = engineCapacity;
            Id = Guid.NewGuid();
        }

        public Race(Guid id, int engineCapacity)
        {
            this.engineCapacity = engineCapacity;
            Id = id;
        }

        public override string ToString()
        {
            return $"{nameof(Id)}: {Id}, {nameof(engineCapacity)}: {engineCapacity}";
        }
    }
}