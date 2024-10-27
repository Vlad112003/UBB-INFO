package client;

import client.controllers.LogInController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import network.rpcprotocol.RpcProxy;
import services.IService;

import java.io.IOException;
import java.util.Properties;

public class StartClient extends Application {
    private final static int defaultPort = 55555;
    private final static String defaultServer = "localhost";

    @Override
    public void start(Stage primaryStage) throws Exception {
        Properties clientProperties = new Properties();
        try {
            clientProperties.load(StartClient.class.getResourceAsStream("/client.properties"));
            clientProperties.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find client.properties " + e);
            return;
        }

        String serverIP = clientProperties.getProperty("server.host", defaultServer);
        int serverPort = defaultPort;
        try {
            serverPort = Integer.parseInt(clientProperties.getProperty("server.port"));
        } catch (NumberFormatException ex) {
            System.err.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port: " + defaultPort);
        }

        IService service = new RpcProxy(serverIP, serverPort);

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("client/LogInView.fxml"));
        Parent root = loader.load();

        LogInController loginController = loader.getController();
        loginController.setService(service);

        primaryStage.setTitle("Log in");
        primaryStage.setScene(new Scene(root, 380, 205));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}