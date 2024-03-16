package ro.ubbcluj.cs.map.lab82nd;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ro.ubbcluj.cs.map.lab82nd.controller.TabController;
import ro.ubbcluj.cs.map.lab82nd.domain.*;
import ro.ubbcluj.cs.map.lab82nd.domain.validators.FriendshipValidator;
import ro.ubbcluj.cs.map.lab82nd.domain.validators.MessageValidator;
import ro.ubbcluj.cs.map.lab82nd.domain.validators.UserValidator;
import ro.ubbcluj.cs.map.lab82nd.repository.*;
import ro.ubbcluj.cs.map.lab82nd.service.Service;

import java.io.IOException;
import java.util.UUID;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try{
            UserPageDatabase userRepository = new UserPageDatabase("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "piupiu");
            Repository<UUID, FriendShip> friendshipRepository = new FriendshipDatabase("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "piupiu");
            Repository<UUID, Message> messageRepository = new MessageDatabase("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "piupiu");
            UserValidator userValidator = new UserValidator();
            FriendshipValidator friendshipValidator = new FriendshipValidator();
            MessageValidator messageValidator = new MessageValidator();

            Service service = new Service(userRepository, userValidator, friendshipRepository, friendshipValidator, messageRepository, messageValidator);



        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/landing-view.fxml"));
//        TabPane root = (TabPane) fxmlLoader.load();
        AnchorPane root = fxmlLoader.load();
        Scene scene;
        scene = new Scene(root);
//        UserController userController = fxmlLoader.getController();
//        userController.setUserRepository(userRepository);
        LandingController landingController = fxmlLoader.getController();
        landingController.setService(service, stage);
//        TabController tabController = fxmlLoader.getController();
//        tabController.setService(service);


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