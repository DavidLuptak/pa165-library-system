package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.config.ServiceConfiguration;
import cz.muni.fi.pa165.library.dao.LoanDao;
import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.BookCopy;
import cz.muni.fi.pa165.library.entity.Loan;
import cz.muni.fi.pa165.library.entity.User;
import cz.muni.fi.pa165.library.enums.BookState;
import cz.muni.fi.pa165.library.enums.UserRole;
import cz.muni.fi.pa165.library.exception.LibraryDAOException;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
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

    private Loan returnedLoan1;
    private Loan returnedLoan2;
    private Loan returnedLoan3;
    private Loan notReturnedLoan1;
    private Loan notReturnedLoan2;
    private Loan notReturnedLoan3;

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
        loan = new Loan(user, bookCopy, LocalDateTime.of(2016,10,10,10,10));
        loan.setId(1L);

        //
        returnedLoan1 = new Loan();
        returnedLoan1.setLoanDate(LocalDateTime.of(2016,10,10,10,10));
        returnedLoan1.setReturnDate(LocalDateTime.of(2016,10,10,10,10));
        returnedLoan2 = new Loan();
        returnedLoan2.setLoanDate(LocalDateTime.of(2016,10,10,10,10));
        returnedLoan2.setReturnDate(LocalDateTime.of(2016,10,10,10,10));
        returnedLoan3 = new Loan();
        returnedLoan3.setLoanDate(LocalDateTime.of(2016,10,10,10,10));
        returnedLoan3.setReturnDate(LocalDateTime.of(2016,10,10,10,10));

        notReturnedLoan1 = new Loan();
        notReturnedLoan1.setLoanDate(LocalDateTime.of(2016,10,10,10,10));
        notReturnedLoan2 = new Loan();
        notReturnedLoan2.setLoanDate(LocalDateTime.of(2016,10,10,10,10));
        notReturnedLoan3 = new Loan();
        notReturnedLoan3.setLoanDate(LocalDateTime.of(2016,10,10,10,10));

        user.addLoan(returnedLoan1);
        user.addLoan(notReturnedLoan1);
        user.addLoan(returnedLoan2);
        user.addLoan(notReturnedLoan2);
        user.addLoan(returnedLoan3);
        user.addLoan(notReturnedLoan3);
    }

    @BeforeMethod(dependsOnMethods = "initEntities")
    public void initMocksBehaviour() {
        // findById
        when(loanDao.findById(1L)).thenReturn(loan);
        when(loanDao.findById(2L)).thenReturn(null);

        // findByUser
        when(loanDao.findByUser(user)).thenReturn(Arrays.asList(
                returnedLoan1, notReturnedLoan1, returnedLoan2,
                notReturnedLoan2, returnedLoan3, notReturnedLoan3));

        // findAll
        when(loanDao.findAll()).thenReturn(Arrays.asList(returnedLoan1,notReturnedLoan1));

        // create
        doAnswer((InvocationOnMock invocation) -> {
            Object argument = invocation.getArguments()[0];
            if (argument == null) {
                throw new IllegalArgumentException("Argument is null.");
            }

            Loan loan = (Loan) argument;
            if (loan.getUser() == null) {
                throw new LibraryDAOException("User is null.");
            }
            if (loan.getBookCopy() == null) {
                throw new LibraryDAOException("BookCopy is null.");
            }

            loan.setId(1L);
            return null;
        }).when(loanDao).create(any(Loan.class));

        // update
        when(loanDao.update(loan)).thenReturn(loan);

        Mockito.doThrow(new LibraryDAOException("User not found.")).when(loanDao).findByUser(new User());
    }

    @Test
    public void testCreate() {
        loanService.create(loan);
        verify(loanDao).create(loan);
    }

    @Test(expectedExceptions = LibraryDAOException.class)
    public void testCreateNoBook() {
        Loan loan = new Loan(user, null, LocalDateTime.of(2016,10,10,10,10));
        loanService.create(loan);
    }

    @Test(expectedExceptions = LibraryDAOException.class)
    public void testCreateNoUser() {
        Loan loan = new Loan(null, bookCopy, LocalDateTime.of(2016,10,10,10,10));
        loanService.create(loan);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateNull() {
        loanService.create(null);
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

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateNull() {
        loanService.update(null);
    }

    @Test
    public void testDelete() {
        loanService.delete(loan);
        verify(loanDao).delete(loan);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDeleteNull() {
        loanService.delete(null);
    }

    @Test
    public void testFindById() {
        assertSame(loanService.findById(loan.getId()), loan);
        verify(loanDao).findById(loan.getId());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindByIdNull() {
        loanService.findById(null);
    }

    @Test
    public void testFindByNonExistingId() {
        assertNull(loanService.findById(2L));
        verify(loanDao).findById(2L);
    }

    @Test
    public void testFindByUser() {
        assertEquals(loanService.findByUser(user).size(), 6);
        verify(loanDao).findByUser(user);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindByUserNull() {
        loanService.findByUser(null);
    }

    @Test(expectedExceptions = LibraryDAOException.class)
    public void testFindByNonExistingUser() {
        loanService.findByUser(new User());
        verify(loanDao).findByUser(new User());
    }

    @Test
    public void testFindAll() {
        assertEquals(loanService.findAll().size(), 2);
        verify(loanDao).findAll();
    }

    @Test
    public void testFindNotReturnedUserLoans() {
        List<Loan> found = loanService.findNotReturnedUserLoans(user);
        assertEquals(found.size(), 3);
        assertEquals(found.get(0), notReturnedLoan2);
        assertEquals(found.get(1), notReturnedLoan1);
        assertEquals(found.get(2), notReturnedLoan3);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindNotReturnedUserLoansNullUser() {
        loanService.findNotReturnedUserLoans(null);
    }

    @Test(expectedExceptions = LibraryDAOException.class)
    public void testFindNotReturnedUserLoansByNonExistingUser() {
        loanService.findNotReturnedUserLoans(new User());
        verify(loanDao).findByUser(new User());
    }

    @Test
    public void testFindReturnedUserLoans() {
        List<Loan> found = loanService.findReturnedUserLoans(user);
        assertEquals(found.size(), 3);
        assertEquals(found.get(0), returnedLoan2);
        assertEquals(found.get(1), returnedLoan1);
        assertEquals(found.get(2), returnedLoan3);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindReturnedUserLoansNullUser() {
        loanService.findReturnedUserLoans(null);
    }

    @Test(expectedExceptions = LibraryDAOException.class)
    public void testFindReturnedUserLoansByNonExistingUser() {
        loanService.findReturnedUserLoans(new User());
        verify(loanDao).findByUser(new User());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testReturnLoan(){
        loanService.returnLoan(null);
    }

    @Test
    public void testFindAllReturned(){
        List<Loan> loans = loanService.findAllReturned();
        assertEquals(loans.size(),1);
        assertEquals(loans.get(0), returnedLoan1);
    }

    @Test
    public void testFindAllNotReturned(){
        List<Loan> loans = loanService.findAllNotReturned();
        assertEquals(loans.size(),1);
        assertEquals(loans.get(0), notReturnedLoan1);
    }

}
