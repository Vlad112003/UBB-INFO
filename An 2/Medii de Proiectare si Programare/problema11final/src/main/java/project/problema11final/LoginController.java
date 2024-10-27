package project.problema11final;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import project.problema11final.Domain.User;
import project.problema11final.Service.Service;

import java.io.IOException;

public class LoginController {

    @FXML
    Button logInButton;
    private Stage stage;
    private Service service;
    @FXML
    TextField usernameField;
    @FXML
    TextField passField;
    public void setService(Stage primaryStage, Service service) {
        this.stage = primaryStage;
        this.service = service;
    }

    public void onLogInButtonClicked() throws IOException {
        String username = usernameField.getText();
        String password = passField.getText();
        if (service.LogIn(username, password)){
            System.out.println("Log in successful");
            EnterAccount(service.getUserByUsername(username));
        } else {
            System.out.println("Log in failed");
        }
    }

    public void EnterAccount(User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/home-view.fxml"));
        Pane myPane = (Pane) loader.load();
        HomeController ctrl = loader.getController();
        ctrl.setService(stage, service, user);
        Scene myScene = new Scene(myPane);
        stage.setScene(myScene);
        stage.show();
    }
}
