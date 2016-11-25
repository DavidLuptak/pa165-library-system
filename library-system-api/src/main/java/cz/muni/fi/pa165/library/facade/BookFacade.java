package cz.muni.fi.pa165.library.facade;

import cz.muni.fi.pa165.library.dto.BookDTO;
import cz.muni.fi.pa165.library.dto.BookNewDTO;

import java.util.List;

/**
 * Facade layer for the Book entity.
 *
 * @author Bedrich Said
 */
public interface BookFacade {
    /**
     * Persists book into database
     *
     * @param bookNewDTO book to be persisted
     * @return new book id
     */
    Long create(BookNewDTO bookNewDTO);

    /**
     * Updates the given book in the database
     *
     * @param bookDTO to be updated
     */
    void update(BookDTO bookDTO);

    /**
     * Removes book from the database
     *
     * @param bookId book id
     */
    void delete(Long bookId);

    /**
     * Finds books that are written by the given author in the database
     *
     * @param author searched author
     * @return found books
     * @throws IllegalArgumentException if author is null or empty
     */
    List<BookDTO> findByAuthor(String author);

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
}
