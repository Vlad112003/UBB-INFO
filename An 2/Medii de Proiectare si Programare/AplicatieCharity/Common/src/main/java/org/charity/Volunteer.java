package org.charity;

public class Volunteer extends Entity<Integer>{
    private String username;
    private String password;

    public Volunteer(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Volunteer(Integer id, String username, String password) {
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
        return "Volunteer{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", _id=" + _id +
                '}';
    }
}
