package cz.muni.fi.pa165.library.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.Category;

import org.apache.commons.lang.Validate;

/**
 * @author Lenka (433591)
 * @version 25.10.2016
 */
//@Repository
public class BookDaoImpl implements BookDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void createBook(Book book) {
        validateBook(book);
        Validate.isTrue(book.getId() == null, "book's id should be null");
        em.persist(book);
    }

    @Override
    public void updateBook(Book book) {
        validateBook(book);
        Validate.notNull(book.getId());
        em.merge(book);
    }

    @Override
    public void deleteBook(Book book) {
        Validate.notNull(book.getId());
        em.remove(book);
    }

    @Override
    public Book findBookById(long id) {
        return em.find(Book.class, id);
    }

    @Override
    public List<Book> findBooksByCategory(Category category) {
        Validate.notNull(category);
        return em.createQuery("SELECT b FROM Book b where b.category = :category", Book.class)
            .setParameter("category", category).getResultList();

    }

    @Override
    public List<Book> findBooksByAuthor(String author) {
        Validate.notNull(author);
        return em.createQuery("SELECT b FROM Book b where b.author = :author", Book.class)
            .setParameter("author", author).getResultList();

    }

    @Override
    public List<Book> findBooksByName(String name) {
        Validate.notNull(name);
        return em.createQuery("SELECT b FROM Book b where b.name = :name", Book.class)
            .setParameter("name", name).getResultList();

    }

    @Override
    public List<Book> findAllBooks() {
        return em.createQuery("SELECT b FROM Book b", Book.class).getResultList();

    }

    private void validateBook(Book book) {
        Validate.notNull(book);
        Validate.notNull(book.getName());
        Validate.notEmpty(book.getName());
        Validate.notNull(book.getAuthor());
        Validate.notEmpty(book.getAuthor());
        Validate.notNull(book.getIsbn());
        Validate.notEmpty(book.getIsbn());
    }

}
