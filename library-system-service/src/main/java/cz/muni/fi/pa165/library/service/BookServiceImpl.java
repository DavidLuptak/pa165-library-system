package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.dao.BookDao;
import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.exception.LibraryDAOException;
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
        try {
            bookDao.create(book);
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public Book update(Book book) {
        if (book == null) throw new IllegalArgumentException("book is null");
        try {
            return bookDao.update(book);
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public void delete(Book book) {
        if (book == null) throw new IllegalArgumentException("book is null");
        try {
            bookDao.delete(book);
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public Book findById(Long id) {
        if (id == null) throw new IllegalArgumentException("id is null");
        try {
            return bookDao.findById(id);
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public List<Book> findByAuthor(String author) {
        if (author == null || author.isEmpty()) throw new IllegalArgumentException("author is null or empty");
        try {
            return bookDao.findByAuthor(author);
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public List<Book> findByTitle(String title) {

        if (title == null || title.isEmpty()) throw new IllegalArgumentException("title is null or empty");
        try {
            return bookDao.findByTitle(title);
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public List<Book> findAll() {

        try {
            return bookDao.findAll();
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }
}
