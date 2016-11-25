package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.config.ServiceConfiguration;
import cz.muni.fi.pa165.library.dao.LoanDao;
import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.BookCopy;
import cz.muni.fi.pa165.library.entity.Loan;
import cz.muni.fi.pa165.library.entity.User;
import cz.muni.fi.pa165.library.enums.BookState;
import cz.muni.fi.pa165.library.enums.UserRole;
import cz.muni.fi.pa165.library.exception.LibrarySystemDataAccessException;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

    @Captor
    private ArgumentCaptor<Loan> loanArgumentCaptor;

    private User user;
    private Loan loan;
    private BookCopy bookCopy;

    @BeforeClass
    public void initMockito() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void initEntities() {
        user = new User("Jan", "Novak", "jan@lala.com", "Brno", UserRole.MEMBER);
        user.setId(1L);
        Book book = new Book("Pride and Prejudice", "Jane Austen", "28392883829");
        book.setId(1L);
        bookCopy = new BookCopy(book, BookState.NEW);
        bookCopy.setId(1L);
        loan = new Loan(user, bookCopy, new Date());
        loan.setId(1L);
    }

    @BeforeMethod(dependsOnMethods = "initEntities")
    public void initMocksBehaviour() {
        // findById
        when(loanDao.findById(1L)).thenReturn(loan);
        when(loanDao.findById(2L)).thenReturn(null);

        // findByUser
        when(loanDao.findByUser(user)).thenReturn(Arrays.asList(loan));

        // findAll
        when(loanDao.findAll()).thenReturn(Arrays.asList(loan));

        // create
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object argument = invocation.getArguments()[0];
                if (argument == null) {
                    throw new IllegalArgumentException("Argument is null.");
                }

                Loan loan = (Loan) argument;
                if (loan.getUser() == null) {
                    throw new LibrarySystemDataAccessException("User is null.");
                }
                if (loan.getBookCopy() == null) {
                    throw new LibrarySystemDataAccessException("BookCopy is null.");
                }

                loan.setId(1L);
                return null;
            }
        }).when(loanDao).create(any(Loan.class));

        // update
        when(loanDao.update(loan)).thenReturn(loan);

        Mockito.doThrow(new LibrarySystemDataAccessException("User not found.")).when(loanDao).findByUser(new User());
    }

    @Test
    public void testCreate() {
        loanService.create(loan);
        verify(loanDao).create(loan);
    }

    @Test(expectedExceptions = LibrarySystemDataAccessException.class)
    public void testCreateNoBook() {
        Loan loan = new Loan(user, null, new Date());
        loanService.create(loan);
    }

    @Test(expectedExceptions = LibrarySystemDataAccessException.class)
    public void testCreateNoUser() {
        Loan loan = new Loan(null, bookCopy, new Date());
        loanService.create(loan);
    }

    @Test
    public void testUpdate() {
        Loan updated = loanService.update(loan);

        verify(loanDao).update(loanArgumentCaptor.capture());
        Loan captured = loanArgumentCaptor.getValue();
        assertEquals(captured, loan);
        assertEquals(captured.getUser(), loan.getUser());
        assertEquals(captured.getBookCopy(), loan.getBookCopy());
        assertEquals(captured.getLoanDate(), loan.getLoanDate());
        assertEquals(updated, loan);
        assertEquals(updated.getUser(), loan.getUser());
        assertEquals(updated.getBookCopy(), loan.getBookCopy());
        assertEquals(updated.getLoanDate(), loan.getLoanDate());
    }

    @Test
    public void testDelete() {
        loanService.delete(loan);
        verify(loanDao).delete(loan);
    }

    @Test
    public void testFindById() {
        assertSame(loanService.findById(loan.getId()), loan);
        verify(loanDao).findById(loan.getId());
    }

    @Test
    public void testFindByNonExistingId() {
        assertNull(loanService.findById(2L));
        verify(loanDao).findById(2L);
    }

    @Test
    public void testFindByUser() {
        List<Loan> loans = new ArrayList<>();
        loans.add(loan);
        assertEquals(loanService.findByUser(user).size(), loans.size());
        verify(loanDao).findByUser(user);
    }

    @Test(expectedExceptions = LibrarySystemDataAccessException.class)
    public void testFindByNonExistingUser() {
        loanService.findByUser(new User());
        verify(loanDao).findByUser(new User());
    }

    @Test
    public void testFindAll() {
        List<Loan> loans = new ArrayList<>();
        loans.add(loan);
        assertEquals(loanService.findAll().size(), loans.size());
        verify(loanDao).findAll();
    }

}
