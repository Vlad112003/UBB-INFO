package client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Player;
import services.IService;

public class LogInController {
    @FXML
    public TextField usernameText;

    private IService service;
    private final Stage stage = new Stage();

    public void setService(IService service) {
        this.service = service;
    }

    public void loginAction() {
        String username = usernameText.getText();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("client/MainView.fxml"));
            Parent parent = loader.load();
            MainController mainController = loader.getController();

            Player player = service.login(new Player(username), mainController);
            if (player == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong username", ButtonType.OK);
                alert.show();
                return;
            }

            mainController.setService(service);
            mainController.setLoggedInPlayer(player);
            mainController.initialise();

            stage.setTitle("Game");
            if (stage.getScene() == null) {
                stage.setScene(new Scene(parent, 440, 450));
            }

            Stage currentStage = (Stage) usernameText.getScene().getWindow();
            stage.show();
            currentStage.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.show();
        }
    }
}