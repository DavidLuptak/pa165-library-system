package cz.muni.fi.pa165.library.persistence.dao.test;

import cz.muni.fi.pa165.dao.BookDao;
import cz.muni.fi.pa165.entity.Book;
import cz.muni.fi.pa165.enums.BookState;
import cz.muni.fi.pa165.spring.LibrarySpringContext;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Bedrich Said
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = LibrarySpringContext.class)
@Transactional
public class BookDaoTest {
    @PersistenceContext
    private EntityManager em;

    @Inject
    private BookDao bookDao;

    private Book book1;
    private Book book2;
    private Book book3;

    @Before
    public void setUp() {
        book1 = new Book();
        book1.setName("Book Name 1");
        book1.setIsbn(1L);
        book1.setState(BookState.NEW);
        book2 = new Book();
        book2.setName("Very Long Long Long Long Long Book Name 2");
        book2.setIsbn(2L);
        book2.setState(BookState.NEW);
        book3 = new Book();
        book3.setName("Light Damaged Book Name 3");
        book3.setIsbn(3L);
        book3.setState(BookState.LIGHT_DAMAGE);
        em.persist(book1);
        em.persist(book2);
        em.persist(book3);
        em.flush();
    }

    @Test
    public void testCreate() {
        assertNotNull(book1.getId());
        assertNotNull(book2.getId());
        assertNotNull(book3.getId());
    }

    @Test(expected = DataAccessException.class)
    public void testCreateNullName() {
        Book newBook = new Book();
        bookDao.create(newBook);
    }

    @Test
    public void testUpdate() {
        book1.setName("changed");
        bookDao.update(book1);
        assertEquals("changed", em.find(Book.class, book1.getId())
                .getName());
    }

    @Test
    public void testFindById() {
        assertSame(book2, bookDao.findById(book2.getId()));
        assertSame(book3, bookDao.findById(book3.getId()));
        assertNull(bookDao.findById(100L));
    }

    @Test
    public void testFindByName() {
        Set<Book> expected = new HashSet<>();
        expected.add(book2);
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
        assertNull(em.find(Book.class, book2.getId()));
    }

    @Test(expected = DataAccessException.class)
    public void testDeleteNonexistent() {
        Book newBook = new Book();
        newBook.setId(100L);
        newBook.setName("New Added Book Name 4");
        newBook.setState(BookState.NEW);
        newBook.setIsbn(100L);
        bookDao.delete(newBook);
    }
}
