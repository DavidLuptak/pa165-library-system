package cz.muni.fi.pa165.library.service;

import java.util.List;

import cz.muni.fi.pa165.library.dao.BookCopyDao;
import cz.muni.fi.pa165.library.dao.BookDao;
import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.BookCopy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Lenka (433591)
 * @version 19.11.2016
 */
@Service
public class BookCopyServiceImpl implements BookCopyService {

    @Autowired
    private BookCopyDao bookCopyDao;

    @Override
    public void create(BookCopy bookCopy) {
        bookCopyDao.create(bookCopy);
    }

    @Override
    public void update(BookCopy bookCopy) {
        bookCopyDao.update(bookCopy);
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
