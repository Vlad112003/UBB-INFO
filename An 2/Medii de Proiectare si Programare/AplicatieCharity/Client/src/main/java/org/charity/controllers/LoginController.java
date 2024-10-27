package org.charity.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.charity.CharityProxy;

import java.io.IOException;
import java.net.URL;

public class LoginController {
    URL charitableCasesPage;
    Stage loginStage;

    CharityProxy charityProxy;
    private static final Logger logger = LogManager.getLogger(LoginController.class);
    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;
    @FXML
    Button loginButton;

    public void setProxy(CharityProxy charityProxy, URL charitableCasesPage, Stage loginStage){
        this.charityProxy = charityProxy;
        this.charitableCasesPage = charitableCasesPage;
        this.loginStage = loginStage;
    }

    public void handleLogin(){
        logger.traceEntry("handleLogin");
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (charityProxy.logIn(username, password)){
            openVolunteerGui();
        } else {
            passwordField.setText("");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Username or password is incorrect. Please try again.");

            alert.showAndWait();
        }
        logger.traceExit();
    }

    public void openVolunteerGui() {
        try {
            logger.traceEntry("openVolunteerGui");
            System.out.println("Login successful!");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(charitableCasesPage);

            AnchorPane root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Charitable cases");
            stage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(root);
            stage.setScene(scene);

            CharitableCasesController charitableCasesConroller = loader.getController();
            charitableCasesConroller.setProxy(charityProxy, loginStage, stage);
            loginStage.close();
            logger.traceExit();

            stage.show();
        } catch (IOException e){
        logger.error(e);
        }
    }


}
