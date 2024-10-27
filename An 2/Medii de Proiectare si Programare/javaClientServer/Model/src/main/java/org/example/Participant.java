package org.example;

import java.util.Objects;
import java.util.UUID;

public class Participant extends Entity<UUID> {
    private String name;
    private String team;

    private int engineCapacity;

    public Participant(String name, String team, int engineCapacity) {
        this.name = name;
        this.team = team;
        this.engineCapacity = engineCapacity;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "name='" + name + '\'' +
                ", team='" + team + '\'' +
                ", engineCapacity=" + engineCapacity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return engineCapacity == that.engineCapacity && Objects.equals(name, that.name) && Objects.equals(team, that.team);
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

    public int getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(int engineCapacity) {
        this.engineCapacity = engineCapacity;
    }
}
