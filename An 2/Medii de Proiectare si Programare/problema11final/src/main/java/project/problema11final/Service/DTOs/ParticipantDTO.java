package project.problema11final.Service.DTOs;

import java.util.Objects;

public class ParticipantDTO {

    private String name;
    private String team;
    private String engineCapacity;

    public ParticipantDTO(String name, String team, String engineCapacity) {
        this.name = name;
        this.team = team;
        this.engineCapacity = engineCapacity;
    }

    @Override
    public String toString() {
        return "ParticipantDTO{" +
                "name='" + name + '\'' +
                ", team='" + team + '\'' +
                ", engineCapacity='" + engineCapacity + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipantDTO that = (ParticipantDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(team, that.team) && Objects.equals(engineCapacity, that.engineCapacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, team, engineCapacity);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(String engineCapacity) {
        this.engineCapacity = engineCapacity;
    }
}
