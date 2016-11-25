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
        bookCopyDao.create(bookCopy);
    }

    @Override
    public BookCopy update(BookCopy bookCopy) {
        return bookCopyDao.update(bookCopy);
    }

    @Override
    public void delete(BookCopy bookCopy) {
        bookCopyDao.delete(bookCopy);
    }

    @Override
    public BookCopy findById(Long id) {
        return bookCopyDao.findById(id);
    }

    @Override
    public List<BookCopy> findByBook(Book book) {
        return bookCopyDao.findByBook(book);
    }
}
