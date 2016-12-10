package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.config.ServiceConfiguration;
import cz.muni.fi.pa165.library.dao.BookDao;
import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.BookCopy;
import cz.muni.fi.pa165.library.entity.Category;
import cz.muni.fi.pa165.library.enums.BookState;
import cz.muni.fi.pa165.library.exception.LibraryDAOException;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

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

    @Captor
    private ArgumentCaptor<Book> bookArgumentCaptor;

    @BeforeClass
    public void initMockito() {
        MockitoAnnotations.initMocks(this);
    }

    private Book book1;
    private Book book2;
    private Book book3;
    private Category category1;
    private Category category2;
    private BookCopy bookCopy;

    @BeforeMethod
    public void initEntities() {
        book1 = new Book("bookTitle1", "bookAuthor1", "1111111111");
        book1.setId(1L);
        book2 = new Book("bookTitle2", "bookAuthor1", "2222222222");
        book2.setId(2L);
        book3 = new Book("bookTitle3", "bookAuthor2", "3333333333");
        book3.setId(3L);
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

        // findByTitle
        when(bookDao.findByTitle(book1.getTitle())).thenReturn(Arrays.asList(book1));
        when(bookDao.findByTitle("Not existing Title")).thenReturn(Arrays.asList());

        // findAll
        when(bookDao.findAll()).thenReturn(Arrays.asList(book1, book2));

        // create
        doAnswer((InvocationOnMock invocation) -> {
            Object argument = invocation.getArguments()[0];
            if (argument == null) {
                throw new IllegalArgumentException("Argument is null.");
            }

            Book book = (Book) argument;
            if (book.getTitle() == null) {
                throw new LibraryDAOException("Title is null.");
            }
            if (book.getAuthor() == null) {
                throw new LibraryDAOException("Author is null.");
            }
            if (book.getIsbn() == null) {
                throw new LibraryDAOException("ISBN is null.");
            }

            book.setId(1L);
            return null;

        }).when(bookDao).create(any(Book.class));

        // update
        when(bookDao.update(book3)).thenReturn(book3);
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
        book2.addBookCopy(bookCopy);
        bookService.create(book2);
        verify(bookDao).create(book2);
    }

    @Test
    public void testCreateNoBookCopy() {
        book3.addCategory(category1);
        bookService.create(book3);
        verify(bookDao).create(book3);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateNull() {
        bookService.create(null);
    }

    @Test
    public void testUpdate() {
        Book updated = bookService.update(book3);

        verify(bookDao).update(bookArgumentCaptor.capture());
        Book captured = bookArgumentCaptor.getValue();
        assertEquals(captured, book3);
        assertEquals(captured.getIsbn(), book3.getIsbn());
        assertEquals(updated, book3);
        assertEquals(updated.getIsbn(), book3.getIsbn());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateNull() {
        bookService.update(null);
    }

    @Test
    public void testDelete() {
        bookService.delete(book1);
        verify(bookDao).delete(book1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDeleteNull() {
        bookService.delete(null);
    }

    @Test
    public void testFindById() {
        assertSame(bookService.findById(book1.getId()), book1);
        verify(bookDao).findById(book1.getId());
    }

    @Test
    public void testFindByNonExistingId() {
        assertNull(bookService.findById(3L));
        verify(bookDao).findById(3L);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindByNullId() {
        bookService.findById(null);
    }

    @Test
    public void testFindByAuthor() {
        assertEquals(bookService.findByAuthor(book1.getAuthor()), Arrays.asList(book1, book2));
        verify(bookDao).findByAuthor(book1.getAuthor());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindByNullAuthor() {
        bookService.findByAuthor(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindByEmptyAuthor() {
        bookService.findByAuthor("");
    }

    @Test
    public void testFindByNonExistingAuthor() {
        assertEquals(bookService.findByAuthor("Not existing Author"), Arrays.asList());
        verify(bookDao).findByAuthor("Not existing Author");
    }

    @Test
    public void testFindByTitle() {
        assertEquals(bookService.findByTitle(book1.getTitle()), Arrays.asList(book1));
        verify(bookDao).findByTitle(book1.getTitle());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindByNullTitle() {
        bookService.findByTitle(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindByEmptyTitle() {
        bookService.findByTitle("");
    }

    @Test
    public void testFindByNonExistingTitle() {
        assertEquals(bookService.findByTitle("Not existing Title"), Arrays.asList());
        verify(bookDao).findByTitle("Not existing Title");
    }

    @Test
    public void testFindAll() {
        assertEquals(bookService.findAll(), Arrays.asList(book1, book2));
        verify(bookDao).findAll();

    }
}
