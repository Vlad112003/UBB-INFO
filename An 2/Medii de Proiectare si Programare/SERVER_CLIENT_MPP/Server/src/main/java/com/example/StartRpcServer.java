package com.example;

import com.example.Domain.Participant;
import com.example.Repository.*;
import com.example.Utils.AbstractServer;
import com.example.Utils.RpcConcurentServer;
import com.example.Utils.ServerException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class StartRpcServer {
    private static final int defaultPort = 55555;
    public static void main(String[] args) {
        System.out.println("Server starting ...");
        Properties serverProps = new Properties();
        try {
            serverProps.load(StartRpcServer.class.getResourceAsStream("/swimming.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find agentieserver.properties "+e);
            return;
        }
        AdminRepo adminRepo = new AdminRepo(serverProps);
        ContestRepo contestRepo = new ContestRepo(serverProps);
        ParticipantRepo participantRepo = new ParticipantRepo(serverProps);
        ParticipationRepo participationRepo = new ParticipationRepo(serverProps);
        IService agentieServerImpl = new ServiceImpl(adminRepo, contestRepo, participantRepo, participationRepo);
        int chatServerPort = defaultPort;
        try {
            chatServerPort = Integer.parseInt(serverProps.getProperty("agentie.server.port"));
        } catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number" + nef.getMessage());
            System.err.println("Using default port " + defaultPort);
        }
        System.out.println("Starting server on port: " + chatServerPort);
        AbstractServer server = new RpcConcurentServer(chatServerPort, agentieServerImpl);
        try {
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        } finally {
            try {
                server.stop();
            } catch(ServerException e){
                System.err.println("Error stopping server " + e.getMessage());
            }
        }
    }
}
