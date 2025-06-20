using System;
using System.Data.SqlClient;
using System.IO;
using System.Threading;

namespace AplicatieLab4
{
    internal class Program
    {
        private const string ConnectionString = "Server=DESKTOP-3A6SA90;Database=Magazin_Jocuri;Trusted_Connection=True;";
        private const int MaxRetries = 3;
        private const string LogFilePath = "log.txt";
        private static readonly object LogLock = new object();

        public static void Main(string[] args)
        {
            Thread thread1 = new Thread(ExecuteTransaction1);
            Thread thread2 = new Thread(ExecuteTransaction2);

            // pornim ambele thread-uri aproape simultan pentru a forta un deadlock
            thread1.Start();
            Thread.Sleep(100); 
            thread2.Start();

            thread1.Join();
            thread2.Join();

            LogAction("Execution completed.");
            Console.WriteLine("Execution completed.");
        }

        private static void ExecuteTransaction1()
        {
            int retries = 0;
            while (retries < MaxRetries)
            {
                try
                {
                    using (var connection = new SqlConnection(ConnectionString))
                    {
                        connection.Open();
                        using (var command = connection.CreateCommand())
                        {
                            // Set isolation level to SERIALIZABLE to increase lock contention
                            command.CommandText = @"
                                SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
                                BEGIN TRANSACTION;
                                
                                UPDATE Client SET Fonduri = 100;
                                WAITFOR DELAY '00:00:03'; -- Longer delay to ensure deadlock
                                UPDATE Reducere SET Procent = 90;
                                
                                COMMIT TRANSACTION;";
                            command.ExecuteNonQuery();
                        }
                    }
                    LogAction("Transaction 1 completed successfully.");
                    Console.WriteLine("Transaction 1 completed successfully.");
                    break;
                }
                catch (SqlException ex)
                {
                    retries++;
                    LogAction($"Transaction 1 error: {ex.Message}");
                    Console.WriteLine($"Transaction 1 error: {ex.Message}");
                }
            }
        }

        private static void ExecuteTransaction2()
        {
            int retries = 0;
            while (retries < MaxRetries)
            {
                try
                {
                    using (var connection = new SqlConnection(ConnectionString))
                    {
                        connection.Open();
                        using (var command = connection.CreateCommand())
                        {
                            // Set isolation level to SERIALIZABLE to increase lock contention
                            command.CommandText = @"
                                SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
                                BEGIN TRANSACTION;
                                
                                UPDATE Reducere SET Procent = 100;
                                WAITFOR DELAY '00:00:03'; -- Longer delay to ensure deadlock
                                UPDATE Client SET Fonduri = 90;
                                
                                COMMIT TRANSACTION;";
                            command.ExecuteNonQuery();
                        }
                    }
                    LogAction("Transaction 2 completed successfully.");
                    Console.WriteLine("Transaction 2 completed successfully.");
                    break;
                }
                catch (SqlException ex)
                {
                    retries++;
                    LogAction($"Transaction 2 error: {ex.Message}");
                    Console.WriteLine($"Transaction 2 error: {ex.Message}");
                }
            }
        }

        private static void LogAction(string message)
        {
            lock (LogLock)
            {
                try
                {
                    File.AppendAllText(LogFilePath, $"{DateTime.Now}: {message}{Environment.NewLine}");
                }
                catch (Exception ex)
                {
                    Console.WriteLine($"Failed to write to log file. Error: {ex.Message}");
                }
            }
        }
    }
}
