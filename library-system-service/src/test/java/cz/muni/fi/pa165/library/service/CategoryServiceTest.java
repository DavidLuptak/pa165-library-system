package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.config.ServiceConfiguration;
import cz.muni.fi.pa165.library.dao.CategoryDao;
import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.Category;
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
public class CategoryServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Mock
    private CategoryDao categoryDao;

    @InjectMocks
    private CategoryService categoryService = new CategoryServiceImpl();

    private Category category1;
    private Category category2;
    private Book book1;
    private Book book2;

    @BeforeClass
    public void initMockito() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void initEntities(){
        category1 = new Category("categoryName1");
        category1.setId(1L);
        category2 = new Category("categoryName2");
        category2.setId(2L);
        book1 = new Book("bookName1","bookAuthor1","1111111111");
        book1.setId(1L);
        book2 = new Book("bookName2","bookAuthor2","2222222222");
        book2.setId(2L);
    }

    @BeforeMethod(dependsOnMethods = "initEntities")
    public void initMocksBehaviour() {
        // findById
        when(categoryDao.findById(category1.getId())).thenReturn(category1);
        when(categoryDao.findById(category2.getId())).thenReturn(category2);
        when(categoryDao.findById(3L)).thenReturn(null);

        // findByName
        when(categoryDao.findByName(category1.getName())).thenReturn(category1);
        when(categoryDao.findByName("not existing")).thenReturn(null);

        // findAll
        when(categoryDao.findAll()).thenReturn(Arrays.asList(category1,category2));

        // create
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object argument = invocation.getArguments()[0];
                if (argument == null) {
                    throw new IllegalArgumentException("Argument is null.");
                }

                Category category = (Category) argument;
                if (category.getName() == null) {
                    throw new LibrarySystemDataAccessException("Category is null.");
                }

                category.setId(1L);
                return null;
            }
        }).when(categoryDao).create(any(Category.class));
    }

    @Test
    public void testCreate(){
        category1.addBook(book1);
        categoryService.create(category1);
        verify(categoryDao).create(category1);
    }

    @Test
    public void testCreateNoBook() {
        categoryService.create(category1);
        verify(categoryDao).create(category1);
    }

    //TODO:
    @Test
    public void testUpdate(){

    }

    @Test
    public void testDelete(){
        categoryService.delete(category1);
        verify(categoryDao).delete(category1);
    }

    @Test
    public void testFindById() {
        assertEquals(categoryService.findById(category1.getId()), category1);
        verify(categoryDao).findById(category1.getId());
    }

    @Test
    public void testFindByNonExistingId() {
        assertNull(categoryService.findById(3L));
        verify(categoryDao).findById(3L);
    }

    @Test
    public void testFindByName(){
        assertEquals(categoryService.findByName(category1.getName()),category1);
        verify(categoryDao).findByName(category1.getName());
    }

    @Test
    public void testFindByNonExistingName(){
        assertNull(categoryService.findByName("not existing"));
        verify(categoryDao).findByName("not existing");
    }

    @Test
    public void testFindAll(){
        assertEquals(categoryService.findAll(),Arrays.asList(category1,category2));
        verify(categoryDao).findAll();
    }
}
