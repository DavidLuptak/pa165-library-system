package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.config.ServiceConfiguration;
import cz.muni.fi.pa165.library.dao.BookCopyDao;
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
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;

/**
 * @author Martin
 * @version 25.11.2016.
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class BookCopyServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Mock
    private BookCopyDao bookCopyDao;

    @InjectMocks
    private BookCopyService bookCopyService = new BookCopyServiceImpl();

    private BookCopy bookCopy1;
    private BookCopy bookCopy2;
    private Book book1;
    private User user;
    private Loan loan;

    @BeforeClass
    public void initMockito() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void initEntities() {
        user = new User("Jan", "Novak", "jan@lala.com", "Brno", UserRole.MEMBER);
        user.setId(1L);
        book1 = new Book("Pride and Prejudice", "Jane Austen", "28392883829");
        book1.setId(1L);
        bookCopy1 = new BookCopy(book1, BookState.NEW);
        bookCopy1.setId(1L);
        loan = new Loan(user, bookCopy1, new Date());
        loan.setId(1L);
        bookCopy2 = new BookCopy(book1, BookState.LIGHT_DAMAGE);
        bookCopy2.setId(2L);
    }

    @BeforeMethod(dependsOnMethods = "initEntities")
    public void initMocksBehaviour() {
        // findById
        when(bookCopyDao.findById(1L)).thenReturn(bookCopy1);
        when(bookCopyDao.findById(3L)).thenReturn(null);

        // findByBook
        when(bookCopyDao.findByBook(book1)).thenReturn(Arrays.asList(bookCopy1,bookCopy2));
        when(bookCopyDao.findByBook(null)).thenReturn(Arrays.asList());

        // create
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object argument = invocation.getArguments()[0];
                if (argument == null) {
                    throw new IllegalArgumentException("Argument is null.");
                }

                BookCopy bookCopy = (BookCopy) argument;
                if (bookCopy.getBook() == null) {
                    throw new LibrarySystemDataAccessException("Book is null.");
                }
                if (bookCopy.getBookState() == null) {
                    throw new LibrarySystemDataAccessException("BookState is null.");
                }

                bookCopy.setId(1L);
                return null;
            }
        }).when(bookCopyDao).create(any(BookCopy.class));
    }

    @Test
    public void testCreate() {
        bookCopyService.create(bookCopy1);
        verify(bookCopyDao).create(bookCopy1);
    }

    @Test(expectedExceptions = LibrarySystemDataAccessException.class)
    public void testCreateNoBook() {
        bookCopy1.setBook(null);
        bookCopyService.create(bookCopy1);
        verify(bookCopyDao).create(bookCopy1);
    }

    @Test(expectedExceptions = LibrarySystemDataAccessException.class)
    public void testCreateNullBookState() {
        bookCopy1.setBookState(null);
        bookCopyService.create(bookCopy1);
        verify(bookCopyDao).create(bookCopy1);
    }

    //TODO:
    @Test
    public void testUpdate() {

    }

    @Test
    public void testDelete() {
        bookCopyService.delete(bookCopy1);
        verify(bookCopyDao).delete(bookCopy1);
    }

    @Test
    public void testFindById() {
        assertSame(bookCopyService.findById(bookCopy1.getId()), bookCopy1);
        verify(bookCopyDao).findById(bookCopy1.getId());
    }

    @Test
    public void testFindByNonExistingId() {
        assertNull(bookCopyService.findById(3L));
        verify(bookCopyDao).findById(3L);
    }

    @Test
    public void testFindByBook() {
        assertEquals(bookCopyService.findByBook(book1),Arrays.asList(bookCopy1,bookCopy2));
        verify(bookCopyDao).findByBook(book1);
    }

    @Test
    public void testFindByNullBook() {
        assertEquals(bookCopyService.findByBook(null),Arrays.asList());
        verify(bookCopyDao).findByBook(null);
    }
}
