using System;
using System.Data;
using System.Collections.Generic;
using System.Data.SQLite;
using Model;

namespace Persistence;

public class SqliteConnectionFactory : ConnectionFactory
{
    public override IDbConnection createConnection(IDictionary<string, string> props)
    {
        //Mono Sqlite Connection

        //String connectionString = "URI=file:/Users/grigo/didactic/MPP/ExempleCurs/2017/database/tasks.db,Version=3";
        String connectionString = props["connectionString"];
        Console.WriteLine("SQLite ---Se deschide o conexiune la  ... {0}", connectionString);
        return new SQLiteConnection(connectionString);

        // Windows SQLite Connection, fisierul .db ar trebuie sa fie in directorul debug/bin
        // String connectionString = "Data Source=identifier.sqlite;Version=3";
        // return new SQLiteConnection(connectionString);
    }
}