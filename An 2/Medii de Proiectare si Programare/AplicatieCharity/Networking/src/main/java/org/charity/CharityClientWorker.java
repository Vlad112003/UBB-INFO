package org.charity;

import org.charity.dto.*;
import org.charity.observer.CharityObserver;
import org.charity.service.AuthenticationService;
import org.charity.service.Service;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CharityClientWorker implements Runnable, CharityObserver {
    Socket socket;
    Service service;
    AuthenticationService authenticationService;
    ObjectOutputStream outputStream;
    ObjectInputStream inputStream;
    private boolean connected;
    private static final Logger logger= LogManager.getLogger(CharityClientWorker.class);

    public CharityClientWorker(Socket clientSocket, Service service, AuthenticationService authenticationService){
        this.socket = clientSocket;
        this.authenticationService = authenticationService;
        this.service = service;
        try
        {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            connected = true;
        }
        catch (Exception e)
        {
            logger.error(e);
        }
    }

    @Override
    public void run() {
        while(connected) {
            try {
                Object request=inputStream.readObject();
                Response response=handleRequest((Request)request);
                if(response!=null)
                    sendResponse(response);
            } catch (Exception e) {
                logger.error(e);
            }
        }
    }

    private Response handleRequest(Request request) {
        Response response = null;
        switch (request.type()) {
            case LOGIN: {
                logger.info("Login request ...");
                VolunteerDTO volunteerDTO = (VolunteerDTO) request.data();
                if (authenticationService.login(volunteerDTO.getUsername(), volunteerDTO.getPassword())) {
                    response = new Response.Builder().type(ResponseType.SUCCESS).build();
                    logger.info("Login successful");
                }
                else {
                    response = new Response.Builder().type(ResponseType.ERROR).data("Invalid username or password").build();
                    logger.error("Invalid username or password");
                }
                break;
            }
            case LOGOUT: {
                logger.info("Logout request ...");
                service.removeObserver(this);
                connected = false;
                try {
                    logger.info("Closing connection ...");
                    sendResponse(new Response.Builder().type(ResponseType.LOGOUT).build());
                    outputStream.close();
                    inputStream.close();
                    logger.info("Connection closed");
                } catch (Exception e) {
                    response = new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
                    logger.error(e);
                }
                break;
            }
            case GET_DONORS: {
                logger.info("Get donors request ...");
                String filter = (String) request.data();
                Iterable<Donor> donors = service.getAllDonorsWithFilter(filter);
                List<DonorDTO> donorDTOs = StreamSupport.stream(donors.spliterator(), false)
                        .map(DTOUtils::getDTO)
                        .collect(Collectors.toList());
                try {
                    response = new Response.Builder().type(ResponseType.SUCCESS).data(donorDTOs).build();
                } catch (Exception e) {
                    response = new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
                    logger.error(e);
                }
                break;
            }
            case GET_ALL_CASES: {
                logger.info("Get all cases request ...");
                Iterable<CharitableCase> cases = service.getAllCharitableCases();
                List<CharitableDTO> caseList = StreamSupport.stream(cases.spliterator(),false)
                        .map(DTOUtils::getDTO)
                        .collect(Collectors.toList());
                try {
                    response = new Response.Builder().type(ResponseType.SUCCESS).data(caseList).build();
                } catch (Exception e) {
                    response = new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
                    logger.error(e);
                }
                break;
            }
            case ADD_DONATION_EXISTING_DONOR: {
                DonationDTO donationDTO = (DonationDTO) request.data();
                Donation donation = DTOUtils.getFromDTO(donationDTO);
                try {
                    service.saveDonation(donation);
                    response = new Response.Builder().type(ResponseType.SUCCESS).build();
                } catch (Exception e) {
                    response = new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
                    logger.error(e);
                }
                break;
            }
            case ADD_DONATION_NEW_DONOR: {
                DonationDTO donationDTO = (DonationDTO) request.data();
                Donation donation = DTOUtils.getFromDTO(donationDTO);
                try {
                    response = new Response.Builder().type(ResponseType.SUCCESS).data(service.saveDonationNewDonor(donation)).build();
                } catch (Exception e) {
                    response = new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
                    logger.error(e);
                }
                break;
            }
            case ADD_OBSERVER: {
                logger.info("Add observer request ...");
                try{
                    service.addObserver(this);
                    response = new Response.Builder().type(ResponseType.SUCCESS).build();
                } catch (Exception e) {
                    response = new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
                    logger.error(e);
                }
                break;
            }
        }
        return response;
    }

    private void sendResponse(Response response) {
        try {
            outputStream.writeObject(response);
            outputStream.flush();
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void update(List<CharitableCase> cases) {
        try {
            List<CharitableDTO> caseList = cases.stream()
                    .map(DTOUtils::getDTO)
                    .collect(Collectors.toList());
            sendResponse(new Response.Builder().type(ResponseType.UPDATE_OBSERVER).data(caseList).build());
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
