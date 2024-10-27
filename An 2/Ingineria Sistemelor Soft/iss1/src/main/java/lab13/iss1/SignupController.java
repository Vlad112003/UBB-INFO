package lab13.iss1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lab13.iss1.Domain.User;
import lab13.iss1.Service.Service;

import java.io.IOException;

public class SignupController {
    private Stage stage;
    private Service service;

    public void setService(Stage stage, Service service){
        this.stage = stage;
        this.service = service;
    }

    @FXML
    Button exitButton;
    @FXML
    Button registerButton;

    @FXML
    TextField emailField;
    @FXML
    TextField usernameField;
    @FXML
    TextField passwordField;

    public void onPressRegister() {
        String email = emailField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        try {
            service.signup(email, username, password);
        }
        catch (Exception e){
            System.out.println("User already exists");
            return;
        }
        User user  = service.login(email, password);
        if (user!=null){
        try{
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("user-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("GameStore");
            stage.setScene(scene);
            stage.show();
            this.stage.hide();
        }
        catch(IOException e){
            System.out.println("Error loading scene");
        }
    }
        else{
            System.out.println("User already exists");
        }
    }

    public void onPressExit() {
        this.stage.close();
    }
}
