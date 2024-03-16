package ro.ubbcluj.cs.map.lab72nd.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import ro.ubbcluj.cs.map.lab72nd.domain.User;
import ro.ubbcluj.cs.map.lab72nd.repository.Repository;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserController {

    private Repository<UUID, User> userRepository;

    @FXML
    TableView<User> userTableView;

    @FXML
    TableColumn<User, String> firstName;
    @FXML
    TableColumn<User, String> lastName;
    @FXML
    TableColumn<User, String> email;
    @FXML
    TableColumn<User, String> password;

    @FXML
    TextField firstNameTextField;
    @FXML
    TextField lastNameTextField;
    @FXML
    TextField emailTextField;
    @FXML
    TextField passwordTextField;

    ObservableList<User> usersModel = FXCollections.observableArrayList();

    public void setUserRepository(Repository<UUID, User> userRepository) {
        this.userRepository = userRepository;
        initModel();
    }

    @FXML
    public void initialize() {
        userTableView.setItems(usersModel);

        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
    }

    private void initModel() {
        usersModel.setAll(StreamSupport.stream(userRepository.findAll().spliterator(),
                false).collect(Collectors.toList()));
    }

    public void onPressDelete() {
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if(selectedUser != null) {
            userRepository.delete(selectedUser.getId());
            initModel();
        }
        else {
            MessageAlert.showErrorMessage(null, "No user selected!");
        }
    }

    public void onPressAdd(){
        User user = new User(firstNameTextField.getText(), lastNameTextField.getText(), emailTextField.getText(), passwordTextField.getText());
        userRepository.save(user);
        initModel();
    }

}
