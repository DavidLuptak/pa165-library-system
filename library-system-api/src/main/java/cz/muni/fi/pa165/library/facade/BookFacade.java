package cz.muni.fi.pa165.library.facade;

import cz.muni.fi.pa165.library.dto.BookDTO;
import cz.muni.fi.pa165.library.dto.BookNewDTO;
import cz.muni.fi.pa165.library.enums.BookState;
import java.util.List;

/**
 * Facade layer for Book entity
 * 
 * @author Bedrich Said
 */
public interface BookFacade {
    /**
     * Persists book into database
     *
     * @param book book to be persisted
     * @return new book id
     */
    Long createBook(BookNewDTO book);

    /**
     * Returns book with given id or null
     *
     * @param id book id
     * @return book or null
     */
    BookDTO findById(Long id);

    /**
     * Finds books by name
     *
     * @param name book name
     * @return list of discovered books
     */
    List<BookDTO> findByName(String name);

    /**
     * Get all persisted books
     *
     * @return list of all books
     */
    List<BookDTO> findAll();

    /**
     * Removes book from the database
     *
     * @param bookId book id
     */
    void delete(Long bookId);

    /**
     * Sets book current state. Can be set only to a worse (or the same) state
     * than it already was
     *
     * @param bookId book id
     * @param newState new state to be set
     */
    void setState(Long bookId, BookState newState);
}
