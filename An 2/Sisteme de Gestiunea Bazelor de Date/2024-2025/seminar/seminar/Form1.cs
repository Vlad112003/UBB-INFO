using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Windows.Forms;
using System.Data.SqlClient;

namespace seminar
{
    public partial class Form1 : Form
    {
        private BindingList<Bakery> bakeries = new BindingList<Bakery>();
        private BindingList<Muffin> muffins = new BindingList<Muffin>();
        private BindingSource bakerySource = new BindingSource();
        private BindingSource muffinSource = new BindingSource();

        private ComboBox comboBoxBakeries;
        private DataGridView dataGridViewMuffins;

        private TextBox textBoxNume;
        private TextBox textBoxDescriere;
        private TextBox textBoxPret;
        private Button buttonAddMuffin;
        private Button buttonModifyMuffin;
        private Button buttonDeleteMuffin;
        
        private string connStr = "Server=localhost;Database=Problema1;Integrated Security=true;";

        public Form1()
        {
            InitializeComponent();
            InitializeCustomComponents();
            LoadBakeriesFromDb();
            SetupDataBindings();
        }

        private void InitializeCustomComponents()
        {
            // combobox for bakeries
            comboBoxBakeries = new ComboBox
            {
                Location = new Point(20, 20),
                Width = 200,
                DropDownStyle = ComboBoxStyle.DropDownList
            };
            comboBoxBakeries.SelectedIndexChanged += ComboBoxBakeries_SelectedIndexChanged;
            Controls.Add(comboBoxBakeries);

            // datagridview for muffins
            dataGridViewMuffins = new DataGridView
            {
                Location = new Point(20, 60),
                Width = 600,
                Height = 250,
                AutoGenerateColumns = false,
                AllowUserToAddRows = false,
                AllowUserToDeleteRows = false,
                SelectionMode = DataGridViewSelectionMode.FullRowSelect,
                MultiSelect = false
            };
            dataGridViewMuffins.Columns.Add(new DataGridViewTextBoxColumn
            {
                DataPropertyName = "Id",
                HeaderText = "Id",
                ReadOnly = true
            });
            dataGridViewMuffins.Columns.Add(new DataGridViewTextBoxColumn
            {
                DataPropertyName = "Name",
                HeaderText = "Nume"
            });
            dataGridViewMuffins.Columns.Add(new DataGridViewTextBoxColumn
            {
                DataPropertyName = "Description",
                HeaderText = "Descriere"
            });
            dataGridViewMuffins.Columns.Add(new DataGridViewTextBoxColumn
            {
                DataPropertyName = "Price",
                HeaderText = "Pret"
            });
            dataGridViewMuffins.SelectionChanged += DataGridViewMuffins_SelectionChanged;
            Controls.Add(dataGridViewMuffins);

            // textbox pentru nume briosa
            textBoxNume = new TextBox
            {
                Location = new Point(250, 20),
                Width = 120,
                Text = "Nume briosa"
            };
            textBoxNume.Enter += (s, e) => { if (textBoxNume.Text == "Nume briosa") textBoxNume.Text = ""; };
            textBoxNume.Leave += (s, e) => { if (string.IsNullOrWhiteSpace(textBoxNume.Text)) textBoxNume.Text = "Nume briosa"; };
            Controls.Add(textBoxNume);

            // textbox pentru descriere briosa
            textBoxDescriere = new TextBox
            {
                Location = new Point(380, 20),
                Width = 150,
                Text = "Descriere"
            };
            textBoxDescriere.Enter += (s, e) => { if (textBoxDescriere.Text == "Descriere") textBoxDescriere.Text = ""; };
            textBoxDescriere.Leave += (s, e) => { if (string.IsNullOrWhiteSpace(textBoxDescriere.Text)) textBoxDescriere.Text = "Descriere"; };
            Controls.Add(textBoxDescriere);

            // textbox pentru pret briosa
            textBoxPret = new TextBox
            {
                Location = new Point(540, 20),
                Width = 60,
                Text = "Pret"
            };
            textBoxPret.Enter += (s, e) => { if (textBoxPret.Text == "Pret") textBoxPret.Text = ""; };
            textBoxPret.Leave += (s, e) => { if (string.IsNullOrWhiteSpace(textBoxPret.Text)) textBoxPret.Text = "Pret"; };
            Controls.Add(textBoxPret);

            // buton pentru adaugare briosa noua
            buttonAddMuffin = new Button
            {
                Text = "Adauga",
                Location = new Point(610, 18),
                Width = 80
            };
            buttonAddMuffin.Click += ButtonAddMuffin_Click;
            Controls.Add(buttonAddMuffin);

            // buton pentru modificare briosa
            buttonModifyMuffin = new Button
            {
                Text = "Modifica",
                Location = new Point(700, 18),
                Width = 80
            };
            buttonModifyMuffin.Click += ButtonModifyMuffin_Click;
            Controls.Add(buttonModifyMuffin);

            // buton pentru stergere briosa
            buttonDeleteMuffin = new Button
            {
                Text = "Sterge",
                Location = new Point(630, 330),
                Width = 100
            };
            buttonDeleteMuffin.Click += ButtonDeleteMuffin_Click;
            Controls.Add(buttonDeleteMuffin);
        }

