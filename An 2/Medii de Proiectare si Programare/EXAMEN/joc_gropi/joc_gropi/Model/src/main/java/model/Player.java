package model;

import java.io.Serializable;
import java.util.Objects;

public class Player implements Entity<Integer>, Serializable {
    private int id;
    private String username;

    public Player() {
        this.id=0;
        this.username="";
    }

    public Player(String username) {
        this.id=0;
        this.username = username;
    }

    public Player(int id, String username) {
        this.id = id;
        this.username = username;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id=id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player player)) return false;
        return getId() == player.getId() && Objects.equals(getUsername(), player.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername());
    }
}
