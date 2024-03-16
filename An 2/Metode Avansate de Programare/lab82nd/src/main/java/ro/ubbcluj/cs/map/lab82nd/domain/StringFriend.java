package ro.ubbcluj.cs.map.lab82nd.domain;

import java.util.Objects;

public class StringFriend {

    String firstName;
    String lastName;
    String status;


    public StringFriend(String firstName, String lastName, String status){
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
    }

    @Override
    public String toString() {
        return "StringFriend{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringFriend that = (StringFriend) o;
        return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, status);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
