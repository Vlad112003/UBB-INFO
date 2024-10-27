package model;

import java.io.Serializable;

public class DTOFinishedGame implements Serializable {
    private int score;
    private String flagsPosition;

    public DTOFinishedGame(int score, String flagsPosition) {
        this.score = score;
        this.flagsPosition = flagsPosition;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getFlagsPosition() {
        return flagsPosition;
    }

    public void setFlagsPosition(String flagsPosition) {
        this.flagsPosition = flagsPosition;
    }
}