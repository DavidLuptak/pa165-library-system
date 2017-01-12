package cz.muni.fi.pa165.library.sampledata;

import cz.muni.fi.pa165.library.entity.*;
import cz.muni.fi.pa165.library.enums.BookState;
import cz.muni.fi.pa165.library.enums.UserRole;
import cz.muni.fi.pa165.library.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Inject
    private LoanService loanService;

    @Inject
    private BookCopyService bookCopyService;

    @Override
    public void loadData() {
        //Users
        User user1 = user("Standard", "User", "user@gmail.com", "Address1", UserRole.MEMBER, "Password1");
        User user2 = user("Basic", "User", "user2@gmail.com", "Address2", UserRole.MEMBER, "Password2");
        User user3 = user("Jan", "Administrator", "admin@library.com", "employee", UserRole.ADMIN, "admin");

        LOGGER.info("Users loaded.");

        //Books
        Book book1 = book("Joshua Bloch", "Effective Java 2nd Edition", "978-0-321-35668-0");
        Book book2 = book("Rod Johnson", "Expert One-on-One J2EE Design and Development", "978-0-7645-4385-2");
        Book book3 = book("Jane Austen", "Pride and Prejudice", "978-1-5032-9056-3");
        Book book4 = book("J. K. Rowling", "Harry Potter and the Chamber of Secrets", "978-0-439-06487-3");
        Book book5 = book("Robert C. Martin", "Clean Code: A Handbook of Agile Software Craftsmanship", "978-0-13-235088-4");
        Book book6 = book("Erich Gamma et al.", "Design Patterns: Elements of Reusable Object-Oriented Software", "978-0-201-63361-0");
        Book book7 = book("Martin Fowler", "UML Distilled", "0-321-19368-7");

        LOGGER.info("Books loaded.");

        //BookCopies (owning Book)
        BookCopy bookCopy11 = bookCopy(book1, BookState.NEW);
        BookCopy bookCopy21 = bookCopy(book2, BookState.NEW);
        BookCopy bookCopy22 = bookCopy(book2, BookState.NEW);
        BookCopy bookCopy23 = bookCopy(book2, BookState.NEW);
        BookCopy bookCopy31 = bookCopy(book3, BookState.NEW);
        BookCopy bookCopy32 = bookCopy(book3, BookState.NEW);
        BookCopy bookCopy33 = bookCopy(book3, BookState.NEW);
        BookCopy bookCopy41 = bookCopy(book4, BookState.NEW);
        BookCopy bookCopy42 = bookCopy(book4, BookState.NEW);
        BookCopy bookCopy43 = bookCopy(book4, BookState.NEW);
        BookCopy bookCopy44 = bookCopy(book4, BookState.NEW);
        BookCopy bookCopy45 = bookCopy(book4, BookState.NEW);

        LOGGER.info("Book copies loaded.");

        //Categories (owning Books)
        Category category1 = category("Information Technologies", Arrays.asList(book1, book2, book5, book6));
        Category category2 = category("Recreational Reading", Arrays.asList(book3, book4));
        Category category3 = category("Adventures", Arrays.asList(book1, book4, book6));
        Category category4 = category("My favourite books", Arrays.asList(book1, book2, book5, book6));
        Category category5 = category("Java", Arrays.asList(book1, book2));
        Category category6 = category("Empty category yet", new ArrayList<>());

        LOGGER.info("Categories loaded.");

        //Loans (owning User, BookCopy)
        Loan loan11 = returnedLoan(user1, bookCopy11, LocalDateTime.of(2016,12,5,10,10), LocalDateTime.of(2016,12,9,12,30), BookState.NEW);
        Loan loan12 = returnedLoan(user1, bookCopy21,LocalDateTime.of(2016,12,5,10,10), LocalDateTime.of(2016,12,11,13,50), BookState.NEW);
        Loan loan13 = returnedLoan(user1, bookCopy11, LocalDateTime.of(2016,12,5,10,10), LocalDateTime.of(2016,12,15,16,5), BookState.LIGHT_DAMAGE);

        Loan loan1x1 = notReturnedLoan(user1, bookCopy32, LocalDateTime.of(2016,12,10,9,25));
        Loan loan1x2 = notReturnedLoan(user1, bookCopy23, LocalDateTime.of(2016,12,10,8,15));

        Loan loan21 = returnedLoan(user2, bookCopy22, LocalDateTime.of(2016,12,6,16,20), LocalDateTime.of(2016,12,10,14,6), BookState.NEW);
        Loan loan22 = returnedLoan(user2, bookCopy31, LocalDateTime.of(2016,12,6,16,20), LocalDateTime.of(2016,12,16,12,0), BookState.NEW);

        Loan loan2x1 = notReturnedLoan(user2, bookCopy11, LocalDateTime.of(2016,12,10,13,15));
        Loan loan2x2 = notReturnedLoan(user2, bookCopy45, LocalDateTime.of(2016,12,10,13,15));

        LOGGER.info("Loans loaded.");
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

    private Loan returnedLoan(User user, BookCopy bookCopy, LocalDateTime loanDate, LocalDateTime returnDate, BookState returnBookState) {
        Loan loan = notReturnedLoan(user, bookCopy, loanDate);
        loan.setReturnDate(returnDate);
        loan.setReturnBookState(returnBookState);
        loanService.returnLoan(loan);
        return loan;
    }

    private Loan notReturnedLoan(User user, BookCopy bookCopy, LocalDateTime loanDate) {
        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBookCopy(bookCopy);
        loan.setLoanDate(loanDate);
        loanService.create(loan);
        return loan;
    }

    private BookCopy bookCopy(Book book, BookState bookState) {
        BookCopy bookCopy = new BookCopy();
        bookCopy.setBook(book);
        bookCopy.setBookState(bookState);
        bookCopyService.create(bookCopy);
        return bookCopy;
    }

}
