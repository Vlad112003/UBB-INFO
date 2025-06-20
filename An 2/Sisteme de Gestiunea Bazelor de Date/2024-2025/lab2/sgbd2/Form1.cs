using System;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Windows.Forms;
using System.Configuration;

namespace sgbd2
{
    public partial class Form1 : Form
    {
        SqlDataAdapter da1 = new SqlDataAdapter();
        DataSet ds1 = new DataSet();

        SqlDataAdapter da2 = new SqlDataAdapter();
        DataSet ds2 = new DataSet();

        static string con = ConfigurationManager.ConnectionStrings["cn"].ConnectionString;
        SqlConnection cs = new SqlConnection(con);

        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            try
            {
                string query = ConfigurationManager.AppSettings["ParentSelectAll"];
                da1.SelectCommand = new SqlCommand(query, cs);
                ds1.Clear();
                da1.Fill(ds1);
                dataGridView1.DataSource = ds1.Tables[0];
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                cs.Close();
            }

            string columnsNames = ConfigurationManager.AppSettings["ParentColumnsNames"];
            string[] columns = columnsNames.Split(',');

            //generate textboxes
            for (int i = 0; i < columns.Length; i++)
            {
                TextBox tb = new TextBox();
                tb.Name = columns[i];
                tb.Location = new Point(10, 10 + i * 30);
                tb.Size = new Size(200, 20);
                panel1.Controls.Add(tb);
            }
            //show textboxes
            for (int i = 0; i < columns.Length; i++)
            {
                panel1.Controls[i].Visible = true;
            }

