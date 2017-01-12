package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.LibraryApplicationContext;
import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.Category;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import static org.testng.Assert.*;


/**
 * Test suite for the Category DAO.
 *
 * @author Martin
 * @version 28.10.2016
 */

@ContextConfiguration(classes = LibraryApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class CategoryDaoTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    EntityManager em;

    @Inject
    private CategoryDao categoryDao;

    @Inject
    private BookDao bookDao;

    private Category dbCategory1;
    private Category dbCategory2;
    private Category dbCategory3;
    private Category category1;

    private Book dbBook1;
    private Book dbBook2;
    private Book dbBook3;

    @BeforeMethod
    public void setUp() {
        dbBook1 = new Book();
        dbBook1.setTitle("Book Title 1");
        dbBook1.setIsbn("978-3-16-148410-0");
        dbBook1.setAuthor("AB");
        bookDao.create(dbBook1);

        dbBook2 = new Book();
        dbBook2.setTitle("Very Long Long Long Long Long Book Title 2");
        dbBook2.setIsbn("978-0-321-35668-0");
        dbBook2.setAuthor("CD");
        bookDao.create(dbBook2);

        dbBook3 = new Book();
        dbBook3.setTitle("Light Damaged Book Title 3");
        dbBook3.setIsbn("0321193687");
        dbBook3.setAuthor("EF");
        bookDao.create(dbBook3);

        category1 = new Category();
        category1.setName("category1Name");
        dbCategory1 = new Category();
        dbCategory1.setName("dbCategory1Name");
        dbCategory1.addBook(dbBook1);
        dbCategory2 = new Category();
        dbCategory2.setName("dbCategory2Name");
        dbCategory2.addBook(dbBook2);
        dbCategory3 = new Category();
        dbCategory3.setName("dbCategory3Name");
        categoryDao.create(dbCategory1);
        categoryDao.create(dbCategory2);
        categoryDao.create(dbCategory3);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testCreateNullCategory() {
        categoryDao.create(null);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testCreateCategoryWithoutName() {
        categoryDao.create(new Category());
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testCreateCategoryWithExistingName() {
        category1.setName(dbCategory1.getName());
        categoryDao.create(category1);
    }

    @Test
    public void testCreateCategorySetsId() {
        assertNotNull(dbCategory1.getId());
    }

    @Test
    public void testFindCategoryById() {
        assertEquals(dbCategory1, categoryDao.findById(dbCategory1.getId()));
        assertNotEquals(dbCategory1, categoryDao.findById(dbCategory2.getId()));
    }

    @Test
    public void testFindCategoryWithNonExistingId() {
        assertNull(categoryDao.findById(100L));
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testFindCategoryWithNullId() {
        categoryDao.findById(null);
    }

    @Test
    public void testFindCategoryByName() {
        assertEquals(dbCategory1, categoryDao.findByName(dbCategory1.getName()));

        assertEquals(dbCategory2, categoryDao.findByName(dbCategory2.getName()));
        assertNotEquals(dbCategory1, categoryDao.findByName(dbCategory2.getName()));
        assertNull(categoryDao.findByName("lala"));
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testFindCategoryWithNullName() {
        categoryDao.findByName(null);
    }

    @Test
    public void testFindAllCategories() {
        assertEquals(3, categoryDao.findAll().size());
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testUpdateNullCategory() {
        categoryDao.update(null);
    }

    @Test
    public void testUpdateCategoryNameToNonExistingOne() {
        dbCategory1.setName("dbCategory1FakeName");
        categoryDao.update(dbCategory1);
        Category category = categoryDao.findById(dbCategory1.getId());
        assertEquals(category, dbCategory1);
    }

    @Test(expectedExceptions = PersistenceException.class)
    public void testUpdateCategoryNameToExistingOne() {
        em.merge(category1);
        dbCategory1.setName(dbCategory2.getName());
        categoryDao.update(dbCategory1);
        em.flush();
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testUpdateCategoryNameToNull() {
        dbCategory1.setName(null);
        categoryDao.update(dbCategory1);
        em.flush();
    }

    @Test
    public void testUpdateCategoryAddBookShouldAddBook() {
        em.clear();
        dbCategory1.addBook(dbBook3); //owning side should add book
        dbCategory1 = categoryDao.update(dbCategory1);
        em.flush();
        em.clear();
        assertTrue(categoryDao.findById(dbCategory1.getId()).getBooks().contains(dbBook3));
    }

    @Test
    public void testUpdateCategoryRemoveBook() {
        dbCategory2.removeBook(dbBook2);
        categoryDao.update(dbCategory2);
        assertFalse(categoryDao.findById(dbCategory2.getId()).getBooks().contains(dbBook2));
    }

    @Test
    public void testDeleteCategory() {
        categoryDao.delete(dbCategory2);
        assertEquals(2, categoryDao.findAll().size());
        assertNull(categoryDao.findById(dbCategory2.getId()));
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testDeleteNotStoredCategory() {
        categoryDao.delete(category1);
    }

}
