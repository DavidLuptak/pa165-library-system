package cz.muni.fi.pa165.library.facade;

import cz.muni.fi.pa165.library.dto.BookCopyDTO;
import cz.muni.fi.pa165.library.dto.BookCopyNewDTO;
import cz.muni.fi.pa165.library.exception.NoEntityFoundException;

import java.util.List;

/**
 * Facade layer for the BookCopy entity.
 *
 * @author Martin
 * @version 24.11.2016.
 */
public interface BookCopyFacade {
    /**
     * Saves the given bookCopy into the database
     *
     * @param bookCopyNewDTO to be saved
     * @return id of stored object
     * @throws IllegalArgumentException if bookCopyNewDTO is null
     */
    Long create(BookCopyNewDTO bookCopyNewDTO);

    /**
     * Updates the given bookCopy in the database
     *
     * @param bookCopyDTO to be updated
     * @throws IllegalArgumentException if bookCopyDTO is null
     */
    void update(BookCopyDTO bookCopyDTO);

    /**
     * Deletes the given bookCopy from the database
     *
     * @param id of bookCopy to be deleted
     * @throws IllegalArgumentException if id is null
     * @throws NoEntityFoundException   if bookCopy cant be retrieved from database
     */
    void delete(Long id);

    /**
     * Finds a bookCopy with the given id in the database
     *
     * @param id of searched bookCopy
     * @return found bookCopy
     * @throws IllegalArgumentException if id is null
     * @throws NoEntityFoundException   if bookCopy cant be retrieved from database
     */
    BookCopyDTO findById(Long id);

    /**
     * Finds all copies of the given book in the database
     *
     * @param bookId of searched book
     * @return found copies
     * @throws IllegalArgumentException if book is null
     * @throws NoEntityFoundException   if book cant be retrieved from database
     */
    List<BookCopyDTO> findByBook(Long bookId);

    /**
     * Finds laonable bookCopy for given bookId
     *
     * @param bookId of searched book
     * @return found bookCopy
     * @throws IllegalArgumentException if book is null
     * @throws NoEntityFoundException   if book cant be retrieved from database
     * @throws NoEntityFoundException   if no bookCopy cant be retrieved from database
     */
    BookCopyDTO findLoanableByBook(Long bookId);

}
