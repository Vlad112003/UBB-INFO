package lab13.iss1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lab13.iss1.Domain.User;
import lab13.iss1.Service.Service;

import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {
    private Service service;

    private Stage stage;

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button exitButton;


    @FXML
    private void onPressLogin(){
        String email = emailField.getText();
        String password = passwordField.getText();
        User user = service.login(email, password);
        if(user != null && user.getUserType().toString().equals("ADMIN")){
            try{
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("admin-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                AdminController ctrl = fxmlLoader.getController();
                ctrl.setService(stage, service);
                stage.setTitle("ADMIN GameStore");
                stage.setScene(scene);
                stage.show();
                emailField.clear();
                passwordField.clear();
//                this.stage.show();
            }
            catch(IOException e){
                System.out.println("Error loading scene");
//                e.printStackTrace();
            }
        }
        else if(user != null && user.getUserType().toString().equals("USER")){
            try{
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("user-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setTitle("GameStore");
                stage.setScene(scene);
                UserController ctrl = fxmlLoader.getController();
                ctrl.setService(stage, service, user);
                stage.show();
                emailField.clear();
                passwordField.clear();
                this.stage.hide();
            }
            catch(IOException e){
                System.out.println("Error loading scene");
            }
        }
        else if(user != null && user.getUserType().toString().equals("PAYMENT")){
            try{
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("payment-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                PaymentController ctrl = fxmlLoader.getController();
                ctrl.setService(stage, service);
                stage.setTitle("GameStore Payment");
                stage.setScene(scene);
                stage.show();
                emailField.clear();
                passwordField.clear();
//                this.stage.hide();
            }
            catch(IOException e){
                System.out.println("Error loading scene");
            }
        }
        else {
            System.out.println("Invalid email or password");
        }
    }

    @FXML
    private void onPressExit(){
        this.stage.close();
    }

    public void setService(Stage stage, Service service){
        this.stage = stage;
        this.service = service;
    }


}
