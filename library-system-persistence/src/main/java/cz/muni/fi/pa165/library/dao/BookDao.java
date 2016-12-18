package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.entity.Book;

import java.util.List;

/**
 * DAO contract for the Book entity.
 *
 * @author Lenka (433591)
 * @version 24.10.2016
 */
public interface BookDao {

    /**
     * Saves the given book into the database
     *
     * @param book to be saved
     */
    void create(Book book);

    /**
     * Updates the given book in the database
     *
     * @param book to be updated
     * @return merged Book entity
     */
    Book update(Book book);

    /**
     * Deletes the given book from the database
     *
     * @param book to be deleted
     */
    void delete(Book book);

    /**
     * Finds a book with the given id in the database
     *
     * @param id of searched book
     * @return found book
     */
    Book findById(Long id);

    /**
     * Finds a book with the given isbn in the database
     *
     * @param isbn of searched book
     * @return found book
     * @throws IllegalArgumentException if isbn is null or empty
     */
    Book findByIsbn(String isbn);

    /**
     * Finds books that are written by the given author in the database
     *
     * @param author searched author
     * @return found books
     * @throws IllegalArgumentException if author is null or empty
     */
    List<Book> findByAuthor(String author);

    /**
     * Finds books with the given title in the database
     *
     * @param title searched title
     * @return found books
     * @throws IllegalArgumentException if title is null or empty
     */
    List<Book> findByTitle(String title);

    /**
     * Finds all books in the database
     *
     * @return list of all books
     */
    List<Book> findAll();
}
