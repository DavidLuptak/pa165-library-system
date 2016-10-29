package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.LibraryApplicationContext;
import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.BookCopy;
import cz.muni.fi.pa165.library.entity.Loan;
import cz.muni.fi.pa165.library.entity.User;
import cz.muni.fi.pa165.library.enums.BookState;
import cz.muni.fi.pa165.library.enums.UserRole;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Martin on 29.10.2016.
 */
@ContextConfiguration(classes = LibraryApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class BookCopyDaoTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    public EntityManager em;

    @Inject
    BookCopyDao bookCopyDao;

    @Inject
    LoanDao loanDao;

    @Inject
    UserDao userDao;

    @Inject
    BookDao bookDao;

    private User dbUser;

    private Loan dbLoan;

    private Book dbBook1;
    private Book dbBook2;

    private BookCopy dbBookCopy11;
    private BookCopy dbBookCopy12;
    private BookCopy dbBookCopy21;
    private BookCopy dbBookCopy22;
    private BookCopy bookCopy1;

    @BeforeMethod
    public void setUp(){
        dbUser = new User();
        dbUser.setAddress("dbUserAdress");
        dbUser.setEmail("dbUserEmail@Email.email");
        dbUser.setGivenName("dbUserGivenName");
        dbUser.setSurname("dbUserSurname");
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
        dbBookCopy22 = new BookCopy();
        dbBookCopy22.setBook(dbBook2);
        dbBookCopy22.setBookState(BookState.LIGHT_DAMAGE);
        bookCopy1 = new BookCopy();
        bookCopy1.setBook(dbBook1);
        bookCopy1.setBookState(BookState.MEDIUM_DAMAGE);
        bookCopyDao.create(dbBookCopy11);
        bookCopyDao.create(dbBookCopy12);
        bookCopyDao.create(dbBookCopy21);
        bookCopyDao.create(dbBookCopy22);

        dbLoan = new Loan();
        dbLoan.addBookCopy(dbBookCopy11);
        dbLoan.addBookCopy(dbBookCopy21);
        dbLoan.setLoanDate(new Date());
        dbLoan.setUser(dbUser);
        loanDao.create(dbLoan);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void createNullBookCopy(){
        bookCopyDao.create(null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void createBookCopyWithoutBookSet(){
        bookCopy1.setBook(null);
        bookCopyDao.create(bookCopy1);
    }

    @Test(expectedExceptions = EntityExistsException.class)
    public void createBookCopyAfterItHasBeenCreated(){
        bookCopyDao.create(dbBookCopy11);
    }

    @Test
    public void createBookCopySetsId(){
        assertNotNull(dbBookCopy11.getId());
        assertNotNull(dbBookCopy21.getId());
    }

    @Test
    public void findBookCopyById(){
        assertEquals(dbBookCopy11,bookCopyDao.findById(dbBookCopy11.getId()));
        assertNotEquals(dbBookCopy11,bookCopyDao.findById(dbBookCopy21.getId()));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void findBookCopyWithNullId(){
        bookCopyDao.findById((Long) null);
    }

    @Test
    public void findBookCopyWithNonExistingId(){
        assertNull(bookCopyDao.findById(100L));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void findBookCopiesByNullBook(){
        bookCopyDao.findByBook(null);
    }

    @Test
    public void findBookCopies(){
        List<BookCopy> bookCopySet = bookCopyDao.findByBook(dbBook1);
        assertTrue(bookCopySet.contains(dbBookCopy11));
        assertTrue(bookCopySet.contains(dbBookCopy12));
        assertFalse(bookCopySet.contains(dbBookCopy21));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void updateNullBookCopy(){
        bookCopyDao.update(null);
    }

    @Test
    public void updateBookCopyState(){
        dbBookCopy11.setBookState(BookState.MEDIUM_DAMAGE);
        bookCopyDao.update(dbBookCopy11);
        assertEquals(BookState.MEDIUM_DAMAGE,bookCopyDao.findById(dbBookCopy11.getId()).getBookState());
    }

    @Test
    public void updateBookCopyBook(){
        dbBookCopy11.setBook(dbBook2);
        bookCopyDao.update(dbBookCopy11);
        assertEquals(dbBook2,bookCopyDao.findById(dbBookCopy11.getId()).getBook());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void deleteNullBookCopy(){
        bookCopyDao.delete(null);
    }

    @Test
    public void deleteBookCopy(){
        bookCopyDao.delete(dbBookCopy11);
        assertNull(bookCopyDao.findById(dbBookCopy11.getId()));
        assertNotNull(bookCopyDao.findById(dbBookCopy21.getId()));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void deleteNotStoredBookCopy(){
        bookCopyDao.delete(bookCopy1);
    }

    //TODO more complex tests

}