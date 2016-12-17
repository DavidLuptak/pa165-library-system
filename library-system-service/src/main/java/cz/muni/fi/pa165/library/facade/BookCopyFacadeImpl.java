package cz.muni.fi.pa165.library.facade;

import cz.muni.fi.pa165.library.dto.BookCopyDTO;
import cz.muni.fi.pa165.library.dto.BookCopyNewDTO;
import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.BookCopy;
import cz.muni.fi.pa165.library.exception.NoEntityFoundException;
import cz.muni.fi.pa165.library.mapping.BeanMappingService;
import cz.muni.fi.pa165.library.service.BookCopyService;
import cz.muni.fi.pa165.library.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Martin
 * @version 24.11.2016.
 */
@Service
@Transactional
public class BookCopyFacadeImpl implements BookCopyFacade {

    @Inject
    BookCopyService bookCopyService;

    @Inject
    BookService bookService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public Long create(BookCopyNewDTO bookCopyNewDTO) {
        if (bookCopyNewDTO == null) {
            throw new IllegalArgumentException("Book cannot be null.");
        }
        BookCopy bookCopy = beanMappingService.mapTo(bookCopyNewDTO, BookCopy.class);
        bookCopyService.create(bookCopy);
        return bookCopy.getId();
    }

    @Override
    public void update(BookCopyDTO bookCopyDTO) {
        if (bookCopyDTO == null) {
            throw new IllegalArgumentException("Book copy cannot be null.");
        }
        BookCopy bookCopy = beanMappingService.mapTo(bookCopyDTO, BookCopy.class);
        if (bookCopyService.findById(bookCopy.getId()) == null) {
            throw new NoEntityFoundException("Book copy not found during update.");
        }
        bookCopyService.update(bookCopy);
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Book copy id cannot be null.");
        }
        BookCopy bookCopy = bookCopyService.findById(id);
        if (bookCopy == null) {
            throw new NoEntityFoundException("Book copy not found during delete.");
        }
        bookCopyService.delete(bookCopy);
    }

    @Override
    public BookCopyDTO findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("User id cannot be null.");
        }
        BookCopy bookCopy = bookCopyService.findById(id);
        if (bookCopy == null) {
            throw new NoEntityFoundException("Book copy not found during findById.");
        }

        return beanMappingService.mapTo(bookCopy, BookCopyDTO.class);
    }

    @Override
    public List<BookCopyDTO> findByBook(Long bookId) {
        if (bookId == null) {
            throw new IllegalArgumentException("Book id cannot be null.");
        }
        Book book = bookService.findById(bookId);
        if (book == null) {
            throw new NoEntityFoundException("Book not found during findByBook.");
        }

        return beanMappingService.mapTo(bookCopyService.findByBook(book), BookCopyDTO.class);
    }

    @Override
    public BookCopyDTO findLoanableByBook(Long bookId) {
        if (bookId == null) {
            throw new IllegalArgumentException("Book id cannot be null.");
        }
        Book book = bookService.findById(bookId);
        if (book == null) {
            throw new NoEntityFoundException("Book not found during findByBook.");
        }
        BookCopy bookCopy = bookCopyService.findLoanableByBook(book);
        if (bookCopy == null) {
            throw new NoEntityFoundException("Loanable bookCopy not found during findLoanableByBook.");
        }
        return beanMappingService.mapTo(bookCopy, BookCopyDTO.class);
    }
}
