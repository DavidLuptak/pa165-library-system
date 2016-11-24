package cz.muni.fi.pa165.library.facade;

import cz.muni.fi.pa165.library.config.ServiceConfiguration;
import cz.muni.fi.pa165.library.dto.CategoryDTO;
import cz.muni.fi.pa165.library.dto.CategoryNewDTO;
import cz.muni.fi.pa165.library.dto.CategoryDTO;
import cz.muni.fi.pa165.library.dto.CategoryNewDTO;
import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.Category;
import cz.muni.fi.pa165.library.entity.Category;
import cz.muni.fi.pa165.library.mapping.BeanMappingService;
import cz.muni.fi.pa165.library.mapping.BeanMappingServiceImpl;
import cz.muni.fi.pa165.library.service.BookService;
import cz.muni.fi.pa165.library.service.CategoryService;
import org.mockito.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
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
public class CategoryFacadeTest extends AbstractTransactionalTestNGSpringContextTests {

    @Mock
    private CategoryService categoryService;

    @Spy
    @Inject
    private final BeanMappingService beanMappingService = new BeanMappingServiceImpl();

    @InjectMocks
    private CategoryFacade categoryFacade = new CategoryFacadeImpl();

    @Captor
    private ArgumentCaptor<Category> categoryArgumentCaptor;

    @BeforeClass
    public void initMockito() {
        MockitoAnnotations.initMocks(this);
    }

    private Book book1;
    private Book book2;
    private Book book3;
    private Category category1;
    private Category category2;

    private CategoryNewDTO categoryNewDTO;
    private CategoryDTO categoryDTO;

    @BeforeMethod
    public void initEntities() {
        book1 = new Book("bookName1", "bookAuthor1", "1111111111");
        book1.setId(1L);
        book2 = new Book("bookName2", "bookAuthor2", "2222222222");
        book1.setId(2L);
        book3 = new Book("bookName3", "bookAuthor3", "3333333333");
        book1.setId(3L);
        category1 = new Category("categoryName1");
        category1.setId(1L);
        category2 = new Category("categoryName2");
        category1.setId(2L);
    }

    @BeforeMethod(dependsOnMethods = "initEntities")
    public void initMockBehaviour() {
        when(categoryService.findById(1L)).thenReturn(category1);
        when(categoryService.findByName("categoryName1")).thenReturn(category1);
        when(categoryService.findAll()).thenReturn(Arrays.asList(category1, category2));
    }

    @Test
    public void testCreate() {
        categoryNewDTO = new CategoryNewDTO(category1.getName());
        categoryFacade.create(categoryNewDTO);

        verify(categoryService).create(categoryArgumentCaptor.capture());

        Category entity = categoryArgumentCaptor.getValue();

        assertNull(entity.getId());
        assertEquals(category1.getName(), entity.getName());
    }

    @Test
    public void testUpdate() {
        categoryDTO = new CategoryDTO(category2.getName());
        categoryDTO.setId(category1.getId());

        categoryFacade.update(categoryDTO);

        verify(categoryService).update(categoryArgumentCaptor.capture());
        assertEquals(categoryDTO.getName(),category2.getName());
    }

    @Test
    public void testDelete() {
        categoryFacade.delete(1L);
        verify(categoryService).delete(category1);
    }

    @Test
    public void testFindById() {
        categoryDTO = categoryFacade.findById(category1.getId());
        assertNotNull(categoryDTO);
        assertEqualsCategoryAndCategoryDTO(category1, categoryDTO);
    }

    @Test
    public void testFindByName() {
        categoryDTO = categoryFacade.findByName(category1.getName());
        assertNotNull(categoryDTO);
        assertEqualsCategoryAndCategoryDTO(category1, categoryDTO);
    }

    @Test
    public void testFindAll() {
        List<CategoryDTO> categoryDTOs = categoryFacade.findAll();
        assertNotNull(categoryDTOs);
        assertEquals(categoryDTOs.size(), 2);
        assertEqualsCategoryAndCategoryDTO(category1, categoryDTOs.get(0));
        assertEqualsCategoryAndCategoryDTO(category2, categoryDTOs.get(1));
    }

    private void assertEqualsCategoryAndCategoryDTO(Category category, CategoryDTO categoryDTO) {
        assertEquals(category.getName(), categoryDTO.getName());
    }

}

