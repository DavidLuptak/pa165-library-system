package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.dao.BookCopyDao;
import cz.muni.fi.pa165.library.dao.BookDao;
import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.BookCopy;
import cz.muni.fi.pa165.library.exception.LibraryDAOException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Martin
 * @version 24.11.2016.
 */
@Service
public class BookServiceImpl implements BookService {
    @Inject
    private BookDao bookDao;

    @Inject
    private BookCopyDao bookCopyDao;

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
    public Book findByIsbn(String isbn) {
        if (isbn == null || isbn.isEmpty()) {
            throw new IllegalArgumentException("isbn is null or empty");
        }

        return bookDao.findByIsbn(isbn);
    }

    @Override
    public List<Book> findAll() {

        try {
            return bookDao.findAll();
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public List<Book> findLoanableBooks() {
        try{
            return bookDao.findAll().stream().filter(this::isLoanable).collect(Collectors.toList());
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public boolean isLoanable(Book book) {
        if(book == null) throw new IllegalArgumentException("book is null");
        try{
            return bookCopyDao.findByBook(book).
                    stream().
                    filter(BookCopy::isLoanable).
                    findFirst().
                    isPresent();
        }catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }
}
