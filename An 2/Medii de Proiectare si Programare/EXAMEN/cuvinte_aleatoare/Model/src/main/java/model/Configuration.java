package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@javax.persistence.Entity
@Table(name = "configurations")
public class Configuration implements Entity<Integer>, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "poz1")
    private String poz1;
    @Column(name = "poz2")
    private String poz2;
    @Column(name = "poz3")
    private String poz3;
    @Column(name = "poz4")
    private String poz4;
    @Column(name = "poz5")
    private String poz5;
    @Column(name = "poz6")
    private String poz6;
    @Column(name = "poz7")
    private String poz7;
    @Column(name = "poz8")
    private String poz8;
    @Column(name = "poz9")
    private String poz9;
    @Column(name = "poz10")
    private String poz10;


    public Configuration() {
        this.id=0;
        this.poz1="";
        this.poz2="";
        this.poz3="";
        this.poz4="";
        this.poz5="";
        this.poz6="";
        this.poz7="";
        this.poz8="";
        this.poz9="";
        this.poz10="";
    }

    public Configuration(int id, String poz1, String poz2, String poz3, String poz4, String poz5, String poz6, String poz7, String poz8, String poz9, String poz10) {
        this.id = id;
        this.poz1 = poz1;
        this.poz2 = poz2;
        this.poz3 = poz3;
        this.poz4 = poz4;
        this.poz5 = poz5;
        this.poz6 = poz6;
        this.poz7 = poz7;
        this.poz8 = poz8;
        this.poz9 = poz9;
        this.poz10 = poz10;
    }

    public Configuration(String poz1, String poz2, String poz3, String poz4, String poz5, String poz6, String poz7, String poz8, String poz9, String poz10) {
        this.id=0;
        this.poz1 = poz1;
        this.poz2 = poz2;
        this.poz3 = poz3;
        this.poz4 = poz4;
        this.poz5 = poz5;
        this.poz6 = poz6;
        this.poz7 = poz7;
        this.poz8 = poz8;
        this.poz9 = poz9;
        this.poz10 = poz10;
    }



    public String getPoz1() {
        return poz1;
    }

    public void setPoz1(String poz1) {
        this.poz1 = poz1;
    }

    public String getPoz2() {
        return poz2;
    }

    public void setPoz2(String poz2) {
        this.poz2 = poz2;
    }

    public String getPoz3() {
        return poz3;
    }

    public void setPoz3(String poz3) {
        this.poz3 = poz3;
    }

    public String getPoz4() {
        return poz4;
    }

    public void setPoz4(String poz4) {
        this.poz4 = poz4;
    }

    public String getPoz5() {
        return poz5;
    }

    public void setPoz5(String poz5) {
        this.poz5 = poz5;
    }

    public String getPoz6() {
        return poz6;
    }

    public void setPoz6(String poz6) {
        this.poz6 = poz6;
    }

    public String getPoz7() {
        return poz7;
    }

    public void setPoz7(String poz7) {
        this.poz7 = poz7;
    }

    public String getPoz8() {
        return poz8;
    }

    public void setPoz8(String poz8) {
        this.poz8 = poz8;
    }

    public String getPoz9() {
        return poz9;
    }

    public void setPoz9(String poz9) {
        this.poz9 = poz9;
    }

    public String getPoz10() {
        return poz10;
    }

    public void setPoz10(String poz10) {
        this.poz10 = poz10;
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
        return "Configuration{" +
                "id=" + id +
                ", poz1='" + poz1 + '\'' +
                ", poz2='" + poz2 + '\'' +
                ", poz3='" + poz3 + '\'' +
                ", poz4='" + poz4 + '\'' +
                ", poz5='" + poz5 + '\'' +
                ", poz6='" + poz6 + '\'' +
                ", poz7='" + poz7 + '\'' +
                ", poz8='" + poz8 + '\'' +
                ", poz9='" + poz9 + '\'' +
                ", poz10='" + poz10 + '\'' +
                '}';
    }
}
