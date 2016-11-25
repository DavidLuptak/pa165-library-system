package cz.muni.fi.pa165.library.facade;

import cz.muni.fi.pa165.library.dto.BookCopyDTO;
import cz.muni.fi.pa165.library.dto.BookCopyNewDTO;

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
     */
    Long create(BookCopyNewDTO bookCopyNewDTO);

    /**
     * Updates the given bookCopy in the database
     *
     * @param bookCopyDTO to be updated
     */
    void update(BookCopyDTO bookCopyDTO);

    /**
     * Deletes the given bookCopy from the database
     *
     * @param id of bookCopy to be deleted
     */
    void delete(Long id);

    /**
     * Finds a bookCopy with the given id in the database
     *
     * @param id of searched bookCopy
     * @return found bookCopy
     */
    BookCopyDTO findById(Long id);

    /**
     * Finds all copies of the given book in the database
     *
     * @param bookId of searched book
     * @return found copies
     * @throws IllegalArgumentException if book is null
     */
    List<BookCopyDTO> findByBook(Long bookId);
}
