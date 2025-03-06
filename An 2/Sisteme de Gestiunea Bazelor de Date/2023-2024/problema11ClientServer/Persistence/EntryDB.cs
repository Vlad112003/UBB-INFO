using System;
using System.Collections.Generic;
using System.Data;
using log4net;
using Model;

namespace Persistence;

public class EntryDB : EntryRepoInterface
{
    
    private static readonly ILog log = LogManager.GetLogger(typeof(EntryDB));

    private ConnectionFactory sqliteConnectionFactory = SqliteConnectionFactory.getInstance();

    private IDbConnection connection;
    
    private IDictionary<string, string> props;
    
    public EntryDB(IDictionary<string, string> properties)
    {
        log.Info("Creating EntryDB");
        this.props = properties;
        connection = sqliteConnectionFactory.createConnection(this.props);
        connection.Open();
    }
    
    
    public Entry FindOne(Guid id)
    {
        throw new NotImplementedException();
    }

    public IEnumerable<Entry> FindAll()
    {
        log.Info("entered the findAll method");
        IList<Entry> entries = new List<Entry>();
        using (var comm = connection.CreateCommand())
        {
            comm.CommandText = "select id, raceId, participantId from entries;";
            using (var dataR = comm.ExecuteReader())
            {
                while (dataR.Read())
                {
                    Guid id = Guid.Parse(dataR.GetString(0));
                    Guid idRace = Guid.Parse(dataR.GetString(1));
                    Guid idParticipant = Guid.Parse(dataR.GetString(2));
                    Entry entry = new Entry(idRace, idParticipant);
                    entry.Id = id;
                    entries.Add(entry);
                }
            }
        }

        return entries;
    }

    public Entry save(Entry e)
    {
        log.Info("entered the save method");
        using (var comm = connection.CreateCommand())
        {
            comm.CommandText = "insert into entries (id, raceId, participantId) values (@id, @raceId, @participantId);";
            var paramId = comm.CreateParameter();
            paramId.ParameterName = "@id";
            paramId.Value = e.Id.ToString();
            comm.Parameters.Add(paramId);
            var paramRaceId = comm.CreateParameter();
            paramRaceId.ParameterName = "@raceId";
            paramRaceId.Value = e.raceId.ToString();
            comm.Parameters.Add(paramRaceId);
            var paramParticipantId = comm.CreateParameter();
            paramParticipantId.ParameterName = "@participantId";
            paramParticipantId.Value = e.participantId.ToString();
            comm.Parameters.Add(paramParticipantId);
            var result = comm.ExecuteNonQuery();
            if (result == 0)
            {
                log.Info("save method failed");
                return e;
            }
            else
            {
                log.Info("save method successful");
                return null;
            }
        }
    }

    public Entry delete(Guid id)
    {
        throw new NotImplementedException();
    }

    public Entry update(Entry e)
    {
        throw new NotImplementedException();
    }

    public int size()
    {
        throw new NotImplementedException();
    }
}