package model;

import javax.persistence.*;
import java.io.Serializable;
@javax.persistence.Entity
@Table(name = "configurations")
public class Configuration implements Entity<Integer>, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "cuvant")
    private String cuvant;
    @Column(name = "masca")
    private String masca;

    @Override
    public String toString() {
        return "Configuration{" +
                "id=" + id +
                ", cuvant='" + cuvant + '\'' +
                ", masca='" + masca + '\'' +
                '}';
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id=id;

    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCuvant() {
        return cuvant;
    }

    public void setCuvant(String cuvant) {
        this.cuvant = cuvant;
    }

    public String getMasca() {
        return masca;
    }

    public void setMasca(String masca) {
        this.masca = masca;
    }

    public Configuration(String cuvant, String masca) {
        this.id = 0;
        this.cuvant = cuvant;
        this.masca = masca;
    }

    public Configuration() {
        this.id = 0;
        this.cuvant = "";
        this.masca = "";

    }

    public Configuration(int id, String cuvant, String masca) {
        this.id = id;
        this.cuvant = cuvant;
        this.masca = masca;
    }
}
