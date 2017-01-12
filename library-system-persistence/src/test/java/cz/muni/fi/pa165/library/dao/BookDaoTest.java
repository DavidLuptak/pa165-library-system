package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.LibraryApplicationContext;
import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.Category;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.testng.Assert.*;

/**
 * Test suite for the Book DAO.
 *
 * @author Bedrich Said
 */
@ContextConfiguration(classes = LibraryApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class BookDaoTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    EntityManager em;

    @Inject
    private BookDao bookDao;

    @Inject
    private CategoryDao categoryDao;

    private Book book1;
    private Book book2;
    private Book book3;

    private Category category1;

    @BeforeMethod
    public void setUp() {
        book1 = new Book();
        book1.setTitle("Book Title 1");
        book1.setIsbn("978-3-16-148410-0");
        book1.setAuthor("AB");

        book2 = new Book();
        book2.setTitle("Very Long Long Long Long Long Book Title 2");
        book2.setIsbn("978-0-321-35668-0");
        book2.setAuthor("CD");

        book3 = new Book();
        book3.setTitle("Light Damaged Book Title 3");
        book3.setIsbn("0321193687");
        book3.setAuthor("EF");

        bookDao.create(book1);
        bookDao.create(book2);
        bookDao.create(book3);

        category1 = new Category();
        category1.setName("categoryName1");
        categoryDao.create(category1);
    }

    @Test
    public void testCreate() {
        assertNotNull(book1.getId());
        assertNotNull(book2.getId());
        assertNotNull(book3.getId());
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testCreateNullTitle() {
        Book newBook = new Book();
        bookDao.create(newBook);
    }

    @Test
    public void testUpdate() {
        book1.setTitle("changed");
        bookDao.update(book1);
        em.flush();
        em.refresh(book1);
        assertEquals("changed", bookDao.findById(book1.getId()).getTitle());
    }

    @Test
    public void testUpdateAddCategoryShouldNotAddCategory(){
        em.clear();
        book1.addCategory(category1); //inverse side doesn't affect database join table
        book1 = bookDao.update(book1);
        em.flush();
        em.clear();
        assertFalse(bookDao.findById(book1.getId()).getCategories().contains(category1));
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testFindByIdNull() {
        bookDao.findById(null);
    }

    @Test
    public void testFindById() {
        assertDeepEquals(bookDao.findById(book2.getId()), book2);
        assertDeepEquals(bookDao.findById(book3.getId()), book3);

        assertNull(bookDao.findById(100L));
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testFindByTitleNull() {
        bookDao.findByTitle(null);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testFindByIsbn(){
        bookDao.findByIsbn(null);
    }

    @Test
    public void testFindByTitle() {
        Set<Book> expected = new HashSet<>();
        expected.add(book1);
        Set<Book> actual = new HashSet<>(bookDao.findByTitle("Book Title 1"));
        assertEquals(expected, actual);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testFindByAuthorNull() {
        bookDao.findByAuthor(null);
    }

    @Test
    public void testFindByAuthorNonAuthor() {
        List<Book> bookList = bookDao.findByAuthor("Joshua Bloch");
        assertNotNull(bookList);
        assertEquals(bookList.size(), 0);
    }

    @Test
    public void testFindByAuthor() {
        String author = book1.getAuthor();
        List<Book> expected = new ArrayList<>();
        expected.add(book1);

        List<Book> actual = bookDao.findByAuthor(author);
        assertEquals(actual, expected);
    }

    @Test
    public void testFindByAuthorMultiple() {
        String author = book1.getAuthor();
        List<Book> expected = new ArrayList<>();
        expected.add(book1);

        Book anotherBookOfTheSameAuthor = new Book();
        anotherBookOfTheSameAuthor.setTitle("Another title");
        anotherBookOfTheSameAuthor.setIsbn("9780764543852");
        anotherBookOfTheSameAuthor.setAuthor(book1.getAuthor());
        bookDao.create(anotherBookOfTheSameAuthor);
        expected.add(anotherBookOfTheSameAuthor);

        List<Book> actual = bookDao.findByAuthor(author);
        assertEquals(actual.size(), 2);
        assertEquals(actual, expected);
        assertDeepEquals(actual.get(0), book1);
        assertDeepEquals(actual.get(1), anotherBookOfTheSameAuthor);
    }

    @Test
    public void testFindAll() {
        Set<Book> expected = new HashSet<>();
        expected.add(book1);
        expected.add(book2);
        expected.add(book3);
        Set<Book> actual = new HashSet<>();
        actual.addAll(bookDao.findAll());
        assertEquals(expected, actual);
    }

    @Test
    public void testDelete() {
        bookDao.delete(book2);
        assertNotNull(bookDao.findById(book2.getId()));
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testDeleteNonexistent() {
        Book newBook = new Book();
        newBook.setId(100L);
        newBook.setTitle("New Added Book Title 4");
        newBook.setIsbn("100L");
        bookDao.delete(newBook);
    }

    private void assertDeepEquals(Book actual, Book expected) {
        assertNotNull(actual);
        assertNotNull(expected);
        assertEquals(actual.getId(), expected.getId());
        assertEquals(actual.getTitle(), expected.getTitle());
        assertEquals(actual.getAuthor(), expected.getAuthor());
        assertEquals(actual.getIsbn(), expected.getIsbn());
    }
}
