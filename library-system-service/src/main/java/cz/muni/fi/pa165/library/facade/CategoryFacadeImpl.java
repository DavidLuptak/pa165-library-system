package cz.muni.fi.pa165.library.facade;

import cz.muni.fi.pa165.library.dto.CategoryDTO;
import cz.muni.fi.pa165.library.dto.CategoryNewDTO;
import cz.muni.fi.pa165.library.entity.Category;
import cz.muni.fi.pa165.library.mapping.BeanMappingService;
import cz.muni.fi.pa165.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Lenka (433591)
 * @version 12.11.2016
 */
@Service
@Transactional
public class CategoryFacadeImpl implements CategoryFacade {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public Long create(CategoryNewDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());
        categoryService.create(category);
        return category.getId();
    }

    @Override
    public void update(CategoryDTO dto) {
        Category category = beanMappingService.mapTo(dto, Category.class);
        categoryService.update(category);
    }

    @Override
    public void delete(Long id) {
        categoryService.delete(categoryService.findById(id));
    }

    @Override
    public CategoryDTO findById(Long id) {
        return beanMappingService.mapTo(categoryService.findById(id), CategoryDTO.class);
    }

    @Override
    public CategoryDTO findByName(String name) {
        return beanMappingService.mapTo(categoryService.findByName(name), CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> findAll() {
        return beanMappingService.mapTo(categoryService.findAll(), CategoryDTO.class);
    }
}
