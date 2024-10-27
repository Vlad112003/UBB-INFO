package com.example.Repository;

import com.example.Domain.Participation;

import java.util.UUID;

public interface ParticipationInterface extends RepoInterface<UUID, Participation> {
    public int getNumberOfParticipants(UUID contestId);
}
