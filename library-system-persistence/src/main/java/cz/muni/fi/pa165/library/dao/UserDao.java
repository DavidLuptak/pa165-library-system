/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.library.dao;
import cz.muni.fi.pa165.library.entity.User;
import java.util.List;
/**
 *
 * @author Martin
 */
public interface UserDao {
    public void create(User user);
    public void remove(User user);
    public List<User> findAll();
    public User findByEmail(String email);
    public User findById(Long id);
    public void update(User user);
}
