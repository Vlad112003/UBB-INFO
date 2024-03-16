package ro.ubbcluj.cs.map.lab82nd;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ro.ubbcluj.cs.map.lab82nd.controller.MessageAlert;
import ro.ubbcluj.cs.map.lab82nd.service.Service;

public class LandingController {
    private Service service;

    private Stage dialogStage;

    public void setService(Service service, Stage dialogStage) {
        this.service = service;
        this.dialogStage = dialogStage;
    }

    public void onPressLogIn() {
        try{
            this.dialogStage.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("views/login-view.fxml"));

            AnchorPane root = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Login");
            dialogStage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            LoginController loginController = loader.getController();
            loginController.setService(service, dialogStage);

            dialogStage.show();

        }
        catch(Exception e)
        {
            MessageAlert.showErrorMessage(null, e.getMessage());
        }
    }

    public void onPressSignUp(){
        try{
            this.dialogStage.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("views/signup-view.fxml"));

            AnchorPane root = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Sign Up");
            dialogStage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            SignUpController signUpController = loader.getController();
            signUpController.setService(service, dialogStage);

            dialogStage.show();

        }
        catch(Exception e)
        {
            MessageAlert.showErrorMessage(null, e.getMessage());
        }
    }

    @FXML
    public void onPressExit(){
        System.exit(0);
    }
}

