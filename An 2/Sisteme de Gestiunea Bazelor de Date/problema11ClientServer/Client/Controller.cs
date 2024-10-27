using System;
using System.Collections;
using System.Collections.Generic;
using Model;
using Networking.DTOs;
using Services;

namespace Client;

public class Controller : IObserver
{
    public event EventHandler<ParticipantAddedEventArgs> updateEvent;
    private IService server;
    private User currentUser;


    public Controller(IService server)
    {
        this.server = server;
    }
    
    
    public void login(string username, string password)
    {
        server.Login(username, password, this);
    }
    
    public void Logout(User user)
    {
        server.Logout(user, this);
    }
    
    
    public void RaceAdded()
    {
        Console.WriteLine("Race added");

        ParticipantAddedEventArgs args = new ParticipantAddedEventArgs(ParticipantAddedEvent.ParticipantAdded, null);
        OnUserEvent(ParticipantAddedEvent.ParticipantAdded, null);
    }
    
    protected virtual void OnUserEvent(ParticipantAddedEvent raceAdded, Race race)
    {
        if (updateEvent == null) return;
        updateEvent(this, new ParticipantAddedEventArgs(raceAdded, race));
        Console.WriteLine("Update event called");
    }

    public IEnumerable getParticipantDTOforTeam(string team)
    {
        return server.getParticipantDTOforTeam(team);
    }

    public IEnumerable racesDTO()
    {
        return server.getRacesDTO();
    }

    public IEnumerable teams()
    {
        return server.getTeams();
    }

    public IEnumerable engines()
    {
        return server.getEngineCapacities();
    }

    public void addParticipant(string username, string team, string engine)
    {
        throw new NotImplementedException();
    }
}