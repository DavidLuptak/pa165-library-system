package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.dao.BookDao;
import cz.muni.fi.pa165.library.entity.Book;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Martin
 * @version 24.11.2016.
 */
@Service
public class BookServiceImpl implements BookService {
    @Inject
    private BookDao bookDao;

    @Override
    public void create(Book book) {
        bookDao.create(book);
    }

    @Override
    public void update(Book book) {
        bookDao.update(book);
    }

    @Override
    public void delete(Book book) {
        bookDao.delete(book);
    }

    @Override
    public Book findById(Long id) {
        return bookDao.findById(id);
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return bookDao.findByAuthor(author);
    }

    @Override
    public List<Book> findByName(String name) {
        return bookDao.findByName(name);
    }

    @Override
    public List<Book> findAll() {
        return bookDao.findAll();
    }
}
