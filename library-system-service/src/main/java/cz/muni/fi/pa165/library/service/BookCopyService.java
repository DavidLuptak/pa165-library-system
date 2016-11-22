package cz.muni.fi.pa165.library.service;

import java.util.List;

import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.BookCopy;

/**
 * @author Lenka (433591)
 * @version 19.11.2016
 */
public interface BookCopyService {

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
     */
    void update(BookCopy bookCopy);

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