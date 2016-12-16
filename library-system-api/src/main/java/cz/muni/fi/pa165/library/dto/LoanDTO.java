package cz.muni.fi.pa165.library.dto;

import cz.muni.fi.pa165.library.enums.BookState;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

/**
 * @author Dávid Lupták
 * @version 15.11.2016
 */
public class LoanDTO {

    private Long id;

    @NotNull
    private UserDTO user;

    @NotNull
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private Date loanDate;

    @NotNull
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private Date returnDate;

    @NotNull
    private BookState returnBookState;

    @NotNull
    private BookCopyDTO bookCopy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public BookState getReturnBookState() {
        return returnBookState;
    }

    public void setReturnBookState(BookState returnBookState) {
        this.returnBookState = returnBookState;
    }

    public BookCopyDTO getBookCopy() {
        return bookCopy;
    }

    public void setBookCopy(BookCopyDTO bookCopy) {
        this.bookCopy = bookCopy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof LoanDTO)) return false;

        LoanDTO other = (LoanDTO) o;

        return Objects.equals(getId(), other.getId()) &&
                Objects.equals(getUser(), other.getUser()) &&
                Objects.equals(getLoanDate(), other.getLoanDate()) &&
                Objects.equals(getReturnDate(), other.getReturnDate()) &&
                Objects.equals(getReturnBookState(), other.getReturnBookState());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getLoanDate(), getReturnDate(), getReturnBookState());
    }

    @Override
    public String toString() {
        return "LoanDTO{" +
                "id=" + id +
                ", user=" + user +
                ", loanDate=" + loanDate +
                ", returnDate=" + returnDate +
                ", returnBookState=" + returnBookState +
                ", bookCopy=" + bookCopy +
                '}';
    }
}
