package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.LibraryApplicationContext;
import cz.muni.fi.pa165.library.entity.*;
import cz.muni.fi.pa165.library.enums.BookState;
import cz.muni.fi.pa165.library.enums.UserRole;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;

/**
 * Test suite for the Loan DAO
 *
 * @author Dávid Lupták
 * @version 26.10.2016
 */
@ContextConfiguration(classes = LibraryApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class LoanDaoTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    EntityManager em;

    @Inject
    private LoanDao loanDao;

    @Inject
    private UserDao userDao;

    @Inject
    private BookCopyDao bookCopyDao;

    @Inject
    private BookDao bookDao;

    @Inject
    private CategoryDao categoryDao;

    private User joshua;
    private Category programming;
    private Book effectiveJava;
    private BookCopy effectiveJava42;

    @BeforeMethod
    public void createUser() {
        joshua = new User();
        joshua.setEmail("joshua@java.com");
        joshua.setGivenName("Joshua");
        joshua.setSurname("Bloch");
        joshua.setAddress("Somewhere in the space");
        joshua.setUserRole(UserRole.MEMBER);
        joshua.setPasswordHash("hash123");

        userDao.create(joshua);
    }

    @BeforeMethod
    public void createBookCopy() {
        programming = new Category();
        programming.setName("Programming");

        categoryDao.create(programming);

        effectiveJava = new Book();
        effectiveJava.setAuthor("JB");
        effectiveJava.setName("EJ");
        effectiveJava.setIsbn("978-0-321-35668-0");
        effectiveJava.addCategory(programming);

        bookDao.create(effectiveJava);

        effectiveJava42 = new BookCopy();
        effectiveJava42.setBookState(BookState.NEW);
        effectiveJava42.setBook(effectiveJava);

        bookCopyDao.create(effectiveJava42);
    }

    @Test
    public void createLoan() {
        Loan aLoan = getJoshuaLoan();
        loanDao.create(aLoan);

        Assert.assertNotNull(aLoan.getId());
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void createNullLoan() {
        loanDao.create(null);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createEmptyLoan() {
        loanDao.create(new Loan());
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void createReturnedLoanWithoutReturnDate() {
        Loan aLoan = new Loan();
        aLoan.setUser(joshua);
        aLoan.addBookCopy(effectiveJava42);
        aLoan.setLoanDate(new Date());
        aLoan.setReturned(true);

        loanDao.create(aLoan);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void createNotReturnedLoanWithReturnDate() {
        Loan aLoan = new Loan();
        aLoan.setUser(joshua);
        aLoan.addBookCopy(effectiveJava42);
        aLoan.setLoanDate(new Date());
        aLoan.setReturnDate(new Date());
        aLoan.setReturned(false);

        loanDao.create(aLoan);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void createLoanWithoutBookCopy() {
        Loan aLoan = new Loan();
        aLoan.setUser(joshua);
        aLoan.setLoanDate(new Date());
        aLoan.setReturned(false);

        loanDao.create(aLoan);
    }

    @Test
    public void createLoanWithoutUser() {
        Loan aLoan = getJoshuaLoan();
        aLoan.setUser(null);

        loanDao.create(aLoan);
        Assert.assertNotNull(aLoan.getId());
    }

    @Test
    public void findLoanById() {
        Loan aLoan = getJoshuaLoan();
        loanDao.create(aLoan);

        Loan bLoan = getBlochLoan();
        loanDao.create(bLoan);

        Loan resultLoan1 = loanDao.findById(aLoan.getId());
        assertDeepEquals(resultLoan1, aLoan);

        Loan resultLoan2 = loanDao.findById(bLoan.getId());
        assertDeepEquals(resultLoan2, bLoan);
    }

//    @Test
//    public void findLoanByIdWithoutAnyBookCopy() {
//        Loan aLoan = new Loan();
//        aLoan.setUser(joshua);
//        aLoan.setLoanDate(new Date());
//        aLoan.setReturned(false);
//
//        loanDao.create(aLoan);
//
//        Loan resultLoan1 = loanDao.findById(aLoan.getId());
//        Assert.assertEquals(resultLoan1.getBookCopy().size(), 0);
//    }

    @Test
    public void findLoanByIdWithoutUser() {
        Loan aLoan = getJoshuaLoan();
        aLoan.setUser(null);
        loanDao.create(aLoan);

        Loan resultLoan1 = loanDao.findById(aLoan.getId());
        Assert.assertEquals(resultLoan1.getUser(), null);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void findLoanByNullId() {
        loanDao.findById(null);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void findNotPersistedLoanById() {
        Loan aLoan = getBlochLoan();
        loanDao.create(aLoan);

        Loan bLoan = getJoshuaLoan();

        Loan result1 = loanDao.findById(bLoan.getId());
    }

    @Test
    public void findAllLoans() {
        Loan aLoan = getBlochLoan();
        loanDao.create(aLoan);

        Loan bLoan = getJoshuaLoan();
        loanDao.create(bLoan);

        List<Loan> resultLoans = loanDao.findAll();
        Assert.assertNotNull(resultLoans);
        Assert.assertEquals(resultLoans.size(), 2);
    }

    @Test
    public void findAllLoansEmpty() {
        Assert.assertEquals(loanDao.findAll().size(), 0);
    }

    @Test
    public void updateLoan() {
        Loan aLoan = getJoshuaLoan();
        loanDao.create(aLoan);

        aLoan.setReturnBookState(BookState.HEAVY_DAMAGE);
        Loan updatedLoan1 = loanDao.update(aLoan);
        Assert.assertEquals(updatedLoan1.getReturnBookState(), BookState.HEAVY_DAMAGE);
    }

    @Test
    public void updateUser() {
        Loan aLoan = getBlochLoan();
        loanDao.create(aLoan);

        aLoan.setUser(joshua);
        Loan updatedLoan1 = loanDao.update(aLoan);
        Assert.assertEquals(updatedLoan1.getUser(), joshua);
        assertDeepEquals(updatedLoan1, aLoan);
    }

    @Test
    public void updateNullUser() {
        Loan aLoan = getBlochLoan();
        loanDao.create(aLoan);

        aLoan.setUser(null);
        Loan updatedLoan1 = loanDao.update(aLoan);
        assertDeepEquals(updatedLoan1, aLoan);
    }

    @Test
    public void updateEmptyUser() {
        Loan aLoan = getBlochLoan();
        loanDao.create(aLoan);

        aLoan.setUser(new User());
        Loan updatedLoan1 = loanDao.update(aLoan);
        assertDeepEquals(updatedLoan1, aLoan);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void updateLoanNoBookCopy() {
        Loan aLoan = getJoshuaLoan();
        loanDao.create(aLoan);

        // such method needed
        // aLoan.removeBookCopy(effectiveJava42);
        Loan updatedLoan1 = loanDao.update(aLoan);
    }

    @Test
    public void deleteLoan() {
        Loan aLoan = getJoshuaLoan();
        loanDao.create(aLoan);

        Loan bLoan = getBlochLoan();
        loanDao.create(bLoan);

        Assert.assertEquals(loanDao.findAll().size(), 2);
        loanDao.delete(aLoan);
        Assert.assertEquals(loanDao.findAll().size(), 1);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void deleteNullLoan(){
        loanDao.delete(null);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void deleteNotPersistedLoan(){
        Loan aLoan = getJoshuaLoan();
        loanDao.delete(aLoan);
    }

    private Loan getBlochLoan() {
        Loan blochLoan = new Loan();
        blochLoan.setLoanDate(new Date());
        blochLoan.setReturnBookState(BookState.NEW);
        blochLoan.setReturned(false);
        blochLoan.addBookCopy(effectiveJava42);
        blochLoan.setUser(joshua);

        return blochLoan;
    }

    private Loan getJoshuaLoan() {
        Loan joshuaLoan = new Loan();
        joshuaLoan.setLoanDate(new Date());
        joshuaLoan.setReturnDate(new Date());
        joshuaLoan.setReturnBookState(BookState.LIGHT_DAMAGE);
        joshuaLoan.setReturned(true);
        joshuaLoan.addBookCopy(effectiveJava42);
        joshuaLoan.setUser(joshua);

        return joshuaLoan;
    }

    private void assertDeepEquals(Loan loan1, Loan loan2) {
        Assert.assertEquals(loan1, loan2);
        Assert.assertEquals(loan1.getId(), loan2.getId());
        Assert.assertEquals(loan1.getUser(), loan2.getUser());
        Assert.assertEquals(loan1.getLoanDate(), loan2.getLoanDate());
        Assert.assertEquals(loan1.getReturnDate(), loan2.getReturnDate());
        Assert.assertEquals(loan1.getReturnBookState(), loan2.getReturnBookState());
        Assert.assertEquals(loan1.isReturned(), loan2.isReturned());
        Assert.assertEquals(loan1.getBookCopy(), loan2.getBookCopy());
    }
}
