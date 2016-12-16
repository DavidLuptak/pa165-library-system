package cz.muni.fi.pa165.library.entity;

import cz.muni.fi.pa165.library.enums.UserRole;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Entity class representing User in the library system.
 * Two roles ('member' by default and 'administrator'
 * with the administrative privileges) exists out there.
 *
 * @author Martin
 */
@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String passwordHash;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.+[a-zA-Z]{2,}$")
    @Column(unique = true)
    private String email;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String address;

    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.MEMBER;

    @OneToMany(orphanRemoval = true, mappedBy = "user")
    private final Set<Loan> loans = new HashSet<>();

    public User() {
    }

    public User(String firstName, String lastName, String email, String address, UserRole userRole) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.userRole = userRole;
    }

    private User(UserBuilder userBuilder) {
        this.id = userBuilder.id;
        this.email = userBuilder.email;
        this.passwordHash = userBuilder.passwordHash;
        this.firstName = userBuilder.firstName;
        this.lastName = userBuilder.lastName;
        this.address = userBuilder.address;
        this.userRole = userBuilder.userRole;
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

    public Set<Loan> getLoans() {
        return Collections.unmodifiableSet(loans);
    }

    public void addLoan(Loan loan) {
        loans.add(loan);
    }

    public void removeLoan(Loan loan) {
        loans.remove(loan);
    }

    public UserRole getUserRole() {
        return userRole;
    }
    
    public boolean isAdmin() {
        return getUserRole() == UserRole.ADMIN;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof User)) return false;

        User other = (User) obj;

        return getEmail() != null ? this.getEmail().equals(other.getEmail()) : other.getEmail() == null;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.getEmail());
        return hash;
    }

    public static class UserBuilder {
        private final String email;
        private Long id;
        private String passwordHash;
        private String firstName;
        private String lastName;
        private String address;
        private UserRole userRole;

        public UserBuilder(String email) {
            this.email = email;
        }

        public UserBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder setPasswordHash(String passwordHash) {
            this.passwordHash = passwordHash;
            return this;
        }

        public UserBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder setAddress(String address) {
            this.address = address;
            return this;
        }

        public UserBuilder setUserRole(UserRole userRole) {
            this.userRole = userRole;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
