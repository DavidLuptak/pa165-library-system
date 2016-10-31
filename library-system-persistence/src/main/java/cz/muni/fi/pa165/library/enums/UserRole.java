package cz.muni.fi.pa165.library.enums;

/**
 * Roles of the users in the system.
 *
 * @author xmlynar
 */
public enum UserRole {
    /**
     * User with administrative privileges.
     */
    ADMIN("Admin"),

    /**
     * User with basic privileges.
     */
    MEMBER("Member");

    private final String name;

    UserRole(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
