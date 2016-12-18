package cz.muni.fi.pa165.library.rest.controller;

import cz.muni.fi.pa165.library.dto.BookDTO;
import cz.muni.fi.pa165.library.dto.BookNewDTO;
import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.exception.NoEntityFoundException;
import cz.muni.fi.pa165.library.facade.BookFacade;
import cz.muni.fi.pa165.library.rest.ApiUris;
import cz.muni.fi.pa165.library.rest.exception.ResourceAlreadyExistingException;
import cz.muni.fi.pa165.library.rest.exception.ResourceNotDeletableException;
import cz.muni.fi.pa165.library.rest.exception.ResourceNotFoundException;
import cz.muni.fi.pa165.library.rest.exception.ResourceNotModifiedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * REST controller for the {@link Book} entity.
 *
 * @author Dávid Lupták
 * @version 9.12.2016
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_BOOKS)
public class BookController {

    final static Logger LOGGER = LoggerFactory.getLogger(BookController.class);

    @Inject
    private BookFacade bookFacade;

    /**
     * Get all books or one specified by ISBN.
     * <p>
     * curl -i -X GET http://localhost:8080/pa165/rest/books
     * curl -i -X GET http://localhost:8080/pa165/rest/books?isbn={value}
     *
     * @param isbn optional parameter for the query
     * @return list of all books available in the system
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<BookDTO> getBooks(@RequestParam(value = "isbn", required = false) String isbn) {
        if (isbn == null) {
            LOGGER.debug("getBooks()");
            return bookFacade.findAll();
        } else {
            try {
                List<BookDTO> bookDTOList = new ArrayList<>();
                bookDTOList.add(bookFacade.findByIsbn(isbn));
                LOGGER.debug("getBook()?isbn found {}", bookDTOList.get(0));
                return bookDTOList;
            } catch (NoEntityFoundException | IllegalArgumentException ex) {
                LOGGER.debug("getBook()?isbn", ex);
                throw new ResourceNotFoundException(ex);
            }
        }
    }

    /**
     * Get the book with the respective id.
     * <p>
     * curl -i -X GET http://localhost:8080/pa165/rest/books/{id}
     *
     * @param id identifier of the book
     * @return DTO object of the book
     * @throws ResourceNotFoundException if the book is not available in the system
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final BookDTO getBook(@PathVariable("id") long id) {
        LOGGER.debug("getBook({})", id);

        try {
            return bookFacade.findById(id);
        } catch (NoEntityFoundException | IllegalArgumentException ex) {
            LOGGER.error("getBook()", ex);
            throw new ResourceNotFoundException(ex);
        }

    }

    /**
     * Delete the book with the respective id.
     * <p>
     * curl -i -X DELETE http://localhost:8080/pa165/rest/books/{id}
     *
     * @param id identifier of the book
     * @throws ResourceNotFoundException     if the book is not available in the system
     * @throws ResourceNotDeletableException if the book cannot be deleted
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteBook(@PathVariable("id") long id) {
        LOGGER.debug("deleteBook({})", id);

        try {
            bookFacade.delete(id);
        } catch (NoEntityFoundException | IllegalArgumentException ex) {
            LOGGER.error("deleteBook()", ex);
            throw new ResourceNotFoundException(ex);
        } catch (DataAccessException ex) {
            LOGGER.error("deleteBook() constraint violation", ex);
            throw new ResourceNotDeletableException(ex);
        }

    }

    /**
     * Create a new book by POST method.
     * <p>
     * curl -X POST -i -H "Content-Type: application/json" --data
     * '{"title":"Title","author":"Author","isbn":"978-0-321-35668-0"}'
     * http://localhost:8080/pa165/rest/books
     *
     * @param book BookNewDTO with the required fields for creation
     * @return the newly created book BookDTO
     * @throws ResourceAlreadyExistingException if the book already exists
     */
    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final BookDTO createBook(@RequestBody BookNewDTO book) {
        LOGGER.debug("createBook({})", book);

        try {
            Long id = bookFacade.create(book);
            return bookFacade.findById(id);
        } catch (Exception ex) {
            LOGGER.error("createBook()", ex);
            throw new ResourceAlreadyExistingException(ex);
        }

    }

    /**
     * Update the book by PUT method.
     * <p>
     * curl -X PUT -i -H "Content-Type: application/json" --data
     * '{"author":"New Author"}' http://localhost:8080/pa165/rest/books/{id}
     *
     * @param id   identifier of the book
     * @param book BookDTO with the required fields to be updated
     * @return the updated book BookDTO
     * @throws ResourceNotFoundException    if the book is not available in the system
     * @throws ResourceNotModifiedException if the book cannot be modified
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final BookDTO updateBook(@PathVariable("id") long id,
                                    @RequestBody BookDTO book) {
        LOGGER.debug("updateBook({})", id);

        BookDTO existing;

        try {
            existing = bookFacade.findById(id);
        } catch (NoEntityFoundException | IllegalArgumentException ex) {
            LOGGER.error("updateBook()", ex);
            throw new ResourceNotFoundException(ex);
        }

        BookDTO toBeUpdated;

        try {
            toBeUpdated = merge(existing, book);
            bookFacade.update(toBeUpdated);
            return toBeUpdated;
        } catch (Exception ex) {
            LOGGER.error("updateBook()", ex);
            throw new ResourceNotModifiedException(ex);
        }
    }

    /**
     * Merge book entity already existing in the system
     * with the desired one.
     *
     * @param existing book in the system
     * @param updating book on the input
     * @return merged book BookDTO
     */
    private BookDTO merge(BookDTO existing, BookDTO updating) {
        BookDTO merged = new BookDTO();

        merged.setId(existing.getId());
        merged.setTitle(updating.getTitle() == null ? existing.getTitle() : updating.getTitle());
        merged.setAuthor(updating.getAuthor() == null ? existing.getAuthor() : updating.getAuthor());
        merged.setIsbn(updating.getIsbn() == null ? existing.getIsbn() : updating.getIsbn());

        List<String> categories;
        if (updating.getCategoryNames().size() == 0) {
            categories = existing.getCategoryNames();
        } else {
            categories = updating.getCategoryNames();
        }
        categories.forEach(merged::addCategory);

        return merged;

    }

}
