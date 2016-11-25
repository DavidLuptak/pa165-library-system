package cz.muni.fi.pa165.library.facade;

import cz.muni.fi.pa165.library.dto.BookCopyDTO;
import cz.muni.fi.pa165.library.dto.BookCopyNewDTO;
import cz.muni.fi.pa165.library.dto.BookDTO;
import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.BookCopy;
import cz.muni.fi.pa165.library.exception.NoEntityFoundException;
import cz.muni.fi.pa165.library.mapping.BeanMappingService;
import cz.muni.fi.pa165.library.service.BookCopyService;
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
public class BookCopyFacadeImpl implements BookCopyFacade {

    @Inject
    BookCopyService bookCopyService;

    @Inject
    BookService bookService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public Long create(BookCopyNewDTO bookCopyNewDTO) {
        BookCopy bookCopy = beanMappingService.mapTo(bookCopyNewDTO, BookCopy.class);
        bookCopyService.create(bookCopy);
        return bookCopy.getId();
    }

    @Override
    public void update(BookCopyDTO bookCopyDTO) {
        BookCopy bookCopy = beanMappingService.mapTo(bookCopyDTO, BookCopy.class);
        bookCopyService.update(bookCopy);
    }

    @Override
    public void delete(Long id) {
        bookCopyService.delete(bookCopyService.findById(id));
    }

    @Override
    public BookCopyDTO findById(Long id) {
        return beanMappingService.mapTo(bookCopyService.findById(id),BookCopyDTO.class);
    }

    @Override
    public List<BookCopyDTO> findByBook(Long bookId) {

        Book book = bookService.findById(bookId);
        if (book == null) {
            throw new NoEntityFoundException("User not found during findByUser.");
        }
        return beanMappingService.mapTo(bookCopyService.findByBook(book),BookCopyDTO.class);
    }
}
