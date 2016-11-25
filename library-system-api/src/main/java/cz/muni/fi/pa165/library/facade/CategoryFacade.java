package cz.muni.fi.pa165.library.facade;

import cz.muni.fi.pa165.library.dto.CategoryDTO;
import cz.muni.fi.pa165.library.dto.CategoryNewDTO;
import cz.muni.fi.pa165.library.exception.NoEntityFoundException;

import java.util.List;

/**
 * Facade layer for the Category entity.
 *
 * @author Lenka (433591)
 * @version 12.11.2016
 */
public interface CategoryFacade {

    /**
     * Creates new category
     *
     * @param categoryNewDTO to be created
     * @return id of the added category
     * @throws IllegalArgumentException if categoryNewDTO is null
     */
    Long create(CategoryNewDTO categoryNewDTO);

    /**
     * Updates already existing CategoryDTO
     *
     * @param categoryDTO to be updated
     * @throws IllegalArgumentException if categoryDTO is null
     * @throws NoEntityFoundException   if category not found in database
     */
    void update(CategoryDTO categoryDTO);

    /**
     * Deletes the CategoryDTO
     *
     * @param id of the category to be deleted
     * @throws IllegalArgumentException if id is null
     * @throws NoEntityFoundException   if category not found in database
     */
    void delete(Long id);

    /**
     * Returns CategoryDTO with the respective id.
     *
     * @param id of the category
     * @return the categoryDTO with the respective id
     * @throws IllegalArgumentException if id is null
     * @throws NoEntityFoundException   if category not found in database
     */
    CategoryDTO findById(Long id);

    /**
     * Returns CategoryDTO with the respective name.
     *
     * @param name of the category
     * @return the category with the respective name or null if such category does not exist
     * @throws IllegalArgumentException if name is null or empty
     * @throws NoEntityFoundException   if category not found in database
     */
    CategoryDTO findByName(String name);

    /**
     * Returns all CategoryDTO
     *
     * @return all CategoryDTO
     */
    List<CategoryDTO> findAll();
}
