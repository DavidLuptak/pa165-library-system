package cz.muni.fi.pa165.library.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.muni.fi.pa165.library.dto.BookDTO;
import cz.muni.fi.pa165.library.dto.BookNewDTO;
import cz.muni.fi.pa165.library.exception.NoEntityFoundException;
import cz.muni.fi.pa165.library.facade.BookFacade;
import cz.muni.fi.pa165.library.rest.controller.BookController;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Arrays;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


/**
 * Test suite for the {@link BookController}.
 *
 * @author Dávid Lupták
 * @version 9.12.2016
 */
@WebAppConfiguration
@ContextConfiguration(classes = RootConfigWebContext.class)
public class BookControllerTest extends AbstractTestNGSpringContextTests {

    @Mock
    BookFacade bookFacade;

    @Inject
    @InjectMocks
    BookController bookController;

    private MockMvc mockMvc;

    private BookDTO effectiveJava;
    private BookDTO cleanCode;
    private BookDTO invalidBook;

    @BeforeClass
    public void setupMockito() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(bookController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

    @BeforeMethod
    public void initEntities() {
        effectiveJava = new BookDTO();
        effectiveJava.setId(1L);
        effectiveJava.setTitle("Effective Java (2nd Edition)");
        effectiveJava.setAuthor("Joshua Bloch");
        effectiveJava.setIsbn("978-0321356680");

        cleanCode = new BookDTO();
        cleanCode.setId(2L);
        cleanCode.setTitle("Clean Code: A Handbook of Agile Software Craftsmanship");
        cleanCode.setAuthor("Robert C. Martin");
        cleanCode.setIsbn("978-0132350884");

        invalidBook = new BookDTO();
        invalidBook.setId(42L);
    }

    @BeforeMethod(dependsOnMethods = "initEntities")
    public void initMocksBehaviour() {
        // findById
        when(bookFacade.findById(effectiveJava.getId())).thenReturn(effectiveJava);
        when(bookFacade.findById(cleanCode.getId())).thenReturn(cleanCode);

        // findAll
        when(bookFacade.findAll()).thenReturn(Arrays.asList(effectiveJava, cleanCode));

        // delete
        doThrow(new NoEntityFoundException("Entity does not exist."))
                .when(bookFacade).delete(invalidBook.getId());
    }

    @Test
    public void getAllBooksTest() throws Exception {
        mockMvc.perform(get(ApiUris.ROOT_URI_BOOKS))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[?(@.id==1)].title").value(effectiveJava.getTitle()))
                .andExpect(jsonPath("$.[?(@.id==1)].author").value(effectiveJava.getAuthor()))
                .andExpect(jsonPath("$.[?(@.id==1)].isbn").value(effectiveJava.getIsbn()))
                .andExpect(jsonPath("$.[?(@.id==2)].title").value(cleanCode.getTitle()))
                .andExpect(jsonPath("$.[?(@.id==2)].author").value(cleanCode.getAuthor()))
                .andExpect(jsonPath("$.[?(@.id==2)].isbn").value(cleanCode.getIsbn()));
    }

    @Test
    public void getBookByIdTest() throws Exception {
        mockMvc.perform(get(ApiUris.ROOT_URI_BOOKS + "/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.title").value(effectiveJava.getTitle()))
                .andExpect(jsonPath("$.author").value(effectiveJava.getAuthor()))
                .andExpect(jsonPath("$.isbn").value(effectiveJava.getIsbn()));

        mockMvc.perform(get(ApiUris.ROOT_URI_BOOKS + "/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.title").value(cleanCode.getTitle()))
                .andExpect(jsonPath("$.author").value(cleanCode.getAuthor()))
                .andExpect(jsonPath("$.isbn").value(cleanCode.getIsbn()));
    }

    @Test
    public void getInvalidBookTest() throws Exception {
        when(bookFacade.findById(invalidBook.getId()))
                .thenThrow(new NoEntityFoundException("Entity does not exist."));

        mockMvc.perform(get(ApiUris.ROOT_URI_BOOKS + "/42"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deleteBookTest() throws Exception {
        mockMvc.perform(delete(ApiUris.ROOT_URI_BOOKS + "/2"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteBookNonExistingTest() throws Exception {
        mockMvc.perform(delete(ApiUris.ROOT_URI_BOOKS + "/42"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createBookTest() throws Exception {
        BookNewDTO designPatterns = new BookNewDTO();
        designPatterns.setTitle("Design Patterns: Elements of Reusable Object-Oriented Software");
        designPatterns.setAuthor("Erich Gamma et al.");
        designPatterns.setIsbn("978-0201633610");

        String json = convertObjectToJsonBytes(designPatterns);

        mockMvc.perform(post(ApiUris.ROOT_URI_BOOKS)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void updateBookTest() throws Exception {
        cleanCode.setTitle("Clean Code");

        String json = convertObjectToJsonBytes(cleanCode);

        mockMvc.perform(put(ApiUris.ROOT_URI_BOOKS + "/2")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
                .andExpect(status().isOk());
    }

    /**
     * Just an utility method to convert Objects to bytes to check JSON format
     */
    private static String convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(object);
    }
}
