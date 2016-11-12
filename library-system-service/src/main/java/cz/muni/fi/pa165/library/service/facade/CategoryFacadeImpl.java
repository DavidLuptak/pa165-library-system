package cz.muni.fi.pa165.library.service.facade;

import java.util.List;

import cz.muni.fi.pa165.library.api.dto.CategoryDTO;
import cz.muni.fi.pa165.library.api.dto.CategoryNewDTO;
import cz.muni.fi.pa165.library.api.facade.CategoryFacade;

/**
 * @author Lenka (433591)
 * @version 12.11.2016
 */
public class CategoryFacadeImpl implements CategoryFacade {

    @Override
    public void create(CategoryNewDTO dto) {

    }

    @Override
    public void update(CategoryDTO dto) {

    }

    @Override
    public void delete(CategoryDTO dto) {

    }

    @Override
    public CategoryDTO findById(Long id) {
        return null;
    }

    @Override
    public CategoryDTO findByName(String name) {
        return null;
    }

    @Override
    public List<CategoryDTO> findAll() {
        return null;
    }
}
