package cz.muni.fi.pa165.library.entity;

import cz.muni.fi.pa165.library.enums.BookState;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
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
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date loanDate;

    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date returnDate;

    @Column
    private BookState returnBookState;

    @NotNull
    @ManyToOne
    private BookCopy bookCopy;

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

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Date getReturnDate() {
        return this.returnDate;
    }

    public void setReturnDate(Date returnDate) {
        if(returnDate.before(getLoanDate())) throw new IllegalArgumentException("returnDate is before loanDate");
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
}
