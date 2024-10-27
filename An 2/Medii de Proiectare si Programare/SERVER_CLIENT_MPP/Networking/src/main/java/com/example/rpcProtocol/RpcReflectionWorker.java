package com.example.rpcProtocol;

import com.example.Domain.Admin;
import com.example.Domain.Contest;
import com.example.Domain.Participant;
import com.example.Domain.Participation;
import com.example.IObserver;
import com.example.IService;
import com.example.dto.dtoContest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RpcReflectionWorker implements Runnable, IObserver {
    private final IService server;
    private final Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;
    public RpcReflectionWorker(IService server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try{
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        while(connected){
            try {
                Object request = input.readObject();
                Response response = handleRequest((Request) request);
                if (response != null){
                    sendResponse(response);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error " + e);
        }
    }

    private Response handleRequest(Request request){
        Response response = null;
        String handlerName = "handle" + (request).type();
        System.out.println("HandlerName " + handlerName);
        try {
            Method method = this.getClass().getDeclaredMethod(handlerName, Request.class);
            response = (Response) method.invoke(this, request);
            System.out.println("Method " + handlerName + " invoked");
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void sendResponse(Response response) throws IOException {
        System.out.println("sending response " + response);
        synchronized (output) {
            output.writeObject(response);
            output.flush();
        }
    }

    @Override
    public void init_model()  {
        System.out.println("Trimit raspuns catre proxy pentru observer");
        Response response = new Response.Builder().type(ResponseType.UPDATE_OBSERVER).build();
        try {
            sendResponse(response);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public Response handleFIND_ADMIN(Request request) {
            List<String> list = (List<String>) request.data();
            Admin angajat = server.findAngajatByUsername(list.get(0), list.get(1));
            if (angajat!=null) {
                return new Response.Builder().type(ResponseType.OK).data(angajat).build();
            }
            return new Response.Builder().type(ResponseType.ERROR).build();
    }

    public Response handleFIND_CONTEST(Request request) {
            Iterable<Contest> toateCompetitiile = server.findAllContest();
            return new Response.Builder().type(ResponseType.OK).data(toateCompetitiile).build();
    }


    public Response handleADD(Request request) {
            server.addParticipation((Participation) request.data());
            return new Response.Builder().type(ResponseType.OK).build();
    }


    public Response handleGET_CONTESTS(Request request) {
        Iterable<Contest> toateCompetitiile = server.getContests((UUID) request.data());
        return new Response.Builder().type(ResponseType.OK).data(toateCompetitiile).build();
    }



    public Response handleFIND_PARTICIPATION(Request request) {
        Iterable<Participation> toateParticiparile = server.findAllParticipations();
        return new Response.Builder().type(ResponseType.OK).data(toateParticiparile).build();
    }


    public Response handleGET_NR_PARTICIPANT(Request request) {
            int nrParticipanti = server.getNrParticipanti((UUID) request.data());
            return new Response.Builder().type(ResponseType.OK).data(nrParticipanti).build();
    }

    public Response handleFIND_PARTICIPANT(Request request) {
        Iterable<Participant> toateParticiparile = server.findAllParticipants();
        return new Response.Builder().type(ResponseType.OK).data(toateParticiparile).build();
    }


    public Response handleLOGIN(Request request) {
        try {
            Admin angajat = (Admin) request.data();
            server.login(angajat, this);
            connected = true;
            return new Response.Builder().type(ResponseType.OK).build();
        } catch (Exception e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }


    public Response handleLOGOUT(Request request) {
            Admin angajat = (Admin) request.data();
            server.logout(angajat, this);
            connected = false;
            return new Response.Builder().type(ResponseType.OK).build();
    }

}
