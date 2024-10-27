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

    @Column(name = "pct")
    private int pct;

    @Column(name = "conf")
    private int conf;

    @Column(name = "poz_curent")
    private int poz_curent;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "tries", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "tries")
    private List<String> tries;


    public Game() {
        this.id = 0;
        this.player = "";
        this.date = "";
        this.time = "";
        this.pct = 0;
        this.conf=0;
        this.poz_curent=0;
        this.tries=null;
    }

    public Game(String player, String date, String time, int pct, int conf) {
        this.id = 0;
        this.player = player;
        this.date = date;
        this.time = time;
        this.pct = pct;
        this.conf=conf;
        this.poz_curent=0;
        this.tries=null;
    }

    public Game(int id, String player, String date, String time, int pct, int conf) {
        this.id = id;
        this.player = player;
        this.date = date;
        this.time = time;
        this.pct = pct;
        this.conf=conf;
        this.poz_curent=0;
        this.tries=null;
    }
    public Game(String player, String date, String time, int pct) {
        this.id = 0;
        this.player = player;
        this.date = date;
        this.time = time;
        this.pct = pct;
        this.conf=0;
        this.poz_curent=0;
        this.tries=null;
    }

    public Game(int id, String player, String date, String time, int pct) {
        this.id = id;
        this.player = player;
        this.date = date;
        this.time = time;
        this.pct = pct;
        this.conf=0;
        this.poz_curent=0;
        this.tries=null;
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

    public int getPct() {
        return pct;
    }

    public void setPct(int pct) {
        this.pct = pct;
    }

    public int getConf() {
        return conf;
    }

    public int getPoz_curent() {
        return poz_curent;
    }

    public void setPoz_curent(int poz_curent) {
        this.poz_curent = poz_curent;
    }

    public void setConf(int conf) {
        this.conf = conf;
    }

    public List<String> getTries() {
        return tries;
    }

    public void setTries(List<String> tries) {
        this.tries = tries;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", player='" + player + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", pct='" + pct + '\'' +
                ", conf='" + conf + '\'' +
                ", poz_curent='" + poz_curent + '\'' +
                ", tries='" + tries + '\'' +
                '}';
    }
}
