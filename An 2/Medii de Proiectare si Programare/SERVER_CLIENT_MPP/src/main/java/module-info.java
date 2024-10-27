module com.example.server_client_mpp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.server_client_mpp to javafx.fxml;
    exports com.example.server_client_mpp;
}