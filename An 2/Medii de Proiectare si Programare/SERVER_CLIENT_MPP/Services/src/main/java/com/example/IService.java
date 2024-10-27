package com.example;

import com.example.Domain.*;

import java.util.Optional;
import java.util.UUID;

public interface IService {
    void login(Admin angajat, IObserver client);
    void logout(Admin angajat, IObserver client);
    Admin findAngajatByUsername(String username, String password);
    Iterable<Contest> findAllContest();
   // Iterable<Participant> cautaParticipant(UUID id, DistEnum dist, StyleEnum style);

    Iterable<Contest> getContests(UUID id);

    Iterable<Participation> findAllParticipations();

    int getNrParticipanti(UUID id);

    Iterable<Participant> findAllParticipants();

    void addParticipation(Participation participation);


}

