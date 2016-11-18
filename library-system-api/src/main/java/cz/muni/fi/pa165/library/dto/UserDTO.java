package cz.muni.fi.pa165.library.dto;

import cz.muni.fi.pa165.library.enums.UserRole;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Martin
 * @version 13.11.2016
 */
public class UserDTO {
    private Long id;

    private String passwordHash;

    private String email;

    private String firstName;

    private String lastName;

    private String address;

    private UserRole userRole;

    private final Set<LoanDTO> loans;

    public UserDTO() {
        this.loans = new HashSet<>();
        this.userRole = UserRole.MEMBER;
    }

    public UserDTO(String firstName, String lastName, String email, String address, UserRole userRole) {
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

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
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

    public Set<LoanDTO> getLoans() {
        return Collections.unmodifiableSet(loans);
    }

    public void addLoan(LoanDTO loan) {
        loans.add(loan);
    }

    public void removeLoan(LoanDTO loan) {
        loans.remove(loan);
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
                ", loans=" + loans +
                '}';
    }
}
