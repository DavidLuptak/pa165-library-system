package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.LibraryApplicationContext;
import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.BookCopy;
import cz.muni.fi.pa165.library.entity.Loan;
import cz.muni.fi.pa165.library.entity.User;
import cz.muni.fi.pa165.library.enums.BookState;
import cz.muni.fi.pa165.library.enums.UserRole;
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
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;

import static org.testng.Assert.*;


/**
 * Test suite for the BookCopy DAO.
 *
 * @author Martin
 * @version 29.10.2016
 */
@ContextConfiguration(classes = LibraryApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class BookCopyDaoTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    EntityManager em;

    @Inject
    private BookCopyDao bookCopyDao;

    @Inject
    private LoanDao loanDao;

    @Inject
    private UserDao userDao;

    @Inject
    private BookDao bookDao;

    private User dbUser;

    private Loan dbLoan1;
    private Loan dbLoan2;

    private Book dbBook1;
    private Book dbBook2;

    private BookCopy dbBookCopy11;
    private BookCopy dbBookCopy12;
    private BookCopy dbBookCopy21;
    private BookCopy bookCopy1;

    @BeforeMethod
    public void setUp() {
        dbUser = new User();
        dbUser.setAddress("dbUserAddress");
        dbUser.setEmail("dbUserEmail@Email.email");
        dbUser.setFirstName("dbUserGivenName");
        dbUser.setLastName("dbUserSurname");
        dbUser.setUserRole(UserRole.MEMBER);
        dbUser.setPasswordHash("hash123");
        userDao.create(dbUser);

        dbBook1 = new Book();
        dbBook1.setName("dbBook1Name");
        dbBook1.setAuthor("dbBook1Author");
        dbBook1.setIsbn("0-14-020652-3");

        dbBook2 = new Book();
        dbBook2.setName("dbBook2Name");
        dbBook2.setAuthor("dbBook2Author");
        dbBook2.setIsbn("0-14-020652-4");
        bookDao.create(dbBook1);
        bookDao.create(dbBook2);

        dbBookCopy11 = new BookCopy();
        dbBookCopy11.setBook(dbBook1);
        dbBookCopy11.setBookState(BookState.LIGHT_DAMAGE);
        dbBookCopy12 = new BookCopy();
        dbBookCopy12.setBook(dbBook1);
        dbBookCopy12.setBookState(BookState.MEDIUM_DAMAGE);
        dbBookCopy21 = new BookCopy();
        dbBookCopy21.setBook(dbBook2);
        dbBookCopy21.setBookState(BookState.HEAVY_DAMAGE);
        bookCopy1 = new BookCopy();
        bookCopy1.setBook(dbBook1);
        bookCopy1.setBookState(BookState.MEDIUM_DAMAGE);
        bookCopyDao.create(dbBookCopy11);
        bookCopyDao.create(dbBookCopy12);
        bookCopyDao.create(dbBookCopy21);

        dbLoan1 = new Loan();
        dbLoan1.setBookCopy(dbBookCopy11);
        dbLoan1.setLoanDate(new Date());
        dbLoan1.setUser(dbUser);
        dbLoan2 = new Loan();
        dbLoan2.setBookCopy(dbBookCopy12);
        dbLoan2.setLoanDate(new Date());
        dbLoan2.setUser(dbUser);
        loanDao.create(dbLoan1);
        loanDao.create(dbLoan2);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testCreateNullBookCopy() {
        bookCopyDao.create(null);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testCreateBookCopyWithoutBookSet() {
        bookCopy1.setBook(null);
        bookCopyDao.create(bookCopy1);
    }

    @Test
    public void testCreateBookCopySetsId() {
        assertNotNull(dbBookCopy11.getId());
        assertNotNull(dbBookCopy21.getId());
    }

    @Test
    public void testFindBookCopyById() {
        assertEquals(dbBookCopy11, bookCopyDao.findById(dbBookCopy11.getId()));
        assertNotEquals(dbBookCopy11, bookCopyDao.findById(dbBookCopy21.getId()));
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testFindBookCopyWithNullId() {
        bookCopyDao.findById(null);
    }

    @Test
    public void testFindBookCopyWithNonExistingId() {
        assertNull(bookCopyDao.findById(100L));
    }

    @Test
    public void testFindBookCopies() {
        List<BookCopy> bookCopySet = bookCopyDao.findByBook(dbBook1);
        assertEquals(2, bookCopySet.size());
        assertTrue(bookCopySet.contains(dbBookCopy11));
        assertTrue(bookCopySet.contains(dbBookCopy12));
        assertFalse(bookCopySet.contains(dbBookCopy21));
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testUpdateNullBookCopy() {
        bookCopyDao.update(null);
    }

    @Test
    public void testUpdateBookCopyState() {
        dbBookCopy11.setBookState(BookState.MEDIUM_DAMAGE);
        bookCopyDao.update(dbBookCopy11);
        assertEquals(BookState.MEDIUM_DAMAGE, bookCopyDao.findById(dbBookCopy11.getId()).getBookState());
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testUpdateBookCopyNullBook() {
        dbBookCopy11.setBook(null);
        bookCopyDao.update(dbBookCopy11);
        em.flush();
    }

    @Test
    public void testUpdateBookCopyBook() {
        dbBookCopy11.setBook(dbBook2);
        bookCopyDao.update(dbBookCopy11);
        assertEquals(dbBook2, bookCopyDao.findById(dbBookCopy11.getId()).getBook());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testDeleteNullBookCopy() {
        bookCopyDao.delete(null);
    }

    @Test
    public void testDeleteBookCopy() {
        bookCopyDao.delete(dbBookCopy11);
        assertNull(bookCopyDao.findById(dbBookCopy11.getId()));
        assertNotNull(bookCopyDao.findById(dbBookCopy21.getId()));
    }

}