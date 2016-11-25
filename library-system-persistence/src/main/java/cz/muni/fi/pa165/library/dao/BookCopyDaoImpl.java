package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.BookCopy;
import cz.muni.fi.pa165.library.exceptions.LibraryDAOException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Implementation of the DAO contract for the BookCopy entity.
 *
 * @author Lenka (433591)
 * @version 26.10.2016
 */
@Repository
public class BookCopyDaoImpl implements BookCopyDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(BookCopy bookCopy) {

        try {
            em.persist(bookCopy);
        }
        catch (Exception e){
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public BookCopy update(BookCopy bookCopy) {
        try {
            return em.merge(bookCopy);
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public void delete(BookCopy bookCopy) {
        try {
            em.remove(findById(bookCopy.getId()));
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public BookCopy findById(Long id) {
        try {
            return em.find(BookCopy.class, id);
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public List<BookCopy> findByBook(Book book) {
        try {
            if (book == null) {
                throw new IllegalArgumentException("Book is not valid");
            }
            return em.createQuery("SELECT b FROM BookCopy b where b.book = :book", BookCopy.class)
                    .setParameter("book", book).getResultList();
        } catch (IllegalArgumentException e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }

    }
}
