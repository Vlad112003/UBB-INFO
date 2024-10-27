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

    @Column(name = "position1")
    private int position1;

    @Column(name = "position2")
    private int position2;

    @Column(name = "position3")
    private int position3;


    public Configuration() {
        this.id = 0;
        this.position1 = 0;
        this.position2 = 0;
        this.position3 = 0;
    }

    public Configuration(int position1, int position2, int position3) {
        this.id = 0;
        this.position1 = position1;
        this.position2 = position2;
        this.position3 = position3;
    }

    public Configuration(int id, int position1, int position2, int position3) {
        this.id = id;
        this.position1 = position1;
        this.position2 = position2;
        this.position3 = position3;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public int getPosition1() {
        return position1;
    }

    public void setPosition1(int position1) {
        this.position1 = position1;
    }

    public int getPosition2() {
        return position2;
    }

    public void setPosition2(int position2) {
        this.position2 = position2;
    }

    public int getPosition3() {
        return position3;
    }

    public void setPosition3(int position3) {
        this.position3 = position3;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "id=" + id +
                ", position1=" + position1 +
                ", position2=" + position2 +
                ", position3=" + position3 +
                '}';
    }
}