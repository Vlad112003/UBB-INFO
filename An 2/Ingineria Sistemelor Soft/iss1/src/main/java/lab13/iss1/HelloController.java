package lab13.iss1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lab13.iss1.Domain.User;
import lab13.iss1.Service.Service;

import java.io.IOException;

public class HelloController {

    private Service service;

    private Stage stage;


    @FXML
    Button exitButton;
    @FXML
    Button loginButton;
    @FXML
    Button signupButton;


    @FXML
    public void onPressExit(){
        System.exit(0);
    }

    @FXML
    public void onPressLogin(){
        try{
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Login");
            stage.setScene(scene);
            LoginController ctrl = fxmlLoader.getController();
            ctrl.setService(stage, service);
            stage.show();
            this.stage.hide();

        }
        catch(IOException e){
            System.out.println("Error loading scene");
        }
    }

    @FXML
    public void onPressSignup(){
        try{
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("signup-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            stage.setTitle("Login");
            stage.setScene(scene);
            stage.show();
            SignupController ctrl = fxmlLoader.getController();
            ctrl.setService(stage, service);
            this.stage.hide();
        }
        catch(IOException e){
            System.out.println("Error loading scene");
        }
    }

    public void setService(Stage stage, Service service){
        this.stage = stage;
        this.service = service;
    }

}