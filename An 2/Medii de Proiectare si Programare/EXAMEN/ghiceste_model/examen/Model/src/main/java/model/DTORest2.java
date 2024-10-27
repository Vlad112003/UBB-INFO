package model;

import java.io.Serializable;
import java.util.List;

public class DTORest2 implements Serializable {
    private int id;
    private int idJoc;
    private List<String> tries;
    private String username;
    private int pct;

    @Override
    public String toString() {
        return "DTORest2{" +
                "idJoc=" + idJoc +
                ", tries=" + tries +
                ", username='" + username + '\'' +
                ", pct=" + pct +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdJoc() {
        return idJoc;
    }

    public void setIdJoc(int idJoc) {
        this.idJoc = idJoc;
    }

    public List<String> getTries() {
        return tries;
    }

    public void setTries(List<String> tries) {
        this.tries = tries;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPct() {
        return pct;
    }

    public void setPct(int pct) {
        this.pct = pct;
    }

    public DTORest2(int idJoc, List<String> tries, String username, int pct) {
        this.idJoc = idJoc;
        this.tries = tries;
        this.username = username;
        this.pct = pct;
    }

    public DTORest2() {
    }

    public DTORest2(int id, int idJoc, List<String> tries, String username, int pct) {
        this.id = id;
        this.idJoc = idJoc;
        this.tries = tries;
        this.username = username;
        this.pct = pct;
    }
}
