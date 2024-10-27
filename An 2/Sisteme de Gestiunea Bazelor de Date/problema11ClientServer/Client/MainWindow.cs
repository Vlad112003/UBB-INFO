using System;
using System.Collections;
using System.Collections.Generic;
using System.Windows.Forms;
using Model;
using Networking.DTOs;

namespace Client;

public partial class MainWindow : Form
{
    private Controller controller;
    private User user;
    private IList<RaceDTO> races;
    private IList<ParticipantDTO> participants;
    public MainWindow(Controller controller, User user)
    {
        InitializeComponent();
        InitializeDataBindings();
        this.controller = controller;
        this.user = user;
        
    }

    private void searchButton_Click(object sender, EventArgs e)
    {
        string team = teamCombo.Text;
        if (team.Equals(""))
        {
            MessageBox.Show("Please select a team!");
            return;
        }

        IEnumerable ps = controller.getParticipantDTOforTeam(team);
        dataGridView2.DataSource = ps;
    }

    private void dataGridView2_CellContentClick(object sender, DataGridViewCellEventArgs e){
        
         }

    private void InitializeDataBindings()
    {
        // dataGridView1.DataSource = controller.racesDTO();
        // teamCombo.DataSource = controller.teams();
        List<string> engines = new List<string>{ "500", "750", "1000" };
        engineCombo.DataSource = engines;
    }

    private void label2_Click(object sender, EventArgs e)
    {
        Console.WriteLine("Label 2 clicked");
    }

    private void refreshButton_Click(object sender, EventArgs e)
    {
        throw new System.NotImplementedException();
    }

    private void addButton_Click(object sender, EventArgs e)
    {
        string username = this.usernameBox.Text;
        string team = this.teamBox.Text;
        string engine = engineCombo.Text;
        if (username.Equals("") || team.Equals("") || engine.Equals(""))
        {
            MessageBox.Show("Please fill all fields!");
            return;
        }

        controller.addParticipant(username, team, engine);
    }

    private void Update(object sender, ParticipantAddedEventArgs e)
    {
        if (e.ParticipantAddedEvent == ParticipantAddedEvent.ParticipantAdded)
        {
            UpdateCallback updateCallback = delegate { InitializeDataBindings(); };
            BeginInvoke(updateCallback);
        }
    }

    public delegate void UpdateCallback();

    private void logoutButton_Click(object sender, EventArgs e)
    {
        Console.WriteLine("User " + this.user.username + " logged out");
        controller.Logout(this.user);
        controller.updateEvent -= Update;
        this.Close();
        LoginWindow loginWindow = new LoginWindow(controller);
        loginWindow.Show();
    }

    private void label3_Click(object sender, EventArgs e)
    {
    }
}