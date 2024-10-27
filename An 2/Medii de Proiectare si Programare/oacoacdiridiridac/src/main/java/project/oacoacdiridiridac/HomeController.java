package project.oacoacdiridiridac;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import project.oacoacdiridiridac.Domain.Race;
import project.oacoacdiridiridac.Domain.Team;
import project.oacoacdiridiridac.Domain.User;
import project.oacoacdiridiridac.Service.Service;

import java.util.ArrayList;
import java.util.Collection;

public class HomeController {
    private Service service;
    private Stage primaryStage;
    private User user;

    @FXML
    TableView<Race> racesTable;
    @FXML
    TableColumn<String, Race> engineColumn;
    @FXML
    TableColumn<String, Race> noRacersColumn;
    @FXML
    ComboBox<String> teamComboBox1;
    @FXML
    ComboBox<String> engineComboBox;

    public Button logOutButton;
    @FXML
    Button searchButton;

    @FXML
    TableView<User> participantsFiltered;
    @FXML
    TableColumn<String, User> firstNameColumn;
    @FXML
    TableColumn<String, User> lastNameColumn;


    @FXML
    TextField lastTextField;
    @FXML
    TextField firstTextField;
    @FXML
    TextField teamTextField;

    ObservableList<Race> raceModel = FXCollections.observableArrayList();


    public void setService(Stage primaryStage, Service service, User user) {
        this.service = service;
        this.primaryStage = primaryStage;
        this.user = user;

        initModel();
    }

    public void onLogOutButtonClicked(ActionEvent actionEvent) {
        primaryStage.close();
    }

    private void loadData(){
        try{
        racesTable.getItems().clear();
        for(Race r : service.getRaces()){
            racesTable.getItems().add(r);
            }
        }
        catch (Exception e) {
            System.out.println("No races found");
        }
    }

    private void loadTeammates(String teamName){
        participantsFiltered.getItems().clear();
        try{
        for(User u : service.getTeamMembers(teamName)){
            participantsFiltered.getItems().add(u);
        }
        }
        catch (Exception e){
            System.out.println("No team found");
        }
    }

    public void onAddClicked(){
        try{
        String last = lastTextField.getText();
        String first = firstTextField.getText();
        String team = teamTextField.getText();
        String engine = engineComboBox.getValue();

        Collection<User> users = (Collection<User>) service.getUsers();
        service.addUserToTeam(user, team);
        for(User u : users){
            if(u.getLastName().equals(last) && u.getFirstName().equals(first)){
                participantsFiltered.getItems().add(u);
            }
        }
        }
        catch (Exception e){
            System.out.println("No user found");
        }
    }

    @FXML
    public void initialize(){
        engineColumn.setCellValueFactory(new PropertyValueFactory<>("engineCapacity"));
        noRacersColumn.setCellValueFactory(new PropertyValueFactory<>("NoRacers"));

        racesTable.setItems(raceModel);
    }

    public void initModel(){
        Iterable<Race> races = service.getRaces();
        ArrayList<Race> raceList = new ArrayList<>();
        for (Race r: races) {
            raceList.add(r);
        }

        raceModel.setAll(raceList);

        loadTeams();

    }
    public void onSearchClicked(){
        String teamName = teamComboBox1.getValue();
        loadTeammates(teamName);
    }

    public void loadTeams(){
        try{
            service.addUsersToTeams();
        teamComboBox1.getItems().clear();
        for(Team t : service.getTeams()){
            teamComboBox1.getItems().add(t.getName());
            }
        }
        catch (Exception e){
            System.out.println("No teams found");
        }

    }


}
