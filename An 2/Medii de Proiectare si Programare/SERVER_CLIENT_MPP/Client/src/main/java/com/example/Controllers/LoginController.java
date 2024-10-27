package com.example.Controllers;

import com.example.IService;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.example.Domain.Admin;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Optional;

public class LoginController {

    private IService server;
    private Stage primaryStage;

    public void setServer(IService server) {
        this.server = server;
    }

    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }


    @FXML
    private TextField username = new TextField();


    @FXML
    private TextField password = new TextField();


    @FXML
    public void login(){
        if(this.password.getText().isEmpty() || this.username.getText().isEmpty()) {
            Alert message1 = new Alert(Alert.AlertType.INFORMATION);
            message1.setTitle("ERROR!");
            message1.setContentText("   YOU MUST INPUT AN USERNAME AND A PASSWORD!");
            message1.showAndWait();
        }
        else{
            String nume = this.username.getText();
            String parola = this.password.getText();
            System.out.println("BWAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA AL2LEA LOGIN??????");
            Admin a = this.server.findAngajatByUsername(nume,parola);
            if(a==null){
                Alert message = new Alert(Alert.AlertType.INFORMATION);
                message.setTitle("ERROR!");
                message.setContentText("   WRONG USERNAME OR PASSWORD!");
                message.showAndWait();
                return;
            }
            if(a.getUsername().equals(nume) && a.getPassword().equals(parola)){
//                Alert message = new Alert(Alert.AlertType.INFORMATION);
//                message.setTitle("ADMIN CONNECTED!!!!!!!!!!!!!!!!!!!!!!!!!11");
//                message.setContentText("WELCOME ADMIN!");
//                message.setContentText("Your message here");
//                message.show();
//                PauseTransition pause = new PauseTransition(Duration.seconds(5));
//                pause.setOnFinished(event -> {
//                    message.close();
//                });
                try {
                    FXMLLoader new_loader = new FXMLLoader(getClass().getClassLoader().getResource("contest.fxml"));
                    System.out.println(new_loader.getLocation());
                    System.out.println("new_loader: " + new_loader);
                    System.out.println(new_loader.getLocation());
                    Scene root =new Scene( new_loader.load());

                    // Create the dialog Stage.
                    Stage dialogStage = new Stage();
                    dialogStage.setTitle("Logged In");
                    dialogStage.initModality(Modality.WINDOW_MODAL);
                    //dialogStage.initOwner(primaryStage);
                    dialogStage.setScene(root);

                    ContestController contestController = new_loader.getController();
                    contestController.setServer(server);
                    contestController.setAdmin(a);
                    contestController.setStage(dialogStage);

                    dialogStage.show();
                    username.clear();;
                    password.clear();


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
