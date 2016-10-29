/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.entity.User;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang.Validate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 * Implementation of UserDao entity.
 *
 * @author Martin
 */
@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void create(User user) {
        validateUser(user);
        Validate.isTrue(user.getId() == null, "User's id should be null");
        em.persist(user);
    }

    @Override
    public void remove(User user) {
        Validate.notNull(user.getId());
        em.remove(user);
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("SELECT u FROM Users u", User.class).getResultList();
    }

    @Override
    public User findByEmail(String email) {
        Validate.notNull(email);
        Validate.notEmpty(email);
        List<User> resultList = em.createQuery("SELECT u FROM Users u where u.email = :email", User.class)
                .setParameter("email", email).getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        if (resultList.size() > 1) {
            throw new IllegalStateException("There should exist only one user with email: " + email + "but there are: " + resultList.size());
        }
        return resultList.get(0);
    }

    @Override
    public User findById(Long id) {
        Validate.notNull(id);
        return em.find(User.class, id);
    }

    @Override
    public void update(User user) {
        validateUser(user);
        Validate.notNull(user.getId());
        em.merge(user);
    }

    private void validateUser(User user){
        Validate.notNull(user);
        Validate.notNull(user.getAddress());
        Validate.notEmpty(user.getAddress());
        Validate.notNull(user.getEmail());
        Validate.notEmpty(user.getEmail());
        Validate.notNull(user.getGivenName());
        Validate.notEmpty(user.getGivenName());
        Validate.notNull(user.getSurname());
        Validate.notEmpty(user.getSurname());
        Validate.notNull(user.getPasswordHash());
        Validate.notEmpty(user.getPasswordHash());
    }

}
