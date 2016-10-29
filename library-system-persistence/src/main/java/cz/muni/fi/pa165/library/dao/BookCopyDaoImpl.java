package cz.muni.fi.pa165.library.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.BookCopy;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Repository;

/**
 * @author Lenka (433591)
 * @version 26.10.2016
 */
@Repository
public class BookCopyDaoImpl implements BookCopyDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(BookCopy bookCopy) {
        validateBookCopy(bookCopy);
        Validate.isTrue(bookCopy.getId() == null, "bookCopy's id should be null");
        em.persist(bookCopy);
    }

    @Override
    public void update(BookCopy bookCopy) {
        validateBookCopy(bookCopy);
        Validate.notNull(bookCopy.getId());
        em.merge(bookCopy);
    }

    @Override
    public void delete(BookCopy bookCopy) {
        Validate.notNull(bookCopy.getId());
        em.remove(bookCopy);
    }

    @Override
    public BookCopy findById(long id) {
        return em.find(BookCopy.class, id);
    }

    @Override
    public List<BookCopy> findByBook(Book book) {
        Validate.notNull(book);
        return em.createQuery("SELECT b FROM BookCopy b where b.book = :book", BookCopy.class)
            .setParameter("book", book).getResultList();

    }

    private void validateBookCopy(BookCopy bookCopy) {
        Validate.notNull(bookCopy);
        Validate.notNull(bookCopy.getBook());
        Validate.notNull(bookCopy.getBookState());
    }
}
