package cz.muni.fi.pa165.library.facade;

import cz.muni.fi.pa165.library.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.library.dto.UserDTO;
import cz.muni.fi.pa165.library.enums.UserRole;

import java.util.List;

/**
 * @author Martin
 * @version 13.11.2016
 */
public interface UserFacade {
    /**
     * Registers the given user with given unencrypted password.
     *
     * @param user                to be created
     * @param unencryptedPassword of registering user
     */
    void register(UserDTO user, String unencryptedPassword);

    /**
     * Updates the given user.
     *
     * @param user to be updated
     */
    void update(UserDTO user);

    /**
     * Deletes the given user.
     *
     * @param id of user to be deleted
     */
    void delete(Long id);

    /**
     * Finds a user with the given id.
     *
     * @param id of searched user
     * @return found user
     */
    UserDTO findById(Long id);

    /**
     * Finds a user with the given email.
     *
     * @param email of the searched user
     * @return the user entity with the respective email or null if such entity does not exist
     * @throws IllegalArgumentException if email is null or empty
     */
    UserDTO findByEmail(String email);

    /**
     * Finds all users.
     *
     * @return found users
     */
    List<UserDTO> findAll();

    /**
     * Gets user's userRole
     *
     * @param user    for whom to get the userRole
     * @return user's UserRole
     */
    UserRole userRole(UserDTO user);

    /**
     * Try to authenticate a user. Return true only if the hashed password matches the records.
     *
     * @param user to be authenticated
     * @return whether authentication succeeds
     */
    boolean authenticate(UserAuthenticateDTO user);
}
