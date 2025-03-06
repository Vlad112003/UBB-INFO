using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Security.Cryptography;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace sgbd1lab
{
    static class Programvechi
    {
        public static SqlConnection connection;
        
        public static void OpenConnection()
        {
            string connectionString = @"Server=DESKTOP-R52BL7R\SQLEXPRESS;" +
                                      "Database=Magazin_Jocuri;" +
                                      "Integrated Security=true;" +
                                      "TrustServerCertificate=true";
            connection = new SqlConnection(connectionString);
            connection.Open();
        }
        
        public static void addClient(string nume, string email, int fonduri)
        {
            SqlCommand insertClientCommand =
                new SqlCommand("Insert into Client values (@nume, @email, @fonduri)", connection);
            insertClientCommand.Parameters.AddWithValue("@nume", nume);
            insertClientCommand.Parameters.AddWithValue("@email", email);
            insertClientCommand.Parameters.AddWithValue("@fonduri", fonduri);
            int insertRowCount = insertClientCommand.ExecuteNonQuery();
            Console.WriteLine("Numarul de inregistrari adaugate: {0}", insertRowCount);
        }
        
        public static void addCard(int id, string nume, string numar, DateTime dataExpirare, int cvv, int id_client)
        {
            SqlCommand insertCardCommand =
                new SqlCommand("Insert into Card values (@id, @nume, @numar, @dataExpirare, @cvv, @id_client)", connection);
            insertCardCommand.Parameters.AddWithValue("@id", id);
            insertCardCommand.Parameters.AddWithValue("@nume", nume);
            insertCardCommand.Parameters.AddWithValue("@numar", numar);
            insertCardCommand.Parameters.AddWithValue("@dataExpirare", dataExpirare);
            insertCardCommand.Parameters.AddWithValue("@cvv", cvv);
            insertCardCommand.Parameters.AddWithValue("@id_client", id_client);
            int insertRowCount = insertCardCommand.ExecuteNonQuery();
            Console.WriteLine("Numarul de inregistrari adaugate: {0}", insertRowCount);
        }
        
        public static void deleteClient(int id)
        {
            SqlCommand deleteClientCommand =
                new SqlCommand("Delete from Client where Id_Client = @id", connection);
            deleteClientCommand.Parameters.AddWithValue("@id", id);
            int deleteRowCount = deleteClientCommand.ExecuteNonQuery();
            Console.WriteLine("Numarul de inregistrari sterse: {0}", deleteRowCount);
            SqlCommand count = new SqlCommand("select count (*) from Client", connection);
            int count_ = (int) count.ExecuteScalar();
            SqlCommand dbccCheckIdentCommand = new SqlCommand("DBCC CHECKIDENT ('Client', RESEED, @count)", connection);
            dbccCheckIdentCommand.Parameters.AddWithValue("@count", count_);
            dbccCheckIdentCommand.ExecuteNonQuery();
        }

        public static void deleteCard(int id)
        {
            SqlCommand deleteCardCommand =
                new SqlCommand("Delete from Card where id = @id", connection);
            deleteCardCommand.Parameters.AddWithValue("@id", id);
            int deleteRowCount = deleteCardCommand.ExecuteNonQuery();
            Console.WriteLine("Numarul de inregistrari sterse: {0}", deleteRowCount);
        }

        public static void updateClient(int id, string nume, string email, int fonduri)
        {
            SqlCommand updateClientCommand =
                new SqlCommand("Update Client set Nume = @nume, Email = @email, Fonduri = @fonduri where Id_Client = @id", connection);
            updateClientCommand.Parameters.AddWithValue("@id", id);
            updateClientCommand.Parameters.AddWithValue("@nume", nume);
            updateClientCommand.Parameters.AddWithValue("@email", email);
            updateClientCommand.Parameters.AddWithValue("@fonduri", fonduri);
            int updateRowCount = updateClientCommand.ExecuteNonQuery();
            Console.WriteLine("Numarul de inregistrari modificate: {0}", updateRowCount);
        }
        
        public static void updateCard(int id, string nume, string numar, DateTime dataExpirare, int cvv, int id_client)
        {
            SqlCommand updateCardCommand =
                new SqlCommand("Update Card set Nume = @nume, Numar = @numar, DataExpirare = @dataExpirare, CVV = @cvv, Id_Client = @id_client where Id = @id", connection);
            updateCardCommand.Parameters.AddWithValue("@id", id);
            updateCardCommand.Parameters.AddWithValue("@nume", nume);
            updateCardCommand.Parameters.AddWithValue("@numar", numar);
            updateCardCommand.Parameters.AddWithValue("@dataExpirare", dataExpirare);
            updateCardCommand.Parameters.AddWithValue("@cvv", cvv);
            updateCardCommand.Parameters.AddWithValue("@id_client", id_client);
            int updateRowCount = updateCardCommand.ExecuteNonQuery();
            Console.WriteLine("Numarul de inregistrari modificate: {0}", updateRowCount);
        }
        
        public static HashSet<string> selectAllClients()
        {
            HashSet<string> clients = new HashSet<string>();
            SqlCommand selectAllClientsCommand = new SqlCommand("Select * from Client", connection);
            SqlDataReader reader = selectAllClientsCommand.ExecuteReader();
            while (reader.Read())
            {
                string str = "";
                // clients.Add(reader["Id_Client"].ToString());
                str += reader["Id_Client"].ToString() + " ";
                // clients.Add(reader["Nume"].ToString());
                str += reader["Nume"].ToString() + " ";
                // clients.Add(reader["email"].ToString());
                str += reader["email"].ToString() + " ";
                // clients.Add(reader["fonduri"].ToString());
                str += reader["fonduri"].ToString();
                clients.Add(str);
            }
            reader.Close();
            return clients;
        }
        
        public static HashSet<string> selectAllCards()
        {
            HashSet<string> cards = new HashSet<string>();
            SqlCommand selectAllCardsCommand = new SqlCommand("Select * from Card", connection);
            SqlDataReader reader = selectAllCardsCommand.ExecuteReader();
            while (reader.Read())
            {
                string str = "";
                // cards.Add(reader["id"].ToString());
                str += reader["id"].ToString() + " ";
                // cards.Add(reader["numeBanca"].ToString());
                str += reader["numeBanca"].ToString() + " ";
                // cards.Add(reader["numar"].ToString());
                str += reader["numar"].ToString() + " ";
                
                // cards.Add(reader["dataExpirare"].ToString());
                DateTime dataExpirare = (DateTime) reader["dataExpirare"];
                string data = dataExpirare.ToString("dd/MM/yyyy");
                str += data + " ";
                
                // cards.Add(reader["cvv"].ToString());
                str += reader["cvv"].ToString() + " ";
                // cards.Add(reader["idClient"].ToString());
                str += reader["idClient"].ToString();
                cards.Add(str);
            }
            reader.Close();
            return cards;
        }

        public static HashSet<String> selectCardsforClient(int idClient)
        {
            SqlCommand selectCardsforClientCommand = new SqlCommand("Select * from Card where idClient = @idClient", connection);
            selectCardsforClientCommand.Parameters.AddWithValue("@idClient", idClient);
            SqlDataReader reader = selectCardsforClientCommand.ExecuteReader();
            HashSet<string> carduri = new HashSet<string>();
            
            while (reader.Read())
            {
                string str = "";
                // cards.Add(reader["id"].ToString());
                str += reader["id"].ToString() + " ";
                // cards.Add(reader["numeBanca"].ToString());
                str += reader["numeBanca"].ToString() + " ";
                // cards.Add(reader["numar"].ToString());
                str += reader["numar"].ToString() + " ";
                
                // cards.Add(reader["dataExpirare"].ToString());
                DateTime dataExpirare = (DateTime) reader["dataExpirare"];
                string data = dataExpirare.ToString("dd/MM/yyyy");
                str += data + " ";
                
                // cards.Add(reader["cvv"].ToString());
                str += reader["cvv"].ToString() + " ";
                // cards.Add(reader["idClient"].ToString());
                str += reader["idClient"].ToString();
                carduri.Add(str);
            }
            reader.Close();
            return carduri;
        }
        
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Mainvechi()
        {
            OpenConnection();
            
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new Form1());
            

        }
    }
}