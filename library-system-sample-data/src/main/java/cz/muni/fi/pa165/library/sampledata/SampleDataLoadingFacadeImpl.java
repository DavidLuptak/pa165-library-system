package cz.muni.fi.pa165.library.sampledata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.Category;
import cz.muni.fi.pa165.library.service.BookService;
import cz.muni.fi.pa165.library.service.CategoryService;
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

    @Inject
    private CategoryService categoryService;

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
        Book book = book("Joshua Bloch", "Effective Java 2nd Edition", "978-0-321-35668-0");
        book("Jane Austen", "Pride and Prejudice", "978-0-345-35768-0");
        book("J. K. Rowling", "Harry Potter and the Chamber of Secrets", "958-1-321-36153-0");
        Category category = category("IT", Arrays.asList(book));

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
        book.setTitle(title);
        book.setIsbn(isbn);
        bookService.create(book);
        return book;
    }

    private Category category(String name, List<Book> books) {
        Category category = new Category(name);
        for (Book book : books) {
            category.addBook(book);
        }
        categoryService.create(category);
        return category;
    }

}
