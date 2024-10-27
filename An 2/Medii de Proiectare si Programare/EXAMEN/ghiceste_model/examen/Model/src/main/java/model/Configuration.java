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
    @Column(name = "model")
    private String model;

    public Configuration() {
        this.id = 0;
        this.model = "";
    }
    public Configuration(String model) {
        this.id = 0;
        this.model = model;
    }

    public Configuration(int id, String model) {
        this.id = id;
        this.model = model;
    }
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "id=" + id +
                ", model='" + model + '\'' +
                '}';
    }
}
