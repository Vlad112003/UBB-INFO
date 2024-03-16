package ro.ubbcluj.cs.map.lab7.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ro.ubbcluj.cs.map.lab7.domain.User;
import ro.ubbcluj.cs.map.lab7.repository.*;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserController {

    private Repository<UUID, User> userRepo;

    @FXML
    TableView<User> userTableView;
    @FXML
    TableColumn<User, String> firstName;
    @FXML
    TableColumn<User, String> lastName;
    @FXML
    TableColumn<User, String> email;
    ObservableList<User> usersModel = FXCollections.observableArrayList();
    public void setUserRepo(Repository<UUID, User> userRepo) {
        this.userRepo = userRepo;
        initModel();
    }
    @FXML
    public void initialize(){
        userTableView.setItems(usersModel);

        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));

    }

    private void initModel(){
        usersModel.setAll(StreamSupport.stream(userRepo.findAll().spliterator(), false).collect(Collectors.toList()));

    }

    public void onPressDelete(ActionEvent actionEvent) {
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if(selectedUser != null){
            userRepo.delete(selectedUser.getId());
            initModel();
        }
        else{
            MessageAlert.showErrorMessage(null, "No user selected!");
        }
    }

}
