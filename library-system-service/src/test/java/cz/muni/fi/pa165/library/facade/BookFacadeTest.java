package cz.muni.fi.pa165.library.facade;

import cz.muni.fi.pa165.library.config.ServiceConfiguration;
import cz.muni.fi.pa165.library.dto.BookDTO;
import cz.muni.fi.pa165.library.dto.BookNewDTO;
import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.Category;
import cz.muni.fi.pa165.library.mapping.BeanMappingService;
import cz.muni.fi.pa165.library.mapping.BeanMappingServiceImpl;
import cz.muni.fi.pa165.library.service.BookService;
import org.mockito.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

/**
 * @author Martin
 * @version 24.11.2016.
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class BookFacadeTest extends AbstractTransactionalTestNGSpringContextTests {

    @Mock
    private BookService bookService;

    @Spy
    @Inject
    private final BeanMappingService beanMappingService = new BeanMappingServiceImpl();

    @InjectMocks
    private BookFacade bookFacade = new BookFacadeImpl();

    @Captor
    private ArgumentCaptor<Book> bookArgumentCaptor;


    @BeforeClass
    public void initMockito() {
        MockitoAnnotations.initMocks(this);
    }

    private Book book1;
    private Book book2;
    private Category category11;
    private Category category12;
    private Category category21;


    private BookNewDTO bookNewDTO;
    private BookDTO bookDTO;

    @BeforeMethod
    public void initEntities() {
        book1 = new Book("bookTitle1", "bookAuthor1", "1111111111");
        book1.setId(1L);
        book2 = new Book("bookTitle2", "bookAuthor1", "2222222222");
        book2.setId(2L);
        category11 = new Category("categoryName11");
        category11.setId(11L);
        category12 = new Category("categoryName12");
        category12.setId(12L);
        category21 = new Category("categoryName21");
        category21.setId(21L);
    }

    @BeforeMethod(dependsOnMethods = "initEntities")
    public void initMockBehaviour() {
        when(bookService.findById(book1.getId())).thenReturn(book1);
        when(bookService.findByTitle(book1.getTitle())).thenReturn(Arrays.asList(book1, book2));
        when(bookService.findByAuthor(book1.getAuthor())).thenReturn(Arrays.asList(book1, book2));
        when(bookService.findAll()).thenReturn(Arrays.asList(book1, book2));
        when(bookService.findLoanableBooks()).thenReturn(Arrays.asList(book1, book2));
    }

    @Test
    public void testCreate() {
        bookNewDTO = new BookNewDTO(book1.getTitle(), book1.getAuthor(), book1.getIsbn());
        bookFacade.create(bookNewDTO);

        verify(bookService).create(bookArgumentCaptor.capture());

        Book entity = bookArgumentCaptor.getValue();

        assertNull(entity.getId());
        assertDeepEquals(book1, entity);
    }

    @Test
    public void testUpdate() {
        bookDTO = new BookDTO();
        bookDTO.setAuthor(book1.getAuthor());
        bookDTO.setId(book1.getId());
        bookDTO.setTitle(book2.getTitle());

        bookFacade.update(bookDTO);

        verify(bookService).update(bookArgumentCaptor.capture());

        Book entity = bookArgumentCaptor.getValue();

        assertEquals(entity.getTitle(), book2.getTitle());
        assertEquals(entity.getAuthor(), book1.getAuthor());
        assertEquals(entity.getId(), book1.getId());
    }

    @Test
    public void testDelete() {
        bookFacade.delete(book1.getId());
        verify(bookService).delete(book1);
    }

    @Test
    public void testFindById() {
        bookDTO = bookFacade.findById(book1.getId());
        assertNotNull(bookDTO);
        assertDeepEquals(book1, bookDTO);
    }

    @Test
    public void testFindByAuthor() {
        List<BookDTO> bookDTOs = bookFacade.findByAuthor(book1.getAuthor());
        assertEquals(bookDTOs.size(), 2);
        assertDeepEquals(book1, bookDTOs.get(0));
        assertDeepEquals(book2, bookDTOs.get(1));
    }

    @Test
    public void testFindByTitle() {
        List<BookDTO> bookDTOs = bookFacade.findByTitle(book1.getTitle());
        assertNotNull(bookDTO);
        assertDeepEquals(book1, bookDTOs.get(0));
        assertDeepEquals(book2, bookDTOs.get(1));
    }

    @Test
    public void testFindLoanableBooks(){
        List<BookDTO> bookDTOs = bookFacade.findLoanableBooks();
        assertNotNull(bookDTOs);
        assertEquals(bookDTOs.size(), 2);
        assertDeepEquals(book1, bookDTOs.get(0));
        assertDeepEquals(book2, bookDTOs.get(1));
    }

    @Test
    public void testFindAll() {
        List<BookDTO> bookDTOs = bookFacade.findAll();
        assertNotNull(bookDTOs);
        assertEquals(bookDTOs.size(), 2);
        assertDeepEquals(book1, bookDTOs.get(0));
        assertDeepEquals(book2, bookDTOs.get(1));
    }

    private void assertDeepEquals(Book book1, BookDTO bookDTO) {
        assertEquals(book1.getTitle(), bookDTO.getTitle());
        assertEquals(book1.getAuthor(), bookDTO.getAuthor());
        assertEquals(book1.getIsbn(), bookDTO.getIsbn());
    }

    private void assertDeepEquals(Book book1, Book book2) {
        assertEquals(book1.getTitle(), book2.getTitle());
        assertEquals(book1.getAuthor(), book2.getAuthor());
        assertEquals(book1.getIsbn(), book2.getIsbn());
    }
}
