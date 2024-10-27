namespace Lab2
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.adaugareButton = new System.Windows.Forms.Button();
            this.actualizareButton = new System.Windows.Forms.Button();
            this.stergereButton = new System.Windows.Forms.Button();
            this.dataGridView2 = new System.Windows.Forms.DataGridView();
            this.dataGridView1 = new System.Windows.Forms.DataGridView();
            this.panel1 = new System.Windows.Forms.Panel();
            this.panel2 = new System.Windows.Forms.Panel();
            this.exitButton = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView2)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).BeginInit();
            this.SuspendLayout();
            // 
            // adaugareButton
            // 
            this.adaugareButton.Location = new System.Drawing.Point(711, 301);
            this.adaugareButton.Name = "adaugareButton";
            this.adaugareButton.Size = new System.Drawing.Size(88, 25);
            this.adaugareButton.TabIndex = 17;
            this.adaugareButton.Text = "Adauga";
            this.adaugareButton.UseVisualStyleBackColor = true;
            this.adaugareButton.Click += new System.EventHandler(this.adaugareButton_Click);
            // 
            // actualizareButton
            // 
            this.actualizareButton.Location = new System.Drawing.Point(711, 358);
            this.actualizareButton.Name = "actualizareButton";
            this.actualizareButton.Size = new System.Drawing.Size(88, 24);
            this.actualizareButton.TabIndex = 16;
            this.actualizareButton.Text = "Update";
            this.actualizareButton.UseVisualStyleBackColor = true;
            this.actualizareButton.Click += new System.EventHandler(this.actualizareButton_Click);
            // 
            // stergereButton
            // 
            this.stergereButton.Location = new System.Drawing.Point(711, 332);
            this.stergereButton.Name = "stergereButton";
            this.stergereButton.Size = new System.Drawing.Size(88, 23);
            this.stergereButton.TabIndex = 15;
            this.stergereButton.Text = "Sterge";
            this.stergereButton.UseVisualStyleBackColor = true;
            this.stergereButton.Click += new System.EventHandler(this.stergereButton_Click);
            // 
            // dataGridView2
            // 
            this.dataGridView2.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView2.Location = new System.Drawing.Point(9, 301);
            this.dataGridView2.Name = "dataGridView2";
            this.dataGridView2.RowHeadersWidth = 51;
            this.dataGridView2.Size = new System.Drawing.Size(301, 244);
            this.dataGridView2.TabIndex = 14;
            this.dataGridView2.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dataGridView2_CellClick);
            // 
            // dataGridView1
            // 
            this.dataGridView1.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView1.Location = new System.Drawing.Point(12, 12);
            this.dataGridView1.Name = "dataGridView1";
            this.dataGridView1.RowHeadersWidth = 51;
            this.dataGridView1.Size = new System.Drawing.Size(298, 244);
            this.dataGridView1.TabIndex = 13;
            this.dataGridView1.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dataGridView1_CellClick);
            // 
            // panel1
            // 
            this.panel1.Location = new System.Drawing.Point(337, 12);
            this.panel1.Margin = new System.Windows.Forms.Padding(2);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(369, 244);
            this.panel1.TabIndex = 18;
            // 
            // panel2
            // 
            this.panel2.Location = new System.Drawing.Point(351, 301);
            this.panel2.Margin = new System.Windows.Forms.Padding(2);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(355, 244);
            this.panel2.TabIndex = 19;
            // 
            // exitButton
            // 
            this.exitButton.Location = new System.Drawing.Point(786, 536);
            this.exitButton.Name = "exitButton";
            this.exitButton.Size = new System.Drawing.Size(136, 44);
            this.exitButton.TabIndex = 20;
            this.exitButton.Text = "EXIT";
            this.exitButton.UseVisualStyleBackColor = true;
            this.exitButton.Click += new System.EventHandler(this.exitButton_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(934, 592);
            this.Controls.Add(this.exitButton);
            this.Controls.Add(this.adaugareButton);
            this.Controls.Add(this.stergereButton);
            this.Controls.Add(this.panel2);
            this.Controls.Add(this.actualizareButton);
            this.Controls.Add(this.panel1);
            this.Controls.Add(this.dataGridView2);
            this.Controls.Add(this.dataGridView1);
            this.Margin = new System.Windows.Forms.Padding(2);
            this.Name = "Form1";
            this.Text = "Form1";
            this.Load += new System.EventHandler(this.Form1_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView2)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).EndInit();
            this.ResumeLayout(false);
        }

        private System.Windows.Forms.Button exitButton;

        #endregion

        private System.Windows.Forms.Button adaugareButton;
        private System.Windows.Forms.Button actualizareButton;
        private System.Windows.Forms.Button stergereButton;
        private System.Windows.Forms.DataGridView dataGridView2;
        private System.Windows.Forms.DataGridView dataGridView1;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.Panel panel2;
    }
}

