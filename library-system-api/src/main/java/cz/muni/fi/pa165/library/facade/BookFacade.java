package cz.muni.fi.pa165.library.facade;

import cz.muni.fi.pa165.library.dto.BookDTO;
import cz.muni.fi.pa165.library.dto.BookNewDTO;
import cz.muni.fi.pa165.library.exception.NoEntityFoundException;

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
     * @throws IllegalArgumentException if bookNewDTO is null
     */
    Long create(BookNewDTO bookNewDTO);

    /**
     * Updates the given book in the database
     *
     * @param bookDTO to be updated
     * @throws IllegalArgumentException if bookDTO is null
     * @throws NoEntityFoundException   if book cant be retrieved from database
     */
    void update(BookDTO bookDTO);

    /**
     * Removes book from the database
     *
     * @param bookId book id
     * @throws IllegalArgumentException if bookId is null
     * @throws NoEntityFoundException   if book cant be retrieved from database
     */
    void delete(Long bookId);

    /**
     * Finds books that are written by the given author in the database
     *
     * @param author searched author
     * @return found books
     * @throws IllegalArgumentException if author is null or empty
     * @throws NoEntityFoundException   if no books found for author
     */
    List<BookDTO> findByAuthor(String author);

    /**
     * Returns book with given id or null
     *
     * @param id book id
     * @return book or null
     * @throws IllegalArgumentException if id is null
     * @throws NoEntityFoundException   if book cant be retrieved from database
     */
    BookDTO findById(Long id);

    /**
     * Finds books by title
     *
     * @param title book title
     * @return list of discovered books
     * @throws IllegalArgumentException if title is null or empty
     * @throws NoEntityFoundException   if no books found for title
     */
    List<BookDTO> findByTitle(String title);

    /**
     * Get all persisted books
     *
     * @return list of all books
     */
    List<BookDTO> findAll();
}
