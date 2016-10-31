package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.Category;

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
    Book findById(long id);

    /**
     * Finds books that belong to the given category in the database
     *
     * @param category searched category
     * @return found books
     * @throws IllegalArgumentException if category is null
     */
    List<Book> findByCategory(Category category);

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
