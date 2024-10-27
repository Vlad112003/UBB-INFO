using System;
using System.Collections.Generic;
using Persistence;
using System.Configuration;

namespace Server;

public class StartServer
{
    private static int DEFAULT_PORT = 55555;
    private static String DEFAULT_IP = "127.0.0.1";

    static void Main(string[] args)
    {
        Console.WriteLine("Reading properties from app.config ...");
        int port = DEFAULT_PORT;
        String ip = DEFAULT_IP;
            
        IDictionary<string, string> props = new SortedList<string, string>();
        props.Add("connectionString", getConnectionStringByName("Curse"));
        
        UserDB userRepository = new UserDB(props);
        RaceDB raceRepository = new RaceDB(props);
        EntryDB entryRepository = new EntryDB(props);
        ParticipantDB participantRepository = new ParticipantDB(props);
        
        ServerImplementation serviceImpl = new ServerImplementation(userRepository, raceRepository, entryRepository,participantRepository);
        Console.WriteLine("Server started ... on port "+port+" and ip "+ip+"...");
        SerialServer server = new SerialServer(ip,port, serviceImpl);
        server.Start();
        Console.WriteLine("Server started ...");
        Console.ReadLine();
    }
    
    private static string getConnectionStringByName(string dbconnectionstring)
    {
        // Assume failure.
        string returnValue = null;

        // Look for the name in the connectionStrings section.
        ConnectionStringSettings settings = ConfigurationManager.ConnectionStrings[dbconnectionstring];

        // If found, return the connection string.
        if (settings != null)
            returnValue = settings.ConnectionString;

        return returnValue;
    }
}