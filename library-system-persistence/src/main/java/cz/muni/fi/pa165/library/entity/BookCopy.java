package cz.muni.fi.pa165.library.entity;

import cz.muni.fi.pa165.library.enums.BookState;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Entity class representing BookCopy in the library system.
 * A bookCopy is exactly one (printed) copy of a book.
 * Making a loan in a system is based on this entity.
 *
 * @author Lenka (433591)
 * @version 25.10.2016
 */
@Entity
public class BookCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Book book;

    @OneToMany(mappedBy = "bookCopy", orphanRemoval = true)
    private List<Loan> loans;

    @NotNull
    private BookState bookState;

    @NotNull
    private boolean deleted = false;

    public BookCopy() {
        loans = new ArrayList<>();
        bookState = BookState.NEW;
    }

    public BookCopy(Book book) {
        this();
        this.book = book;
    }

    public BookCopy(Book book, BookState bookState) {
        this();
        this.book = book;
        this.bookState = bookState;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public BookState getBookState() {
        return bookState;
    }

    public void setBookState(BookState bookState) {
        this.bookState = bookState;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public List<Loan> getLoans() {
        return Collections.unmodifiableList(loans);
    }

    public void addLoan(Loan loan) {
        loans.add(loan);
    }

    public boolean isLoanable() {
        return !loans.stream().
                filter(x -> !x.isReturned()).
                findFirst().
                isPresent();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookCopy)) return false;

        BookCopy bookCopy = (BookCopy) o;

        if (getId() == null ? bookCopy.getId() != null : !getId().equals(bookCopy.getId()))
            return false;

        return getBook() == null ? bookCopy.getBook() == null : getBook().equals(bookCopy.getBook());
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getBook() != null ? getBook().hashCode() : 0);
        return result;
    }
}
