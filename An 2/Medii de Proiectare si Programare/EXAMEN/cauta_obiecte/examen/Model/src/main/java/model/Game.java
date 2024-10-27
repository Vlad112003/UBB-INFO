package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@javax.persistence.Entity
@Table(name = "games")
public class Game implements Entity<Integer>, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "player")
    private String player;

    @Column(name = "date")
    private String date;

    @Column(name = "time")
    private String time;

    @Column(name = "score")
    private int score;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "found", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "found")
    private List<String> found;

    public Game() {
        this.id = 0;
        this.player = "";
        this.date = "";
        this.time = "";
        this.score = 0;
        this.found = null;
    }

    public Game(String player, String date, String time, int score, List<String> found) {
        this.id = 0;
        this.player = player;
        this.date = date;
        this.time = time;
        this.score = score;
        this.found = found;
    }

    public Game(int id, String player, String date, String time, int score, List<String> found) {
        this.id = id;
        this.player = player;
        this.date = date;
        this.time = time;
        this.score = score;
        this.found = found;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<String> getFound() {
        return found;
    }

    public void setFound(List<String> found) {
        this.found = found;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", player='" + player + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", score='" + score + '\'' +
                ", found=" + found +
                '}';
    }
}