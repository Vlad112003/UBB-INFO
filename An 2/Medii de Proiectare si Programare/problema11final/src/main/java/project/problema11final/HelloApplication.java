package project.problema11final;

import project.problema11final.Domain.Entry;
import project.problema11final.Domain.Participant;
import project.problema11final.Domain.Race;
import project.problema11final.Domain.User;
import project.problema11final.Repository.EntryDB;
import project.problema11final.Repository.ParticipantDB;
import project.problema11final.Repository.RaceDB;
import project.problema11final.Repository.UserDB;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import project.problema11final.Service.DTOs.RaceDTO;
import project.problema11final.Service.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

public class HelloApplication extends Application{
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
//        User admin = new User("admin", "admin");
//        UUID id = UUID.randomUUID();
//        admin.setId(id);
//        userDB.save(admin);

        RaceDB raceDB=new RaceDB(serverProps);
        ParticipantDB participantDB = new ParticipantDB(serverProps);
        EntryDB entryDB = new EntryDB(serverProps);

        Service service = new Service(userDB, raceDB, entryDB, participantDB);

//        Race race = new Race(250);
//        UUID id = UUID.randomUUID();
//        race.setId(id);
//        raceDB.save(race);


//        Participant P = new Participant("dirt", "mcrae", 250);
//        UUID id = UUID.randomUUID();
//        P.setId(id);
//        participantDB.save(P);

//        Entry e = new Entry(UUID.fromString("b3edcbb3-1145-4b9b-a566-4b9029224adc"),UUID.fromString("38d4a691-b551-414e-a5d2-f8e78c327fac"));
//        UUID id = UUID.randomUUID();
//        e.setId(id);
//        entryDB.save(e);

//        Iterable<RaceDTO> raceDTOS = service.racesDTO();
//        for (RaceDTO r : raceDTOS){
//            System.out.println(r.toString());
//        }



        FXMLLoader loader=new FXMLLoader(getClass().getResource("/login-view.fxml"));
        AnchorPane myPane =  loader.load();
        LoginController ctrl=loader.getController();

        ctrl.setService(primaryStage, service);

        Scene myScene = new Scene(myPane);
        primaryStage.setScene(myScene);


        primaryStage.show();
    }
}
