package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.Category;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
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
    public void update(Book book) {
        em.merge(book);
    }

    @Override
    public void delete(Book book) {
        em.remove(book);
    }

    @Override
    public Book findById(long id) {
        return em.find(Book.class, id);
    }

    @Override
    public List<Book> findByCategory(Category category) {
        return em.createQuery("SELECT b FROM Book b where b.category = :category", Book.class)
            .setParameter("category", category).getResultList();

    }

    @Override
    public List<Book> findByAuthor(String author) {
        return em.createQuery("SELECT b FROM Book b where b.author = :author", Book.class)
            .setParameter("author", author).getResultList();

    }

    @Override
    public List<Book> findByName(String name) {
        return em.createQuery("SELECT b FROM Book b where b.name = :name", Book.class)
            .setParameter("name", name).getResultList();

    }

    @Override
    public List<Book> findAll() {
        return em.createQuery("SELECT b FROM Book b", Book.class).getResultList();

    }


}
