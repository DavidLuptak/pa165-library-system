package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.dao.BookCopyDao;
import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.BookCopy;
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
        bookCopyDao.create(bookCopy);
    }

    @Override
    public BookCopy update(BookCopy bookCopy) {
        if (bookCopy == null) throw new IllegalArgumentException("bookCopy is null");
        return bookCopyDao.update(bookCopy);
    }

    @Override
    public void delete(BookCopy bookCopy) {
        if (bookCopy == null) throw new IllegalArgumentException("bookCopy is null");
        bookCopyDao.delete(bookCopy);
    }

    @Override
    public BookCopy findById(Long id) {
        if (id == null) throw new IllegalArgumentException("id is null");
        return bookCopyDao.findById(id);
    }

    @Override
    public List<BookCopy> findByBook(Book book) {
        if (book == null) throw new IllegalArgumentException("book is null");
        return bookCopyDao.findByBook(book);
    }
}
