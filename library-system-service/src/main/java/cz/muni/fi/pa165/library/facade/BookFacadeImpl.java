package cz.muni.fi.pa165.library.facade;

import cz.muni.fi.pa165.library.dto.BookDTO;
import cz.muni.fi.pa165.library.dto.BookNewDTO;
import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.mapping.BeanMappingService;
import cz.muni.fi.pa165.library.service.BookService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
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
    public Long createBook(BookNewDTO bookNewDTO) {
        Book book = beanMappingService.mapTo(bookNewDTO,Book.class);
        bookService.create(book);
        return book.getId();
    }

    @Override
    public void updateBook(BookDTO bookDTO) {
        Book book = beanMappingService.mapTo(bookDTO,Book.class);
        bookService.update(book);
    }

    @Override
    public List<BookDTO> findByAuthor(String author) {
        return beanMappingService.mapTo(bookService.findByAuthor(author),BookDTO.class);
    }

    @Override
    public BookDTO findById(Long id) {
        return beanMappingService.mapTo(bookService.findById(id),BookDTO.class);
    }

    @Override
    public List<BookDTO> findByName(String name) {
        return beanMappingService.mapTo(bookService.findByName(name),BookDTO.class);
    }

    @Override
    public List<BookDTO> findAll() {
        return beanMappingService.mapTo(bookService.findAll(),BookDTO.class);
    }

    @Override
    public void delete(Long bookId) {
        bookService.delete(bookService.findById(bookId));
    }
}
