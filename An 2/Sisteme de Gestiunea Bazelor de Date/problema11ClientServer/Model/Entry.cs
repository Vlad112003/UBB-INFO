using System;

namespace Model
{
    public class Entry : Entity<Guid>
    {
        public Guid raceId { get; set; }
        public Guid participantId { get; set; }
        
        public Entry(Guid raceId, Guid participantId)
        {
            this.raceId = raceId;
            this.participantId = participantId;
            Id = Guid.NewGuid();
        }
    }
}