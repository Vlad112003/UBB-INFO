package model;

import java.io.Serializable;
import java.util.List;

public class DTORest implements Entity<Integer>, Serializable {
    int id;

    private String player;
    private List<String> tries;
    private Configuration configuration;
    private Integer pct;

    public DTORest() {
        this.player="";
        this.tries=null;
        this.configuration=null;
        this.pct=0;
    }

    public DTORest(String player, List<String> tries, Configuration configuration, Integer pct) {
        this.player = player;
        this.tries = tries;
        this.configuration = configuration;
        this.pct = pct;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public List<String> getTries() {
        return tries;
    }

    public void setTries(List<String> tries) {
        this.tries = tries;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public Integer getPct() {
        return pct;
    }

    public void setPct(Integer pct) {
        this.pct = pct;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id=id;
    }

    @Override
    public String toString() {
        return "DTORest{" +
                "player='" + player + '\'' +
                ", tries=" + tries +
                ", configuration=" + configuration +
                ", pct=" + pct +
                '}';
    }
}
