package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.BookCopy;

import java.util.List;

/**
 * DAO contract for the BookCopy entity.
 *
 * @author Lenka (433591)
 * @version 26.10.2016
 */
public interface BookCopyDao {

    /**
     * Saves the given bookCopy into the database
     *
     * @param bookCopy to be saved
     */
    void create(BookCopy bookCopy);

    /**
     * Updates the given bookCopy in the database
     *
     * @param bookCopy to be updated
     * @return merged BookCopy entity
     */
    BookCopy update(BookCopy bookCopy);

    /**
     * Deletes the given bookCopy from the database
     *
     * @param bookCopy to be deleted
     */
    void delete(BookCopy bookCopy);

    /**
     * Finds a bookCopy with the given id in the database
     *
     * @param id of searched bookCopy
     * @return found bookCopy
     */
    BookCopy findById(Long id);

    /**
     * Finds all copies of the given book in the database
     *
     * @param book searched book
     * @return found copies
     * @throws IllegalArgumentException if book is null
     */
    List<BookCopy> findByBook(Book book);
}
