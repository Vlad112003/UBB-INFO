package project.oacoacdiridiridac.Domain;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class Race extends Entity<UUID>{
    private HashSet<Team> teams;
    private User organizer;

    private int engineCapacity;
    private int NoRacers = 0;

    public Race(HashSet<Team> teams, User organizer, int engineCapacity){
        this.teams = teams;
        this.organizer = organizer;
        this.engineCapacity = engineCapacity;
        this.setId(UUID.randomUUID());
        initNoRacers();
    }

    public Race(int engineCapacity, int NoRacers){
        this.teams = new HashSet<Team>();
        this.engineCapacity = engineCapacity;
        this.NoRacers = NoRacers;
    }



    //init NoRacers
    public void initNoRacers(){
        for(Team t : teams){
            NoRacers += t.getTeammates().size();
        }
    }

    public HashSet<Team> getTeams() {
        return teams;
    }

    public void setTeams(HashSet<Team> teams) {
        this.teams = teams;
    }

    public User getOrganizer() {
        return organizer;
    }

    public void setOrganizer(User organizer) {
        this.organizer = organizer;
    }

    public int getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(int engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public int getNoRacers() {
        return NoRacers;
    }

    public void setNoRacers(int noRacers) {
        NoRacers = noRacers;
    }

    public Team addTeam(Team team){
        teams.add(team);
        return team;
    }
}
