package ro.ubbcluj.cs.map.lab82nd.domain;

import java.util.Objects;
import java.util.UUID;

public class StringFriendshipStatus extends Entity<UUID> {

    private String user1;
    private String user2;
    private String status;

    public StringFriendshipStatus(String user1, String user2, String status) {
        this.user1 = user1;
        this.user2 = user2;
        this.status = status;
        this.setId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return "StringFriendshipStatus{" +
                "user1='" + user1 + '\'' +
                ", user2='" + user2 + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StringFriendshipStatus that = (StringFriendshipStatus) o;
        return Objects.equals(user1, that.user1) && Objects.equals(user2, that.user2) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), user1, user2, status);
    }

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
