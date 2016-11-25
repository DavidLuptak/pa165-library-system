package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.exceptions.LibraryDAOException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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
        try {
            em.persist(book);
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public Book update(Book book) {
        try {
            return em.merge(book);
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public void delete(Book book) {
        try {
            em.remove(findById(book.getId()));
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public Book findById(Long id) {
        try {
            return em.find(Book.class, id);
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public List<Book> findByAuthor(String author) {
        try {
            if (author == null || author.isEmpty()) {
                throw new IllegalArgumentException("Author is not valid");
            }
            return em.createQuery("SELECT b FROM Book b where b.author = :author", Book.class)
                    .setParameter("author", author).getResultList();
        } catch (IllegalArgumentException e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }

    }

    @Override
    public List<Book> findByName(String name) {
        try {
            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException("Name is not valid");
            }
            return em.createQuery("SELECT b FROM Book b where b.name = :name", Book.class)
                    .setParameter("name", name).getResultList();
        } catch (IllegalArgumentException e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }

    }

    @Override
    public List<Book> findAll() {
        try {
            return em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }


}
