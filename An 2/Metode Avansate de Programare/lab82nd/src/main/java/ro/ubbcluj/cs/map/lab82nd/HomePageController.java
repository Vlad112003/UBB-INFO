package ro.ubbcluj.cs.map.lab82nd;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ro.ubbcluj.cs.map.lab82nd.controller.MessageAlert;
import ro.ubbcluj.cs.map.lab82nd.domain.*;
import ro.ubbcluj.cs.map.lab82nd.repository.Page;
import ro.ubbcluj.cs.map.lab82nd.repository.Pageable;
import ro.ubbcluj.cs.map.lab82nd.service.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class HomePageController {

    private Service service;
    private User user;

    private int currentPage = 0;
    private int pageSize = 5;
    private int totalNumberOfElements = 0;


    private Stage dialogStage;


    @FXML
    Label nameLabel1;

    @FXML
    Label nameLabel2;

    public void setService(Service service, User user, Stage stage) {
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        nameLabel1.setText("Welcome " + firstName + " " + lastName +"!");
        nameLabel2.setText("Welcome " + firstName + " " + lastName +"!");

        this.service = service;
        this.user = user;
        this.dialogStage = stage;
        initModel();
    }

    @FXML
    private TableView<StringFriend> friendsTableView;

    @FXML
    TableColumn<StringFriend, String> friendsFirstName;
    @FXML
    TableColumn<StringFriend, String> friendsLastName;
    @FXML
    TableColumn<StringFriend, String> friendsStatus;

    ObservableList<StringFriend> friendsModel = FXCollections.observableArrayList();

    @FXML
    private TableView<User> usersTableView;

    @FXML
    TableColumn<User, String> userFirstName;
    @FXML
    TableColumn<User, String> userLastName;

    @FXML
    Button nextUserButton;
    @FXML
    Button prevUserButton;
    @FXML
    TextField pageNumberTextField;

    ObservableList<User> usersModel = FXCollections.observableArrayList();


    @FXML
    public void initialize() {
        friendsFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        friendsLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        friendsStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        friendsTableView.setItems(friendsModel);

        userFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        userLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        usersTableView.setItems(usersModel);
    }

    public void initModel(){
        Iterable<FriendShip> friends = service.getFriendsforUser(user);
        ArrayList<StringFriend> friendos = service.convertToFriendNameForUser(friends, user);

        friendsModel.setAll(StreamSupport.stream(friendos.spliterator(), false)
                .collect(Collectors.toList()));

        Page<User> page = service.getAllUsersPageable(new Pageable(currentPage, pageSize));

        int maxPage = (int) Math.ceil((double) page.getTotalElementCount() / pageSize );
        if(currentPage > maxPage) {
            currentPage = maxPage;
            page = service.getAllUsersPageable(new Pageable(currentPage, pageSize));
        }

        usersModel.setAll(StreamSupport.stream(page.getElementsOnPage().spliterator(),
                false).collect(Collectors.toList()));

        totalNumberOfElements = page.getTotalElementCount();

        prevUserButton.setDisable(currentPage == 0);
        nextUserButton.setDisable((currentPage+1)*pageSize >= totalNumberOfElements);
    }


    @FXML
    public void onPressAccept() {
        FriendRequest accepted = FriendRequest.ACCEPTED;
        StringFriend selectedFriend = friendsTableView.getSelectionModel().getSelectedItem();
        if (selectedFriend == null) {
            MessageAlert.showErrorMessage(null, "No friend selected!");
            return;
        }

        String firstName = selectedFriend.getFirstName();
        String lastName = selectedFriend.getLastName();
        FriendShip friendShip = service.getFriendshipByFriendName(this.user, firstName, lastName);

        if (friendShip.getStatus().equals(FriendRequest.ACCEPTED)) {
            MessageAlert.showErrorMessage(null, "You are already friends!");
            return;
        }
        if (friendShip.getUser1().getEmail().equals(this.user.getEmail())){
            MessageAlert.showErrorMessage(null, "You can't accept your own friend request!");
            return;
        }

        service.setStatus(friendShip, accepted);
        initModel();

    }

    @FXML
    public void onPressDelete(){
        StringFriend selectedFriend = friendsTableView.getSelectionModel().getSelectedItem();
        String firstName = selectedFriend.getFirstName();
        String lastName = selectedFriend.getLastName();
        FriendShip friendShip = service.getFriendshipByFriendName(this.user, firstName, lastName);
        String friendEmail = friendShip.getUser1().getEmail();
        if(!friendEmail.equals(this.user.getEmail()))
            service.deleteFriendship(this.user.getEmail(), friendEmail);
        else{
            service.deleteFriendship(this.user.getEmail(), friendShip.getUser2().getEmail());
        }
        initModel();
    }

    @FXML
    public void onPressDecline(){
        FriendRequest rejected = FriendRequest.REJECTED;
        StringFriend selectedFriend = friendsTableView.getSelectionModel().getSelectedItem();
        if (selectedFriend == null) {
            MessageAlert.showErrorMessage(null, "No friend selected!");
            return;
        }

        String firstName = selectedFriend.getFirstName();
        String lastName = selectedFriend.getLastName();
        FriendShip friendShip = service.getFriendshipByFriendName(this.user, firstName, lastName);

        if (friendShip.getStatus().equals(FriendRequest.ACCEPTED)) {
            MessageAlert.showErrorMessage(null, "You are already friends!");
            return;
        }
        if (friendShip.getUser1().getEmail().equals(this.user.getEmail())){
            MessageAlert.showErrorMessage(null, "You can't decline your own friend request!");
            return;
        }

        service.setStatus(friendShip, rejected);
        initModel();
    }

    @FXML
    public void onPressExit(){
        System.exit(0);
    }

    public void onPressSendRequest(){
        User selectedUser = usersTableView.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            MessageAlert.showErrorMessage(null, "No user selected!");
            return;
        }
        if(Objects.equals(selectedUser.getEmail(), this.user.getEmail())){
            MessageAlert.showErrorMessage(null, "You can't send a friend request to yourself!");
            return;
        }
        if(service.getFriendshipByFriendName(this.user, selectedUser.getFirstName(), selectedUser.getLastName()) != null){
            MessageAlert.showErrorMessage(null, "You are already friends!");
            return;
        }
        String email = selectedUser.getEmail();
        service.createFriendship(this.user.getEmail(), email);
        MessageAlert.showMessage(null, "Friend request sent!");
        initModel();
    }

    public void onPressSignOut(){
        try{
            this.dialogStage.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("views/landing-view.fxml"));

            AnchorPane root = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Landing Page");
            dialogStage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            LandingController landingController = loader.getController();
            landingController.setService(service, dialogStage);

            dialogStage.show();

        }
        catch(Exception e)
        {
            MessageAlert.showErrorMessage(null, e.getMessage());
        }
    }

    public void onPressMessage(){
        StringFriend selectedFriend = friendsTableView.getSelectionModel().getSelectedItem();
        if (selectedFriend == null) {
            MessageAlert.showErrorMessage(null, "No user selected!");
            return;
        }
        FriendShip getFriend = service.getFriendshipByFriendName(this.user, selectedFriend.getFirstName(), selectedFriend.getLastName());
        if (getFriend.getStatus().equals(FriendRequest.REJECTED) || getFriend.getStatus().equals(FriendRequest.PENDING)) {
            MessageAlert.showErrorMessage(null, "You are not friends!");
            return;
        }

        User selectedUser;
        if (getFriend.getUser1().getEmail().equals(this.user.getEmail())){
            selectedUser = getFriend.getUser2();
        }
        else{
            selectedUser = getFriend.getUser1();
        }

        if(selectedUser.getId() == this.user.getId()){
            MessageAlert.showErrorMessage(null, "You can't send a message to yourself!");
            return;
        }
        User userTo = selectedUser;
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("views/message-view.fxml"));

            AnchorPane root = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Chat with " + userTo.getFirstName() + " " + userTo.getLastName() + "!");
            dialogStage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            MessageController messageController = loader.getController();
            messageController.setService(service, this.user, userTo, dialogStage);

            dialogStage.show();

        }
        catch(Exception e)
        {
            MessageAlert.showErrorMessage(null, e.getMessage());
        }
    }

    public void onPressNextUser(){
        currentPage++;
        initModel();
    }

    public void onPressPreviousUser(){
        currentPage--;
        initModel();
    }

    public void onPressOKPage(){
        if (pageNumberTextField.getText().equals("")){
            MessageAlert.showErrorMessage(null, "No page number selected!");
            return;
        }
        pageSize = Integer.parseInt(pageNumberTextField.getText());
        currentPage = 0;
        initModel();
    }

}
