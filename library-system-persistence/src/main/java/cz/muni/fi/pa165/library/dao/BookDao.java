package cz.muni.fi.pa165.library.dao;

import java.util.List;

import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.Category;
import org.springframework.dao.DataAccessException;

/**
 * @author Lenka (433591)
 * @version 24.10.2016
 */
public interface BookDao {

    /**
     * Saves the given book into the database
     * @param book to be saved
     */
    void create(Book book) throws DataAccessException;

    /**
     * Updates the given book in the database
     * @param book to be updated
     */
    void update(Book book) throws DataAccessException;

    /**
     * Deletes the given book from the database
     * @param book to be deleted
     */
    void delete(Book book) throws DataAccessException;

    /**
     * Finds a book with the given id in the database
     * @param id of searched book
     * @return found book
     */
    Book findById(long id);

    /**
     * Finds books that belong to the given category in the database
     * @param category searched category
     * @return found books
     */
    List<Book> findByCategory(Category category) throws DataAccessException;

    /**
     * Finds books that are written by the given author in the database
     * @param author searched author
     * @return found books
     */
    List<Book> findByAuthor(String author) throws DataAccessException;

    /**
     * Finds books with the given name in the database
     * @param name searched name
     * @return found books
     */
    List<Book> findByName(String name) throws DataAccessException;

    /**
     * Finds all books in the database
     * @return list of all books
     */
    List<Book> findAll();

}
