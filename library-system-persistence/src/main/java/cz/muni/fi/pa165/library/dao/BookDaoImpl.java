package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.entity.Book;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Implementation of the DAO contract for the Book entity.
 *
 * @author Lenka (433591)
 * @version 25.10.2016
 */
@Repository
public class BookDaoImpl implements BookDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Book book) {
        em.persist(book);
    }

    @Override
    public Book update(Book book) {
        return em.merge(book);
    }

    @Override
    public void delete(Book book) {
        if (findById(book.getId()) == null) throw new IllegalArgumentException("Trying to delete not existing book");
        book.setDeleted(true);
        em.merge(book);
    }

    @Override
    public Book findById(Long id) {
        return em.find(Book.class, id);
    }

    @Override
    public Book findByIsbn(String isbn) {
        if (isbn == null || isbn.isEmpty()) {
            throw new IllegalArgumentException("ISBN is not valid.");
        }
        try {
            return em.createQuery("SELECT b FROM Book b WHERE b.isbn = :isbn", Book.class)
                    .setParameter("isbn", isbn).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Book> findByAuthor(String author) {
        if (author == null || author.isEmpty()) {
            throw new IllegalArgumentException("Author is not valid");
        }
        return em.createQuery("SELECT b FROM Book b where b.author = :author", Book.class)
                .setParameter("author", author).getResultList();
    }

    @Override
    public List<Book> findByTitle(String title) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title is not valid");
        }
        return em.createQuery("SELECT b FROM Book b where b.title = :title", Book.class)
                .setParameter("title", title).getResultList();
    }

    @Override
    public List<Book> findAll() {
        return em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }
}
