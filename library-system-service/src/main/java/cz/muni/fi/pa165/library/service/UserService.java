package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.entity.User;
import cz.muni.fi.pa165.library.enums.UserRole;

import java.util.List;

/**
 * @author Martin
 * @version 13.11.2016
 */
public interface UserService {

    /**
     * Registers the given user with given unencrypted password.
     *
     * @param user                to be created
     * @param unencryptedPassword of registering user
     */
    void register(User user, String unencryptedPassword);

    /**
     * Updates the given user.
     *
     * @param user to be updated
     * @return updated User entity
     */
    User update(User user);

    /**
     * Deletes the given user.
     *
     * @param user to be deleted
     */
    void delete(User user);

    /**
     * Finds a user with the given id.
     *
     * @param id of searched user
     * @return found user
     */
    User findById(Long id);

    /**
     * Finds a user with the given email.
     *
     * @param email of the searched user
     * @return the user entity with the respective email or null if such entity does not exist
     * @throws IllegalArgumentException if email is null or empty
     */
    User findByEmail(String email);

    /**
     * Finds all users.
     *
     * @return found users
     */
    List<User> findAll();

    /**
     * Gets user's userRole
     *
     * @param user for whom to get the userRole
     * @return user's UserRole
     */
    UserRole userRole(User user);

    /**
     * Try to authenticate a user. Return true only if the hashed password matches the records.
     *
     * @param user                to be authenticated
     * @param unencryptedPassword of authenticating user
     * @return whether authentication succeeds
     */
    boolean authenticate(User user, String unencryptedPassword);

    /**
     * Find users who are loaning something
     *
     * @return users who have at least one not returned loan
     */
    List<User> findUsersWithNotReturnedLoans();
}
