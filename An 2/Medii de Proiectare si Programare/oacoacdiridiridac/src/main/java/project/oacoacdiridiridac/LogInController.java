package project.oacoacdiridiridac;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import project.oacoacdiridiridac.Domain.User;
import project.oacoacdiridiridac.HomeController;
import project.oacoacdiridiridac.Service.Service;

import java.io.IOException;

public class LogInController {
    private Service service;

    @FXML
    TextField usernameField;
    @FXML
    PasswordField passField;

    Stage primaryStage = new Stage();


    public void setService(Stage primaryStage, Service service) {
        this.service = service;
    }


    public void onLogInButtonClicked(ActionEvent actionEvent) throws IOException {
        String email = usernameField.getText();
        String password = passField.getText();
        if (service.LogIn(email, password)) {
            System.out.println("Log in successful");
            EnterAccount(service.getUser(email));
        } else {
            System.out.println("Log in failed");
        }
    }

    private void EnterAccount(User user) throws IOException {

        FXMLLoader loader=new FXMLLoader(getClass().getResource("home-view.fxml"));
        Pane myPane = (Pane) loader.load();
        HomeController ctrl=loader.getController();
        ctrl.setService(primaryStage,service,user);
        Scene myScene = new Scene(myPane);
        primaryStage.setScene(myScene);
        primaryStage.show();
    }
}
