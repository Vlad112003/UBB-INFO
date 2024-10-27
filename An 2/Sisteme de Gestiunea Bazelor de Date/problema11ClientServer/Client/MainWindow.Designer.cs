using System.ComponentModel;

namespace Client;

partial class MainWindow
{
    /// <summary>
    /// Required designer variable.
    /// </summary>
    private IContainer components = null;

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
        this.dataGridView1 = new System.Windows.Forms.DataGridView();
        this.dataGridView2 = new System.Windows.Forms.DataGridView();
        this.label1 = new System.Windows.Forms.Label();
        this.searchButton = new System.Windows.Forms.Button();
        this.teamCombo = new System.Windows.Forms.ComboBox();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.usernameBox = new System.Windows.Forms.TextBox();
        this.teamBox = new System.Windows.Forms.TextBox();
        this.engineCombo = new System.Windows.Forms.ComboBox();
        this.addButton = new System.Windows.Forms.Button();
        this.refreshButton = new System.Windows.Forms.Button();
        this.logoutButton = new System.Windows.Forms.Button();
        ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).BeginInit();
        ((System.ComponentModel.ISupportInitialize)(this.dataGridView2)).BeginInit();
        this.SuspendLayout();
        // 
        // dataGridView1
        // 
        this.dataGridView1.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
        this.dataGridView1.Location = new System.Drawing.Point(43, 12);
        this.dataGridView1.Name = "dataGridView1";
        this.dataGridView1.Size = new System.Drawing.Size(380, 185);
        this.dataGridView1.TabIndex = 0;
        // 
        // dataGridView2
        // 
        this.dataGridView2.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
        this.dataGridView2.Location = new System.Drawing.Point(43, 271);
        this.dataGridView2.Name = "dataGridView2";
        this.dataGridView2.Size = new System.Drawing.Size(380, 167);
        this.dataGridView2.TabIndex = 1;
        // 
        // label1
        // 
        this.label1.Location = new System.Drawing.Point(43, 223);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(71, 20);
        this.label1.TabIndex = 2;
        this.label1.Text = "team";
        // 
        // searchButton
        // 
        this.searchButton.Location = new System.Drawing.Point(339, 219);
        this.searchButton.Name = "searchButton";
        this.searchButton.Size = new System.Drawing.Size(84, 27);
        this.searchButton.TabIndex = 4;
        this.searchButton.Text = "search";
        this.searchButton.UseVisualStyleBackColor = true;
        this.searchButton.Click += new System.EventHandler(this.searchButton_Click);
        // 
        // teamCombo
        // 
        this.teamCombo.FormattingEnabled = true;
        this.teamCombo.Location = new System.Drawing.Point(105, 223);
        this.teamCombo.Name = "teamCombo";
        this.teamCombo.Size = new System.Drawing.Size(228, 21);
        this.teamCombo.TabIndex = 5;
        // 
        // label2
        // 
        this.label2.Location = new System.Drawing.Point(487, 176);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(64, 23);
        this.label2.TabIndex = 6;
        this.label2.Text = "username";
        this.label2.Click += new System.EventHandler(this.label2_Click);
        // 
        // label3
        // 
        this.label3.Location = new System.Drawing.Point(506, 199);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(45, 27);
        this.label3.TabIndex = 7;
        this.label3.Text = "team";
        this.label3.Click += new System.EventHandler(this.label3_Click);
        // 
        // label4
        // 
        this.label4.Location = new System.Drawing.Point(459, 226);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(92, 30);
        this.label4.TabIndex = 8;
        this.label4.Text = "engineCapacity";
        // 
        // usernameBox
        // 
        this.usernameBox.Location = new System.Drawing.Point(566, 173);
        this.usernameBox.Name = "usernameBox";
        this.usernameBox.Size = new System.Drawing.Size(101, 20);
        this.usernameBox.TabIndex = 9;
        // 
        // teamBox
        // 
        this.teamBox.Location = new System.Drawing.Point(566, 199);
        this.teamBox.Name = "teamBox";
        this.teamBox.Size = new System.Drawing.Size(101, 20);
        this.teamBox.TabIndex = 10;
        // 
        // engineCombo
        // 
        this.engineCombo.FormattingEnabled = true;
        this.engineCombo.Location = new System.Drawing.Point(566, 226);
        this.engineCombo.Name = "engineCombo";
        this.engineCombo.Size = new System.Drawing.Size(101, 21);
        this.engineCombo.TabIndex = 11;
        // 
        // addButton
        // 
        this.addButton.Location = new System.Drawing.Point(515, 271);
        this.addButton.Name = "addButton";
        this.addButton.Size = new System.Drawing.Size(121, 26);
        this.addButton.TabIndex = 12;
        this.addButton.Text = "add";
        this.addButton.UseVisualStyleBackColor = true;
        this.addButton.Click += new System.EventHandler(this.addButton_Click);
        // 
        // refreshButton
        // 
        this.refreshButton.Location = new System.Drawing.Point(515, 312);
        this.refreshButton.Name = "refreshButton";
        this.refreshButton.Size = new System.Drawing.Size(121, 26);
        this.refreshButton.TabIndex = 13;
        this.refreshButton.Text = "refresh";
        this.refreshButton.UseVisualStyleBackColor = true;
        this.refreshButton.Click += new System.EventHandler(this.refreshButton_Click);
        // 
        // logoutButton
        // 
        this.logoutButton.Location = new System.Drawing.Point(636, 395);
        this.logoutButton.Name = "logoutButton";
        this.logoutButton.Size = new System.Drawing.Size(137, 42);
        this.logoutButton.TabIndex = 14;
        this.logoutButton.Text = "log out";
        this.logoutButton.UseVisualStyleBackColor = true;
        this.logoutButton.Click += new System.EventHandler(this.logoutButton_Click);
        // 
        // MainWindow
        // 
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(800, 450);
        this.Controls.Add(this.logoutButton);
        this.Controls.Add(this.refreshButton);
        this.Controls.Add(this.addButton);
        this.Controls.Add(this.engineCombo);
        this.Controls.Add(this.teamBox);
        this.Controls.Add(this.usernameBox);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.teamCombo);
        this.Controls.Add(this.searchButton);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.dataGridView2);
        this.Controls.Add(this.dataGridView1);
        this.Name = "MainWindow";
        this.Text = "MainWindow";
        ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).EndInit();
        ((System.ComponentModel.ISupportInitialize)(this.dataGridView2)).EndInit();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.Button logoutButton;

    private System.Windows.Forms.Button addButton;
    private System.Windows.Forms.Button refreshButton;

    private System.Windows.Forms.Label label3;
    private System.Windows.Forms.Label label4;
    private System.Windows.Forms.TextBox usernameBox;
    private System.Windows.Forms.TextBox teamBox;
    private System.Windows.Forms.ComboBox engineCombo;

    private System.Windows.Forms.Label label2;

    private System.Windows.Forms.ComboBox teamCombo;

    private System.Windows.Forms.Label label1;
    private System.Windows.Forms.Button searchButton;

    private System.Windows.Forms.DataGridView dataGridView2;

    private System.Windows.Forms.DataGridView dataGridView1;

    #endregion
}