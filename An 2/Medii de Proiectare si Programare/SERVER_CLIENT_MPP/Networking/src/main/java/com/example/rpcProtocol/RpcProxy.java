package com.example.rpcProtocol;

import com.example.Domain.*;
import com.example.IObserver;
import com.example.IService;
import com.example.dto.dtoContest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RpcProxy implements IService {
    private static final int TIMEOUT_IN_MILLISECONDS = 500;
    private final String host;
    private final int port;

    private IObserver client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private final BlockingQueue<Response> qresponses;
    private volatile boolean finished;
    public RpcProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses = new LinkedBlockingQueue<>();
    }


    @Override
    public void login(Admin angajat, IObserver client)  {
        System.out.println("Initizlizez conexiunea pentru client PROXY");
        System.out.println("Fac req de log in catre worker PROXY");
        System.out.println("SUNT IN LOGIN PROXY \n\n\n\n\n\n");
        Request req = new Request.Builder().type(RequestType.LOGIN).data(angajat).build();
        sendRequest(req);
        Response response = readResponse();
        System.out.println("Am primit raspuns " + response.type() + "PROXY");
        if (response.type() == ResponseType.OK) {
            this.client = client;
            return;
        }
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            System.out.println("Inchid conexiune: " + err + "PROXY");
            closeConnection();
        }
    }

    @Override
    public void logout(Admin angajat, IObserver client) {
        Request req = new Request.Builder().type(RequestType.LOGOUT).data(angajat).build();
        sendRequest(req);
        Response response = readResponse();
        closeConnection();
        if (response.type() == ResponseType.ERROR){
            String err = response.data().toString();
            System.out.println(err);
        }
    }

    @Override
    public Admin findAngajatByUsername(String username,String password) {
        initializeConnection();
        List<String> list = List.of(username,password);
        Request req = new Request.Builder().type(RequestType.FIND_ADMIN).data(list).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            System.out.println(err);
        }
        return  (Admin) response.data();
    }

    @Override
    public Iterable<Contest> findAllContest() {
        Request req = new Request.Builder().type(RequestType.FIND_CONTEST).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            System.out.println(err);
        }
        return (Iterable<Contest>) response.data();
    }

    @Override
    public Iterable<Contest> getContests(UUID id) {
        Request req = new Request.Builder().type(RequestType.GET_CONTESTS).data(id).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            System.out.println(err);
        }
        return (Iterable<Contest>) response.data();
    }

    @Override
    public Iterable<Participation> findAllParticipations() {
        Request req = new Request.Builder().type(RequestType.FIND_PARTICIPATION).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            System.out.println(err);
        }
        return (Iterable<Participation>) response.data();
    }

    @Override
    public int getNrParticipanti(UUID id) {
        Request req = new Request.Builder().type(RequestType.GET_NR_PARTICIPANT).data(id).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            System.out.println(err);
        }
        return (int) response.data();
    }

    @Override
    public Iterable<Participant> findAllParticipants() {
        Request req = new Request.Builder().type(RequestType.FIND_PARTICIPANT).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            System.out.println(err);
        }
        return (Iterable<Participant>) response.data();
    }


    @Override
    public void addParticipation(Participation participation) {
        Request req = new Request.Builder().type(RequestType.ADD).data(participation).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            System.out.println(err);
        }
    }


    public void initializeConnection() {
        try {
            connection = new Socket(host,port);// Set connection timeout
            System.out.println(connection);
            System.out.println("Local Address: " + connection.getLocalAddress());
            System.out.println("Local Port: " + connection.getLocalPort());
            System.out.println("Remote Address: " + connection.getInetAddress());
            System.out.println("Remote Port: " + connection.getPort());
            //connection.setSoTimeout(TIMEOUT_IN_MILLISECONDS); // Set socket read timeout

            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            System.out.println("Am initializat conexiunea PROXY");
            input = new ObjectInputStream(connection.getInputStream());
            System.out.println("Am initializat inputul PROXY");
            finished = false;

            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        finished = true;
        try {
            input.close();
            output.close();
            connection.close();
            client = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendRequest(Request request) {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private Response readResponse() {
        Response response = null;
        try{
            response = qresponses.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void startReader(){
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }

    private void handleUpdate(Response response) {
        if (response.type().equals(ResponseType.UPDATE_OBSERVER)) {
            System.out.println("Incerc sa modific observerele din PROXY");
            client.init_model();
        }
    }

    private boolean isUpdate(Response response){
        return response.type() == ResponseType.UPDATE_OBSERVER;
    }

    private class ReaderThread implements Runnable {
        public void run() {
            while(!finished){
                try {
                    Object response = input.readObject();
                    System.out.println("response received " + response);
                    if (isUpdate((Response) response)){
                        handleUpdate((Response) response);
                    } else {
                        try {
                            qresponses.put((Response) response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Reading error " + e);
                }
            }
        }
    }
}