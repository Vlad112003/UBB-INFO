package com.example.dto;

import java.io.Serializable;

public class dtoParticipant implements Serializable {

    private String nume;
    private int varsta;

    public dtoParticipant(String nume, int varsta) {
        this.nume = nume;
        this.varsta = varsta;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getVarsta() {
        return varsta;
    }


    public void setVarsta(int varsta){
        this.varsta=varsta;
    }

    @Override
    public String toString() {
        return "dtoParticipant{" +
                "nume='" + nume + '\'' +
                ", varsta=" + varsta +
                '}';
    }
}
