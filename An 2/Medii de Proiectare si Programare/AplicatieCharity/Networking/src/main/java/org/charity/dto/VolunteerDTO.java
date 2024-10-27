package org.charity.dto;

import org.charity.Entity;

import java.io.Serializable;

public class VolunteerDTO extends Entity<Integer> implements Serializable {
    private String username;
    private String password;

    public VolunteerDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public VolunteerDTO(Integer id, String username, String password) {
        this.setId(id);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "VolunteerDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", _id=" + _id +
                '}';
    }
}
