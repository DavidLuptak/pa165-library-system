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
    public void create(User user);

    /**
     * Removes the given user from the database.
     *
     * @param user to be removed
     */
    public void remove(User user);

    /**
     * Finds all users in the database.
     *
     * @return found users
     */
    public List<User> findAll();

    /**
     * Finds users with the given email in the database.
     *
     * @param email of the searched user
     * @return found users
     */
    public User findByEmail(String email);

    /**
     * Finds a user with the given id in the database.
     *
     * @param id of searched user
     * @return found user
     */
    public User findById(Long id);

    /**
     * Updates the given user in the database.
     *
     * @param user to be updated
     */
    public void update(User user);
}
