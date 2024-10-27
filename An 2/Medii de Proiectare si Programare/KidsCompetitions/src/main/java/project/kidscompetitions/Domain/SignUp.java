package project.kidscompetitions.Domain;

import java.util.Objects;

public class SignUp extends Entity<Long> {

    private Participant participant;

    private Competition competition;

    public SignUp(Participant participant, Competition competition) {
        this.participant = participant;
        this.competition = competition;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public Competition getCompetition() {return competition;}

    public void setCompetition(Competition competition) {this.competition = competition;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SignUp signUp = (SignUp) o;
        return Objects.equals(participant, signUp.participant) && Objects.equals(competition, signUp.competition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), participant, competition);
    }

    @Override
    public String toString() {
        return "SignUp{" +
                "participant=" + participant +
                ", competition=" + competition +
                "} " + super.toString();
    }
}

