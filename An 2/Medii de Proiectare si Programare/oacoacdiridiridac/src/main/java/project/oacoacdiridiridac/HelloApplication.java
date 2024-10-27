package project.oacoacdiridiridac;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import project.oacoacdiridiridac.Repository.RaceDB;
import project.oacoacdiridiridac.Repository.TeamDB;
import project.oacoacdiridiridac.Repository.UserDB;
import project.oacoacdiridiridac.Service.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        Properties serverProps=new Properties();
        try {
            serverProps.load(new FileReader("db.config"));
            System.out.println("Properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.out.println("Cannot find db.config "+e);
        }


        UserDB userDB=new UserDB(serverProps);
        TeamDB teamDB=new TeamDB(serverProps);
        RaceDB raceDB=new RaceDB(serverProps);
        Service service=new Service(userDB,teamDB,raceDB);


        FXMLLoader loader=new FXMLLoader(getClass().getResource("login-view.fxml"));
        AnchorPane myPane =  loader.load();
        LogInController ctrl=loader.getController();

        ctrl.setService(primaryStage, service);

        Scene myScene = new Scene(myPane);
        primaryStage.setScene(myScene);


        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}