package com.example;

import com.example.Controllers.LoginController;
import com.example.rpcProtocol.RpcProxy;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Properties;

public class StartRpcClientFX extends Application {


    private static final int defaultAgentiePort = 55555;
    private static final String defaultServer = "localhost";


    public void start(Stage primaryStage) throws Exception {
        System.out.println("In start");
        Properties clientProps = new Properties();
        try {
            clientProps.load(StartRpcClientFX.class.getResourceAsStream("/swimmingclientfx.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find swimmingclientfx.properties " + e);
            return;
        } catch (Exception e) {
            System.err.println("Cannot read swimmingclientfx.properties " + e);
        }
        String serverIP = clientProps.getProperty("agentie.server.host", defaultServer);
        int serverPort = defaultAgentiePort;

        try {
            serverPort = Integer.parseInt(clientProps.getProperty("agentie.server.port"));
        } catch (NumberFormatException ex) {
            System.err.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port: " + defaultAgentiePort);
        }
        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);

        IService server = new RpcProxy(serverIP, serverPort);

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("login.fxml"));
        System.out.println(loader);
        System.out.println(loader.getLocation());
        Parent root = loader.load();
        primaryStage.setTitle("Login!");
        primaryStage.setScene(new Scene(root));
        LoginController logInController = loader.getController();
        logInController.setServer(server);
        logInController.setStage(primaryStage);
        primaryStage.show();
    }
}