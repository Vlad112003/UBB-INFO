package ro.mpp.javaMPPServer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JavaType;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

class MotorcycleRaceClient {
    static HttpClient client = HttpClient.newHttpClient();
    static ObjectMapper objectMapper = new ObjectMapper();
    static String BASE_URL = "http://localhost:8080";

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        try {
            // ---------- add race -------------
            MotorcycleRaceDTO newRace = new MotorcycleRaceDTO("Test Race", 1000, 10);
            MotorcycleRaceResponseDTO addedRace = addMotorcycleRace(newRace);
            assert newRace.getName().equals(addedRace.getName());
            assert newRace.getEngineCapacity() == addedRace.getEngineCapacity();
            assert newRace.getParticipantsCount() == addedRace.getParticipantsCount();

            // ---------- update race -------------
            addedRace.setEngineCapacity(1200);
            MotorcycleRaceResponseDTO updatedRace = updateMotorcycleRace(addedRace);
            assert updatedRace.getEngineCapacity() == 1200;
            assert updatedRace.getName().equals(addedRace.getName());
            assert updatedRace.getParticipantsCount() == addedRace.getParticipantsCount();

            // ---------- find by id race -------------
            MotorcycleRaceResponseDTO foundRace = findMotorcycleRace(updatedRace.getId());
            assert foundRace.getEngineCapacity() == 1200;
            assert foundRace.getName().equals(addedRace.getName());
            assert foundRace.getParticipantsCount() == addedRace.getParticipantsCount();

            // ---------- get all races -------------
            addMotorcycleRace(new MotorcycleRaceDTO("Test Race2", 1000, 10));
            addMotorcycleRace(new MotorcycleRaceDTO("Test Race3", 1500, 10));
            List<MotorcycleRaceResponseDTO> allRaces = getAllMotorcycleRaces();
            assert allRaces.size() == 3;

            // ---------- delete race -------------
            boolean deleted = deleteMotorcycleRace(updatedRace.getId());
            assert deleted;
            System.out.println("5/5 tests passed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static MotorcycleRaceResponseDTO addMotorcycleRace(MotorcycleRaceDTO race) throws InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/motorcycle-race"))
                .POST(BodyPublishers.ofString(convertObjectToJson(race)))
                .header("Content-Type", "application/json")
                .build();
        return sendRequest(request, MotorcycleRaceResponseDTO.class);
    }

    static MotorcycleRaceResponseDTO updateMotorcycleRace(MotorcycleRaceResponseDTO race) throws InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/motorcycle-race/" + race.getId()))
                .PUT(BodyPublishers.ofString(convertObjectToJson(race)))
                .header("Content-Type", "application/json")
                .build();
        return sendRequest(request, MotorcycleRaceResponseDTO.class);
    }

    static MotorcycleRaceResponseDTO findMotorcycleRace(UUID id) throws InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/motorcycle-race/" + id))
                .GET()
                .build();
        return sendRequest(request, MotorcycleRaceResponseDTO.class);
    }

    static List<MotorcycleRaceResponseDTO> getAllMotorcycleRaces() throws InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/motorcycle-race"))
                .GET()
                .build();
        return sendRequest(request, new TypeReference<List<MotorcycleRaceResponseDTO>>() {});
    }

    static boolean deleteMotorcycleRace(UUID id) throws InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/motorcycle-race/" + id))
                .DELETE()
                .build();
        return sendRequest(request, Boolean.class);
    }

    static <T> T sendRequest(HttpRequest request, Class<T> dataClass) throws InterruptedException {
        try {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), dataClass);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send request", e);
        }
    }

    static <T> T sendRequest(HttpRequest request, TypeReference<T> typeReference) throws InterruptedException {
        try {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), typeReference);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send request", e);
        }
    }
    static String convertObjectToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert object to JSON", e);
        }
    }
}

class MotorcycleRaceDTO {
    private String name;
    private int engineCapacity;
    private int participantsCount;

    public MotorcycleRaceDTO(String name, int engineCapacity, int participantsCount) {
        this.name = name;
        this.engineCapacity = engineCapacity;
        this.participantsCount = participantsCount;
    }

    public String getName() {
        return name;
    }

    public int getEngineCapacity() {
        return engineCapacity;
    }

    public int getParticipantsCount() {
        return participantsCount;
    }
}

class MotorcycleRaceResponseDTO {
    private UUID id;
    private String name;
    private int engineCapacity;
    private int participantsCount;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getEngineCapacity() {
        return engineCapacity;
    }

    public int getParticipantsCount() {
        return participantsCount;
    }

    public void setEngineCapacity(int engineCapacity) {
        this.engineCapacity = engineCapacity;
    }
}

