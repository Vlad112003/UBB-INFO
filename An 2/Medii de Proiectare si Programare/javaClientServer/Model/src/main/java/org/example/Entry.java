package org.example;

import java.util.Objects;
import java.util.UUID;

public class Entry extends Entity<UUID> {
    private UUID raceID;
    private UUID participantID;

    public Entry(UUID raceID, UUID participantID) {
        this.raceID = raceID;
        this.participantID = participantID;
    }

    public UUID getRaceId() {
        return raceID;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "raceID=" + raceID +
                ", participantID=" + participantID +
                '}';
    }

    public UUID getParticipantId() {
        return participantID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entry entry = (Entry) o;
        return Objects.equals(raceID, entry.raceID) && Objects.equals(participantID, entry.participantID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(raceID, participantID);
    }

    public void setRaceID(UUID raceID) {
        this.raceID = raceID;
    }

    public void setParticipantID(UUID participantID) {
        this.participantID = participantID;
    }
}
