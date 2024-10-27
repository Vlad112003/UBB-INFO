package org.charity;

public class Donation extends Entity<Integer>{
    private Donor donor;
    private CharitableCase charitableCase;

    public Donation(Donor donor, CharitableCase charitableCase, Double amount) {
        this.donor = donor;
        this.charitableCase = charitableCase;
        this.amount = amount;
    }

    public Donation(Integer id, Donor donor, CharitableCase charitableCase, Double amount) {
        this.setId(id);
        this.donor = donor;
        this.charitableCase = charitableCase;
        this.amount = amount;
    }

    private Double amount;

    public Donor getDonor() {
        return donor;
    }

    public void setDonor(Donor donor) {
        this.donor = donor;
    }

    public CharitableCase getCharitableCase() {
        return charitableCase;
    }

    public void setCharitableCase(CharitableCase charitableCase) {
        this.charitableCase = charitableCase;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Donation{" +
                "donor=" + donor +
                ", charitableCase=" + charitableCase +
                ", amount=" + amount +
                ", _id=" + _id +
                '}';
    }
}
