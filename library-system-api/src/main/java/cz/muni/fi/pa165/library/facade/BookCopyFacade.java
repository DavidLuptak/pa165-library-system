package cz.muni.fi.pa165.library.facade;

import cz.muni.fi.pa165.library.dto.BookCopyDTO;
import cz.muni.fi.pa165.library.dto.BookCopyNewDTO;
import cz.muni.fi.pa165.library.dto.BookDTO;

import java.util.List;

/**
 * Created by Martin on 24.11.2016.
 */
public interface BookCopyFacade {
    /**
     * Saves the given bookCopy into the database
     *
     * @param bookCopy to be saved
     */
    Long create(BookCopyNewDTO bookCopy);

    /**
     * Updates the given bookCopy in the database
     *
     * @param bookCopy to be updated
     */
    void update(BookCopyDTO bookCopy);

    /**
     * Deletes the given bookCopy from the database
     *
     * @param bookCopy to be deleted
     */
    void delete(BookCopyDTO bookCopy);

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
     * @param book searched book
     * @return found copies
     * @throws IllegalArgumentException if book is null
     */
    List<BookCopyDTO> findByBook(BookDTO book);
}