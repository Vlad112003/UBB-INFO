package ro.mpp.javaMPPServer.exceptions;

public class MotorcycleRaceNotFound extends RuntimeException{
    public MotorcycleRaceNotFound(String message) {
        super(message);
    }

    public MotorcycleRaceNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
