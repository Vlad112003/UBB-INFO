package model;

import java.io.Serializable;

public class DTORest1 implements Serializable {
    private int id;
    private int idJoc;
    private int idConf;
    private String model;

    @Override
    public String toString() {
        return "DTORest1{" +
                "idJoc=" + idJoc +
                ", idConf=" + idConf +
                ", model='" + model + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdJoc() {
        return idJoc;
    }

    public void setIdJoc(int idJoc) {
        this.idJoc = idJoc;
    }

    public int getIdConf() {
        return idConf;
    }

    public void setIdConf(int idConf) {
        this.idConf = idConf;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public DTORest1() {
    }

    public DTORest1(int idJoc, int idConf, String model) {
        this.idJoc = idJoc;
        this.idConf = idConf;
        this.model = model;
    }

    public DTORest1(int id, int idJoc, int idConf, String model) {
        this.id = id;
        this.idJoc = idJoc;
        this.idConf = idConf;
        this.model = model;
    }
}
