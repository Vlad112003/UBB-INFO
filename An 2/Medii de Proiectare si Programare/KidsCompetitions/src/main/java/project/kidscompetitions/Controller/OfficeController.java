package project.kidscompetitions.Controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import project.kidscompetitions.Domain.Competition;
import project.kidscompetitions.Domain.Employee;
import project.kidscompetitions.Domain.Participant;
import project.kidscompetitions.Domain.SignUp;
import project.kidscompetitions.Service.Service;

import java.util.ArrayList;
import java.util.Optional;

public class OfficeController {
    public Button FilterButton;
    @FXML
    TableView<Competition> tableCompetitions;
    @FXML
    TableColumn<String,Competition> ageGroup;
    @FXML
    TableColumn<Integer,Competition> proba;
    @FXML
    TableColumn<String,Competition> unit;
    @FXML
    ListView<Participant> participantsFiltered;
    @FXML
    TableView<Participant> tableAthleticTestsFiltered;
    @FXML
    TableColumn<String,Competition> nameFiltered;
    @FXML
    TableColumn<String,Competition> birthDateFiltered;
    @FXML
    ComboBox<Long> idTestCombo;
    @FXML
    ListView<Participant> clientList;
    @FXML
    TextField participantField;
    @FXML
    TextField competitionField;
    @FXML
    TextField noSeatsField;

    private Service service;
    private Stage primaryStage;
    private Employee responsibleEmployee;
    public void setService(Stage primaryStage, Service service, Employee responsibleEmployee) {
        this.service=service;
        this.primaryStage=primaryStage;
        this.responsibleEmployee=responsibleEmployee;
        loadData();
        loadClientsData();
    }

    private void loadData() {
        participantsFiltered.getItems().clear();
        tableCompetitions.getItems().clear();
        for (Competition prb:service.getAllCompetitions()) {
            tableCompetitions.getItems().add(prb);
        }
    }

    private void loadClientsData() {
        clientList.getItems().clear();
        for (Participant client:service.getAllClients()) {
            clientList.getItems().add(client);
        }
    }

    private void loadFilteredData(Long idComp) {
        participantsFiltered.getItems().clear();
        for (Participant client:service.findAllSignUps(idComp)) {
            participantsFiltered.getItems().add(client);
        }
    }

    public void AthleticTestsFilterButton(ActionEvent actionEvent) {
        try
        {
            long idTest= idTestCombo.getValue();
            loadFilteredData(idTest);

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void initialize(){
        ArrayList<Long> listId = new ArrayList<>();
        for(long i=1; i<=6; i++)
            listId.add(i);
        System.out.println(listId);
        idTestCombo.setItems(FXCollections.observableList(listId));
    }


    public void LogOutHandler(ActionEvent actionEvent) {
        primaryStage.close();
    }

    public void onSignUpButtonClicked(ActionEvent actionEvent) {
        if(participantField.getText() != null && competitionField.getText() != null ) {
            int idParticipant = Integer.parseInt(participantField.getText());
            int competition = Integer.parseInt(competitionField.getText());
            Participant client = clientList.getSelectionModel().getSelectedItem();
            try {
                Optional<SignUp> result = service.signUpParticipant(idParticipant, competition);
                MessageAlert.showMessage(primaryStage, Alert.AlertType.INFORMATION, "Info", "Successful reservation...");

            } catch (Exception e) {
                System.out.println(e.getMessage());
                MessageAlert.showMessage(primaryStage, Alert.AlertType.ERROR, "Error", "Could not save the reservation...");
            }
        }
    }

    public void onRefreshButtonClicked(ActionEvent actionEvent) {
        loadData();
    }
}
