package project.problema11final.Service;

import project.problema11final.Domain.Entry;
import project.problema11final.Domain.Participant;
import project.problema11final.Domain.Race;
import project.problema11final.Domain.User;
import project.problema11final.Repository.EntryDB;
import project.problema11final.Repository.ParticipantDB;
import project.problema11final.Repository.RaceDB;
import project.problema11final.Repository.UserDB;
import project.problema11final.Service.DTOs.ParticipantDTO;
import project.problema11final.Service.DTOs.RaceDTO;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

public class Service {
    private UserDB userDB;
    private RaceDB raceDB;
    private EntryDB entryDB;
    private ParticipantDB participantDB;

    public Service(UserDB userdb, RaceDB racedb, EntryDB entrydb, ParticipantDB participantdb) {
        this.userDB = userdb;
        this.raceDB = racedb;
        this.entryDB = entrydb;
        this.participantDB = participantdb;
    }

    public Iterable<User> getUsers() {
        return userDB.findAll();
    }

    public boolean LogIn(String username, String password) {
        Iterable<User> users = getUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public User getUserByUsername(String username){
        Iterable<User> users = getUsers();
        for(User user : users){
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    public Iterable<Race> getRaces() {
        return raceDB.findAll();
    }

    public Iterable<RaceDTO> racesDTO(){
        Iterable<Race> races = raceDB.findAll();
        Iterable<Entry> entries = entryDB.findAll();
        HashSet<RaceDTO> raceDTOS = new HashSet<>();

        for (Race r : races){
            int noRacers = 0;
            for (Entry e : entries){
                if(e.getRaceId().equals(r.getId())){
                    noRacers += 1;
                }

                }
            RaceDTO raceDTO = new RaceDTO(String.valueOf(r.getEngineCapacity()), String.valueOf(noRacers));
            raceDTOS.add(raceDTO);
            }
        return raceDTOS;
        }

        public Iterable<String> getTeams(){
            Iterable<Participant> participants = participantDB.findAll();
            HashSet<String> teams = new HashSet<>();
            for (Participant p : participants){
                teams.add(p.getTeam());
            }
            return teams;
        }

        public Iterable<Entry> getEntries(){
            return entryDB.findAll();
        }

        public Iterable<Participant> getParticipants(){
            return participantDB.findAll();
        }

        public Iterable<ParticipantDTO> participantsDTO(){
            Iterable<Entry> entries = getEntries();
            Iterable<Participant> participants = getParticipants();

            ArrayList<ParticipantDTO> participantDTOS = new ArrayList<>();

//            for(Entry e : entries){
                for (Participant p : participants){
//                    if(e.getParticipantId().equals(p.getId())){
                        ParticipantDTO pDTO = new ParticipantDTO(p.getName(), p.getTeam(), String.valueOf(p.getEngineCapacity()));
                        participantDTOS.add(pDTO);
                    }
//                }
//            }

            return participantDTOS;
        }

        public Iterable<ParticipantDTO> getParticipantDTOforTeam(String team){
            Iterable<ParticipantDTO> participants = participantsDTO();
            ArrayList<ParticipantDTO> filtered = new ArrayList<>();
            for (ParticipantDTO p : participants){
                if(p.getTeam().equals(team)){
                    filtered.add(p);
                }
            }
            return filtered;
        }

    public void addParticipant(String username, String team, String engine) {
        Participant participant = new Participant(username, team, Integer.parseInt(engine));
        UUID id = UUID.randomUUID();
        participant.setId(id);
        participantDB.save(participant);
    }

    public Iterable<String> engineCapacities(){
        Iterable<Participant> participants = getParticipants();
        HashSet<String> engine = new HashSet<>();
        for (Participant p : participants){
            engine.add(String.valueOf(p.getEngineCapacity()));
        }
        return engine;
    }

    public void createEntry(Participant p){
        Iterable<Race> rs = getRaces();

        for (Race r : rs){
            if(p.getEngineCapacity() == r.getEngineCapacity()){
                Entry e = new Entry(r.getId(), p.getId());
                UUID id = UUID.randomUUID();
                e.setId(id);
                entryDB.save(e);

            }
        }
    }

    public void createAllEntries(){
        Iterable<Participant> ps = getParticipants();
        Iterable<Race> rs = getRaces();
        for (Participant p : ps ){
            for (Race r : rs) {
                if (p.getEngineCapacity() == r.getEngineCapacity()){
                    createEntry(p);
                }
            }
        }
    }

    public Participant getParticipant(String name, String team, String engineCapacity){
        for (Participant p : getParticipants()){
            if (p.getName().equals(name) && p.getTeam().equals(team) && String.valueOf(p.getEngineCapacity()).equals(engineCapacity)){
                return p;
            }
        }
        return null;
    }
}
