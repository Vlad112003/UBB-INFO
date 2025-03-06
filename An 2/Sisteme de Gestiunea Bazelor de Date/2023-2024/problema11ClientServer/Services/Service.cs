// using System;
// using System.Collections.Generic;
// using Model;
// using Persistence;
// using Networking.DTOs;
//
// namespace Services;
//
// public class Service
// {
//     private UserDB _userDb;
//     private RaceDB _raceDb;
//     private ParticipantDB _participantDb;
//     private EntryDB _entryDb;
//
//     public Service(UserDB userdb, RaceDB racedb, ParticipantDB participantdb, EntryDB entrydb)
//     {
//         this._userDb = userdb;
//         this._raceDb = racedb;
//         this._participantDb = participantdb;
//         this._entryDb = entrydb;
//     }
//
//     public IEnumerable<User> getUsers()
//     {
//         return _userDb.FindAll();
//     }
//     
//     public User Login(String username, String password)
//     {
//         IEnumerable<User> users = getUsers();
//         foreach (var user in users)
//         {
//             if (user.username.Equals(username) && user.password.Equals(password))
//             {
//                 return user;
//             }
//         }
//         return null;
//     }
//     
//     public IEnumerable<Race> getRaces()
//     {
//         return _raceDb.FindAll();
//     }
//
//     public IEnumerable<Entry> getEntries()
//     {
//         return _entryDb.FindAll();
//     }
//
//     public IEnumerable<RaceDTO> racesDTO()
//     {
//         IEnumerable<Race> races = getRaces();
//         IEnumerable<Entry> entries = getEntries();
//         List<RaceDTO> raceDtos = new List<RaceDTO>();
//         
//         foreach (var r in races)
//         {
//             int noRacers = 0;
//             foreach (var e in entries)
//             {
//                 if (e.raceId.Equals(r.Id))
//                 {
//                     noRacers++;
//                 }
//             }
//             RaceDTO raceDto = new RaceDTO(r.engineCapacity.ToString(), noRacers.ToString());
//             raceDtos.Add(raceDto);
//         }
//
//         return raceDtos;
//     }
//
//     public IEnumerable<Participant> getParticipants()
//     {
//         return _participantDb.FindAll();
//     }
//
//     public IEnumerable<string> getTeams()
//     {
//         IEnumerable<Participant> participants = getParticipants();
//         List<string> teams = new List<string>();
//         foreach (var p in participants)
//         {
//             if (!teams.Contains(p.team))
//             {
//                 teams.Add(p.team);
//             }
//         }
//         return teams;
//     }
//
//     public IEnumerable<ParticipantDTO> participantsDTO()
//     {
//         IEnumerable<Entry> entries = getEntries();
//         IEnumerable<Participant> participants = getParticipants();
//         
//         List<ParticipantDTO> participantDtos = new List<ParticipantDTO>();
//
//         foreach (var p in participants)
//         {
//             ParticipantDTO pDTO = new ParticipantDTO(p.name, p.team, p.engineCapacity.ToString());
//             participantDtos.Add(pDTO);
//         }
//
//         return participantDtos;
//     }
//
//     public IEnumerable<ParticipantDTO> getParticipantDTOforTeam(string team)
//     {
//         IEnumerable<ParticipantDTO> participantDtos = participantsDTO();
//         List<ParticipantDTO> teamParticipants = new List<ParticipantDTO>();
//         foreach (var p in participantDtos)
//         {
//             if (p.team.Equals(team))
//             {
//                 teamParticipants.Add(p);
//             }
//         }
//
//         return teamParticipants;
//     }
//
//     public void addParticipant(string username, string team, string engine)
//     {
//         Participant participant = new Participant(username, team, Int32.Parse(engine));
//         Guid id = Guid.NewGuid();
//         participant.Id = id;
//         _participantDb.save(participant);
//     }
//
//     public IEnumerable<string> engineCapacities()
//     {
//         IEnumerable<Participant> participants = getParticipants();
//         HashSet<string> engine = new HashSet<string>();
//         foreach (var p in participants)
//         {
//             engine.Add(p.engineCapacity.ToString());
//         }
//
//         return engine;
//     }
//
//     public IEnumerable<string> capacities()
//     {
//         IEnumerable<string> engines = engineCapacities();
//         List<string> capacities = new List<string>();
//         foreach (var e in engines)
//         {
//             capacities.Add(e);
//         }
//
//         return capacities;
//     }
//
//     public void createEntry(Participant p)
//     {
//         IEnumerable<Race> races = getRaces();
//
//         foreach (var r in races)
//         {
//             if(p.engineCapacity == r.engineCapacity)
//             {
//                 Entry entry = new Entry(r.Id, p.Id);
//                 Guid id = Guid.NewGuid();
//                 entry.Id = id;
//                 _entryDb.save(entry);
//             }
//         }
//     }
//     
//     public Participant getParticipant(string name, string team, string engine)
//     {
//         IEnumerable<Participant> participants = getParticipants();
//         foreach (var p in participants)
//         {
//             if (p.name.Equals(name) && p.team.Equals(team) && p.engineCapacity.ToString().Equals(engine))
//             {
//                 return p;
//             }
//         }
//
//         return null;
//     }
// }