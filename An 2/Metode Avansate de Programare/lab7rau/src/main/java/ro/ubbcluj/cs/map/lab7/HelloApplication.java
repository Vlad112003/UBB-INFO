package ro.ubbcluj.cs.map.lab7;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ro.ubbcluj.cs.map.lab7.controller.UserController;
import ro.ubbcluj.cs.map.lab7.domain.User;
import ro.ubbcluj.cs.map.lab7.repository.Repository;
import ro.ubbcluj.cs.map.lab7.repository.UserDatabase;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
            Repository<UUID, User> repoUser = new UserDatabase("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "piupiu");

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/users-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            UserController userController = fxmlLoader.getController();
            userController.setUserRepo(repoUser);
            stage.setTitle("Users");
            stage.setScene(scene);
            stage.show();
    }



    public static void main(String[] args) {
        launch();
    }

}
