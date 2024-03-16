package ro.ubbcluj.cs.map.lab7.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class FriendShip extends Entity<UUID>{

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private User user1;
    private User user2;
    private LocalDateTime friendsFrom;
    private LocalDate friendsFromDate;
    private FriendRequest request;
    /**
     * Constructor for FriendShip
     * @param user1 - the first user
     * @param user2 - the second user
     */
    public FriendShip(User user1, User user2){
        this.user1 = user1;
        this.user2 = user2;
        this.setId(UUID.randomUUID());
        friendsFrom = LocalDateTime.now();
        this.request = FriendRequest.PENDING;
    }

    public FriendShip(String email1, String email2, String friendsFrom){
        this.user1 = new User(email1);
        this.user2 = new User(email2);
        this.setId(UUID.randomUUID());
        this.friendsFrom = LocalDateTime.parse(friendsFrom, formatter);
        this.request = FriendRequest.PENDING;
    }

    /**
     * Constructor for FriendShip
     * @param user1 - the first user
     * @param user2 - the second user
     * @param friendsFrom - the date from which the two users are friends
     */


    public FriendShip(User user1, User user2, LocalDateTime friendsFrom){
        this.user1 = user1;
        this.user2 = user2;
        this.setId(UUID.randomUUID());
        this.friendsFrom = friendsFrom;
        this.request = FriendRequest.PENDING;
    }
    /**
     * Constructor for FriendShip
     * @param user1 - the first user
     * @param user2 - the second user
     * @param request - the request status of the friendship
     */
    public FriendShip(User user1, User user2, FriendRequest request){
        this.user1 = user1;
        this.user2 = user2;
        this.setId(UUID.randomUUID());
        friendsFrom = LocalDateTime.now();
        this.request = request;
    }

    public FriendShip(Optional u1, Optional u2, LocalDateTime from) {
        this.user1 = (User) u1.get();
        this.user2 = (User) u2.get();
        this.setId(UUID.randomUUID());
        this.friendsFrom = from;
        this.request = FriendRequest.PENDING;
    }

    public FriendShip(Optional u1, Optional u2, LocalDate friendsFromDate){
        this.user1 = (User) u1.get();
        this.user2 = (User) u2.get();
        this.setId(UUID.randomUUID());
        this.friendsFromDate = LocalDate.now();
        this.request = FriendRequest.PENDING;
    }

    public FriendShip(Optional u1, Optional u2, String fromString){
        this.user1 = (User) u1.get();
        this.user2 = (User) u2.get();
        this.setId(UUID.randomUUID());
        this.friendsFrom = LocalDateTime.parse(fromString, formatter);
        this.request = FriendRequest.PENDING;
    }

    /**
     * getter for the first user
     * @return the first user
     * @type User
     */
    public User getUser1() {
        return user1;
    }
    /**
     * setter for the first user
     * @param user1 - the new first user
     * @type User
     */
    public void setUser1(User user1) {
        this.user1 = user1;
    }
    /**
     * getter for the second user
     * @return the second user
     * @type User
     */
    public User getUser2() {
        return user2;
    }

    /**
     * setter for the second user
     * @param user2 - the new second user
     */
    public void setUser2(User user2) {
        this.user2 = user2;
    }
    /**
     * getter for the date from which the two users are friends
     * @return the date from which the two users are friends
     */
    public LocalDateTime getFriendsFrom() {
        return friendsFrom;
    }

    public LocalDate getFriendsFromDate() {
        return friendsFromDate;
    }

    /**
     * setter for the date from which the two users are friends
     * @param friendsFrom - the new date from which the two users are friends
     */


    public void setFriendsFrom(LocalDateTime friendsFrom) {
        this.friendsFrom = friendsFrom;
    }
    /**
     * getter for the request status of the friendship
     * @return the request status of the friendship
     */
    public FriendRequest getRequest() {
        return request;
    }
    /**
     * setter for the request status of the friendship
     * @param request - the new request status of the friendship
     */
    public void setRequest(FriendRequest request) {
        this.request = request;
    }

    /**
     * Overridden toString method
     * @return a string containing the first user, the second user, the id and the request status of the friendship
     */
    @Override
    public String toString() {
        return "FriendShip {" +
                "user1=" + user1 +
                ", user2=" + user2 +
                ", friendsFrom=" + friendsFrom.format(formatter) +
//                ", request=" + request +
                '}';
    }

    /**
     * Overridden equals method
     * @param o - the object to be compared to
     * @return true if the two objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FriendShip))    return false;
        FriendShip that = (FriendShip) o;
        return id.equals(that.getId());
    }
    /**
     * Overridden hashCode method
     * @return the hashcode of the friendship
     */
    @Override
    public int hashCode() {
        return Objects.hash(getUser1(), getUser2());
    }
}