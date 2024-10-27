package project.oacoacdiridiridac.Domain;

import java.util.*;

public class Team extends Entity<UUID>{

    String name;
    HashSet<User> teammates;

    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                ", teammates=" + teammates +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Team team = (Team) o;
        return Objects.equals(name, team.name) && Objects.equals(teammates, team.teammates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, teammates);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashSet<User> getTeammates() {
        return teammates;
    }

    public User addTeammate(User user){
        teammates.add(user);
        return user;
    }

    public void setTeammates(HashSet<User> teammates) {
        this.teammates = teammates;
    }

    public Team(String name, HashSet<User> teammates){
        this.name = name;
        this.teammates = teammates;
        setId(UUID.randomUUID());
    }

    public Team(String name){
        this.name = name;
        this.teammates = new HashSet<User>();
        setId(UUID.randomUUID());
    }
}
