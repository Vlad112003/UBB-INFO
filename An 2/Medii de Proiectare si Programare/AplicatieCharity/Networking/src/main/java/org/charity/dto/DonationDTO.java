package org.charity.dto;

import org.charity.Entity;
import java.io.Serializable;

public class DonationDTO extends Entity<Integer> implements Serializable {
    private DonorDTO donor;
    private CharitableDTO charitableCase;

    public DonationDTO(DonorDTO donor, CharitableDTO charitableCase, Double amount) {
        this.donor = donor;
        this.charitableCase = charitableCase;
        this.amount = amount;
    }

    public DonationDTO(Integer id, DonorDTO donor, CharitableDTO charitableCase, Double amount) {
        this.setId(id);
        this.donor = donor;
        this.charitableCase = charitableCase;
        this.amount = amount;
    }

    private Double amount;

    public DonorDTO getDonor() {
        return donor;
    }

    public void setDonor(DonorDTO donor) {
        this.donor = donor;
    }

    public CharitableDTO getCharitableDTO() {
        return charitableCase;
    }

    public void setCharitableDTO(CharitableDTO charitableCase) {
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
        return "DonationDTO{" +
                "donor=" + donor +
                ", charitableCase=" + charitableCase +
                ", amount=" + amount +
                ", _id=" + _id +
                '}';
    }

}
