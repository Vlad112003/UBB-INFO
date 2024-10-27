package ro.mpp.javaMPPServer.domain.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "motorcycle_races")
public class MotorcycleRace extends Model<UUID> {
    @Column(name = "name")
    String name;
    @Column(name = "engine_capacity")
    Integer engineCapacity;
    @Column(name = "participants_count")
    Integer participantsCount;

    public String getName() {
        return name;
    }

    public MotorcycleRace() {}

    public MotorcycleRace(String name, Integer engineCapacity, Integer participantsCount) {
        this.name = name;
        this.engineCapacity = engineCapacity;
        this.participantsCount = participantsCount;
    }

    public Integer getEngineCapacity() {
        return engineCapacity;
    }

    public Integer getParticipantsCount() {
        return participantsCount;
    }

    public void setEngineCapacity(Integer engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParticipantsCount(Integer participantsCount) {
        this.participantsCount = participantsCount;
    }
}
