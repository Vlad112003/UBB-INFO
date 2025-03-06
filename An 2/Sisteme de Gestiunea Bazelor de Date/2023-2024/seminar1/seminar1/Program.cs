using Microsoft.Data.SqlClient;
namespace serinar1;

class Program
{
    static void Main(string[] args)
    {
        Console.Title = "Consolitza";
        Console.BackgroundColor = ConsoleColor.Black;
        Console.ForegroundColor = ConsoleColor.Green;
        Console.Clear();
        Console.WriteLine("Hello, World!");
        Console.ReadKey();
        try
        {
            string connectionString = @"Server=DESKTOP-R52BL7R\SQLEXPRESS;" +
                                      "Database=SGBDIR;" +
                                      "Integrated Security=true;" +
                                      "TrustServerCertificate=true";
            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                Console.WriteLine("Starea conexiunii inainte de apelul metodei Open(): {0}",
                    connection.State);
                connection.Open();
                Console.WriteLine("Starea conexiunii dupa apelul metodei Open(): {0}",
                    connection.State);
                SqlCommand insertCommand = new SqlCommand("Insert into Cadouri (descriere, " +
                                                          "posesor, pret) values (@desc1, @posesor1, @pret1), " +
                                                          "(@desc2, @posesor2, @pret2)" +
                                                          ", (@desc3, @posesor3, @pret3) ", connection);
                insertCommand.Parameters.AddWithValue("@desc1", "telefon");
                insertCommand.Parameters.AddWithValue("@posesor1", "George");
                insertCommand.Parameters.AddWithValue("@pret1", 7.5);
                insertCommand.Parameters.AddWithValue("@desc2", "laptop");
                insertCommand.Parameters.AddWithValue("@posesor2", "Marius");
                insertCommand.Parameters.AddWithValue("@pret2", 12100);
                insertCommand.Parameters.AddWithValue("@desc3", "carte");
                insertCommand.Parameters.AddWithValue("@posesor3", "Andreea");
                insertCommand.Parameters.AddWithValue("@pret3", 50);
                int insertRowCount = insertCommand.ExecuteNonQuery();
                Console.WriteLine("Numarul de inregistrari adaugate: {0}", insertRowCount);
                //citirea si afisarea datelor in consola
                SqlCommand selectCommand = new SqlCommand("Select descriere, posesor, pret from Cadouri", connection);
                SqlDataReader reader = selectCommand.ExecuteReader();
                if (reader.HasRows)
                {
                    Console.WriteLine("Continutul tabelului Cadouri:");
                        while (reader.Read())
                        {
                            Console.WriteLine("Descriere: {0}, Posesor: {1}, Pret: {2}",
                                reader["descriere"], reader["posesor"], reader["pret"]);
                        }
                    
                }
                else
                {
                    Console.WriteLine("Nu sunt inregistrari in tabela Cadouri");
                }
                reader.Close();
                //actualizarea datelor
                SqlCommand updateCommand = new SqlCommand("Update Cadouri set pret = @pretnou " +
                                                          "where descriere=@descriere", connection);
                updateCommand.Parameters.AddWithValue("@pretnou", 8);
                updateCommand.Parameters.AddWithValue("@descriere", "telefon");
                int updateRowCount = updateCommand.ExecuteNonQuery();
                Console.WriteLine("Numarul de inregistrari actualizate: {0}", updateRowCount);
                //stergerea datelor
                SqlCommand deleteCommand = new SqlCommand("delete from cadouri where descriere=@descriere", connection);
                deleteCommand.Parameters.AddWithValue("@descriere", "laptop");
                int deleteRowCount = deleteCommand.ExecuteNonQuery();
                Console.WriteLine("Numarul de inregistrari sterse: {0}", deleteRowCount);
                //afisarea datelor dupa actualizare si stergere
                reader = selectCommand.ExecuteReader();
                if (reader.HasRows)
                {
                    Console.WriteLine("Continutul tabelului Cadouri dupa actualizare si stergere:");
                    while (reader.Read())
                    {
                        Console.WriteLine("Descriere: {0}, Posesor: {1}, Pret: {2}",
                            reader["descriere"], reader["posesor"], reader["pret"]);
                    }
                }
                else
                {
                    Console.WriteLine("Nu sunt inregistrari in tabela Cadouri");
                }
                reader.Close();
                

            }
            
        }
        catch (Exception ex)
        {
            Console.ForegroundColor = ConsoleColor.Black;
            Console.WriteLine("Mesajul erorii este: {0}", ex.Message);
        }
    }
}