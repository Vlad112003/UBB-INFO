using System;
using System.Collections.Generic;
using System.Data;
using log4net;
using log4net.Repository.Hierarchy;
using Model;

namespace Persistence;

public class UserDB : UserRepoInterface
{
    private static readonly ILog log = LogManager.GetLogger(typeof(UserDB));

    private ConnectionFactory sqliteConnectionFactory = SqliteConnectionFactory.getInstance();

    private IDbConnection connection;
    
    private IDictionary<string, string> props;
    
    public UserDB(IDictionary<string, string> properties)
    {
        log.Info("Creating UserDB");
        this.props = properties;
        connection = sqliteConnectionFactory.createConnection(this.props);
        connection.Open();
    }


    public User FindOne(Guid id)
    {
        throw new NotImplementedException();
    }

    public IEnumerable<User> FindAll()
    {
        log.Info("entered the findAll method ");
        IList<User> users = new List<User>();
        using (var comm = connection.CreateCommand())
        {
            comm.CommandText = "select id, username, password from users;";

            using (var dataR = comm.ExecuteReader())
            {
                while (dataR.Read())
                {
                    Guid id = Guid.Parse(dataR.GetString(0));
                    string username = dataR.GetString(1);
                    string password = dataR.GetString(2);

                    User user = new User(username, password);
                    user.Id = id;
                    users.Add(user);
                }
            }
        }
        return users;
    }

    public User save(User e)
    {
        throw new NotImplementedException();
    }

    public User delete(Guid id)
    {
        throw new NotImplementedException();
    }

    public User update(User e)
    {
        throw new NotImplementedException();
    }

    public int size()
    {
        throw new NotImplementedException();
    }

    public User FindByUsername(string username)
    {
        log.Info("entered the findByUsername method ");
        using (var comm = connection.CreateCommand())
        {
            comm.CommandText = "select id, username, password from users where username=@username;";
            var paramUsername = comm.CreateParameter();
            paramUsername.ParameterName = "@username";
            paramUsername.Value = username;
            comm.Parameters.Add(paramUsername);

            using (var dataR = comm.ExecuteReader())
            {
                if (dataR.Read())
                {
                    Guid id = Guid.Parse(dataR.GetString(0));
                    string password = dataR.GetString(2);

                    User user = new User(username, password);
                    user.Id = id;
                    return user;
                }
            }
        }
        return null;
    }
}