package model;

import java.io.Serializable;

public class DTOFinishedGame implements Serializable {
    private int numberOfTries;
    private String hintPosition;

    public DTOFinishedGame(int numberOfTries, String hintPosition) {
        this.numberOfTries = numberOfTries;
        this.hintPosition = hintPosition;
    }

    public int getNumberOfTries() {
        return numberOfTries;
    }

    public void setNumberOfTries(int numberOfTries) {
        this.numberOfTries = numberOfTries;
    }

    public String getHintPosition() {
        return hintPosition;
    }

    public void setHintPosition(String hintPosition) {
        this.hintPosition = hintPosition;
    }
}
