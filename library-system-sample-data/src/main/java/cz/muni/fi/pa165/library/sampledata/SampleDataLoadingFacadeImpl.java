package cz.muni.fi.pa165.library.sampledata;

import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.Category;
import cz.muni.fi.pa165.library.entity.User;
import cz.muni.fi.pa165.library.enums.UserRole;
import cz.muni.fi.pa165.library.service.BookService;
import cz.muni.fi.pa165.library.service.CategoryService;
import cz.muni.fi.pa165.library.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

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
    
    @Inject
    private UserService userService;

    @Override
    public void loadData() {
        createUsers();
        createCategories();
        createBooks();
        createBookCopies();
        createLoans();
    }

    private void createUsers() {
        user("Jan", "Administrator", "admin@library.com", "employee", UserRole.ADMIN, "HiddenSecretPassword");
        user("Standard", "User", "user@gmail.com", "Address1", UserRole.MEMBER, "Password1");
        user("Basic", "User", "user2@gmail.com", "Address2", UserRole.MEMBER, "Password2");
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
        //todo: Why we don't do it by Book constructor?
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
    
    private User user(String name, String surname, String email, String address, UserRole role, String passwd) {
        User user = new User();
        user.setFirstName(name);
        user.setLastName(surname);
        user.setEmail(email);
        user.setAddress(address);
        user.setUserRole(role);
        userService.register(user, passwd);
        return user;
    }

}
