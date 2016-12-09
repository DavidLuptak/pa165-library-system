package cz.muni.fi.pa165.library.facade;

import cz.muni.fi.pa165.library.dto.BookDTO;
import cz.muni.fi.pa165.library.dto.BookNewDTO;
import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.exception.NoEntityFoundException;
import cz.muni.fi.pa165.library.mapping.BeanMappingService;
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
public class BookFacadeImpl implements BookFacade {

    @Inject
    private BookService bookService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public Long create(BookNewDTO bookNewDTO) {
        if (bookNewDTO == null) {
            throw new IllegalArgumentException("Book cannot be null.");
        }
        Book book = beanMappingService.mapTo(bookNewDTO, Book.class);
        bookService.create(book);
        return book.getId();
    }

    @Override
    public void update(BookDTO bookDTO) {
        if (bookDTO == null) {
            throw new IllegalArgumentException("Book cannot be null.");
        }
        Book book = beanMappingService.mapTo(bookDTO, Book.class);
        if (bookService.findById(book.getId()) == null) {
            throw new NoEntityFoundException("Book not found during update.");
        }
        bookService.update(book);
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Book id cannot be null.");
        }
        Book book = bookService.findById(id);
        if (book == null) {
            throw new NoEntityFoundException("Book not found during delete.");
        }
        bookService.delete(book);
    }

    @Override
    public BookDTO findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Book id cannot be null.");
        }
        Book book = bookService.findById(id);
        if (book == null) {
            throw new NoEntityFoundException("Book not found during findById.");
        }

        return beanMappingService.mapTo(book, BookDTO.class);
    }

    @Override
    public List<BookDTO> findByAuthor(String author) {
        if (author == null || author.isEmpty()) {
            throw new IllegalArgumentException("Author name cannot be null.");
        }
        List<Book> books = bookService.findByAuthor(author);
        if (books.size() == 0) {
            throw new NoEntityFoundException("Books not found during findByAuthor.");
        }

        return beanMappingService.mapTo(books, BookDTO.class);
    }

    @Override
    public List<BookDTO> findByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null.");
        }
        List<Book> books = bookService.findByName(name);
        if (books.size() == 0) {
            throw new NoEntityFoundException("Books not found during findByName.");
        }

        return beanMappingService.mapTo(books, BookDTO.class);
    }

    @Override
    public List<BookDTO> findAll() {
        return beanMappingService.mapTo(bookService.findAll(), BookDTO.class);
    }
}
