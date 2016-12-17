package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.dao.BookCopyDao;
import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.BookCopy;
import cz.muni.fi.pa165.library.exception.LibraryDAOException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Lenka (433591)
 * @version 19.11.2016
 */
@Service
public class BookCopyServiceImpl implements BookCopyService {

    @Inject
    private BookCopyDao bookCopyDao;

    @Override
    public void create(BookCopy bookCopy) {
        if (bookCopy == null) throw new IllegalArgumentException("bookCopy is null");
        try {
            bookCopyDao.create(bookCopy);
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public BookCopy update(BookCopy bookCopy) {
        if (bookCopy == null) throw new IllegalArgumentException("bookCopy is null");
        try {
            return bookCopyDao.update(bookCopy);
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public void delete(BookCopy bookCopy) {
        if (bookCopy == null) throw new IllegalArgumentException("bookCopy is null");
        try {
            bookCopyDao.delete(bookCopy);
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public BookCopy findById(Long id) {
        if (id == null) throw new IllegalArgumentException("id is null");
        try {
            return bookCopyDao.findById(id);
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public List<BookCopy> findByBook(Book book) {
        if (book == null) throw new IllegalArgumentException("book is null");
        try {
            return bookCopyDao.findByBook(book);
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public BookCopy findLoanableByBook(Book book) {
        if (book == null) throw new IllegalArgumentException("book is null");
        try {
            return bookCopyDao.findByBook(book).stream().filter(x -> x.isLoanable()).findFirst().get();
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }
}
