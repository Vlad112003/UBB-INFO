using System;
using System.Windows.Forms;
using Model;

namespace Client;

public partial class LoginWindow : Form
{
    private Controller controller;
    public LoginWindow(Controller ctrl)
    {
        InitializeComponent();
        this.controller = ctrl;
    }
    
    private void LoginWindow_Load(object sender, EventArgs e)
    {
        Console.WriteLine("Login window loaded");
    }


    private void loginButton_Click(object sender, EventArgs e)
    {
        String username = usernameBox.Text;
        String password = passwordBox.Text;
        try
        {
            controller.login(username, password);
            MainWindow mainWindow = new MainWindow(controller,new User(username, password));
            mainWindow.Show();
            this.Hide();
        }
        catch (Exception ex)
        {
            MessageBox.Show("Login failed" + ex.Message);
            return;
        }
    }

    private void exitButton_Click(object sender, EventArgs e)
    {
        this.Close();
        Application.Exit();
    }
}