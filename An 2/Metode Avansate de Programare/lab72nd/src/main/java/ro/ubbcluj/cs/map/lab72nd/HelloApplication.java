package ro.ubbcluj.cs.map.lab72nd;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ro.ubbcluj.cs.map.lab72nd.controller.TabController;
import ro.ubbcluj.cs.map.lab72nd.controller.UserController;
import ro.ubbcluj.cs.map.lab72nd.domain.FriendShip;
import ro.ubbcluj.cs.map.lab72nd.domain.User;
import ro.ubbcluj.cs.map.lab72nd.domain.validators.FriendshipValidator;
import ro.ubbcluj.cs.map.lab72nd.domain.validators.UserValidator;
import ro.ubbcluj.cs.map.lab72nd.repository.FriendshipDatabase;
import ro.ubbcluj.cs.map.lab72nd.repository.Repository;
import ro.ubbcluj.cs.map.lab72nd.repository.UserDatabase;
import ro.ubbcluj.cs.map.lab72nd.service.Service;

import java.io.IOException;
import java.util.UUID;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try{
            Repository<UUID, User> userRepository = new UserDatabase("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "piupiu");
            Repository<UUID, FriendShip> friendshipRepository = new FriendshipDatabase("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "piupiu");
            UserValidator userValidator = new UserValidator();
            FriendshipValidator friendshipValidator = new FriendshipValidator();
            Service service = new Service(userRepository, userValidator, friendshipRepository, friendshipValidator);



        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/tabs-view.fxml"));
        TabPane root = (TabPane) fxmlLoader.load();
//        AnchorPane root = (AnchorPane) fxmlLoader.load();
        Scene scene;
        scene = new Scene(root);
//        UserController userController = fxmlLoader.getController();
//        userController.setUserRepository(userRepository);
        TabController tabController = fxmlLoader.getController();
        tabController.setService(service);

        stage.setTitle("Tabs");
        stage.setScene(scene);
        stage.show();
    }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch();
    }
}