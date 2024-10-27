package com.example.Domain;

import java.util.UUID;

public class Participation extends Entity<UUID> {
    private Participant participant;
    private Contest contest;

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }

    public Participation(Participant participant, Contest contest) {
        this.participant = participant;
        this.contest = contest;
    }
}
