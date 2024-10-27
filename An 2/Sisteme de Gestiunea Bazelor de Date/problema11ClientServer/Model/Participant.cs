using System;

namespace Model
{
    public class Participant : Entity<Guid>
    {
        public string name { get; set; }
        public string team { get; set; }
        public int engineCapacity { get; set; }
        
        public Participant(string name, string team, int engineCapacity)
        {
            this.name = name;
            this.team = team;
            this.engineCapacity = engineCapacity;
            Id = Guid.NewGuid();
        }

        public Participant(Guid id, string name, string team, int engineCapacity)
        {
            this.name = name;
            this.team = team;
            this.engineCapacity = engineCapacity;
            Id = id;
        }
    }
}