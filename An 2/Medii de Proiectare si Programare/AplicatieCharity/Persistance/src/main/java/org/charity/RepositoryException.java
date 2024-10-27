package org.charity;

public class RepositoryException extends RuntimeException {
    public RepositoryException(String message) {
        super(message);
    }

    public RepositoryException(Exception ex) {
        super(ex);
    }
}