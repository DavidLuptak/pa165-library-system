package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.BookCopy;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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
        em.persist(bookCopy);
    }

    @Override
    public void update(BookCopy bookCopy) {
        em.merge(bookCopy);
    }

    @Override
    public void delete(BookCopy bookCopy) {
        em.remove(bookCopy);
    }

    @Override
    public BookCopy findById(Long id) {
        return em.find(BookCopy.class, id);
    }

    @Override
    public List<BookCopy> findByBook(Book book) {
        return em.createQuery("SELECT b FROM BookCopy b where b.book = :book", BookCopy.class)
            .setParameter("book", book).getResultList();

    }
}
