package com.example.Controllers;

import com.example.IObserver;
import com.example.IService;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import com.example.Domain.*;

import java.util.ArrayList;
import java.util.List;

public class ContestController implements IObserver {
    @FXML
    private ObservableList<Contest> model_contest = FXCollections.observableArrayList();


    @FXML
    private ObservableList<Participant> model_participant = FXCollections.observableArrayList();

    @FXML
    private TableView<Contest> tabel_contest = new TableView<>();

    @FXML
    private TableView<Participant> tabel_participants = new TableView<>();

    @FXML
    private TableColumn<Participant, Integer> age_column = new TableColumn<>();

    @FXML
    private TableColumn<Participant, String> name_column = new TableColumn<>();

    @FXML
    private TableColumn<Participant, String> list_participations_column = new TableColumn<>();

    @FXML
    private TableColumn<Contest, StyleEnum> style_column = new TableColumn<>();

    @FXML
    private TableColumn<Contest, DistEnum> distance_column = new TableColumn<>();


    @FXML
    private TableColumn<Contest, Integer> number_column = new TableColumn<>();

    @FXML
    private TextField name = new TextField();

    @FXML
    private TextField age = new TextField();

    private IService server;

    private Stage stage;
    private Admin admin;

    public void setServer(IService server) {
        this.server = server;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
        this.server.login(admin, this);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        init_model();
    }

    public void initialize(){
        System.out.println("INITIALIZEZ TABELUL");
        distance_column.setCellValueFactory(new PropertyValueFactory<>("distance"));
        style_column.setCellValueFactory(new PropertyValueFactory<>("style"));
        number_column.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Contest, Integer>, ObservableValue<Integer>>() {
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Contest, Integer> param) {
                System.out.println("AICI IN NUMAR PARTICIPANTI");
                Contest contest = param.getValue();
                System.out.println("Contest id: " + contest.getId());
                int numberOfParticipants = server.getNrParticipanti(contest.getId());
                System.out.println("A MERS GET NR PARTICIPANTI");// Assuming getId() gets the contest_id
                return new SimpleIntegerProperty(numberOfParticipants).asObject();
            }
        });
        tabel_contest.setItems(model_contest);
    }

    @Override
    public void init_model(){
        System.out.println("\n \n \nINITIALIZEZ MODEL !!!!!! \n \n \n");
        Platform.runLater(() -> {
            System.out.println("RUNNING LATER");
            initialize();
            Iterable<Contest> iterableContests = server.findAllContest();
            List<Contest> contestList = new ArrayList<>();
            iterableContests.forEach(contestList::add);
            model_contest.setAll(contestList);
        });
    }

    @FXML
    public void cauta(){
        if(!this.tabel_contest.getSelectionModel().isEmpty()){
            Contest contest = this.tabel_contest.getSelectionModel().getSelectedItem();
            Iterable<Participation> iterableParticipation = server.findAllParticipations();
            List<Participant> participants = new ArrayList<>();
            for (Participation p : iterableParticipation) {
                if (p.getContest().getId().equals(contest.getId())) {
                    participants.add(p.getParticipant());
                }
            }
            this.age_column.setCellValueFactory(new PropertyValueFactory<>("age"));
            this.name_column.setCellValueFactory(new PropertyValueFactory<>("name"));
            this.list_participations_column.setCellValueFactory(cellDataFeatures -> {
                Participant participant = cellDataFeatures.getValue();
                Iterable<Contest> contests = this.server.getContests(participant.getId());
                StringBuilder contestsString = new StringBuilder();
                for (Contest c : contests) {
                    contestsString.append(c.getDistance()).append(", ").append(c.getStyle()).append(" | ");
                }
                // Remove the trailing " | "
                if (contestsString.length() > 0) {
                    contestsString.setLength(contestsString.length() - 3);
                }
                return new SimpleStringProperty(contestsString.toString());
            });
            this.tabel_participants.setItems(model_participant);
            model_participant.setAll(participants);
        }
        else{
            Alert message= new Alert(Alert.AlertType.INFORMATION);
            message.setTitle("ERROR!");
            message.setContentText("YOU MUST SELECT AN CONTEST FOR SEARCH!");
            message.showAndWait();
        }
    }

    @FXML
    public void add_participation(){
        if(!this.tabel_contest.getSelectionModel().isEmpty()){
            Contest contest = this.tabel_contest.getSelectionModel().getSelectedItem();
            if(!this.name.getText().isEmpty() && !this.age.getText().isEmpty()){
                Iterable<Participant> participants = this.server.findAllParticipants();
                boolean found = false;
                for (Participant p : participants) {
                    if (p.getName().equals(this.name.getText()) && p.getAge() == Integer.parseInt(this.age.getText())) {
                        found = true;
                        Iterable<Participation> participations = this.server.findAllParticipations();
                        for(Participation part : participations){
                            if(part.getParticipant().getId().equals(p.getId()) && part.getContest().getId().equals(contest.getId())){
                                Alert message= new Alert(Alert.AlertType.INFORMATION);
                                message.setTitle("ERROR!");
                                message.setContentText("PARTICIPANT ALREADY PARTICIPATES TO THIS CONTEST!");
                                message.showAndWait();
                                return;
                            }
                        }
                        Participation participation = new Participation(p, contest);
                        this.server.addParticipation(participation);
                        Alert message= new Alert(Alert.AlertType.INFORMATION);
                        message.setTitle("PARTICIPATION ADDED!");
                        message.setContentText("PARTICIPATION HAS BEEN ADDED!");
                        message.showAndWait();
                        break;
                    }
                }
                if(!found){
                    Alert message1= new Alert(Alert.AlertType.INFORMATION);
                    message1.setTitle("ERROR!");
                    message1.setContentText("PARTICIPANT NOT FOUND!");
                    message1.showAndWait();
                }
            }
            else{
                Alert message2= new Alert(Alert.AlertType.INFORMATION);
                message2.setTitle("ERROR!");
                message2.setContentText("YOU MUST FILL ALL THE FIELDS IN ORDER TO ADD A PARTICIPATION!");
                message2.showAndWait();
            }
        }
        else{
            Alert message3= new Alert(Alert.AlertType.INFORMATION);
            message3.setTitle("ERROR!");
            message3.setContentText("YOU MUST SELECT AN CONTEST FOR ADDING A PARTICIPATION!");
            message3.showAndWait();
        }
    }

    @FXML
    public void LogOut(){
        this.stage.close();
        this.server.logout(this.admin,this);
    }


}
