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

    @Column(name = "conf")
    private int conf;

    @Column(name = "date")
    private String date;

    @Column(name = "time")
    private String time;

    @Column(name = "pct")
    private Integer pct;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "tries", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "tries")
    private List<String> tries;

    public Game() {
        this.id = 0;
        this.player = "";
        this.conf = 0;
        this.date = "";
        this.time = "";
        this.tries = null;
        this.pct=0;
    }

    public Game(String player, Integer conf, String date, String time, List<String> tries, Integer pct) {
        this.id = 0;
        this.player = player;
        this.conf = conf;
        this.date = date;
        this.time = time;
        this.tries = tries;
        this.pct=pct;
    }

    public Game(int id, String player, Integer conf, String date, String time, List<String> tries, Integer pct) {
        this.id = id;
        this.player = player;
        this.conf = conf;
        this.date = date;
        this.time = time;
        this.tries = tries;
        this.pct=pct;
    }

    public Game(String player, String date, String time, Integer pct) {
        this.id=0;
        this.player = player;
        this.conf=0;
        this.date = date;
        this.time = time;
        this.tries=null;
        this.pct = pct;
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

    public Integer getConf() {
        return conf;
    }

    public void setConf(Integer conf) {
        this.conf = conf;
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

    public List<String> getTries() {
        return tries;
    }

    public void setTries(List<String> tries) {
        this.tries = tries;
    }

    public Integer getPct() {
        return pct;
    }

    public void setPct(Integer pct) {
        this.pct = pct;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", player='" + player + '\'' +
                ", conf=" + conf +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", pct=" + pct +
                ", tries=" + tries +
                '}';
    }
}
