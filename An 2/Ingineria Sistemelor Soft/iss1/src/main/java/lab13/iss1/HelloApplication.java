package lab13.iss1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lab13.iss1.Domain.Game;
import lab13.iss1.Domain.User;
import lab13.iss1.Domain.UserType;
import lab13.iss1.Repository.GameDB;
import lab13.iss1.Repository.OrderDB;
import lab13.iss1.Repository.UserDB;
import lab13.iss1.Service.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        Properties serverProps=new Properties();
        try {
            serverProps.load(new FileReader("db.config"));
            System.out.println("Properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.out.println("Cannot find db.config "+e);
        }

        UserDB userDB = new UserDB(serverProps);
        GameDB gameDB = new GameDB(serverProps);
        OrderDB orderDB = new OrderDB(serverProps);
        Service service = new Service(userDB,gameDB, orderDB);



        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloController ctrl = fxmlLoader.getController();
        stage.setTitle("GameStore");
        stage.setScene(scene);
        ctrl.setService(stage, service);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}