package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.entity.Category;
import cz.muni.fi.pa165.library.exceptions.LibraryDAOException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Implementation of the DAO contract for the Category entity.
 *
 * @author Dávid Lupták
 * @version 29.10.2016
 */
@Repository
public class CategoryDaoImpl implements CategoryDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Category category) {
        try {
            em.persist(category);
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Category update(Category category) {
        try {
            return em.merge(category);
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void delete(Category category) {
        try {
            em.remove(findById(category.getId()));
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Category findById(Long id) {
        try {
            return em.find(Category.class, id);
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Category findByName(String name) {
        try {
            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException("Name is not valid");
            }
            try {
                return em.createQuery("SELECT c FROM Category c WHERE c.name = :name", Category.class)
                        .setParameter("name", name).getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<Category> findAll() {
        try {
            return em.createQuery("SELECT c FROM Category c", Category.class).getResultList();
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(), e.getCause());
        }
    }


}
