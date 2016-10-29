package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.LibraryApplicationContext;
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
public class CategoryDaoTest extends AbstractTestNGSpringContextTests{

    @PersistenceContext
    public EntityManager em;

    @Inject
    CategoryDao categoryDao;

    private Category dbCategory1;
    private Category dbCategory2;
    private Category dbCategory3;
    private Category category1;

    @BeforeMethod
    public void setUp(){
        category1 = new Category();
        category1.setName("category1Name");
        dbCategory1 = new Category();
        dbCategory1.setName("dbCategory1Name");
        dbCategory2 = new Category();
        dbCategory2.setName("dbCategory2Name");
        dbCategory3 = new Category();
        dbCategory3.setName("dbCategory3Name");
        categoryDao.create(dbCategory1);
        categoryDao.create(dbCategory2);
        categoryDao.create(dbCategory3);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void createNullCategory() {
        categoryDao.create(null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void createCategoryWithoutName(){
        categoryDao.create(new Category());
    }

    @Test
    public void createCategorySetsId(){
        assertNotNull(dbCategory1.getId());
    }

    @Test(expectedExceptions = EntityExistsException.class)
    public void createCategoryAfterItHasBeenCreated(){
        categoryDao.create(dbCategory1);
    }

    @Test(expectedExceptions = EntityExistsException.class)
    public void createSameCategory(){
        dbCategory1.setId(null);
        categoryDao.create(dbCategory1);
    }

    @Test
    public void findCategoryById() {
        assertEquals(dbCategory1,categoryDao.findById(dbCategory1.getId()));
        assertNotEquals(dbCategory1,categoryDao.findById(dbCategory2.getId()));
    }

    @Test
    public void findCategoryWithNonExistingId(){
        assertNull(categoryDao.findById(100L));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void findCategoryWithNullId() {
        categoryDao.findById(null);
    }

    @Test
    public void findCategoryByName() {
        assertEquals(dbCategory1,categoryDao.findByName(dbCategory1.getName()));
        assertThat(dbCategory1,is(equalTo(categoryDao.findByName(dbCategory1.getName()))));
        assertEquals(dbCategory2,categoryDao.findByName(dbCategory2.getName()));
        assertNotEquals(dbCategory1,categoryDao.findByName(dbCategory2.getName()));
    }

    @Test
    public void findNotSavedCategory(){
        assertNull(categoryDao.findByName(category1.getName()));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void findCategoryWithNullName() {
        categoryDao.findByName(null);
    }

    @Test
    public void findAllCategories() {
        assertEquals(3,categoryDao.findAll());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void updateNullCategory(){
        categoryDao.update(null);
    }

    @Test
    public void updateCategoryWithNullName(){
        dbCategory1.setName(null);
        categoryDao.update(dbCategory1);
    }

    @Test
    public void updateCategoryNameToNonExistingOne(){
        dbCategory1.setName("dbCategory1FakeName");
        categoryDao.update(dbCategory1);
        Category category = categoryDao.findById(dbCategory1.getId());
        assertEquals(category, dbCategory1);
    }

    @Test(expectedExceptions = EntityExistsException.class)
    public void updateCategoryNameToExisingOne(){
        dbCategory1.setName(dbCategory2.getName());
        categoryDao.update(dbCategory1);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void deleteNullCategory() throws Exception {
        categoryDao.delete(null);
    }

    @Test
    public void deleteCategory(){
        categoryDao.delete(dbCategory2);
        assertEquals(2,categoryDao.findAll().size());
        assertFalse(categoryDao.findAll().contains(dbCategory2));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void deleteNotStoredCategory(){
        categoryDao.delete(category1);
    }
}