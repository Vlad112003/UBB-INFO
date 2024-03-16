package ro.ubbcluj.cs.map.lab82nd;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ro.ubbcluj.cs.map.lab82nd.controller.MessageAlert;
import ro.ubbcluj.cs.map.lab82nd.domain.User;
import ro.ubbcluj.cs.map.lab82nd.service.Service;

public class LoginController {
    private Service service;

    private Stage dialogStage;

    @FXML
    TextField emailTextField;
    @FXML
    PasswordField passwordTextField;

    @FXML
    public void initialize() {
    }

    @FXML
    public void onPressLogIn() {
        try {
            service.login(emailTextField.getText(), passwordTextField.getText());
            MessageAlert.showMessage(null, "Logged in!");
            dialogStage.close();

            User user = service.getUserByEmail(emailTextField.getText());

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("views/homepage-view.fxml"));

            TabPane root = (TabPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Home Page");
            dialogStage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            HomePageController homePageController = loader.getController();
            homePageController.setService(service, user, dialogStage);

            dialogStage.show();


        } catch (Exception e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
        }
    }

    @FXML
    public void onPressCancel(){
        dialogStage.close();
    }
    public void setService(Service service, Stage dialogStage) {
        this.service = service;
        this.dialogStage = dialogStage;
    }
    public void onPressBack(){
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

}
