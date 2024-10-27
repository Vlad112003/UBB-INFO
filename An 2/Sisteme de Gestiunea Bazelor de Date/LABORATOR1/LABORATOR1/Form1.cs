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

namespace LABORATOR1
{
    public partial class Form1 : Form
    {
        private SqlConnection cs = new SqlConnection();
        private SqlDataAdapter da = new SqlDataAdapter();
        private DataSet ds = new DataSet();
        private BindingSource bs = new BindingSource();

        private bool Connect()
        {
            try
            {
                string connectionString = @"Server=DESKTOP-R52BL7R\SQLEXPRESS;" +
                                          "Database=Magazin_Jocuri;" +
                                          "Integrated Security=true;" +
                                          "TrustServerCertificate=true";
                cs = new SqlConnection(connectionString);
                cs.Open();
                return true;
            }
            catch (Exception e)
            {
                MessageBox.Show(e.Message);
                return false;
            }
        }
        
        public Form1()
        {
            try
            {
                if (Connect())
                {
                    InitializeComponent();
                }
                else 
                {
                    MessageBox.Show("Nu s-a putut realiza conexiunea la baza de date");
                }
                
            }
            catch (Exception e)
            {
                MessageBox.Show(e.Message);
            }
        }
    }
}