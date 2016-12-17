package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.BookCopy;
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
        em.persist(bookCopy);
    }

    @Override
    public BookCopy update(BookCopy bookCopy) {
        return em.merge(bookCopy);
    }

    @Override
    public void delete(BookCopy bookCopy) {
        em.remove(findById(bookCopy.getId()));
    }

    @Override
    public BookCopy findById(Long id) {
        return em.find(BookCopy.class, id);
    }

    @Override
    public List<BookCopy> findByBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book is not valid");
        }
        return em.createQuery("SELECT b FROM BookCopy b where b.book = :book ORDER BY bookState", BookCopy.class)
                .setParameter("book", book).getResultList();
    }
}
