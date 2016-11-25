package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.dao.CategoryDao;
import cz.muni.fi.pa165.library.entity.Category;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Lenka (433591)
 * @version 12.11.2016
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Inject
    private CategoryDao categoryDao;

    @Override
    public void create(Category category) {
        if(category == null) throw new IllegalArgumentException("category is null");
        categoryDao.create(category);
    }

    @Override
    public Category update(Category category) {
        if(category == null) throw new IllegalArgumentException("category is null");
        return categoryDao.update(category);
    }

    @Override
    public void delete(Category category) {
        if(category == null) throw new IllegalArgumentException("category is null");
        categoryDao.delete(category);
    }

    @Override
    public Category findById(Long id) {
        if(id == null) throw new IllegalArgumentException("id is null");
        return categoryDao.findById(id);
    }

    @Override
    public Category findByName(String name) {
        if(name == null || name.isEmpty()) throw new IllegalArgumentException("name is null or empty");
        return categoryDao.findByName(name);
    }

    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }
}
