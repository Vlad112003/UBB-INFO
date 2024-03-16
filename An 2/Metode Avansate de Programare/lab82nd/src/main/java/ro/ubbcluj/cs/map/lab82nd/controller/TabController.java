package ro.ubbcluj.cs.map.lab82nd.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import ro.ubbcluj.cs.map.lab82nd.domain.*;
import ro.ubbcluj.cs.map.lab82nd.repository.Page;
import ro.ubbcluj.cs.map.lab82nd.repository.Pageable;
import ro.ubbcluj.cs.map.lab82nd.repository.Repository;
import ro.ubbcluj.cs.map.lab82nd.service.Service;

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

    private int currentPage = 0;
    private int pageSize = 5;
    private int totalNumberOfElements = 0;

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

    @FXML
    Button nextButton;
    @FXML
    Button previousButton;

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
    TableColumn<String, String> status;

    @FXML
    TextField user1TextField;
    @FXML
    TextField user2TextField;


    ObservableList<User> usersModel = FXCollections.observableArrayList();
//
    ObservableList<StringFriendship> friendshipsModel = FXCollections.observableArrayList();
//
    @FXML
    TextField userTextField;
    @FXML
    TextField monthTextField;
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
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
//
    public void initModel() {
        Page<User> page = service.getAllUsersPageable(new Pageable(currentPage, pageSize));

        int maxPage = (int) Math.ceil((double) page.getTotalElementCount() / pageSize ) - 1;
        if(currentPage > maxPage) {
            currentPage = maxPage;
            page = service.getAllUsersPageable(new Pageable(currentPage, pageSize));
        }

        usersModel.setAll(StreamSupport.stream(page.getElementsOnPage().spliterator(),
                false).collect(Collectors.toList()));
        totalNumberOfElements = page.getTotalElementCount();

        previousButton.setDisable(currentPage == 0);
        nextButton.setDisable((currentPage+1)*pageSize >= totalNumberOfElements);

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
            String status = fr.getStatus().toString();
            StringFriendship stringFriendship = new StringFriendship(email1, email2, friendsFrom, status);
            friendships.add(stringFriendship);

        }
//        System.out.println(friendships);
//        System.out.println(friendshipsModel.size());

        friendshipsModel.setAll(StreamSupport.stream(friendships.spliterator(), false)
                .collect(Collectors.toList()));
    }
//

    public void onPressNext(){
        currentPage++;
        initModel();
    }

    public void onPressPrevious(){
        currentPage--;
        initModel();
    }
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
        try {
            String errors = "";
            String userFirstName = firstNameTextField.getText();
            if(userFirstName.isEmpty())
                errors += "Invalid first name!\n";
            String userLastName = lastNameTextField.getText();
            if(userLastName.isEmpty())
                errors += "Invalid last name!\n";
            String userEmail = emailTextField.getText();
            if(userEmail.contains(" ") || !userEmail.contains("@") || !userEmail.contains("."))
                errors += "Invalid email!\n";
            String userPassword = passwordTextField.getText();
            if(!errors.isEmpty())
                MessageAlert.showErrorMessage(null, errors);
            else {
                User user = new User(userFirstName, userLastName, userEmail, userPassword);
                service.addUser(user);
                initModel();
            }
        }
        catch(Exception e){
            MessageAlert.showErrorMessage(null, "Invalid user!");
        }

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

    public void onPressAccept(){
        StringFriendship selectedFriendship = friendshipTableView.getSelectionModel().getSelectedItem();
        if(selectedFriendship != null) {
            if (selectedFriendship.getStatus().equals("PENDING")){
            User user1 = service.getUserByStringEmail(selectedFriendship.getUser1());
            User user2 = service.getUserByStringEmail(selectedFriendship.getUser2());
            FriendShip friendship = service.getFriendshipByUsers(user1, user2);
            FriendRequest accepted =  FriendRequest.ACCEPTED;
            service.setStatus(friendship, accepted);
            MessageAlert.showMessage(null, "Friendship accepted!");
            initModel();
            }
            else {
                MessageAlert.showErrorMessage(null, "Friendship already accepted or declined!");
            }
        }
        else{
            MessageAlert.showErrorMessage(null, "No friendship selected!");
        }
    }

    public void onPressDecline(){
        StringFriendship selectedFriendship = friendshipTableView.getSelectionModel().getSelectedItem();
        if(selectedFriendship != null) {
            if (selectedFriendship.getStatus().equals("PENDING")) {
                User user1 = service.getUserByStringEmail(selectedFriendship.getUser1());
                User user2 = service.getUserByStringEmail(selectedFriendship.getUser2());
                FriendShip friendship = service.getFriendshipByUsers(user1, user2);
                FriendRequest rejected = FriendRequest.REJECTED;
                service.setStatus(friendship, rejected);
                MessageAlert.showMessage(null, "Friendship declined!");
                initModel();
            }
            else {
                MessageAlert.showErrorMessage(null, "Friendship already accepted or declined!");
            }
        }
        else{
            MessageAlert.showErrorMessage(null, "No friendship selected!");
        }

    }

    public void onPressAddFriendship(){
        try {
            String email1 = user1TextField.getText();
            String email2 = user2TextField.getText();
            service.createFriendship(email1, email2);
        }
        catch(Exception e){
            MessageAlert.showErrorMessage(null, "Invalid user!");
        }

        initModel();
    }



    public void onPressNumberOfCommunities(){
        MessageAlert.showMessage(null, "Number of communities: " + service.numberOfCommunities());
    }

    public void onPressMostSociableCommunity(){
        MessageAlert.showMessage(null, "Most sociable community: " + service.mostSociableCommunity());
    }

    public void onPressShowFriendshipsByUserinMonth(){
        try {
            String email = userTextField.getText();
            if(email.contains(" ") || !email.contains("@") || !email.contains(".")) {
                MessageAlert.showErrorMessage(null, "Invalid email!");
                return;
            }
            User user = service.getUserByEmail(email);
            if(user == null) {
                MessageAlert.showErrorMessage(null, "User does not exist!");
                return;
            }
            String month = monthTextField.getText();
            int monthInt = Integer.parseInt(month);
            if(monthInt < 1 || monthInt > 12)
                MessageAlert.showErrorMessage(null, "Invalid month! Please enter a number between 1 and 12");

            else {
                Iterable<FriendShip> friends = service.getFriendshipsForUserFromMonth(email, monthInt);
                if(StreamSupport.stream(friends.spliterator(), false).findAny().isEmpty())
                    MessageAlert.showMessage(null, "No friendships in month: " + monthInt);
                else
                    MessageAlert.showMessage(null, "Friendships by user in month: " + service.getFriendshipsForUserFromMonth(email, monthInt));
            }
        }
        catch(Exception e){
            MessageAlert.showErrorMessage(null, "Invalid month!");
        }
    }

}
