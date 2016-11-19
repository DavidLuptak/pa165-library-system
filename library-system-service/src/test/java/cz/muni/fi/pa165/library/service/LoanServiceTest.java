package cz.muni.fi.pa165.library.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.muni.fi.pa165.library.config.ServiceConfiguration;
import cz.muni.fi.pa165.library.dao.LoanDao;
import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.BookCopy;
import cz.muni.fi.pa165.library.entity.Loan;
import cz.muni.fi.pa165.library.entity.User;
import cz.muni.fi.pa165.library.enums.BookState;
import cz.muni.fi.pa165.library.enums.UserRole;
import cz.muni.fi.pa165.library.exception.LibrarySystemDataAccessException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

/**
 * @author Lenka (433591)
 * @version 19.11.2016
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class LoanServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Mock
    private LoanDao loanDao;

    @InjectMocks
    private LoanService loanService = new LoanServiceImpl();

    private User user;
    private Loan loan;
    private BookCopy bookCopy;

    @BeforeClass
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        user = new User("Jan", "Novak", "jan@lala.com", "Brno", UserRole.MEMBER);
        user.setId(1L);
        Book book = new Book("Pride and Prejudice", "Jane Austen", "28392883829");
        book.setId(1L);
        bookCopy = new BookCopy(book, BookState.NEW);
        bookCopy.setId(1L);
        Date now = new Date();
        loan = new Loan(user, bookCopy, now);
        loan.setId(1L);
    }

    @Test
    public void testCreate() throws Exception {

        loanService.create(loan);
        verify(loanDao).create(loan);
    }

    @Test(expectedExceptions = LibrarySystemDataAccessException.class)
    public void testCreateNoBook() throws Exception {
        Loan loan = new Loan(user, null, new Date());
        Mockito.doThrow(new LibrarySystemDataAccessException("bookCopy is null"))
            .when(loanDao).create(loan);
        loanService.create(loan);
    }

    @Test(expectedExceptions = LibrarySystemDataAccessException.class)
    public void testCreateNoUser() throws Exception {
        Loan loan = new Loan(null, bookCopy, new Date());
        Mockito.doThrow(new LibrarySystemDataAccessException("user is null"))
            .when(loanDao).create(loan);
        loanService.create(loan);
    }


    @Test
    public void testDelete() throws Exception {
        loanService.delete(loan);
        verify(loanDao).delete(loan);
    }

    @Test
    public void testFindById() throws Exception {
        when(loanDao.findById(loan.getId())).thenReturn(loan);
        assertSame(loanService.findById(loan.getId()), loan);
        verify(loanDao).findById(loan.getId());
    }

    @Test
    public void testFindByNonexistingId() throws Exception {
        when(loanDao.findById(2L)).thenReturn(null);
        assertNull(loanService.findById(2L));
        verify(loanDao).findById(2L);
    }

    @Test
    public void testFindByUser() throws Exception {
        List<Loan> loans = new ArrayList<>();
        loans.add(loan);
        when(loanDao.findByUser(user)).thenReturn(loans);
        assertSame(loans, loanService.findByUser(user));
        verify(loanDao).findByUser(user);
    }

    @Test(expectedExceptions = LibrarySystemDataAccessException.class)
    public void testFindByNonexistingUser() throws Exception {
        User user = new User();
        Mockito.doThrow(new LibrarySystemDataAccessException("user was not found"))
            .when(loanDao).findByUser(user);
        loanService.findByUser(user);
        verify(loanDao).findByUser(user);
    }

    @Test
    public void testFindAll() throws Exception {
        List<Loan> loans = new ArrayList<>();
        loans.add(loan);
        when(loanDao.findAll()).thenReturn(loans);
        assertSame(loans, loanService.findAll());
        verify(loanDao).findAll();
    }

}