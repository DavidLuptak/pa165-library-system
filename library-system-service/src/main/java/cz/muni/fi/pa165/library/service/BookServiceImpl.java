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
        if (book == null) throw new IllegalArgumentException("book is null");
        bookDao.create(book);
    }

    @Override
    public Book update(Book book) {
        if (book == null) throw new IllegalArgumentException("book is null");
        return bookDao.update(book);
    }

    @Override
    public void delete(Book book) {
        if (book == null) throw new IllegalArgumentException("book is null");
        bookDao.delete(book);
    }

    @Override
    public Book findById(Long id) {
        if (id == null) throw new IllegalArgumentException("id is null");
        return bookDao.findById(id);
    }

    @Override
    public List<Book> findByAuthor(String author) {
        if (author == null || author.isEmpty()) throw new IllegalArgumentException("author is null or empty");
        return bookDao.findByAuthor(author);
    }

    @Override
    public List<Book> findByName(String name) {

        if (name == null || name.isEmpty()) throw new IllegalArgumentException("name is null or empty");
        return bookDao.findByName(name);
    }

    @Override
    public List<Book> findAll() {
        return bookDao.findAll();
    }
}
