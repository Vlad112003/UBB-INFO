package com.example;

import com.example.Domain.*;
import com.example.Repository.AdminRepo;
import com.example.Repository.ContestRepo;
import com.example.Repository.ParticipantRepo;
import com.example.Repository.ParticipationRepo;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceImpl implements IService {

    private final AdminRepo adminRepo;
    private final ContestRepo contestRepo;
    private final ParticipantRepo participantRepo;
    private final ParticipationRepo participationRepo;

    private final Map<String, IObserver> loggedAngajati;


    public ServiceImpl(AdminRepo adminRepo, ContestRepo contestRepo, ParticipantRepo participantRepo, ParticipationRepo participationRepo) {
        this.adminRepo = adminRepo;
        this.contestRepo = contestRepo;
        this.participantRepo = participantRepo;
        this.participationRepo = participationRepo;
        this.loggedAngajati = new ConcurrentHashMap<>();
    }

    @Override
    public void login(Admin angajat, IObserver client) {
        //Admin angajatIntreg = adminRepo.find_by_username_and_password(angajat.getUsername(),angajat.getPassword());
        //if (angajatIntreg == null) {
          //  throw new RuntimeException("Authentication failed!");
        //} else {
            loggedAngajati.put(angajat.getUsername(), client);
            for (IObserver c : loggedAngajati.values()) {
                System.out.println("Logged in: " + c.toString() +"\n \n\n\n\n\n");
            }
        //}
    }

    @Override
    public void logout(Admin angajat, IObserver client) {
        IObserver localClient = loggedAngajati.remove(angajat.getUsername());
        if (localClient == null)
            throw new RuntimeException("User " + angajat.getUsername() + " is not logged in.");
    }

    @Override
    public Admin findAngajatByUsername(String username,String password){
        return adminRepo.find_by_username_and_password(username,password);
    }

    @Override
    public Iterable<Contest> findAllContest() {
        return contestRepo.find_all();
    }

   @Override
    public Iterable<Contest> getContests(UUID id) {
        return participationRepo.getContests(id);
    }

    @Override
    public Iterable<Participation> findAllParticipations() {
        return participationRepo.find_all();
    }

    @Override
    public int getNrParticipanti(UUID id) {
        return participationRepo.getNumberOfParticipants(id);
    }

    @Override
    public Iterable<Participant> findAllParticipants() {
        return participantRepo.find_all();
    }

    @Override
    public void addParticipation(Participation participation) {
        participationRepo.save(participation);
        for (IObserver client : loggedAngajati.values()) {
            client.init_model();
        }
    }



}
