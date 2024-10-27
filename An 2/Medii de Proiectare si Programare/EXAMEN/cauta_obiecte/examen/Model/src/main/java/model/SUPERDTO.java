package model;

import java.io.Serializable;
import java.util.List;

public class SUPERDTO implements Serializable {
    private int idjoc;
    private String username;
    private List<String> found;
    private int score;

    public SUPERDTO() {
    }

    public SUPERDTO(int idjoc, String username, List<String> found, int score) {
        this.idjoc = idjoc;
        this.username = username;
        this.found = found;
        this.score = score;
    }

    @Override
    public String toString() {
        return "SUPERDTO{" +
                "idjoc=" + idjoc +
                ", username='" + username + '\'' +
                ", found=" + found +
                ", score=" + score +
                '}';
    }

    public int getIdjoc() {
        return idjoc;
    }

    public void setIdjoc(int idjoc) {
        this.idjoc = idjoc;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getFound() {
        return found;
    }

    public void setFound(List<String> found) {
        this.found = found;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
