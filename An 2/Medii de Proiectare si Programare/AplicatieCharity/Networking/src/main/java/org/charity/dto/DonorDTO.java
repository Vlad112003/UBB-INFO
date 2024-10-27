package org.charity.dto;

import org.charity.Entity;

import java.io.Serializable;

public class DonorDTO extends Entity<Integer> implements Serializable {
    private String name;
    private String address;
    private String phone;

    public DonorDTO(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public DonorDTO(Integer id, String name, String address, String phone) {
        this.setId(id);
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "DonorDTO{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", _id=" + _id +
                '}';
    }
}
