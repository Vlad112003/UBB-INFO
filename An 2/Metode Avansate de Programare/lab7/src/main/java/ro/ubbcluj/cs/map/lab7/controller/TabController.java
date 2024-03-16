package ro.ubbcluj.cs.map.lab7.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import ro.ubbcluj.cs.map.lab7.domain.Entity;
import ro.ubbcluj.cs.map.lab7.domain.FriendShip;
import ro.ubbcluj.cs.map.lab7.domain.StringFriendship;
import ro.ubbcluj.cs.map.lab7.domain.User;
import ro.ubbcluj.cs.map.lab7.repository.Repository;
import ro.ubbcluj.cs.map.lab7.service.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class TabController {
    private Service service;
//
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//
    @FXML
    private TableView<User> userTableView;

    @FXML
    TableColumn<User, String> firstName;
    @FXML
    TableColumn<User, String> lastName;
    @FXML
    TableColumn<User, String> email;
    @FXML
    TableColumn<User, String> password;
//
    @FXML
    TextField firstNameTextField;
    @FXML
    TextField lastNameTextField;
    @FXML
    TextField emailTextField;
    @FXML
    TextField passwordTextField;
    @FXML
    private TableView<StringFriendship> friendshipTableView;

    @FXML
    TableColumn<String, String> user1;
    @FXML
    TableColumn<String, String> user2;
    @FXML
    TableColumn<String, String> friendsFrom;

    @FXML
    TextField user1TextField;
    @FXML
    TextField user2TextField;


    ObservableList<User> usersModel = FXCollections.observableArrayList();
//
    ObservableList<StringFriendship> friendshipsModel = FXCollections.observableArrayList();
//
    public void setService(Service service) {
        this.service = service;
        initModel();
    }
//
    @FXML
    public void initialize() {
        userTableView.setItems(usersModel);

        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
//
        friendshipTableView.setItems(friendshipsModel);
        user1.setCellValueFactory(new PropertyValueFactory<>("user1"));
        user2.setCellValueFactory(new PropertyValueFactory<>("user2"));
        friendsFrom.setCellValueFactory(new PropertyValueFactory<>("friendsFrom"));
    }
//
    public void initModel() {
        usersModel.setAll(StreamSupport.stream(service.getAllUsers().spliterator(), false)
                .collect(Collectors.toList()));

//        Iterator<FriendShip> iterator = service.getAllFriendships().iterator();
//        while(iterator.hasNext()) {
//            FriendShip fr = iterator.next();
//            System.out.println(fr.getUser1().getEmail());
//            System.out.println(fr.getUser2().getEmail());
//            System.out.println(fr.getFriendsFrom());
//        }

        Iterator<FriendShip> iterator = service.getAllFriendships().iterator();
        ArrayList<StringFriendship> friendships = new ArrayList<>();
        while(iterator.hasNext()) {
            FriendShip fr = iterator.next();
            String email1 = (fr.getUser1().getEmail());
            String email2 = (fr.getUser2().getEmail());
            String friendsFrom = (fr.getFriendsFrom().toString());
            StringFriendship stringFriendship = new StringFriendship(email1, email2, friendsFrom);
            friendships.add(stringFriendship);

        }
//        System.out.println(friendships);
//        System.out.println(friendshipsModel.size());

        friendshipsModel.setAll(StreamSupport.stream(friendships.spliterator(), false)
                .collect(Collectors.toList()));
    }
//
    public void onPressDeleteUser() {
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if(selectedUser != null) {
            service.deleteUser(selectedUser.getEmail());
            initModel();
        }
        else {
            MessageAlert.showErrorMessage(null, "No user selected!");
        }
    }

    public void onPressAddUser(){
        User user = new User(firstNameTextField.getText(), lastNameTextField.getText(), emailTextField.getText(), passwordTextField.getText());
        service.addUser(user);
        initModel();
    }

    public void onPressDeleteFriendship(){
        StringFriendship selectedFriendship = friendshipTableView.getSelectionModel().getSelectedItem();
        if(selectedFriendship != null) {
            service.deleteFriendship(selectedFriendship.getUser1(), selectedFriendship.getUser2());
            initModel();
        }
        else {
            MessageAlert.showErrorMessage(null, "No friendship selected!");
        }
    }

    public void onPressAddFriendship(){
        String email1 = user1TextField.getText();
        String email2 = user2TextField.getText();
        service.createFriendship(email1, email2);


        initModel();
    }

}
