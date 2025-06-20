﻿using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace sgbd1lab
{
    public partial class Form1 : Form
    {
        static string connectionString = @"Server=DESKTOP-3A6SA90\SQLEXPRESS;" +
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
            dataGridView1.SelectionMode = DataGridViewSelectionMode.FullRowSelect;
            dataGridView2.SelectionMode = DataGridViewSelectionMode.FullRowSelect;
            dataGridView2.SelectionChanged += dataGridView2_SelectionChanged;
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
                int id = Convert.ToInt32(selectedRow.Cells["Id_Client"].Value);

                using (SqlCommand command = new SqlCommand("SELECT * FROM Card WHERE idClient = @id", cs))
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
        
        //populate the text fields with the data from dataGridView2
        private void dataGridView2_SelectionChanged(object sender, EventArgs e)
        {
            
            if (dataGridView2.SelectedRows.Count > 0)
            {
                DataGridViewRow selectedRow = dataGridView2.SelectedRows[0];
                textBox1.Text = selectedRow.Cells["numeBanca"].Value.ToString();
                textBox2.Text = selectedRow.Cells["numar"].Value.ToString();
                dateTimePicker1.Value = Convert.ToDateTime(selectedRow.Cells["dataExpirare"].Value);
                textBox3.Text = selectedRow.Cells["cvv"].Value.ToString();
            }
        }
        
        
        
         private void adauga_Click(object sender, EventArgs e)
        {
            
            if (dataGridView1.SelectedRows.Count != 1)
            {
                MessageBox.Show("Trebuie selectată o singură linie în părinte!");
                return;
            }

            string nume = textBox1.Text;
            string numar = textBox2.Text;
            DateTime data = dateTimePicker1.Value;
            string cvv = this.textBox3.Text;
            int idClient = Convert.ToInt32(dataGridView1.SelectedRows[0].Cells["Id_Client"].Value);

            if (string.IsNullOrWhiteSpace(nume) || string.IsNullOrWhiteSpace(numar) || string.IsNullOrWhiteSpace(cvv) || idClient == 0 || string.IsNullOrWhiteSpace(data.ToString("yy/mm")))
            {
                MessageBox.Show("Numele bancii, numarul, data, cvv nu pot fi goale!");
                return;
            }

            try
            {
                using (SqlCommand cmd =
                       new SqlCommand("Insert into Card values (@Nume, @numar, @data, @cvv, @Id_Client)", cs))
                {
                    String errors = "";

                    if (string.IsNullOrWhiteSpace(nume))
                        errors = errors + "Name Error! ";
                    else
                    {
                        cmd.Parameters.AddWithValue("@Nume", nume);
                    }

                    if (numar.Length != 16)
                        errors = errors + "Number Error! (Must be equal to 16) ";
                    else
                    {
                        cmd.Parameters.AddWithValue("@numar", numar);
                    }

                    if (data.Date <= DateTime.Now.Date)
                        errors = errors + "Date Error! (The card must not be expired) ";
                    else
                    {
                        cmd.Parameters.AddWithValue("@data", data);
                    }

                    if (cvv.Length != 3)
                        errors = errors + "CVV Error! (Must be equal to 3) ";
                    else
                    {
                        cmd.Parameters.AddWithValue("@cvv", cvv);
                    }

                    cmd.Parameters.AddWithValue("@Id_Client", idClient);

                    if (errors == "")
                    {
                        cs.Open();
                        int rowsAffected = cmd.ExecuteNonQuery();
                        cs.Close();
                        
                        if (rowsAffected > 0)
                            MessageBox.Show("Înregistrarea a fost adaugata cu succes!");
                        else
                            MessageBox.Show("Nu s-a adaugat nimic.");
                    }
                    else
                    {
                        MessageBox.Show(errors + "\nNo changes were made!");
                    }
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
            Console.WriteLine(dataGridView2.SelectedRows.Count);
            if (dataGridView2.SelectedRows.Count == 0)
            {
                MessageBox.Show("O linie in copil trebuie selectata!");
                return;
            }
            else if (dataGridView2.SelectedRows.Count > 1)
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
            if (dataGridView2.SelectedRows.Count == 0)
            {
                MessageBox.Show("O linie în copil trebuie să fie selectată!");
                return;
            }
            else if (dataGridView2.SelectedRows.Count > 1)
            {
                MessageBox.Show("O singură linie în copil trebuie să fie selectată!");
                return;
            }

            string nume = textBox1.Text;
            string numar = textBox2.Text;
            DateTime data = dateTimePicker1.Value;
            string cvv = this.textBox3.Text;
            int idClient = Convert.ToInt32(dataGridView1.SelectedRows[0].Cells["Id_Client"].Value);
            int idCard = Convert.ToInt32(dataGridView2.SelectedRows[0].Cells["id"].Value);

            if (string.IsNullOrWhiteSpace(nume) || string.IsNullOrWhiteSpace(numar) || string.IsNullOrWhiteSpace(cvv) || idClient == 0 || string.IsNullOrWhiteSpace(data.ToString("yy/mm")))
            {
                MessageBox.Show("Numele bancii, numarul, data, cvv nu pot fi goale!");
                return;
            }

            try
            {
                using (SqlCommand cmd =
                       new SqlCommand(
                           "UPDATE Card SET numeBanca = @Nume, numar = @numar, dataExpirare = @data, cvv = @cvv, idClient = @Id_Client where id = @id_card",
                           cs))
                {
                    String errors = "";

                    if (string.IsNullOrWhiteSpace(nume))
                        errors = errors + "Name Error! ";
                    else
                    {
                        cmd.Parameters.AddWithValue("@Nume", nume);
                    }

                    if (numar.Length != 16)
                        errors = errors + "Number Error! (Must be equal to 16) ";
                    else
                    {
                        cmd.Parameters.AddWithValue("@numar", numar);
                    }

                    if (data.Date <= DateTime.Now.Date)
                        errors = errors + "Date Error! (The card must not be expired) ";
                    else
                    {
                        cmd.Parameters.AddWithValue("@data", data);
                    }

                    if (cvv.Length != 3)
                        errors = errors + "CVV Error! (Must be equal to 3) ";
                    else
                    {
                        cmd.Parameters.AddWithValue("@cvv", cvv);
                    }

                    cmd.Parameters.AddWithValue("@Id_Client", idClient);
                    cmd.Parameters.AddWithValue("@id_card", idCard);

                    if (errors == "")
                    {
                        cs.Open();
                        int rowsAffected = cmd.ExecuteNonQuery();
                        cs.Close();

                        if (rowsAffected > 0)
                            MessageBox.Show("Înregistrarea a fost adaugata cu succes!");
                        else
                            MessageBox.Show("Nu s-a adaugat nimic.");
                    }
                    else
                    {
                        MessageBox.Show(errors + "\nNo changes were made!");
                    }
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