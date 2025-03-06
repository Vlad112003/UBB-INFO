using System;
using System.Data.Common;
using System.Data.SqlClient;
using System.Threading;

namespace ConsoleApplication1
{
    internal class Program
    {
        public static void Main(string[] args)
        {
            const string connectionString = @"Server=DESKTOP-C2K0K50\SQLEXPRESS;Database=Magazin_Jocuri;
                                                     Integrated Security=true;TrustServerCertificate=true;";
            
            var retryCount = 0;
            var success = false;

            while (!success && retryCount < 3)
            {
                Console.WriteLine("Retry count: " + retryCount);
                
                var thread1 = new Thread(() =>
                {
                    Console.WriteLine("Thread1 is running!");

                    using var connection = new SqlConnection(connectionString);
                    connection.Open();

                    using (var setTransactionLevelUncommited = connection.CreateCommand())
                    {
                        setTransactionLevelUncommited.CommandText = "SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED";
                        setTransactionLevelUncommited.ExecuteNonQuery();
                    }
                    
                    using (var transaction = connection.BeginTransaction())
                    {
                        try
                        {
                            using (var command = connection.CreateCommand())
                            {
                                command.Transaction = transaction;

                                // Update statement 1
                                command.CommandText = "UPDATE Client SET fonduri = 90 WHERE Id_Client = 1";
                                command.ExecuteNonQuery();

                                // Delay for 10 seconds
                                Thread.Sleep(10000);

                                // Update statement 2
                                command.CommandText = "UPDATE Client SET fonduri = 190 WHERE Id_Client = 2";
                                command.ExecuteNonQuery();
                            }

                            // Commit the transaction
                            transaction.Commit();
                            Console.WriteLine("Transaction committed successfully.");
                            success = true;
                        }
                        catch (SqlException ex)
                        {
                            if (ex.Number == 1205)
                            {
                                Console.WriteLine("Deadlock occurred. Retrying...");

                                transaction.Rollback();
                                Console.WriteLine("Transaction rolled back.");
                                retryCount++;
                            }
                            else
                            {
                                Console.WriteLine("Error occurred: " + ex.Message);
                                transaction.Rollback();
                                Console.WriteLine("Transaction rolled back.");
                            }
                        }
                    }
                });
                var thread2 = new Thread(() =>
                {
                    Console.WriteLine("Thread2 is running!");
                    var connection = new SqlConnection(connectionString);
                    connection.Open();

                    using (var setTransactionLevelUncommited = connection.CreateCommand())
                    {
                        setTransactionLevelUncommited.CommandText = "SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED";
                        setTransactionLevelUncommited.ExecuteNonQuery();
                    }

                    using var transaction = connection.BeginTransaction();
                    try
                    {
                        using (var command = connection.CreateCommand())
                        {
                            command.Transaction = transaction;

                            command.CommandText = "UPDATE Client SET fonduri = 90 WHERE Id_Client = 2";
                            command.ExecuteNonQuery();

                            Thread.Sleep(10000);

                            command.CommandText = "UPDATE Client SET fonduri = 190 WHERE Id_Client = 1";
                            command.ExecuteNonQuery();
                        }

                        transaction.Commit();
                        Console.WriteLine("Transaction committed successfully.");
                        success = true;
                    }
                    catch (SqlException ex)
                    {
                        if (ex.Number == 1205)
                        {
                            Console.WriteLine("Deadlock occurred. Retrying...");

                            transaction.Rollback();
                            Console.WriteLine("Transaction rolled back.");
                            retryCount++;
                        }
                        else
                        {
                            Console.WriteLine("Error occurred: " + ex.Message);
                            transaction.Rollback();
                            Console.WriteLine("Transaction rolled back.");
                        }
                    }
                });
                
                thread1.Start();
                thread2.Start();
                thread1.Join();
                thread2.Join();
            }
            Console.WriteLine("Retry count: " + retryCount);
            Console.WriteLine(retryCount >= 3
                ? "Exceeded maximum retry attempts. Aborting."
                : "All transactions completed.");
        }
    }
}