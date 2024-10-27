package org.charity;

import org.charity.observer.ServerObserver;
import org.charity.service.AuthenticationService;
import org.charity.service.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class CharityConcurrentServer implements ServerObserver {

    Service service;
    AuthenticationService authenticationService;
    private static final Logger logger= LogManager.getLogger(CharityConcurrentServer.class);
    ServerSocket serverSocket;
    Timer timer = new Timer();
    Long timeoutSeconds;
    boolean running = true;

    public CharityConcurrentServer(Service service, AuthenticationService authenticationService) {
        this.service = service;
        this.authenticationService = authenticationService;
        try {
            InetAddress address = InetAddress.getByName("127.0.0.1");
            timeoutSeconds = 1500L;
            serverSocket = new ServerSocket(12345, 50, address);
            logger.info("Server created successfully");
            acceptClients();
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void restartTimeOutCount() {
        startTimer();
    }

    public void acceptClients() {
        startTimer();
        while (running) {
            try {
                service.setServerObserver(this);
                logger.info("Waiting for clients");
                Socket clientSocket = serverSocket.accept();
                timer.cancel();
                CharityClientWorker concursClientWorker = new CharityClientWorker(clientSocket, service, authenticationService);
                Thread th = new Thread(concursClientWorker);
                th.start();
            } catch (Exception e) {
                logger.error(e);
            }
        }
    }

    private void startTimer(){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                logger.info("Timeout reached. Closing server.");
                running = false;
                try {
                    serverSocket.close();
                } catch (Exception e) {
                    logger.error(e);
                }
            }
        }, timeoutSeconds * 1000);
    }

}