        private void DataGridViewMuffins_SelectionChanged(object sender, EventArgs e)
        {
            if (dataGridViewMuffins.SelectedRows.Count > 0 && dataGridViewMuffins.SelectedRows[0].DataBoundItem != null)
            {
                var selectedMuffin = (Muffin)dataGridViewMuffins.SelectedRows[0].DataBoundItem;

                // populeaza textbox-urile cu datele briosei selectate
                textBoxNume.Text = selectedMuffin.Name;
                textBoxDescriere.Text = selectedMuffin.Description;
                textBoxPret.Text = selectedMuffin.Price.ToString();
            }
        }

        private void LoadBakeriesFromDb()
        {
            bakeries.Clear();
            using (var conn = new SqlConnection(connStr))
            {
                conn.Open();
                var cmd = new SqlCommand("SELECT cod_cofetarie, nume_cofetarie FROM Cofetarii", conn);
                using (var reader = cmd.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        bakeries.Add(new Bakery
                        {
                            Id = reader.GetInt32(0),
                            Name = reader.GetString(1)
                        });
                    }
                }
            }
        }

        private void LoadMuffinsForBakery(int bakeryId)
        {
            muffins.Clear();
            using (var conn = new SqlConnection(connStr))
            {
                conn.Open();
                var cmd = new SqlCommand("SELECT cod_briosa, nume_briosa, descriere, pret FROM Briose WHERE cod_cofetarie = @id", conn);
                cmd.Parameters.AddWithValue("@id", bakeryId);
                using (var reader = cmd.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        muffins.Add(new Muffin
                        {
                            Id = reader.GetInt32(0),
                            Name = reader.GetString(1),
                            Description = reader.IsDBNull(2) ? "" : reader.GetString(2),
                            Price = reader.IsDBNull(3) ? 0 : Convert.ToDecimal(reader.GetValue(3)),
                            BakeryId = bakeryId
                        });
                    }
                }
            }
        }

        private void SetupDataBindings()
        {
            bakerySource.DataSource = bakeries;
            comboBoxBakeries.DataSource = bakerySource;
            comboBoxBakeries.DisplayMember = "Name";
            comboBoxBakeries.ValueMember = "Id";
            if (bakeries.Count > 0)
                comboBoxBakeries.SelectedIndex = 0;
        }

        private void ComboBoxBakeries_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (comboBoxBakeries.SelectedValue == null)
                return;
            int selectedBakeryId = (int)comboBoxBakeries.SelectedValue;
            LoadMuffinsForBakery(selectedBakeryId);
            muffinSource.DataSource = muffins;
            dataGridViewMuffins.DataSource = muffinSource;
        }

        private void ButtonSave_Click(object sender, EventArgs e)
        {
            if (comboBoxBakeries.SelectedValue == null)
                return;
            int selectedBakeryId = (int)comboBoxBakeries.SelectedValue;
            var gridMuffins = (BindingList<Muffin>)muffinSource.DataSource;

            using (var conn = new SqlConnection(connStr))
            {
                conn.Open();

                // get current muffin ids from db
                var dbIds = new List<int>();
                var cmdIds = new SqlCommand("SELECT cod_briosa FROM Briose WHERE cod_cofetarie = @id", conn);
                cmdIds.Parameters.AddWithValue("@id", selectedBakeryId);
                using (var reader = cmdIds.ExecuteReader())
                {
                    while (reader.Read())
                        dbIds.Add(reader.GetInt32(0));
                }

                // prepare lists for insert/update/delete
                var gridIds = gridMuffins.Where(m => m.Id != 0).Select(m => m.Id).ToList();
                var toDelete = dbIds.Except(gridIds).ToList();

                // delete removed muffins
                foreach (var id in toDelete)
                {
                    var cmdDel = new SqlCommand("DELETE FROM Briose WHERE cod_briosa = @id", conn);
                    cmdDel.Parameters.AddWithValue("@id", id);
                    cmdDel.ExecuteNonQuery();
                }

                // insert/update muffins
                foreach (var m in gridMuffins)
                {
                    if (m.Id == 0)
                    {
                        // insert
                        var cmdIns = new SqlCommand(
                            "INSERT INTO Briose (nume_briosa, descriere, pret, cod_cofetarie) VALUES (@numeBriosa, @descriere, @pret, @codCofetarie)", conn);
                        cmdIns.Parameters.AddWithValue("@numeBriosa", m.Name);
                        cmdIns.Parameters.AddWithValue("@descriere", m.Description ?? "");
                        cmdIns.Parameters.AddWithValue("@pret", m.Price);
                        cmdIns.Parameters.AddWithValue("@codCofetarie", selectedBakeryId);
                        cmdIns.ExecuteNonQuery();
                    }
                    else
                    {
                        // update
                        var cmdUpd = new SqlCommand(
                            "UPDATE Briose SET nume_briosa=@numeBriosa, descriere=@descriere, pret=@pret WHERE cod_briosa=@codBriosa", conn);
                        cmdUpd.Parameters.AddWithValue("@numeBriosa", m.Name);
                        cmdUpd.Parameters.AddWithValue("@descriere", m.Description ?? "");
                        cmdUpd.Parameters.AddWithValue("@pret", m.Price);
                        cmdUpd.Parameters.AddWithValue("@codBriosa", m.Id);
                        cmdUpd.ExecuteNonQuery();
                    }
                }
            }

            MessageBox.Show("Changes saved!");
            ComboBoxBakeries_SelectedIndexChanged(null, null); // refresh grid
        }

        private void ButtonAddMuffin_Click(object sender, EventArgs e)
        {
            // validare simpla
            if (textBoxNume.Text == "Nume briosa" ||
                textBoxPret.Text == "Pret" ||
                string.IsNullOrWhiteSpace(textBoxNume.Text) ||
                string.IsNullOrWhiteSpace(textBoxPret.Text))
            {
                MessageBox.Show("Completeaza numele si pretul!");
                return;
            }

            if (!decimal.TryParse(textBoxPret.Text, out decimal pret))
            {
                MessageBox.Show("Pret invalid!");
                return;
            }

            if (comboBoxBakeries.SelectedValue == null)
            {
                MessageBox.Show("Selecteaza o cofetarie!");
                return;
            }

            int selectedBakeryId = (int)comboBoxBakeries.SelectedValue;

            var muffin = new Muffin
            {
                Id = 0, // va fi inserat acum
                Name = textBoxNume.Text,
                Description = textBoxDescriere.Text == "Descriere" ? "" : textBoxDescriere.Text,
                Price = pret,
                BakeryId = selectedBakeryId
            };

            // adaugare in baza de date direct
            using (var conn = new SqlConnection(connStr))
            {
                conn.Open();
                var cmdIns = new SqlCommand(
                    "INSERT INTO Briose (nume_briosa, descriere, pret, cod_cofetarie) VALUES (@numeBriosa, @descriere, @pret, @codCofetarie); SELECT SCOPE_IDENTITY();", conn);
                cmdIns.Parameters.AddWithValue("@numeBriosa", muffin.Name);
                cmdIns.Parameters.AddWithValue("@descriere", muffin.Description ?? "");
                cmdIns.Parameters.AddWithValue("@pret", muffin.Price);
                cmdIns.Parameters.AddWithValue("@codCofetarie", selectedBakeryId);

                // obtine id-ul nou creat
                var newId = Convert.ToInt32(cmdIns.ExecuteScalar());
                muffin.Id = newId;
            }

            // adauga la lista locala
            muffins.Add(muffin);
            muffinSource.ResetBindings(false);

            // goleste campurile
            textBoxNume.Text = "Nume briosa";
            textBoxDescriere.Text = "Descriere";
            textBoxPret.Text = "Pret";

            MessageBox.Show("Briosa a fost adaugata cu succes!");
        }

        private void ButtonModifyMuffin_Click(object sender, EventArgs e)
        {
            if (dataGridViewMuffins.SelectedRows.Count == 0 || dataGridViewMuffins.SelectedRows[0].DataBoundItem == null)
            {
                MessageBox.Show("Selecteaza o briosa pentru a o modifica!");
                return;
            }

            // validare simpla
            if (textBoxNume.Text == "Nume briosa" ||
                textBoxPret.Text == "Pret" ||
                string.IsNullOrWhiteSpace(textBoxNume.Text) ||
                string.IsNullOrWhiteSpace(textBoxPret.Text))
            {
                MessageBox.Show("Completeaza numele si pretul!");
                return;
            }

            if (!decimal.TryParse(textBoxPret.Text, out decimal pret))
            {
                MessageBox.Show("Pret invalid!");
                return;
            }

            var selectedMuffin = (Muffin)dataGridViewMuffins.SelectedRows[0].DataBoundItem;

            // actualizeaza proprietatile
            selectedMuffin.Name = textBoxNume.Text;
            selectedMuffin.Description = textBoxDescriere.Text == "Descriere" ? "" : textBoxDescriere.Text;
            selectedMuffin.Price = pret;

            // actualizeaza in baza de date direct
            using (var conn = new SqlConnection(connStr))
            {
                conn.Open();
                var cmdUpd = new SqlCommand(
                    "UPDATE Briose SET nume_briosa=@numeBriosa, descriere=@descriere, pret=@pret WHERE cod_briosa=@codBriosa", conn);
                cmdUpd.Parameters.AddWithValue("@numeBriosa", selectedMuffin.Name);
                cmdUpd.Parameters.AddWithValue("@descriere", selectedMuffin.Description ?? "");
                cmdUpd.Parameters.AddWithValue("@pret", selectedMuffin.Price);
                cmdUpd.Parameters.AddWithValue("@codBriosa", selectedMuffin.Id);
                cmdUpd.ExecuteNonQuery();
            }

            // actualizeaza lista
            muffinSource.ResetBindings(false);

            // goleste campurile
            textBoxNume.Text = "Nume briosa";
            textBoxDescriere.Text = "Descriere";
            textBoxPret.Text = "Pret";

            MessageBox.Show("Briosa a fost modificata cu succes!");
        }

        private void ButtonDeleteMuffin_Click(object sender, EventArgs e)
        {
            if (dataGridViewMuffins.SelectedRows.Count == 0 || dataGridViewMuffins.SelectedRows[0].DataBoundItem == null)
            {
                MessageBox.Show("Selecteaza o briosa pentru a o sterge!");
                return;
            }

            var selectedMuffin = (Muffin)dataGridViewMuffins.SelectedRows[0].DataBoundItem;

            // confirmare stergere
            var result = MessageBox.Show($"Esti sigur ca vrei sa stergi briosa '{selectedMuffin.Name}'?",
                "Confirmare stergere", MessageBoxButtons.YesNo, MessageBoxIcon.Question);

            if (result == DialogResult.No)
                return;

            // sterge din baza de date
            using (var conn = new SqlConnection(connStr))
            {
                conn.Open();
                var cmdDel = new SqlCommand("DELETE FROM Briose WHERE cod_briosa = @id", conn);
                cmdDel.Parameters.AddWithValue("@id", selectedMuffin.Id);
                cmdDel.ExecuteNonQuery();
            }

            // sterge din lista locala
            muffins.Remove(selectedMuffin);
            muffinSource.ResetBindings(false);

            // goleste campurile
            textBoxNume.Text = "Nume briosa";
            textBoxDescriere.Text = "Descriere";
            textBoxPret.Text = "Pret";

            MessageBox.Show("Briosa a fost stearsa cu succes!");
        }
    }

    // models for db
    public class Bakery
    {
        public int Id { get; set; }
        public string Name { get; set; }
    }

    public class Muffin
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }
        public decimal Price { get; set; }
        public int BakeryId { get; set; }
    }
}