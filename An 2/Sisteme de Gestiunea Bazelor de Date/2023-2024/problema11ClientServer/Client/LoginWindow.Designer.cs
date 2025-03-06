using System.ComponentModel;

namespace Client;

partial class LoginWindow
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
        this.components = new System.ComponentModel.Container();
        this.usernameLabel = new System.Windows.Forms.Label();
        this.passwordLabel = new System.Windows.Forms.Label();
        this.loginButton = new System.Windows.Forms.Button();
        this.usernameBox = new System.Windows.Forms.TextBox();
        this.timer1 = new System.Windows.Forms.Timer(this.components);
        this.passwordBox = new System.Windows.Forms.TextBox();
        this.exitButton = new System.Windows.Forms.Button();
        this.SuspendLayout();
        // 
        // usernameLabel
        // 
        this.usernameLabel.Location = new System.Drawing.Point(218, 194);
        this.usernameLabel.Name = "usernameLabel";
        this.usernameLabel.Size = new System.Drawing.Size(66, 23);
        this.usernameLabel.TabIndex = 0;
        this.usernameLabel.Text = "username";
        // 
        // passwordLabel
        // 
        this.passwordLabel.Location = new System.Drawing.Point(218, 220);
        this.passwordLabel.Name = "passwordLabel";
        this.passwordLabel.Size = new System.Drawing.Size(66, 20);
        this.passwordLabel.TabIndex = 1;
        this.passwordLabel.Text = "password";
        // 
        // loginButton
        // 
        this.loginButton.Location = new System.Drawing.Point(303, 274);
        this.loginButton.Name = "loginButton";
        this.loginButton.Size = new System.Drawing.Size(202, 60);
        this.loginButton.TabIndex = 2;
        this.loginButton.Text = "log in";
        this.loginButton.UseVisualStyleBackColor = true;
        this.loginButton.Click += new System.EventHandler(this.loginButton_Click);
        // 
        // usernameBox
        // 
        this.usernameBox.Location = new System.Drawing.Point(290, 191);
        this.usernameBox.Name = "usernameBox";
        this.usernameBox.Size = new System.Drawing.Size(227, 20);
        this.usernameBox.TabIndex = 3;
        // 
        // passwordBox
        // 
        this.passwordBox.Location = new System.Drawing.Point(290, 217);
        this.passwordBox.Name = "passwordBox";
        this.passwordBox.PasswordChar = '*';
        this.passwordBox.Size = new System.Drawing.Size(227, 20);
        this.passwordBox.TabIndex = 4;
        // 
        // exitButton
        // 
        this.exitButton.Location = new System.Drawing.Point(620, 376);
        this.exitButton.Name = "exitButton";
        this.exitButton.Size = new System.Drawing.Size(154, 57);
        this.exitButton.TabIndex = 5;
        this.exitButton.Text = "exit";
        this.exitButton.UseVisualStyleBackColor = true;
        this.exitButton.Click += new System.EventHandler(this.exitButton_Click);
        // 
        // LoginWindow
        // 
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(800, 450);
        this.Controls.Add(this.exitButton);
        this.Controls.Add(this.passwordBox);
        this.Controls.Add(this.usernameBox);
        this.Controls.Add(this.loginButton);
        this.Controls.Add(this.passwordLabel);
        this.Controls.Add(this.usernameLabel);
        this.Name = "LoginWindow";
        this.Text = "LoginWindow";
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.Button exitButton;

    private System.Windows.Forms.TextBox usernameBox;
    private System.Windows.Forms.Timer timer1;
    private System.Windows.Forms.TextBox passwordBox;

    private System.Windows.Forms.Label usernameLabel;
    private System.Windows.Forms.Label passwordLabel;
    private System.Windows.Forms.Button loginButton;

    #endregion
}