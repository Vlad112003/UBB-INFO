package ro.ubbcluj.cs.map.lab82nd.domain;

import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

public class StringFriendship extends Entity<UUID> {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private String user1;
    private String user2;
    private String friendsFrom;

    private String status;

    public StringFriendship(String user1, String user2, String friendsFrom, String status) {
        this.user1 = user1;
        this.user2 = user2;
        this.friendsFrom = friendsFrom;
        this.setId(UUID.randomUUID());
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StringFriendship that = (StringFriendship) o;
        return Objects.equals(formatter, that.formatter) && Objects.equals(user1, that.user1) && Objects.equals(user2, that.user2) && Objects.equals(friendsFrom, that.friendsFrom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), formatter, user1, user2, friendsFrom);
    }

    public DateTimeFormatter getFormatter() {
        return formatter;
    }

    public void setFormatter(DateTimeFormatter formatter) {
        this.formatter = formatter;
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

    public String getFriendsFrom() {
        return friendsFrom;
    }

    public void setFriendsFrom(String friendsFrom) {
        this.friendsFrom = friendsFrom;
    }

    @Override
    public String toString() {
        return "StringFriendship{" +
                "user1='" + user1 + '\'' +
                ", user2='" + user2 + '\'' +
                ", friendsFrom='" + friendsFrom + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
