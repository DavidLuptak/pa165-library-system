package cz.muni.fi.pa165.library.entity;

import cz.muni.fi.pa165.library.enums.BookState;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;


/**
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

    @OneToMany(mappedBy = "bookCopy",orphanRemoval = true)
    private List<Loan> loans;

    @NotNull
    private boolean isLoaned;

    @NotNull
    private BookState bookState;

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

    public List<Loan> getLoans() {
        return Collections.unmodifiableList(loans);
    }

    public void addLoan(Loan loan){
        if(!isLoaned()) throw new IllegalStateException("Book is currently loaned");
        loans.add(loan);
    }

    public boolean isLoaned() {
        return isLoaned;
    }

    public void setLoaned(boolean loaned) {
        isLoaned = loaned;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookCopy)) return false;

        BookCopy bookCopy = (BookCopy) o;

        if (getId() == null ? bookCopy.getId() != null : !getId().equals(bookCopy.getId()) )

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
