package model;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;
import java.util.List;

public class DTORest implements Entity<Integer>, Serializable {
    int id;

    private String player;
    private List<String> tries;
    private int durata;
    private Integer pct;

    public DTORest() {
        this.player="";
        this.tries=null;
        this.durata=0;
        this.pct=0;
    }

    public DTORest(String player, List<String> tries, Integer durata, Integer pct) {
        this.player = player;
        this.tries = tries;
        this.durata = durata;
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

    public Integer getDurata() {
        return durata;
    }

    public void setDurata(Integer durata) {
        this.durata = durata;
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
                "nume='" + player + '\'' +
                ", incercari=" + tries +
                ", durata=" + durata +
                ", pct=" + pct +
                '}';
    }
}
