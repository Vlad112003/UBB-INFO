package project.kidscompetitions.Domain;

import java.util.Objects;

public class Competition extends Entity<Long> {

    private String ageGroups;
    private String name;

    public Competition(String ageGroups, String name) {
        this.ageGroups = ageGroups;
        this.name = name;
    }

    public String getAgeGroups() {
        return ageGroups;
    }

    public String getName() {
        return name;
    }

    public void setAgeGroups(String ageGroups) {
        this.ageGroups = ageGroups;
    }

    public void setname(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Competition that = (Competition) o;
        return Objects.equals(ageGroups, that.ageGroups) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), ageGroups, name);
    }

    @Override
    public String toString() {
        return "Athletics_Tests " + id + ": " + "age groups= " + ageGroups +
                ", name= " + name + '\'';
    }
}

