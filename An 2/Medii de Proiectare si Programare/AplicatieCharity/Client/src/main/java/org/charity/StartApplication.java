package org.charity;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.charity.controllers.LoginController;


import java.io.IOException;
import java.net.URL;

public class StartApplication extends Application {
    private static final Logger logger = LogManager.getLogger(StartApplication.class);


    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        try {
            CharityProxy charityProxy = new CharityProxy();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/loginPage.fxml"));
            AnchorPane layout = loader.load();
            primaryStage.setScene(new Scene(layout));


            URL charitableCasesPage = getClass().getResource("/charitableCases.fxml");
            LoginController loginController = loader.getController();
            loginController.setProxy(charityProxy, charitableCasesPage, primaryStage);
            primaryStage.setWidth(800);
            primaryStage.show();
        }
        catch (IOException e){
            logger.error(e);
        }
    }
}