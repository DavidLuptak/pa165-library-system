package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.dao.CategoryDao;
import cz.muni.fi.pa165.library.entity.Category;
import cz.muni.fi.pa165.library.exception.LibraryDAOException;
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
        if (category == null) throw new IllegalArgumentException("category is null");
        try {
            categoryDao.create(category);
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public Category update(Category category) {
        if (category == null) throw new IllegalArgumentException("category is null");
        try {
            return categoryDao.update(category);
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public void delete(Category category) {
        if (category == null) throw new IllegalArgumentException("category is null");
        try {
            categoryDao.delete(category);
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public Category findById(Long id) {
        if (id == null) throw new IllegalArgumentException("id is null");
        try {
            return categoryDao.findById(id);
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public Category findByName(String name) {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("name is null or empty");
        try {
            return categoryDao.findByName(name);
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public List<Category> findAll() {
        try {
            return categoryDao.findAll();
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }
}
