/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.library.dao;
import cz.muni.fi.pa165.library.entity.User;
import java.util.List;
/**
 * Data access object interface for User entity
 *
 * @author Martin
 */
public interface UserDao {

    /**
     * Saves the given user into the database.
     *
     * @param user to be created
     */
    void create(User user);

    /**
     * Updates the given user in the database.
     *
     * @param user to be updated
     */
    void update(User user);

    /**
     * Deletes the given user from the database.
     *
     * @param user to be deleted
     */
    void delete(User user);

    /**
     * Finds a user with the given id in the database.
     *
     * @param id of searched user
     * @return found user
     */
    User findById(Long id);

    /**
     * Finds users with the given email in the database.
     *
     * @param email of the searched user
     * @return the user entity with the respective email or null if such entity does not exist
     * @throws IllegalArgumentException if email is null or empty
     */
    User findByEmail(String email);

    /**
     * Finds all users in the database.
     *
     * @return found users
     */
    List<User> findAll();


}
