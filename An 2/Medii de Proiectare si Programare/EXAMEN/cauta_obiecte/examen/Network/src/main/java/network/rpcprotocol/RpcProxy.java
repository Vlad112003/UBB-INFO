package network.rpcprotocol;

import model.DTOFinishedGame;
import model.Game;
import model.Player;
import services.IObserver;
import services.IService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RpcProxy implements IService {
    private final String host;
    private final int port;

    private IObserver client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private final BlockingQueue<Response> responses;
    private volatile boolean finished;

    public RpcProxy(String host, int port) {
        this.host = host;
        this.port = port;
        responses = new LinkedBlockingQueue<>();
    }

    private void sendRequest(Request request) throws Exception {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new Exception("Error sending object " + e);
        }
    }

    private Response readResponse() throws Exception {
        Response response;
        try {
            response = responses.take();
        } catch (InterruptedException e) {
            throw new Exception("Error reading object " + e);
        }
        return response;
    }

    private void initializeConnection() {
        try {
            connection = new Socket(host, port);
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReader() {
        Thread tw = new Thread(new ReaderThread());
        tw.start();
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

    private boolean isUpdate(Response response) {
        return response.type() == ResponseType.GAME_FINISHED;
    }

    private void handleUpdate(Response response) throws Exception {
        Collection<Game> finishedGame = (Collection<Game>) response.data();
        client.gameFinished(finishedGame);
    }

    private class ReaderThread implements Runnable {
        public void run() {
            while (!finished) {
                try {
                    Object response = input.readObject();
                    System.out.println("response received " + response);
                    if (isUpdate((Response) response)) {
                        handleUpdate((Response) response);
                    } else {
                        try {
                            responses.put((Response) response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Reading error " + e);
                }
            }
        }
    }

    @Override
    public Player login(Player player, IObserver client) throws Exception {
        initializeConnection();
        Request request = new Request.Builder().type(RequestType.LOGIN).data(player).build();
        sendRequest(request);
        Response response = readResponse();
        switch (response.type()) {
            case OK -> {
                this.client = client;
                return (Player) response.data();
            }
            case ERROR -> {
                String error = response.data().toString();
                closeConnection();
                throw new Exception(error);
            }
        }
        return null;
    }

    @Override
    public void logout(Player player) {
        Request request = new Request.Builder().type(RequestType.LOGOUT).data(player).build();
        try {
            sendRequest(request);
            Response response = readResponse();
            closeConnection();
            switch (response.type()) {
                case OK -> {
                }
                case ERROR -> {
                    String error = response.data().toString();
                    throw new Exception(error);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<Game> getScores() throws Exception {
        Request request = new Request.Builder().type(RequestType.GET_SCORES).build();
        sendRequest(request);
        Response response = readResponse();
        switch (response.type()) {
            case OK -> {
                return (Collection<Game>) response.data();
            }
            case ERROR -> {
                String error = response.data().toString();
                System.out.println(error);
            }
        }
        return null;
    }

    @Override
    public String guess(int id, int guess) {
        Request request = new Request.Builder().type(RequestType.GUESS).data(new int[]{id, guess}).build();
        try {
            sendRequest(request);
            Response response = readResponse();
            switch (response.type()) {
                case OK -> {
                    return (String) response.data();
                }
                case ERROR -> {
                    String error = response.data().toString();
                    throw new Exception(error);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public DTOFinishedGame getFinishedGameInfo(Integer id) {
        Request request = new Request.Builder().type(RequestType.GET_FINISHED_GAME_INFO).data(id).build();
        try {
            sendRequest(request);
            Response response = readResponse();
            switch (response.type()) {
                case OK -> {
                    return (DTOFinishedGame) response.data();
                }
                case ERROR -> {
                    String error = response.data().toString();
                    throw new Exception(error);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}