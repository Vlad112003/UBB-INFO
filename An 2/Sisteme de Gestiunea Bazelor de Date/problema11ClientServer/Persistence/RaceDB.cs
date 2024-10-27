using System;
using System.Collections;
using System.Collections.Generic;
using System.Data;
using log4net;
using Model;

namespace Persistence;

public class RaceDB : RaceRepoInterface
{
    
    private static readonly ILog log = LogManager.GetLogger(typeof(RaceDB));

    private ConnectionFactory sqliteConnectionFactory = SqliteConnectionFactory.getInstance();

    private IDbConnection connection;
    
    private IDictionary<string, string> props;
    
    public RaceDB(IDictionary<string, string> properties)
    {
        log.Info("Creating RaceDB");
        this.props = properties;
        connection = sqliteConnectionFactory.createConnection(this.props);
        connection.Open();
    }
    
    
    public Race FindOne(Guid id)
    {
        throw new NotImplementedException();
    }

    public IEnumerable<Race> FindAll()
    {
        log.Info("entered the findAll method ");
        IList<Race> races = new List<Race>();
        using (var comm = connection.CreateCommand())
        {
            comm.CommandText = "select id, engineCapacity from races;";
            using (var dataR = comm.ExecuteReader())
            {
                while (dataR.Read())
                {
                    Guid id = Guid.Parse(dataR.GetString(0));
                    int engineCapacity = dataR.GetInt32(1);
                    Race race = new Race(engineCapacity);
                    race.Id = id;
                    races.Add(race);
                }
            }
        }

        return races;
    }

    public Race save(Race e)
    {
        throw new NotImplementedException();
    }

    public Race delete(Guid id)
    {
        throw new NotImplementedException();
    }

    public Race update(Race e)
    {
        throw new NotImplementedException();
    }

    public int size()
    {
        throw new NotImplementedException();
    }
}