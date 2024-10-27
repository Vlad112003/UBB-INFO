using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using Model;
using Services.DTOs;
using Persistence;
using Services;

namespace Server;

public class ServerImplementation : IService
{
    private UserDB _userDb;
    private RaceDB _raceDb;
    private EntryDB _entryDb;
    private ParticipantDB _participantDb;

    private readonly IDictionary<String, IObserver> loggedClients;

    public ServerImplementation(UserDB userDb, RaceDB raceDb, EntryDB entryDb, ParticipantDB participantDb)
    {
        this._userDb = userDb;
        this._raceDb = raceDb;
        this._entryDb = entryDb;
        this._participantDb = participantDb;
        loggedClients = new Dictionary<string, IObserver>();
    }
    
    public IEnumerable<User> getUsers()
    {
        return _userDb.FindAll();
    }

    public bool Login(string username, string password, IObserver client)
    {
        User user = getUser(username, password);
        if (user == null || !user.password.Equals(password))
        {
            return false;
        }
        if (loggedClients.ContainsKey(username))
        {
            return false;
        }
        loggedClients[username] = client;
        return true;
    }

    public void Logout(User user, IObserver client)
    {
        var localClient = loggedClients.Remove(user.username);
        if (localClient == false)
        {
            Console.WriteLine("Logout failed!");
            throw new Exception("User not logged in.");
        }
    }

    public User getUser(string username, string password)
    {
        IEnumerable<User> users = getUsers();
        foreach (var user in users)
        {
            if (user.username.Equals(username) && user.password.Equals(password))
            {
                return user;
            }
        }

        return null;
    }
    
    public IEnumerable<Race> getRaces()
    {
        throw new System.NotImplementedException();
    }

    public IEnumerable<Entry> getEntries()
    {
        throw new System.NotImplementedException();
    }
    

    public IEnumerable<Participant> getParticipants()
    {
        throw new System.NotImplementedException();
    }

    public IEnumerable<string> getTeams()
    {
        throw new System.NotImplementedException();
    }

    public void addParticipant(string username, string team, string engine)
    {
        throw new System.NotImplementedException();
    }

    public IEnumerable<string> engineCapacities()
    {
        throw new System.NotImplementedException();
    }

    public IEnumerable<string> capacities()
    {
        throw new System.NotImplementedException();
    }

    public void createEntry(Participant p)
    {
        throw new System.NotImplementedException();
    }

    public Participant getParticipant(string name, string team, string engine)
    {
        throw new System.NotImplementedException();
    }

    public IEnumerable getParticipantDTOforTeam(string team)
    {
        return null;
    }

    public IEnumerable getRacesDTO()
    {
        return null;
    }

    public IEnumerable getEngineCapacities()
    {
        IEnumerable<string> capacities = new List<string>();
        capacities.Append("500");
        capacities.Append("1000");
        capacities.Append("750");
        return capacities;
    }

    public User getUserByUsername(string username)
    {
        return _userDb.FindByUsername(username);
    }
}