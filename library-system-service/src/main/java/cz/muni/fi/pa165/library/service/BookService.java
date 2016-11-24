package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.entity.Book;

import java.util.List;

/**
 * @author Martin
 * @version 24.11.2016.
 */
public interface BookService {
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
     */
    void update(Book book);

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
     * Finds books that are written by the given author in the database
     *
     * @param author searched author
     * @return found books
     * @throws IllegalArgumentException if author is null or empty
     */
    List<Book> findByAuthor(String author);

    /**
     * Finds books with the given name in the database
     *
     * @param name searched name
     * @return found books
     * @throws IllegalArgumentException if name is null or empty
     */
    List<Book> findByName(String name);

    /**
     * Finds all books in the database
     *
     * @return list of all books
     */
    List<Book> findAll();
}