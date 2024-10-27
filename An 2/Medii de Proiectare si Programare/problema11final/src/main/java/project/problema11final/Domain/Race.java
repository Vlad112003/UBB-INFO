package project.problema11final.Domain;

import java.util.Objects;
import java.util.UUID;

public class Race extends Entity<UUID>{
    private int engineCapacity;

    public Race (int capacity){
        this.engineCapacity = capacity;
        this.setId(UUID.randomUUID());
    }

    public Race (UUID id, int capacity){
        this.engineCapacity = capacity;
        this.setId(id);
    }

    public int getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(int engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    @Override
    public String toString() {
        return "Race{" +
                "capacity=" + engineCapacity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Race race = (Race) o;
        return engineCapacity == race.engineCapacity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(engineCapacity);
    }
}
