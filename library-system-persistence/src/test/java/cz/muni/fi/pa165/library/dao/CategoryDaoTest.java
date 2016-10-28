package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.entity.Category;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import static org.junit.Assert.*;
/**
 * Created by Martin on 28.10.2016.
 */

@ContextConfiguration(classes = LibraryApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class CategoryDaoTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    public EntityManager em;

    @Inject
    public CategoryDao categoryDao;

    private Category existingCategory1;
    private Category existingCategory2;
    private Category existingCategory3;
    private Category category1;

    @BeforeMethod
    public void setUp(){
        category1 = new Category();
        category1.setName("Travel");
        existingCategory1 = new Category();
        existingCategory1.setName("Drama");
        existingCategory2 = new Category();
        existingCategory2.setName("Horror");
        existingCategory3 = new Category();
        existingCategory3.setName("Romance");
        categoryDao.create(existingCategory1);
        categoryDao.create(existingCategory2);
        categoryDao.create(existingCategory3);
    }



    @Test(expectedExceptions = NullPointerException.class)
    public void testCreateNullCategory() {
        categoryDao.create(null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testCreateCategoryWithoutName(){
        categoryDao.create(new Category());
    }

    @Test
    public void testCreateCategorySetsId(){
        assertNotNull(existingCategory1.getId());
    }

    @Test(expectedExceptions = EntityExistsException.class)
    public void testCreateCategoryAfterItHasBeenCreated(){
        categoryDao.create(existingCategory1);
    }

    @Test(expectedExceptions = EntityExistsException.class)
    public void testCreateSameCategory(){
        existingCategory1.setId(null);
        categoryDao.create(existingCategory1);
    }

    @Test
    public void testFindCategoryById() {
        assertEquals(existingCategory1,categoryDao.findById(existingCategory1.getId()));
        assertEquals(existingCategory2,categoryDao.findById(existingCategory2.getId()));
    }

    @Test
    public void testFindCategoryWithNonExistingId(){
        assertNull(categoryDao.findById(100L));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testFindCategoryWithNullId() {
        categoryDao.findById(null);
    }

    @Test
    public void testFindCategoryByName() {
        assertEquals(existingCategory1,categoryDao.findByName(existingCategory1.getName()));
        assertEquals(existingCategory2,categoryDao.findByName(existingCategory2.getName()));
        assertNotEquals(existingCategory1,categoryDao.findByName(existingCategory2.getName()));
    }

    @Test
    public void testFindNotSavedCategory(){
        assertNull(categoryDao.findByName(category1.getName()));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testFindCategoryWithNullName() {
        categoryDao.findByName(null);
    }

    @Test
    public void testFindAllCategories() {
        assertEquals(3,categoryDao.findAll());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testUpdateNullCategory(){
        categoryDao.update(null);
    }

    @Test
    public void testUpdateCategoryWithNullName(){
        existingCategory1.setName(null);
        categoryDao.update(existingCategory1);
    }

    @Test
    public void testUpdateCategoryNameToNonExistingOne(){
        existingCategory1.setName("XXAB");
        categoryDao.update(existingCategory1);
        Category category = categoryDao.findById(existingCategory1.getId());
        assertEquals(category,existingCategory1);
    }

    @Test(expectedExceptions = EntityExistsException.class)
    public void testUpdateCategoryNameToExisingOne(){
        existingCategory1.setName(existingCategory2.getName());
        categoryDao.update(existingCategory1);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testDeleteNullCategory() throws Exception {
        categoryDao.delete(null);
    }

    @Test
    public void testDeleteCategory(){
        categoryDao.delete(existingCategory2);
        assertEquals(2,categoryDao.findAll().size());
        assertFalse(categoryDao.findAll().contains(existingCategory2));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDeleteNotStoredCategory(){
        categoryDao.delete(category1);
    }

}