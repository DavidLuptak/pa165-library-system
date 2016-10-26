package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.entity.Category;

import java.util.List;

/**
 * @author Dávid Lupták
 * @version 25.10.2016
 */
public interface CategoryDao {

    /**
     * Stores Category entity into the database.
     *
     * @param category to be persisted
     */
    void create(Category category);

    /**
     * Returns Category entity with the respective id.
     *
     * @param id of the entity
     * @return the entity with the respective id
     */
    Category findById(Category id);

    /**
     * Returns Category entity with the respective name.
     *
     * @param name of the entity
     * @return the entity with the respective name
     */
    Category findByName(String name);

    /**
     * Returns all Category entities stored in the database.
     *
     * @return all Category entities
     */
    List<Category> findAll();

    /**
     * Updates already persisted Category entity in the database.
     *
     * @param category to be updated
     * @return merged Category entity
     */
    Category update(Category category);

    /**
     * Deletes the Category entity from the persistence context
     *
     * @param category to be deleted
     */
    void delete(Category category);
}
