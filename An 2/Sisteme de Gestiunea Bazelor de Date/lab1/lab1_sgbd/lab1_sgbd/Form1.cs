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

namespace lab1_sgbd
{
    public partial class Form1 : Form
    {
        SqlConnection cs = new SqlConnection("Data Source=DESKTOP-C2K0K50\\SQLEXPRESS;Initial Catalog=SGBD;Integrated Security=True");
        SqlDataAdapter da = new SqlDataAdapter();

        BindingSource bsP = new BindingSource();
        BindingSource bsC = new BindingSource();

        DataSet dsP = new DataSet();
        DataSet dsC = new DataSet();

        public Form1()
        {
            InitializeComponent();
        }
        private void connectbtn_Click(object sender, EventArgs e)
        {
            da.SelectCommand = new SqlCommand("SELECT * FROM Adresa", cs);
            dsP.Clear();
            da.Fill(dsP);

            dataGridView1.DataSource = dsP.Tables[0];
            bsP.DataSource = dsP.Tables[0];
            bsP.MoveLast();
        }

        private void DataGridViewParentClick(object sender, DataGridViewCellEventArgs e)
        {
            if (dataGridView1.Rows[e.RowIndex].Cells[e.ColumnIndex].Value == null)
                return;


            string Id_Adresa = dataGridView1.Rows[e.RowIndex].Cells[0].Value.ToString();

            da.SelectCommand = new SqlCommand("SELECT * from Adresa " +
                    "where Adresa.Id_Adresa = " + Id_Adresa + "; ", cs);
            dsC.Clear();
            da.Fill(dsC);
            dataGridView2.DataSource = dsC.Tables[0];
            bsC.DataSource = dsC.Tables[0];
        }
        private void DataGridViewChildClick(object sender, DataGridViewCellEventArgs e)
        {

        }

        private void adauga_Click(object sender, EventArgs e)
        {
            if (dataGridView1.SelectedCells.Count == 0)
            {
                MessageBox.Show("O linie in parinte trebuie slectata!");
                return;
            }
            else if (dataGridView1.SelectedCells.Count > 1)
            {
                MessageBox.Show("O singura linie in parinte trebuie slectata!");
                return;
            }

            try
            {
                float.Parse(textNume.Text);
            }
            catch (Exception exception)
            {
                MessageBox.Show("Numele trebuie sa fie un cuvant!", exception.Message);
                return;
            }

            try
            {
                float.Parse(textEmail.Text);
            }
            catch (Exception exception)
            {
                MessageBox.Show("Email trebuie sa fie cuvant!", exception.Message);
                return;
            }

            da.InsertCommand = new
                SqlCommand("Insert into Client(Nume,AdresaEmail,Id_Adresa)" +
                "values(@Nume,@AdresaEmail,@Id_Adresa)", cs);
            da.InsertCommand.Parameters.Add("@id",
                SqlDbType.Int).Value = dsP.Tables[dataGridView1.CurrentCell.ColumnIndex].Rows[dataGridView1.CurrentCell.RowIndex][0];

            da.InsertCommand.Parameters.Add("@Nume",
                SqlDbType.VarChar).Value = textNume.Text;

            da.InsertCommand.Parameters.Add("@Descriere",
                SqlDbType.VarChar).Value = textEmail.Text;

            cs.Open();
            da.InsertCommand.ExecuteNonQuery();
            cs.Close();
            dsC.Clear();
            da.Fill(dsC);
        }

    }
}
