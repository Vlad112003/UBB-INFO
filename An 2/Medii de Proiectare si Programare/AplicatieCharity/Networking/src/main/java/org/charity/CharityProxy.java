package org.charity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.charity.dto.CharitableDTO;
import org.charity.dto.DTOUtils;
import org.charity.dto.DonorDTO;
import org.charity.dto.VolunteerDTO;
import org.charity.observer.UIObservalbe;
import org.charity.observer.UIObserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

public class CharityProxy implements UIObservalbe {
    Socket socket;
    ObjectInputStream inputStream;
    ObjectOutputStream outputStream;
    private static final Logger logger = LogManager.getLogger(CharityProxy.class.getName());

    private BlockingQueue<Response> qresponses;

    UIObserver uiObserver;

    private boolean finished;

    public CharityProxy() {
        try {
            logger.info("Creating a new client");
            socket = new Socket("127.0.0.1", 12345);
            qresponses = new LinkedBlockingQueue<Response>();
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            finished = false;
            Thread th = new Thread(new ReaderThread());
            th.start();
        } catch (Exception e) {
            logger.error(e);
        }
    }

    private void sendRequest(Request request) {
        try
        {
            outputStream.writeObject(request);
            outputStream.flush();
        }
        catch (IOException e)
        {
            logger.error(e);
        }
    }

    private Response readResponse() {
        Response response=null;
        try
        {
            response=qresponses.take();
        }
        catch (InterruptedException e)
        {
            logger.error(e);
        }
        return response;
    }

    public boolean logIn(String username, String password) {
        try {
            VolunteerDTO volunteerDTO = DTOUtils.getDTO(new Volunteer(username, password));
            Request request = new Request.Builder().type(RequestType.LOGIN).data(volunteerDTO).build();
            sendRequest(request);
            Response response = readResponse();
            return response.type() != ResponseType.ERROR;
        } catch (Exception e) {
            logger.error(e);
        }
        return false;
    }

    public List<CharitableCase> getAllCases() {
        try {
            Request request = new Request.Builder().type(RequestType.GET_ALL_CASES).build();
            sendRequest(request);
            Response response = readResponse();
            if (response.type() == ResponseType.ERROR) {
                return null;
            }
            return ((List<CharitableDTO>) response.data()).stream()
                    .map(DTOUtils::getFromDTO).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    public List<Donor> getDonorsWithFilter(String filter) {
        try {
            Request request = new Request.Builder().type(RequestType.GET_DONORS).data(filter).build();
            sendRequest(request);
            Response response = readResponse();
            if (response.type() == ResponseType.ERROR) {
                return null;
            }
            return ((List<DonorDTO>) response.data()).stream()
                    .map(DTOUtils::getFromDTO).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    public void addDonationExistingDonor(Donation donation) {
        try {
            Request request = new Request.Builder().type(RequestType.ADD_DONATION_EXISTING_DONOR).data(DTOUtils.getDTO(donation)).build();
            sendRequest(request);
            Response response = readResponse();
            if (response.type() == ResponseType.ERROR) {
                throw (Exception) response.data();
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public Integer addDonationNewDonor(Donation donation) {
        try {
            Request request = new Request.Builder().type(RequestType.ADD_DONATION_NEW_DONOR).data(DTOUtils.getDTO(donation)).build();
            sendRequest(request);
            Response response = readResponse();
            if (response.type() == ResponseType.ERROR) {
                throw (Exception) response.data();
            }
            return (Integer) response.data();
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    public void addObserver(UIObserver observer) {
        try {
            Request request = new Request.Builder().type(RequestType.ADD_OBSERVER).build();
            sendRequest(request);
            Response response = readResponse();
            if (response.type() == ResponseType.ERROR) {
                throw (Exception) response.data();
            }
            uiObserver = observer;
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public void logout(){
        try {
            Request request = new Request.Builder().type(RequestType.LOGOUT).build();
            sendRequest(request);
            /*Response response = readResponse();
            if (response.type() == ResponseType.ERROR) {
                throw (Exception) response.data();
            }*/
        } catch (Exception e) {
            logger.error(e);
        }
    }
    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    Object response = inputStream.readObject();
                    logger.info("response received " + response);
                    if (((Response)response).type() == ResponseType.LOGOUT) {
                        disconnect();
                    } else if (((Response)response).type() == ResponseType.UPDATE_OBSERVER) {
                        handleUpdate((Response)response);
                    } else {
                        try {
                            qresponses.put((Response)response);
                        } catch (InterruptedException e) {
                            logger.error(e);
                        }
                    }
                } catch (Exception e) {
                    logger.error(e);
                }
            }
        }
    }

    private void disconnect() {
        finished=true;
        try {
            inputStream.close();
            outputStream.close();
            socket.close();
        }
        catch (Exception e)
        {
            logger.error(e);
        }
    }

    @Override
    public void notifyObserver(List<CharitableCase> cases) {
        uiObserver.update(cases);
    }

    private void handleUpdate(Response response){
        List<CharitableCase> cases = ((List<CharitableDTO>) response.data()).stream()
                .map(DTOUtils::getFromDTO).collect(Collectors.toList());
        notifyObserver(cases);
    }

}
