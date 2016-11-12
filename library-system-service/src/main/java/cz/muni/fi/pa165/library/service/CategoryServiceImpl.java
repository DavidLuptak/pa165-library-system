package cz.muni.fi.pa165.library.service;

import java.util.List;

import cz.muni.fi.pa165.library.dao.CategoryDao;
import cz.muni.fi.pa165.library.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Lenka (433591)
 * @version 12.11.2016
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public void create(Category category) {
        categoryDao.create(category);
    }

    @Override
    public void update(Category category) {
        categoryDao.update(category);
    }

    @Override
    public void delete(Category category) {
        categoryDao.delete(category);
    }

    @Override
    public Category findById(Long id) {
        return categoryDao.findById(id);
    }

    @Override
    public Category findByName(String name) {
        return categoryDao.findByName(name);
    }

    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }
}
