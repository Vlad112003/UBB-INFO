package ro.ubbcluj.cs.map.lab72nd.controller;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class MessageAlert {
    public static void showMessage(Stage owner, Alert.AlertType type, String header, String text){
        Alert message=new Alert(type);
        message.setHeaderText(header);
        message.setContentText(text);
        message.initOwner(owner);
        message.showAndWait();
    }

    public static void showErrorMessage(Stage owner, String text){
        Alert message=new Alert(Alert.AlertType.ERROR);
        message.initOwner(owner);
        message.setTitle("Error Message");
        message.setContentText(text);
        message.showAndWait();
    }

    public static void showMessage(Object o, String s) {
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        message.setTitle("Message");
        message.setContentText(s);
        message.showAndWait();

    }
}
