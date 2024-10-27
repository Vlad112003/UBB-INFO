package model;

import javax.swing.text.Position;
import java.io.Serializable;
import java.util.List;

public class DTORest implements Serializable {
    private List<String> tries;
    private List<String> gropi;
    private Integer points;
    private String time;
    private String player;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DTORest(Integer id,List<String> tries, List<String> gropi, Integer points, String time, String player) {
        this.id=id;
        this.tries = tries;
        this.gropi = gropi;
        this.points = points;
        this.time = time;
        this.player = player;
    }

    public DTORest() {
    }

    public List<String> getTries() {
        return tries;
    }

    public void setTries(List<String> tries) {
        this.tries = tries;
    }

    public List<String> getGropi() {
        return gropi;
    }

    public void setGropi(List<String> gropi) {
        this.gropi = gropi;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

}
