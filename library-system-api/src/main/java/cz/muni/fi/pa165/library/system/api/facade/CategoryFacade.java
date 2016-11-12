package cz.muni.fi.pa165.library.system.api.facade;

import java.util.List;

import cz.muni.fi.pa165.library.system.api.dto.CategoryDTO;
import cz.muni.fi.pa165.library.system.api.dto.CategoryNewDTO;


/**
 * @author Lenka (433591)
 * @version 12.11.2016
 */
public interface CategoryFacade {

    /**
     * Creates new category
     *
     * @param dto to be created
     */
    void create(CategoryNewDTO dto);

    /**
     * Updates already existing CategoryDTO
     *
     * @param dto to be updated
     */
    void update(CategoryDTO dto);

    /**
     * Deletes the CategoryDTO
     *
     * @param dto to be deleted
     */
    void delete(CategoryDTO dto);

    /**
     * Returns CategoryDTO with the respective id.
     *
     * @param id of the category
     * @return the categoryDTO with the respective id
     */
    CategoryDTO findById(Long id);

    /**
     * Returns CategoryDTO with the respective name.
     *
     * @param name of the category
     * @return the category with the respective name or null if such category does not exist
     * @throws IllegalArgumentException if name is null or empty
     */
    CategoryDTO findByName(String name);

    /**
     * Returns all CategoryDTO
     *
     * @return all CategoryDTO
     */
    List<CategoryDTO> findAll();
}
