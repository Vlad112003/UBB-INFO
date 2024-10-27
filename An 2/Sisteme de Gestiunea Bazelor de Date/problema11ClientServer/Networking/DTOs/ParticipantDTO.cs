namespace Networking.DTOs;

public class ParticipantDTO
{
    public string name { get; set; }
    public string team { get; set; }
    public string engineCapacity { get; set; }

    public ParticipantDTO(string name, string team, string engineCapacity)
    {
        this.name = name;
        this.team = team;
        this.engineCapacity = engineCapacity;
    }
}