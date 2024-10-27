using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace lab
{
    public partial class Form1 : Form
    {
        static string connectionString = @"Server=DESKTOP-R52BL7R\SQLEXPRESS;" +
                                  "Database=Magazin_Jocuri;" +
                                  "Integrated Security=true;" +
                                  "TrustServerCertificate=true";
        SqlConnection cs = new SqlConnection(connectionString);
        SqlDataAdapter da = new SqlDataAdapter();

        BindingSource bsP = new BindingSource();
        BindingSource bsC = new BindingSource();

        DataSet dsP = new DataSet();
        DataSet dsC = new DataSet();

        public Form1()
        {
            InitializeComponent();
            dataGridView1.SelectionChanged += dataGridView1_SelectionChanged;
        }
        private void connectbtn_Click(object sender, EventArgs e)
        {
            da.SelectCommand = new SqlCommand("SELECT * FROM Client", cs);
            dsP.Clear();
            da.Fill(dsP);

            da.SelectCommand = new SqlCommand("SELECT * FROM Card", cs);
            dsC.Clear();
            da.Fill(dsC);

            dataGridView1.DataSource = dsP.Tables[0];
            bsP.DataSource = dsP.Tables[0];
            bsP.MoveLast();

            dataGridView2.DataSource = dsC.Tables[0];
            bsC.DataSource = dsC.Tables[0];
            bsC.MoveLast();
        }
        private void dataGridView1_SelectionChanged(object sender, EventArgs e)
        {
            if (dataGridView1.SelectedRows.Count > 0)
            {
                DataGridViewRow selectedRow = dataGridView1.SelectedRows[0];
                int id = Convert.ToInt32(selectedRow.Cells["id"].Value);

                using (SqlCommand command = new SqlCommand("SELECT * FROM Client WHERE id = @id", cs))
                {
                    command.Parameters.AddWithValue("@id", id);
                    dsC.Clear();
                    da.SelectCommand = command;
                    da.Fill(dsC);
                    dataGridView2.DataSource = dsC.Tables[0];
                    bsC.DataSource = dsC.Tables[0];
                }
            }
        }

        private void DataGridViewChildClick(object sender, DataGridViewCellEventArgs e)
        {
            return;
        }

        private void adauga_Click(object sender, EventArgs e)
        {
            if (dataGridView1.SelectedRows.Count != 1)
            {
                MessageBox.Show("Trebuie selectată o singură linie în părinte!");
                return;
            }

            int Id_Adresa = Convert.ToInt32(dataGridView1.SelectedRows[0].Cells["id"].Value);
            if (string.IsNullOrWhiteSpace(textNume.Text) || string.IsNullOrWhiteSpace(textEmail.Text))
            {
                MessageBox.Show("Numele și adresa de email nu pot fi goale!");
                return;
            }

            try
            {
                using (SqlCommand cmd = new SqlCommand("INSERT INTO Client (Nume, Email, Id_Adresa) VALUES (@Nume, @Email, @Id_Adresa)", cs))
                {
                    cmd.Parameters.AddWithValue("@Nume", textNume.Text);
                    cmd.Parameters.AddWithValue("@Email", textEmail.Text);
                    cmd.Parameters.AddWithValue("@Id_Adresa", Id_Adresa);

                    cs.Open();
                    cmd.ExecuteNonQuery();
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Eroare la adăugarea înregistrării fiu: " + ex.Message);
            }
            finally
            {
                cs.Close();
            }

            try
            {
                dsC.Clear();
                da.Fill(dsC);
                dataGridView2.DataSource = dsC.Tables[0];
            }
            catch (Exception ex)
            {
                MessageBox.Show("Eroare la reîncărcarea datelor în DataGridView-ul fiu: " + ex.Message);
            }
        }



        private void sterge_Click(object sender, EventArgs e)
        {
            if (dataGridView2.SelectedCells.Count == 0)
            {
                MessageBox.Show("O linie in copil trebuie selectata!");
                return;
            }
            else if (dataGridView2.SelectedCells.Count > 1)
            {
                MessageBox.Show("O singura linie in copil trebuie selectata!");
                return;
            }

            da.DeleteCommand = new SqlCommand("Delete from Card where id = @id;", cs);

            da.DeleteCommand.Parameters.Add("id", SqlDbType.Int).Value = dsC.Tables[0].Rows[dataGridView2.CurrentCell.RowIndex][0];

            cs.Open();
            da.DeleteCommand.ExecuteNonQuery();
            cs.Close();
            dsC.Clear();
            da.Fill(dsC);
        }

        private void update_Click(object sender, EventArgs e)
        {
            if (dataGridView2.SelectedCells.Count == 0)
            {
                MessageBox.Show("O linie în copil trebuie să fie selectată!");
                return;
            }
            else if (dataGridView2.SelectedCells.Count > 1)
            {
                MessageBox.Show("O singură linie în copil trebuie să fie selectată!");
                return;
            }

            string nume = textNume.Text;
            string email = textEmail.Text;

            if (string.IsNullOrWhiteSpace(nume) || string.IsNullOrWhiteSpace(email))
            {
                MessageBox.Show("Numele și adresa de email nu pot fi goale!");
                return;
            }

            int idClient = Convert.ToInt32(dsC.Tables[0].Rows[dataGridView2.CurrentCell.RowIndex][0]);

            try
            {
                using (SqlCommand cmd = new SqlCommand("UPDATE Client SET Nume = @Nume, AdresaEmail = @Email WHERE Id_Client = @Id_Client", cs))
                {
                    cmd.Parameters.AddWithValue("@Nume", nume);
                    cmd.Parameters.AddWithValue("@Email", email);
                    cmd.Parameters.AddWithValue("@Id_Client", idClient);

                    cs.Open();
                    int rowsAffected = cmd.ExecuteNonQuery();
                    cs.Close();

                    if (rowsAffected > 0)
                        MessageBox.Show("Înregistrarea a fost actualizată cu succes!");
                    else
                        MessageBox.Show("Nu s-au găsit înregistrări de actualizat.");
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("A intervenit o eroare la actualizarea înregistrării: " + ex.Message);
            }

            try
            {
                dsC.Clear();
                da.Fill(dsC);
                dataGridView2.DataSource = dsC.Tables[0];
            }
            catch (Exception ex)
            {
                MessageBox.Show("A intervenit o eroare la reîncărcarea datelor în DataGridView-ul fiu: " + ex.Message);
            }
        }

    }
}



