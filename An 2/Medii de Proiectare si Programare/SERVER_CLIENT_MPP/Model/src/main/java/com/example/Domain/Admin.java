package com.example.Domain;

import java.util.UUID;

public class Admin extends Entity<UUID>{
    private String username;
    private String password;

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

    public Admin(UUID id,String username, String password) {
        super.setId(id);
        this.username = username;
        this.password = password;
    }
}
