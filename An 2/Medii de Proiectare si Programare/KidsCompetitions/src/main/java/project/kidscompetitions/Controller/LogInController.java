package project.kidscompetitions.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import project.kidscompetitions.Domain.Employee;
import project.kidscompetitions.Service.Service;

import java.io.IOException;
import java.util.Optional;

public class LogInController {
    @FXML
    TextField usernameField;
    @FXML
    PasswordField passField;
    private Service service;
    Stage primaryStage;

    public void setService(Stage primaryStage ,Service service) {
        this.service=service;
        this.primaryStage=primaryStage;
    }
    public void onLogInButtonClicked(ActionEvent actionEvent) throws IOException {
        String user =usernameField.getText();
        String pass=passField.getText();
        Optional<Employee> result=service.findUser(user,pass);
        if(result.isPresent())
            EnterAccount(result.get());
        else
            MessageAlert.showMessage(null, Alert.AlertType.ERROR,"Something went erong","Username or password wrong...");
    }

    private void EnterAccount(Employee employee) throws IOException {

        primaryStage.setTitle("FXML TableView Example");
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/office-view.fxml"));
        Pane myPane = (Pane) loader.load();
        OfficeController ctrl=loader.getController();

        ctrl.setService(primaryStage,service,employee);
        Scene myScene = new Scene(myPane);
        primaryStage.setScene(myScene);
        primaryStage.show();
    }
}