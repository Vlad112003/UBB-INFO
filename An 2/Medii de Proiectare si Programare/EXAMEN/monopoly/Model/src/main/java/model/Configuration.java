package model;

import javax.persistence.*;

import java.io.Serializable;

@javax.persistence.Entity
@Table(name = "configurations")
public class Configuration implements Entity<Integer>, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;
    @Column(name = "poz1")
    int poz1;
    @Column(name = "poz2")
    int poz2;
    @Column(name = "poz3")
    int poz3;
    @Column(name = "poz4")
    int poz4;
    @Column(name = "poz5")
    int poz5;

    public Configuration() {
        this.id=0;
        this.poz1=0;
        this.poz2=0;
        this.poz3=0;
        this.poz4=0;
        this.poz5=0;

    }

    public Configuration(int poz1, int poz2, int poz3, int poz4, int poz5) {
        this.id=0;
        this.poz1 = poz1;
        this.poz2 = poz2;
        this.poz3 = poz3;
        this.poz4 = poz4;
        this.poz5 = poz5;
    }

    public Configuration(int id, int poz1, int poz2, int poz3, int poz4, int poz5) {
        this.id = id;
        this.poz1 = poz1;
        this.poz2 = poz2;
        this.poz3 = poz3;
        this.poz4 = poz4;
        this.poz5 = poz5;
    }

    public int getPoz1() {
        return poz1;
    }

    public void setPoz1(int poz1) {
        this.poz1 = poz1;
    }

    public int getPoz2() {
        return poz2;
    }

    public void setPoz2(int poz2) {
        this.poz2 = poz2;
    }

    public int getPoz3() {
        return poz3;
    }

    public void setPoz3(int poz3) {
        this.poz3 = poz3;
    }

    public int getPoz4() {
        return poz4;
    }

    public void setPoz4(int poz4) {
        this.poz4 = poz4;
    }

    public int getPoz5() {
        return poz5;
    }

    public void setPoz5(int poz5) {
        this.poz5 = poz5;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id=id;
    }
}
