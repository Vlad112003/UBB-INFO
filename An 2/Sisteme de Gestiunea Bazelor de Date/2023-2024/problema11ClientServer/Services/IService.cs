using System;
using System.Collections;
using System.Collections.Generic;
using Model;
using Services.DTOs;

namespace Services;

public interface IService
{
    // public IEnumerable<User> getUsers();
    public bool Login(String username, String password, IObserver client);

    public void Logout(User user, IObserver client);
    public IEnumerable<Race> getRaces();
    public IEnumerable<Entry> getEntries();
    
    public User getUser(string username, string password);

    public IEnumerable<Participant> getParticipants();
    public IEnumerable<string> getTeams();
    public void addParticipant(string username, string team, string engine);
    public IEnumerable<string> engineCapacities();
    public IEnumerable<string> capacities();
    public void createEntry(Participant p);
    public Participant getParticipant(string name, string team, string engine);
    
    public IEnumerable getParticipantDTOforTeam(string team);
    IEnumerable getRacesDTO();
    IEnumerable getEngineCapacities();
    User getUserByUsername(string username);
}