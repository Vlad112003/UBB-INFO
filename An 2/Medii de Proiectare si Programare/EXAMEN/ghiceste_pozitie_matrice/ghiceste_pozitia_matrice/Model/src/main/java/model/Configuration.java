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
    @Column(name = "x")
    private int x;

    @Column(name = "y")
    private int y;

    @Column(name = "indiciu")
    private String indiciu;
    public Configuration() {
        this.id = 0;
        this.x = 0;
        this.y = 0;
        this.indiciu = "";
    }
    public Configuration(int x, int y, String indiciu) {
        this.id = 0;
        this.x = x;
        this.y = y;
        this.indiciu = indiciu;
    }

    public Configuration(int id, int x, int y, String indiciu) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.indiciu = indiciu;
    }
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getIndiciu() {
        return indiciu;
    }

    public void setIndiciu(String indiciu) {
        this.indiciu = indiciu;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", indiciu='" + indiciu + '\'' +
                '}';
    }
}
