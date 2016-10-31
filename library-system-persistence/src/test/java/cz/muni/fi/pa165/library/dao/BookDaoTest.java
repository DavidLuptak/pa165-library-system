package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.LibraryApplicationContext;
import cz.muni.fi.pa165.library.entity.Book;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 *
 * @author Bedrich Said
 */
@ContextConfiguration(classes = LibraryApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class BookDaoTest extends AbstractTestNGSpringContextTests {

    @Inject
    private BookDao bookDao;

    private Book book1;
    private Book book2;
    private Book book3;

    @BeforeMethod
    public void setUp() {
        book1 = new Book();
        book1.setName("Book Name 1");
        book1.setIsbn("1L");
        book1.setAuthor("AB");

        book2 = new Book();
        book2.setName("Very Long Long Long Long Long Book Name 2");
        book2.setIsbn("2L");
        book2.setAuthor("CD");

        book3 = new Book();
        book3.setName("Light Damaged Book Name 3");
        book3.setIsbn("3L");
        book3.setAuthor("EF");

        bookDao.create(book1);
        bookDao.create(book2);
        bookDao.create(book3);
    }

    @Test
    public void testCreate() {
        assertNotNull(book1.getId());
        assertNotNull(book2.getId());
        assertNotNull(book3.getId());
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testCreateNullName() {
        Book newBook = new Book();
        bookDao.create(newBook);
    }

    @Test
    public void testUpdate() {
        book1.setName("changed");
        bookDao.update(book1);
        assertEquals("changed", bookDao.findById(book1.getId())
                .getName());
    }

    @Test
    public void testFindById() {
        assertDeepEquals(bookDao.findById(book2.getId()), book2);
        assertDeepEquals(bookDao.findById(book3.getId()), book3);

        assertNull(bookDao.findById(100L));
    }

    @Test
    public void testFindByName() {
        Set<Book> expected = new HashSet<>();
        expected.add(book1);
        Set<Book> actual = new HashSet<>(bookDao.findByName("Book Name 1"));
        assertEquals(expected, actual);
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
        assertNull(bookDao.findById(book2.getId()));
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testDeleteNonexistent() {
        Book newBook = new Book();
        newBook.setId(100L);
        newBook.setName("New Added Book Name 4");
        newBook.setIsbn("100L");
        bookDao.delete(newBook);
    }

    private void assertDeepEquals(Book actual, Book expected) {
        assertNotNull(actual);
        assertNotNull(expected);
        assertEquals(actual.getId(), expected.getId());
        assertEquals(actual.getName(), expected.getName());
        assertEquals(actual.getAuthor(), expected.getAuthor());
        assertEquals(actual.getIsbn(), expected.getIsbn());

    }
}
