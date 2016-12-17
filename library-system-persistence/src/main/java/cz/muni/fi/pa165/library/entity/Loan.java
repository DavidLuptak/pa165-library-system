package cz.muni.fi.pa165.library.entity;

import cz.muni.fi.pa165.library.enums.BookState;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Entity class representing Loan in the library system.
 * It serves for the purpose of making a loan of book copies.
 * More than one book copy can be included in a single loan.
 *
 * @author Bedrich Said
 */
@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private User user;

    @NotNull
    @ManyToOne
    private BookCopy bookCopy;

    @NotNull
    @Column
    private LocalDateTime loanDate;

    @Column
    private LocalDateTime returnDate;

    @Column
    private BookState returnBookState;


    public Loan() {
    }

    public Loan(User user, BookCopy bookCopy, LocalDateTime loanDate) {
        this.user = user;
        this.bookCopy = bookCopy;
        this.loanDate = loanDate;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDateTime loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDateTime getReturnDate() {
        return this.returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isReturned() {
        return returnDate != null;
    }

    public BookState getReturnBookState() {
        return this.returnBookState;
    }

    public void setReturnBookState(BookState returnBookState) {
        this.returnBookState = returnBookState;
    }

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public void setBookCopy(BookCopy bookCopy) {
        this.bookCopy = bookCopy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Loan)) return false;

        Loan loan = (Loan) o;

        if (getUser() != null ? !getUser().equals(loan.getUser()) : loan.getUser() != null) return false;
        if (getLoanDate() != null ? !getLoanDate().equals(loan.getLoanDate()) : loan.getLoanDate() != null)
            return false;
        return getBookCopy() != null ? getBookCopy().equals(loan.getBookCopy()) : loan.getBookCopy() == null;
    }

    @Override
    public int hashCode() {
        int result = getUser() != null ? getUser().hashCode() : 0;
        result = 31 * result + (getLoanDate() != null ? getLoanDate().hashCode() : 0);
        result = 31 * result + (getBookCopy() != null ? getBookCopy().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", user=" + user +
                ", bookCopy=" + bookCopy +
                ", loanDate=" + loanDate +
                ", returnDate=" + returnDate +
                ", returnBookState=" + returnBookState +
                '}';
    }
}
