package ro.mpp.javaMPPServer.exceptions;

public class FieldEmpty extends RuntimeException{
    public FieldEmpty(String message) {
        super(message);
    }

    public FieldEmpty(String message, Throwable cause) {
        super(message, cause);
    }
}
