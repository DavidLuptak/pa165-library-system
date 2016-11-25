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
        book1 = new Book("bookName1", "bookAuthor1", "1111111111");
        book1.setId(1L);
        book2 = new Book("bookName2", "bookAuthor1", "2222222222");
        book1.setId(2L);
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
        when(bookService.findByName(book1.getName())).thenReturn(Arrays.asList(book1,book2));
        when(bookService.findByAuthor(book1.getAuthor())).thenReturn(Arrays.asList(book1,book2));
        when(bookService.findAll()).thenReturn(Arrays.asList(book1, book2));
    }

    @Test
    public void testCreate() {
        bookNewDTO = new BookNewDTO(book1.getName(),book1.getAuthor(),book1.getIsbn());
        bookFacade.create(bookNewDTO);

        verify(bookService).create(bookArgumentCaptor.capture());

        Book entity = bookArgumentCaptor.getValue();

        assertNull(entity.getId());
        assertDeepEquals(book1, entity);
    }

    @Test
    public void testUpdate() {
        bookDTO = new BookDTO(book1.getName(),book1.getAuthor(),book1.getIsbn(), Arrays.asList(category11.getId(),category12.getId()));
        bookDTO.setId(book1.getId());
        bookDTO.setName(book2.getName());

        bookFacade.update(bookDTO);

        verify(bookService).update(bookArgumentCaptor.capture());
        assertEquals(bookDTO.getName(),book2.getName());
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
        assertDeepEquals(category1, bookDTO);
    }

    @Test
    public void testFindByName() {
        bookDTO = bookFacade.findByName(book1.getName());
        assertNotNull(bookDTO);
        assertEqualsCategoryAndCategoryDTO(category1, bookDTO);
    }

    @Test
    public void testFindAll() {
        List<bookDTO> categoryDTOs = categoryFacade.findAll();
        assertNotNull(categoryDTOs);
        assertEquals(categoryDTOs.size(), 2);
        assertEqualsCategoryAndCategoryDTO(category1, categoryDTOs.get(0));
        assertEqualsCategoryAndCategoryDTO(category2, categoryDTOs.get(1));
    }

    private void assertDeepEquals(Book book1, BookDTO bookDTO){
        assertEquals(book1.getName(),bookDTO.getName());
        assertEquals(book1.getAuthor(),bookDTO.getAuthor());
        assertEquals(book1.getBookCopies(),bookDTO.getBookCopies());
        assertEquals(book1.getCategories(),bookDTO.getCategories());
        assertEquals(book1.getIsbn(),bookDTO.getIsbn());
    }

    private void assertDeepEquals(Book book1, Book book2){
        assertEquals(book1.getName(),book2.getName());
        assertEquals(book1.getAuthor(),book2.getAuthor());
        assertEquals(book1.getBookCopies(),book2.getBookCopies());
        assertEquals(book1.getCategories(),book2.getCategories());
        assertEquals(book1.getIsbn(),book2.getIsbn());
    }






}
