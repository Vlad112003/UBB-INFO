using System;
using System.Collections.Generic;
using System.Data;
using log4net;
using Model;

namespace Persistence;

public class ParticipantDB : ParticipantRepoInterface
{
    
    private static readonly ILog log = LogManager.GetLogger(typeof(ParticipantDB));

    private ConnectionFactory sqliteConnectionFactory = SqliteConnectionFactory.getInstance();

    private IDbConnection connection;
    
    private IDictionary<string, string> props;
    
    public ParticipantDB(IDictionary<string, string> properties)
    {
        log.Info("Creating ParticipantDB with properties: " + properties);
        this.props = properties;
        connection = sqliteConnectionFactory.createConnection(this.props);
        connection.Open();
    }
    
    
    public Participant FindOne(Guid id)
    {
        throw new NotImplementedException();
    }

    public IEnumerable<Participant> FindAll()
    {
        log.Info("entered the findAll method ");
        IList<Participant> participants = new List<Participant>();
        using (var comm = connection.CreateCommand())
        {
            comm.CommandText = "select id, name, team, engineCapacity from participants;";
            using (var dataR = comm.ExecuteReader())
            {
                while (dataR.Read())
                {
                    Guid id = Guid.Parse(dataR.GetString(0));
                    string name = dataR.GetString(1);
                    string team = dataR.GetString(2);
                    int engineCapacity = dataR.GetInt32(3);
                    Participant participant = new Participant(name, team, engineCapacity);
                    participant.Id = id;
                    participants.Add(participant);
                }
            }
        }

        return participants;
    }

    public Participant save(Participant e)
    {
        log.Info("entered the save method ");
        using (var comm = connection.CreateCommand())
        {
            comm.CommandText = "insert into participants (id, name, team, engineCapacity) values (@id, @name, @team, @engineCapacity);";
            IDbDataParameter paramId = comm.CreateParameter();
            paramId.ParameterName = "@id";
            paramId.Value = e.Id.ToString();
            comm.Parameters.Add(paramId);
            IDbDataParameter paramName = comm.CreateParameter();
            paramName.ParameterName = "@name";
            paramName.Value = e.name;
            comm.Parameters.Add(paramName);
            IDbDataParameter paramTeam = comm.CreateParameter();
            paramTeam.ParameterName = "@team";
            paramTeam.Value = e.team;
            comm.Parameters.Add(paramTeam);
            IDbDataParameter paramEngineCapacity = comm.CreateParameter();
            paramEngineCapacity.ParameterName = "@engineCapacity";
            paramEngineCapacity.Value = e.engineCapacity;
            comm.Parameters.Add(paramEngineCapacity);
            comm.ExecuteNonQuery();
        }
        return e; 
    }

    public Participant delete(Guid id)
    {
        throw new NotImplementedException();
    }

    public Participant update(Participant e)
    {
        throw new NotImplementedException();
    }

    public int size()
    {
        throw new NotImplementedException();
    }
}