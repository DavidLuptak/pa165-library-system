package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.entity.Category;

import java.util.List;

/**
 * @author Lenka (433591)
 * @version 12.11.2016
 */
public interface CategoryService {

    /**
     * Stores Category entity into the database.
     *
     * @param category to be persisted
     */
    void create(Category category);

    /**
     * Updates already persisted Category entity in the database.
     *
     * @param category to be updated
     * @return updated Category entity
     */
    Category update(Category category);

    /**
     * Deletes the Category entity from the persistence context
     *
     * @param category to be deleted
     */
    void delete(Category category);

    /**
     * Returns Category entity with the respective id.
     *
     * @param id of the entity
     * @return the entity with the respective id
     */
    Category findById(Long id);

    /**
     * Returns Category entity with the respective name.
     *
     * @param name of the entity
     * @return the entity with the respective name or null if such entity does not exist
     * @throws IllegalArgumentException if name is null or empty
     */
    Category findByName(String name);

    /**
     * Returns all Category entities stored in the database.
     *
     * @return all Category entities
     */
    List<Category> findAll();
}
