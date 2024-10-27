package project.kidscompetitions;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import project.kidscompetitions.Controller.LogInController;
import project.kidscompetitions.Repository.CompetitionRepository;
import project.kidscompetitions.Repository.EmployeeRepository;
import project.kidscompetitions.Repository.ParticipantRepository;
import project.kidscompetitions.Repository.SignUpRepository;
import project.kidscompetitions.Service.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("FXML TableView Example");
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/logIn-view.fxml"));
        Pane myPane = (Pane) loader.load();
        LogInController ctrl=loader.getController();

//        ctrl.setService(primaryStage,getService());
        Scene myScene = new Scene(myPane);
        primaryStage.setScene(myScene);


       /* primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                //  System.out.println("Stage is closing");
                ctrl.close();
            }
        });*/
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    static Service getService(){
        Properties serverProps=new Properties();
        try {
            serverProps.load(new FileReader("db.config"));
            System.out.println("Properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
            return null;
        }
        CompetitionRepository athleticsTestsRepository=new CompetitionRepository(serverProps);
        SignUpRepository signUpRepository=new SignUpRepository(serverProps);
        EmployeeRepository employeeRepository=new EmployeeRepository(serverProps);
        ParticipantRepository participantRepository=new ParticipantRepository(serverProps);
        return new Service(athleticsTestsRepository,signUpRepository,employeeRepository,participantRepository);
    }
}

