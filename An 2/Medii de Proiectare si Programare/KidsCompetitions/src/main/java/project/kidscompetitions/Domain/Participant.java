package project.kidscompetitions.Domain;

import java.util.Objects;

public class Participant extends Entity<Long> {

    private String childName;
    private String birthDate;

    public Participant(String childName, String birthDate) {
        this.childName = childName;
        this.birthDate = birthDate;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Participant signUp = (Participant) o;
        return Objects.equals(childName, signUp.childName) && Objects.equals(birthDate, signUp.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), childName, birthDate);
    }

    @Override
    public String toString() {
        return "Sign_Up " + id + ": " + "childName= " + childName + ", birthDate= " + birthDate + '\'';
    }
}

