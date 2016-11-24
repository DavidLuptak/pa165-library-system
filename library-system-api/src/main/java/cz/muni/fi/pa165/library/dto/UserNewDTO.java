package cz.muni.fi.pa165.library.dto;

import cz.muni.fi.pa165.library.enums.UserRole;

import java.util.Objects;

/**
 * Created by Martin on 24.11.2016.
 */
public class UserNewDTO {
    private Long id;

    private String email;

    private String firstName;

    private String lastName;

    private String address;

    private UserRole userRole;

    public UserNewDTO() {
        this.userRole = UserRole.MEMBER;
    }

    public UserNewDTO(String firstName, String lastName, String email, String address, UserRole userRole) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.userRole = userRole;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof UserDTO)) return false;

        UserDTO other = (UserDTO) obj;

        return getEmail() != null ? this.getEmail().equals(other.getEmail()) : other.getEmail() == null;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.getEmail());
        return hash;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", userRole=" + userRole + '\'' +
                '}';
    }
}
