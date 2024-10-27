package project.problema11final;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import project.problema11final.Domain.Participant;
import project.problema11final.Domain.Race;
import project.problema11final.Domain.User;
import project.problema11final.Service.DTOs.ParticipantDTO;
import project.problema11final.Service.DTOs.RaceDTO;
import project.problema11final.Service.Service;

import java.util.ArrayList;

public class HomeController {

    private Stage stage;
    private Service service;

    private User user;

    @FXML
    TableView<RaceDTO> racesTable;
    @FXML
    TableColumn<String, RaceDTO> engineColumn;
    @FXML
    TableColumn<String, RaceDTO> noRacersColumn;
    @FXML
    ComboBox<String> teamComboBox;
    @FXML
    ComboBox<String> engineComboBox;

    public Button logOutButton;
    @FXML
    Button searchButton;

    @FXML
    TableView<ParticipantDTO> filteredTable;
    @FXML
    TableColumn<String, ParticipantDTO> usernameColumn;
    @FXML
    TableColumn<String, ParticipantDTO> capacityColumn;



    @FXML
    TextField usernameTextField;
    @FXML
    TextField teamTextField;

    ObservableList<RaceDTO> raceModel = FXCollections.observableArrayList();

    ObservableList<ParticipantDTO> participantModel = FXCollections.observableArrayList();



    public void setService(Stage stage, Service service, User user) {
        this.stage = stage;
        this.service = service;
        this.user = user;
        initModel();
    }

    public void onSearchClicked(ActionEvent actionEvent) {
        String team = teamComboBox.getValue();
        if(team == null){
            MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Error", "Please select a team");
            return;
        }
        Iterable<ParticipantDTO> ps = service.getParticipantDTOforTeam(team);
        participantModel.setAll((ArrayList<ParticipantDTO>) ps);
    }

    public void onAddClicked(ActionEvent actionEvent) {
        String username = usernameTextField.getText();
        if(username.isEmpty()){
            MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Error", "Please enter a username");
            return;
        }
        String team = teamTextField.getText();
        if(team.isEmpty()){
            MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Error", "Please enter a team");
            return;
        }
        String engine = engineComboBox.getValue();
        if(!engine.isEmpty()){
            service.addParticipant(username, team, engine);
            loadTeams();
            Participant p = service.getParticipant(username, team, engine);
            service.createEntry(p);
            loadData();

        }
        else MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Error", "Please select an engine capacity");

    }

    public void onLogOutButtonClicked(ActionEvent actionEvent) {
        System.out.println("Log out clicked");
        stage.close();
    }

    public void onRefreshClicked(ActionEvent actionEvent){
        loadData();
    }

    public void loadData(){
        initModel();
    }

    @FXML
    public void initialize(){
        engineColumn.setCellValueFactory(new PropertyValueFactory<>("engineCapacity"));
        noRacersColumn.setCellValueFactory(new PropertyValueFactory<>("noRacers"));

        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("engineCapacity"));

        racesTable.setItems(raceModel);
        filteredTable.setItems(participantModel);
    }

    public void initModel(){
        Iterable<RaceDTO> raceDTOS = service.racesDTO();
        ArrayList<RaceDTO> raceList = new ArrayList<>();
        for (RaceDTO r: raceDTOS) {
            raceList.add(r);
        }

        raceModel.setAll(raceList);

        loadTeams();
        loadEngines();

    }

    public void loadTeams(){
        teamComboBox.getItems().clear();
        Iterable<String> teams = service.getTeams();
        for (String team: teams){
            teamComboBox.getItems().add(team);
        }
    }

    public void loadEngines(){
        engineComboBox.getItems().clear();
        Iterable<String> engines = service.engineCapacities();
        for (String engine: engines){
            engineComboBox.getItems().add(engine);
        }
    }

}
