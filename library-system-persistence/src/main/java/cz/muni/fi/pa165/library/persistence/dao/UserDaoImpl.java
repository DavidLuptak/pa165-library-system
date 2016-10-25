/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.library.persistence.dao;

import cz.muni.fi.pa165.library.persistence.entity.User;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
//import org.springframework.stereotype.Repository;

/**
 *
 * @author Martin
 */
//@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void create(User user) {
        em.persist(user);
    }

    @Override
    public void remove(User user) {
        em.remove(user);
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("SELECT u FROM Users u", User.class).getResultList();
    }

    @Override
    public User findByEmail(String email) {
        Objects.requireNonNull(email, "email is null");
        if (email.isEmpty()) {
            throw new IllegalArgumentException("email is empty");
        }
        List<User> resultList = em.createQuery("SELECT u FROM Users u where u.email = :email", User.class)
                .setParameter("email", email).getResultList();
        if (resultList == null || resultList.isEmpty()) {
            return null;
        }
        if (resultList.size() > 1) {
            throw new IllegalStateException("duplicit email: " + email);
        }
        return resultList.get(0);
    }

    @Override
    public User findById(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public void update(User user) {
        em.merge(user);
    }
    
}
