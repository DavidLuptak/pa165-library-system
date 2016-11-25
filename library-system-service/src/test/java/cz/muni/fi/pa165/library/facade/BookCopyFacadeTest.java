package cz.muni.fi.pa165.library.facade;

import cz.muni.fi.pa165.library.config.ServiceConfiguration;
import cz.muni.fi.pa165.library.dto.BookCopyDTO;
import cz.muni.fi.pa165.library.dto.BookCopyNewDTO;
import cz.muni.fi.pa165.library.dto.BookDTO;
import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.BookCopy;
import cz.muni.fi.pa165.library.enums.BookState;
import cz.muni.fi.pa165.library.mapping.BeanMappingService;
import cz.muni.fi.pa165.library.mapping.BeanMappingServiceImpl;
import cz.muni.fi.pa165.library.service.BookCopyService;
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
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

/**
 * @author Martin
 * @version 24.11.2016.
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class BookCopyFacadeTest extends AbstractTransactionalTestNGSpringContextTests {

    @Mock
    private BookService bookService;

    @Mock
    private BookCopyService bookCopyService;

    @Spy
    @Inject
    private BeanMappingService beanMappingService = new BeanMappingServiceImpl();

    @InjectMocks
    private BookCopyFacade bookCopyFacade = new BookCopyFacadeImpl();

    @Captor
    private ArgumentCaptor<BookCopy> bookCopyArgumentCaptor;

    @BeforeClass
    public void initMockito() {
        MockitoAnnotations.initMocks(this);
    }

    private Book book1;
    private Book book2;
    private BookCopy bookCopy1;
    private BookCopy bookCopy2;

    private BookCopyNewDTO bookCopyNewDTO;
    private BookDTO bookDTO;
    private BookCopyDTO bookCopyDTO;

    @BeforeMethod
    public void initEntities(){
        book1 = new Book("bookName1", "bookAuthor1", "1111111111");
        book1.setId(1L);
        book2 = new Book("bookName2", "bookAuthor1", "2222222222");
        book2.setId(2L);

        bookCopy1 = new BookCopy(book1, BookState.NEW);
        bookCopy1.setId(1L);
        bookCopy2 = new BookCopy(book2, BookState.NEW);
        bookCopy1.setId(2L);

        bookDTO = new BookDTO();
        bookDTO.setName(book1.getName());
        bookDTO.setAuthor(book1.getAuthor());
        bookDTO.setIsbn(book1.getIsbn());

    }

    @BeforeMethod(dependsOnMethods = "initEntities")
    public void initMockBehaviour() {
        when(bookCopyService.findById(bookCopy1.getId())).thenReturn(bookCopy1);
        when(bookCopyService.findByBook(book1)).thenReturn(Arrays.asList(bookCopy1, bookCopy2));
        when(bookService.findById(book1.getId())).thenReturn(book1);
    }

    @Test
    public void testCreate() {
        bookCopyNewDTO = new BookCopyNewDTO( );
        bookCopyNewDTO.setBook(bookDTO);
        bookCopyNewDTO.setBookState(BookState.NEW);
        bookCopyFacade.create(bookCopyNewDTO);

        verify(bookCopyService).create(bookCopyArgumentCaptor.capture());

        BookCopy entity = bookCopyArgumentCaptor.getValue();

        assertNull(entity.getId());
        assertDeepEquals(bookCopy1, entity);
    }

    @Test
    public void testUpdate() {
        bookCopyDTO = new BookCopyDTO();
        bookCopyDTO.setBook(bookDTO);
        bookCopyDTO.setBookState(BookState.LIGHT_DAMAGE);
        bookCopyDTO.setId(bookCopy1.getId());

        bookCopyFacade.update(bookCopyDTO);

        verify(bookCopyService).update(bookCopyArgumentCaptor.capture());
        assertEquals(bookCopyDTO.getBookState(),BookState.LIGHT_DAMAGE);
        assertEquals(bookCopyDTO.getBook(),bookDTO);
        assertEquals(bookCopyDTO.getId(),bookCopy1.getId());
    }

    @Test
    public void testDelete() {
        bookCopyFacade.delete(bookCopy1.getId());
        verify(bookCopyService).delete(bookCopy1);
    }

    @Test
    public void testFindById() {
        bookCopyDTO = bookCopyFacade.findById(bookCopy1.getId());
        assertNotNull(bookCopyDTO);
        assertDeepEquals(bookCopy1, bookCopyDTO);
    }

    @Test
    public void testFindByBook(){
        List<BookCopyDTO> result = bookCopyFacade.findByBook(book1.getId());
        assertNotNull(result);
        assertEquals(result.size(), 2);
        assertDeepEquals(bookCopy1, result.get(0));
        assertDeepEquals(bookCopy2, result.get(1));
    }

    private void assertDeepEquals(BookCopy bookCopy1, BookCopyDTO bookCopyDTO){
        assertEquals(bookCopy1.getBook().getId(),bookCopyDTO.getBook().getId());
        assertEquals(bookCopy1.getBookState(),bookCopyDTO.getBookState());
    }

    private void assertDeepEquals(BookCopy bookCopy1, BookCopy bookCopy2){
        assertEquals(bookCopy1.getBook(),bookCopy2.getBook());
        assertEquals(bookCopy1.getBookState(),bookCopy2.getBookState());
    }
}
