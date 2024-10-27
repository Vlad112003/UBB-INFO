package org.charity.dto;
import org.charity.Entity;

import java.io.Serializable;

public class CharitableDTO extends Entity<Integer> implements Serializable{
    private String caseName;
    private Double amount;

    public CharitableDTO(String caseName, Double amount) {
        this.caseName = caseName;
        this.amount = amount;
    }

    public CharitableDTO(Integer id, String caseName, Double amount) {
        this.setId(id);
        this.caseName = caseName;
        this.amount = amount;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public Double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "CharitableCaseDTO{" +
                "caseName='" + caseName + '\'' +
                ", amount=" + amount +
                ", _id=" + _id +
                '}';
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
