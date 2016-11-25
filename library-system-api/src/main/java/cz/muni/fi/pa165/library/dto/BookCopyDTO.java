package cz.muni.fi.pa165.library.dto;

import cz.muni.fi.pa165.library.enums.BookState;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lenka (433591)
 * @version 19.11.2016
 */
public class BookCopyDTO {

    private Long id;

    private BookDTO book;

    private BookState bookState;

    private List<LoanDTO> loans;

    public BookCopyDTO() {
        loans = new ArrayList<>();
    }

    public BookCopyDTO(BookDTO book, BookState bookState, List<LoanDTO> loans) {
        this.book = book;
        this.bookState = bookState;
        this.loans = loans;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BookDTO getBook() {
        return book;
    }

    public void setBook(BookDTO book) {
        this.book = book;
    }

    public BookState getBookState() {
        return bookState;
    }

    public void setBookState(BookState bookState) {
        this.bookState = bookState;
    }

    public List<LoanDTO> getLoans() {
        return loans;
    }

    public void setLoans(List<LoanDTO> loans) {
        this.loans = loans;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookCopyDTO)) return false;

        BookCopyDTO that = (BookCopyDTO) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        return getBook() != null ? getBook().equals(that.getBook()) : that.getBook() == null;

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getBook() != null ? getBook().hashCode() : 0);
        return result;
    }
}
