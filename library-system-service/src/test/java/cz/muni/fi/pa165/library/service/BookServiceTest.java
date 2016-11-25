package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.config.ServiceConfiguration;
import cz.muni.fi.pa165.library.dao.BookDao;
import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.BookCopy;
import cz.muni.fi.pa165.library.entity.Category;
import cz.muni.fi.pa165.library.enums.BookState;
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
public class BookServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Mock
    private BookDao bookDao;

    @InjectMocks
    private BookService bookService = new BookServiceImpl();

    @BeforeClass
    public void initMockito() {
        MockitoAnnotations.initMocks(this);
    }

    private Book book1;
    private Book book2;
    private Category category1;
    private Category category2;
    private BookCopy bookCopy;

    @BeforeMethod
    public void initEntities() {
        book1 = new Book("bookName1", "bookAuthor1", "1111111111");
        book1.setId(1L);
        book2 = new Book("bookName2", "bookAuthor1", "2222222222");
        book2.setId(2L);
        category1 = new Category("categoryName11");
        category1.setId(1L);
        category2 = new Category("categoryName12");
        category2.setId(2L);
        bookCopy = new BookCopy(book1, BookState.NEW);
        bookCopy.setId(1L);
    }

    @BeforeMethod(dependsOnMethods = "initEntities")
    public void initMocksBehaviour() {
        // findById
        when(bookDao.findById(book1.getId())).thenReturn(book1);
        when(bookDao.findById(3L)).thenReturn(null);

        // findByAuthor
        when(bookDao.findByAuthor(book1.getAuthor())).thenReturn(Arrays.asList(book1, book2));
        when(bookDao.findByAuthor("Not existing Author")).thenReturn(Arrays.asList());

        // findByName
        when(bookDao.findByName(book1.getName())).thenReturn(Arrays.asList(book1));
        when(bookDao.findByName("Not existing Name")).thenReturn(Arrays.asList());

        // findAll
        when(bookDao.findAll()).thenReturn(Arrays.asList(book1, book2));

        // create
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object argument = invocation.getArguments()[0];
                if (argument == null) {
                    throw new IllegalArgumentException("Argument is null.");
                }

                Book book = (Book) argument;
                if (book.getName() == null) {
                    throw new LibrarySystemDataAccessException("Name is null.");
                }
                if (book.getAuthor() == null) {
                    throw new LibrarySystemDataAccessException("Author is null.");
                }
                if (book.getIsbn() == null) {
                    throw new LibrarySystemDataAccessException("ISBN is null.");
                }

                book.setId(1L);
                return null;
            }
        }).when(bookDao).create(any(Book.class));
    }

    @Test
    public void testCreate() {
        book1.addCategory(category1);
        book1.addBookCopy(bookCopy);
        bookService.create(book1);
        verify(bookDao).create(book1);
    }

    @Test
    public void testCreateNoCategory() {
        book1.addBookCopy(bookCopy);
        bookService.create(book1);
        verify(bookDao).create(book1);
    }

    @Test
    public void testCreateNoBookCopy() {
        book1.addCategory(category1);
        bookService.create(book1);
        verify(bookDao).create(book1);
    }

    //TODO: do
    @Test
    public void testUpdate() {

    }

    @Test
    public void testDelete() {
        bookService.delete(book1);
        verify(bookDao).delete(book1);
    }

    @Test
    public void testFindById() {
        assertSame(bookService.findById(book1.getId()),book1);
        verify(bookDao).findById(book1.getId());
    }

    @Test
    public void testFindByNonExistingId() {
        assertNull(bookService.findById(3L));
        verify(bookDao).findById(3L);

    }

    @Test
    public void testFindByAuthor() {
        assertEquals(bookService.findByAuthor(book1.getAuthor()),Arrays.asList(book1, book2));
        verify(bookDao).findByAuthor(book1.getAuthor());
    }

    @Test
    public void testFindByNonExistingAuthor() {
        assertEquals(bookService.findByAuthor("Not existing Author"),Arrays.asList());
        verify(bookDao).findByAuthor("Not existing Author");
    }

    @Test
    public void testFindByName() {
        assertEquals(bookService.findByName(book1.getName()), Arrays.asList(book1));
        verify(bookDao).findByName(book1.getName());
    }

    @Test
    public void testFindByNonExistingName() {
        assertEquals(bookService.findByName("Not existing Name"),Arrays.asList());
        verify(bookDao).findByName("Not existing Name");
    }

    @Test
    public void testFindAll() {
        assertEquals(bookService.findAll(),Arrays.asList(book1, book2));
        verify(bookDao).findAll();

    }
}