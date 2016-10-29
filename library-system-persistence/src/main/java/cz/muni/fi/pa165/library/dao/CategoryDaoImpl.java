package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.entity.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Dávid Lupták
 * @version 29.10.2016
 */
@Repository
public class CategoryDaoImpl implements CategoryDao {
    @Override
    public void create(Category category) {

    }

    @Override
    public Category findById(Long id) {
        return null;
    }

    @Override
    public Category findByName(String name) {
        return null;
    }

    @Override
    public List<Category> findAll() {
        return null;
    }

    @Override
    public Category update(Category category) {
        return null;
    }

    @Override
    public void delete(Category category) {

    }
}
