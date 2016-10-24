package cz.muni.fi.pa165.library.persistence.dao;

import java.util.List;

import cz.muni.fi.pa165.library.persistence.entity.Book;
import cz.muni.fi.pa165.library.persistence.entity.Category;

/**
 * @author Lenka (433591)
 * @version 24.10.2016
 */
public interface BookDao {

    /**
     * Saves the given book into the database
     * @param book to be saved
     */
    void createBook(Book book);

    /**
     * Updates the given book in the database
     * @param book to be updated
     */
    void updateBook(Book book);

    /**
     * Deletes the given book from the database
     * @param book to be deleted
     */
    void deleteBook(Book book);

    /**
     * Finds a book with the given id in the database
     * @param id of searched book
     * @return found book
     */
    Book findBookById(long id);

    /**
     * Finds books that belong to the given category in the database
     * @param category searched category
     * @return found books
     */
    List<Book> findBooksByCategory(Category category);

    /**
     * Finds books that are written by the given author in the database
     * @param author searched author
     * @return found books
     */
    List<Book> findBooksByAuthor(String author);

    /**
     * Finds books with the given name in the database
     * @param name searched name
     * @return found books
     */
    List<Book> findBooksByName(String name);

    /**
     * Finds all books in the database
     * @return list of all books
     */
    List<Book> findAllBooks();

}
