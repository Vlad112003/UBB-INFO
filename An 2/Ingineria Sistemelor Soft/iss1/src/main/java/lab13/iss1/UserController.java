package lab13.iss1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lab13.iss1.Domain.User;
import lab13.iss1.Service.Service;

public class UserController {
    private Service service;
    private Stage stage;
    private User user;

    public void setService(Stage stage, Service service, User user){
        this.stage = stage;
        this.service = service;
        this.user=user;
    }
    @FXML
    Button logoutButton;
    @FXML
    Button storeButton;
    @FXML
    Button libraryButton;

    public void onPressLogout(){
        stage.close();
    }

    public void onPressStore() {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("store-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Store");
            stage.setScene(scene);
            StoreController ctrl = fxmlLoader.getController();
            ctrl.setService(stage, service, this.user);
            stage.show();
        } catch (Exception e) {
            System.out.println("Error loading scene");
        }
    }

    public void onPressLibrary() {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("library-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Library");
            stage.setScene(scene);
            LibraryController ctrl = fxmlLoader.getController();
            ctrl.setService(stage, service, this.user);
            stage.show();
        } catch (Exception e) {
            System.out.println("Error loading scene");
        }
    }


}
