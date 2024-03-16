package ro.ubbcluj.cs.map.lab82nd.domain;

import java.util.*;

public class User extends Entity<UUID>{
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Map<UUID, User> friends;
    /**
     * Constructor for User
     * @param firstName - the first name of the user
     * @param lastName - the last name of the user
     * @param email - the email of the user
     */
    public User(String firstName, String lastName, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.friends = new HashMap<>();

        //generates a random unique ID
        this.setId(UUID.randomUUID());
    }

    /**
     * Constructor for User
     * @param firstName - the first name of the user
     * @param lastName - the last name of the user
     * @param email - the email of the user
     * @param password - the password of the user
     */
    public User(String firstName, String lastName, String email, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.friends = new HashMap<>();

        //generates a random unique ID
        this.setId(UUID.randomUUID());
    }

    public User(String email) {
        this.email = email;
    }


    /**
     * Getter for the first name of the user
     * @return the first name of the user
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * Setter for the first name of the user
     * @param firstName - the new first name of the user
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * Getter for the last name of the user
     * @return the last name of the user
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * Setter for the last name of the user
     * @param lastName - the new last name of the user
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * Getter for the email of the user
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }
    /**
     * Setter for the email of the user
     * @param email - the new email of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * Getter for the password of the user
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }
    /**
     * Setter for the password of the user
     * @param password - the new password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Overrides toString method
     * @return a string containing the user's first name, last name, email and password
     */
    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
    /**
     * Overrides equals method
     * @param o - the object to be compared to
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o){
        if (this==o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId().equals(user.getId());
    }
    /**
     * Overrides hashCode method
     * @return the hash code of the user
     */
    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getEmail());
    }

    /**
     * Adds a friend
     * @param u - the user to be added as a friend
     */
    public void addFriend(User u){
        this.friends.put(u.getId(), u);
    }

    /**
     * Removes a friend
     * @param u - the user to be removed as a friend
     * @return true if the user was removed, false otherwise
     */
    public boolean removeFriend(User u){
        return this.friends.remove(u.getId()) != null;
    }


    /**
     * returns number of friends
     * @return the number of friends
     */
    public int getNumberOfFriends(){
        return this.friends.size();
    }

    /**
     * returns a list of friends
     * @return a list of friends
     */
    public List<User> getFriends(){
        return new ArrayList<>(this.friends.values());
    }
}