            //generate labels
            for (int i = 0; i < columns.Length; i++)
            {
                Label lb = new Label();
                lb.Text = columns[i];
                lb.Location = new Point(220, 10 + i * 30);
                lb.Size = new Size(200, 20);
                panel1.Controls.Add(lb);
            }

        }
        private void dataGridView1_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            try
            {
                if (dataGridView1.Rows[e.RowIndex].Cells[e.ColumnIndex].Value != null)
                {
                    dataGridView1.CurrentRow.Selected = true;
                    //populate textboxes
                    for (int i = 0; i < dataGridView1.Columns.Count; i++)
                    {
                        panel1.Controls[i].Text = dataGridView1.Rows[e.RowIndex].Cells[i].Value.ToString();
                    }

                    string query = ConfigurationManager.AppSettings["ChildSelectAll"];
                    query = query.Replace("@ParentId", dataGridView1.Rows[e.RowIndex].Cells[0].Value.ToString());

                    da2.SelectCommand = new SqlCommand(query, cs);
                    ds2.Clear();
                    da2.Fill(ds2);
                    dataGridView2.DataSource = ds2.Tables[0];

                    //generate textboxes
                    string columnsNames2 = ConfigurationManager.AppSettings["ChildColumsNames"];
                    string[] columns2 = columnsNames2.Split(',');
                    for (int i = 0; i < columns2.Length; i++)
                    {
                        TextBox tb = new TextBox();
                        tb.Name = columns2[i];
                        tb.Location = new Point(10, 10 + i * 30);
                        tb.Size = new Size(200, 20);
                        panel2.Controls.Add(tb);
                    }
                    //show textboxes
                    for (int i = 0; i < columns2.Length; i++)
                    {
                        panel2.Controls[i].Visible = true;
                    }

                    //generate labels
                    for (int i = 0; i < columns2.Length; i++)
                    {
                        Label lb = new Label();
                        lb.Text = columns2[i];
                        lb.Location = new Point(220, 10 + i * 30);
                        lb.Size = new Size(200, 20);
                        panel2.Controls.Add(lb);
                    }
                }

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                cs.Close();
            }
        }

        private void dataGridView2_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            try
            {
                if (dataGridView2.Rows[e.RowIndex].Cells[e.ColumnIndex].Value != null)
                {
                    dataGridView2.CurrentRow.Selected = true;
                    //populate textboxes
                    for (int i = 0; i < dataGridView2.Columns.Count; i++)
                    {
                        panel2.Controls[i].Text = dataGridView2.Rows[e.RowIndex].Cells[i].Value.ToString();
                    }
                }

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                cs.Close();
            }

        }

        private void stergereButton_Click(object sender, EventArgs e)
        {
            try
            {
                string quey = ConfigurationManager.AppSettings["Delete"];
                quey = quey.Replace("@Id", dataGridView2.Rows[dataGridView2.CurrentCell.RowIndex].Cells[0].Value.ToString());
                cs.Open();
                SqlCommand cmd = new SqlCommand(quey, cs);
                cmd.ExecuteNonQuery();
                cs.Close();
                MessageBox.Show("Sters cu succes!", "Succes", MessageBoxButtons.OK, MessageBoxIcon.Information);
                //refresh
                quey = ConfigurationManager.AppSettings["ChildSelectAll"];
                quey = quey.Replace("@ParentId", dataGridView1.Rows[dataGridView1.CurrentCell.RowIndex].Cells[0].Value.ToString());
                da2.SelectCommand = new SqlCommand(quey, cs);
                ds2.Clear();
                da2.Fill(ds2);
                dataGridView2.DataSource = ds2.Tables[0];
            }catch(Exception ex)
            {
                MessageBox.Show(ex.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                cs.Close();
            }
        }

        private void actualizareButton_Click(object sender, EventArgs e)
        {
            //get selected row
            string query = ConfigurationManager.AppSettings["Update"];

            if (panel1.Controls[0].Text == "")
            {
                MessageBox.Show("Selectati un client", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

            if (panel2.Controls[0].Text == "")
            {
                MessageBox.Show("Selectati un Card/Server", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

           
            string updateVars = ConfigurationManager.AppSettings["UpdateVar"];
            string[] vars = updateVars.Split(',');
            for (int i = 1; i < vars.Length; i++)
            { 
                query = query.Replace(vars[i-1], panel2.Controls[i].Text);
            }
            
            query = query.Replace(vars[vars.Length -1 ], dataGridView2.Rows[dataGridView2.CurrentCell.RowIndex].Cells[0].Value.ToString());

            try
            {
                cs.Open();
                SqlCommand cmd = new SqlCommand(query, cs);
                cmd.ExecuteNonQuery();
                cs.Close();
                MessageBox.Show("Actualizat cu succes!", "Succes", MessageBoxButtons.OK, MessageBoxIcon.Information);
                //refresh
                query = ConfigurationManager.AppSettings["ChildSelectAll"];
                query = query.Replace("@ParentId", dataGridView1.Rows[dataGridView1.CurrentCell.RowIndex].Cells[0].Value.ToString());
                da2.SelectCommand = new SqlCommand(query, cs);
                ds2.Clear();
                da2.Fill(ds2);
                dataGridView2.DataSource = ds2.Tables[0];
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                cs.Close();
            }
        }

        private void adaugareButton_Click(object sender, EventArgs e)
        {
            string query = ConfigurationManager.AppSettings["Insert"];
            string insertVars = ConfigurationManager.AppSettings["InsertVar"];
            string[] vars = insertVars.Split(',');
           
            for (int i = 1; i < vars.Length; i++)
            {
                query = query.Replace(vars[i-1], panel2.Controls[i].Text);
            }
            query = query.Replace(vars[vars.Length - 1], dataGridView1.Rows[dataGridView1.CurrentCell.RowIndex].Cells[0].Value.ToString());

            try
            {
                cs.Open();
                SqlCommand cmd = new SqlCommand(query, cs);
                cmd.ExecuteNonQuery();
                cs.Close();
                MessageBox.Show("Adaugat cu succes!", "Succes", MessageBoxButtons.OK, MessageBoxIcon.Information);
                //refresh
                query = ConfigurationManager.AppSettings["ChildSelectAll"];
                query = query.Replace("@ParentId", dataGridView1.Rows[dataGridView1.CurrentCell.RowIndex].Cells[0].Value.ToString());
                da2.SelectCommand = new SqlCommand(query, cs);
                ds2.Clear();
                da2.Fill(ds2);
                dataGridView2.DataSource = ds2.Tables[0];
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                cs.Close(); 
            }
        }


        private void exitButton_Click(object sender, EventArgs e)
        {
            Close();
        }
    }
}
