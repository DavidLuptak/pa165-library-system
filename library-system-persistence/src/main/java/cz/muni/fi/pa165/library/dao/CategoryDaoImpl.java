package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.entity.Category;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Dávid Lupták
 * @version 29.10.2016
 */
@Repository
public class CategoryDaoImpl implements CategoryDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Category category) {
        em.persist(category);
    }

    @Override
    public Category findById(Long id) {
        return em.find(Category.class, id);
    }

    @Override
    public Category findByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name is not valid");
        }

        return em.createQuery("SELECT c FROM Category c WHERE c.name = :name",Category.class).setParameter("name", name).getSingleResult();
    }

    @Override
    public List<Category> findAll() {
        return em.createQuery("SELECT  c FROM  Category  c", Category.class).getResultList();
    }

    @Override
    public Category update(Category category) {
        return em.merge(category);
    }

    @Override
    public void delete(Category category) {
        em.remove(findById(category.getId()));

    }
}
