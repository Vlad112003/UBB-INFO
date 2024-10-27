package project.oacoacdiridiridac.Service;

import project.oacoacdiridiridac.Domain.*;
import project.oacoacdiridiridac.Repository.*;

import java.util.HashSet;

public class Service {
    private RaceDB raceDB;
    private TeamDB teamDB;
    private UserDB userDB;

    public Service(UserDB userdb, TeamDB teamdb, RaceDB racedb) {
        this.userDB = userdb;
        this.teamDB = teamdb;
        this.raceDB = racedb;
    }

    public boolean LogIn(String email, String password) {
        User user = userDB.findByEmail(email);
        if (user == null) {
            return false;
        }
        return user.getPassword().equals(password);
    }

    public User getUser(String email) {
        return userDB.findByEmail(email);
    }

    public Iterable<Race> getRaces(){
        return raceDB.findAll();
    }

    public Iterable<User> getUsers(){
        return userDB.findAll();
    }

    public void addUsersToTeams(){
        Iterable<User> users = userDB.findAll();
        Iterable<Team> teams = teamDB.findAll();
        for (Team t: teams){
            String memberIDs = teamDB.getMembers(t.getId().toString());
            String[] members = memberIDs.split(",");
            for (String member: members){
                for (User u: users){
                    if (u.getId().toString().equals(member)){
                        t.addTeammate(u);
                    }
                }
            }

        }
    }

    public Iterable<Team> getTeams(){
        return teamDB.findAll();
    }

    public Iterable<User> getTeamMembers(String teamName){

        Iterable<Team> teams = teamDB.findAll();
        for(Team t : teams){
            if(t.getName().equals(teamName)){
                return t.getTeammates();
            }
        }
        return null;
    }

    public void addUserToTeam(User user, String teamName){
        Iterable<Team> teams = teamDB.findAll();
        for(Team t : teams){
            if(t.getName().equals(teamName)){
                t.addTeammate(user);
            }
        }
    }
}
