package cz.muni.fi.pa165.library.sampledata;

import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * @author Dávid Lupták
 * @version 9.12.2016
 */
@Component
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {

    final static Logger LOGGER = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);

    @Inject
    private BookService bookService;

    @Override
    public void loadData() {
        createUsers();
        createCategories();
        createBooks();
        createBookCopies();
        createLoans();
    }

    private void createUsers() {
        // TODO
    }

    private void createCategories() {
        // TODO
    }

    private void createBooks() {
        book("Joshua Bloch", "Effective Java 2nd Edition", "978-0-321-35668-0");

        // TODO

        LOGGER.info("A book loaded.");
    }

    private void createBookCopies() {
        // TODO
    }

    private void createLoans() {
        // TODO
    }

    private Book book(String author, String title, String isbn) {
        Book book = new Book();
        book.setAuthor(author);
        book.setName(title);
        book.setIsbn(isbn);

        bookService.create(book);
        return book;
    }

}
