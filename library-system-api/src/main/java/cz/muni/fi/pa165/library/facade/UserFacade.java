package cz.muni.fi.pa165.library.facade;

import cz.muni.fi.pa165.library.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.library.dto.UserDTO;
import cz.muni.fi.pa165.library.dto.UserNewDTO;
import cz.muni.fi.pa165.library.enums.UserRole;
import cz.muni.fi.pa165.library.exception.NoEntityFoundException;

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
     * @throws IllegalArgumentException if user is null
     * @throws IllegalArgumentException if unencryptedPassword is null or empty
     * @throws NoEntityFoundException   if user does not exist
     */
    Long register(UserNewDTO userNewDTO, String unencryptedPassword);

    /**
     * Updates the given user.
     *
     * @param user to be updated
     * @throws IllegalArgumentException if user is null
     * @throws NoEntityFoundException   if user does not exist
     */
    void update(UserDTO userDTO);

    /**
     * Deletes the given user.
     *
     * @param id of user to be deleted
     * @throws IllegalArgumentException if user id is null
     * @throws NoEntityFoundException   if user does not exist
     */
    void delete(Long id);

    /**
     * Finds a user with the given id.
     *
     * @param id of searched user
     * @return found user
     * @throws IllegalArgumentException if user id is null
     * @throws NoEntityFoundException   if user does not exist
     */
    UserDTO findById(Long id);

    /**
     * Finds a user with the given email.
     *
     * @param email of the searched user
     * @return the user entity with the respective email or null if such entity does not exist
     * @throws IllegalArgumentException if email is null or empty
     * @throws NoEntityFoundException   if user does not exist
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
     * @param user for whom to get the userRole
     * @return user's UserRole
     * @throws IllegalArgumentException if user is null
     * @throws NoEntityFoundException   if user does not exist
     */
    UserRole userRole(UserDTO userDTO);

    /**
     * Try to authenticate a user. Return true only if the hashed password matches the records.
     *
     * @param user to be authenticated
     * @return whether authentication succeeds
     * @throws IllegalArgumentException if user is null
     * @throws NoEntityFoundException   if user does not exist
     */
    boolean authenticate(UserAuthenticateDTO userDTO);
}
