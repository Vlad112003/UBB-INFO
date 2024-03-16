package ro.ubbcluj.cs.map.lab72nd.domain.validators;

public class ValidationException extends RuntimeException{
    public ValidationException(){}

    /**
     * @param message the message to be displayed
     */
    public ValidationException(String message){
        super(message);
    }

    /**
     * @param message the message to be displayed
     * @param cause the cause of the exception
     */
    public ValidationException(String message, Throwable cause){
        super(message, cause);
    }
    /**
     * @param cause the cause of the exception
     */
    public ValidationException(Throwable cause){
        super(cause);
    }

    /**
     * @param message the message to be displayed
     * @param cause the cause of the exception
     * @param enableSuppression whether the exception is enabled or suppressed
     * @param writableStackTrace whether the stack trace is writable
     */
    public ValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
