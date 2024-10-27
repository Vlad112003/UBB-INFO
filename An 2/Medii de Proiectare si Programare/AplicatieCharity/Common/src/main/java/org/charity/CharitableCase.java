package org.charity;

public class CharitableCase extends Entity<Integer>{
    private String caseName;
    private Double amount;

    public CharitableCase(String caseName, Double amount) {
        this.caseName = caseName;
        this.amount = amount;
    }

    public CharitableCase(Integer id, String caseName, Double amount) {
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
        return "CharitableCase{" +
                "caseName='" + caseName + '\'' +
                ", amount=" + amount +
                ", _id=" + _id +
                '}';
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
