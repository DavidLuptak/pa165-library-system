package cz.muni.fi.pa165.library.dto;

import cz.muni.fi.pa165.library.enums.BookState;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author Martin
 * @version 24.11.2016
 */
public class BookCopyNewDTO {

    private Long id;

    @NotNull
    private BookDTO book;

    @NotNull
    private BookState bookState;

    public BookCopyNewDTO() {
    }

    public BookCopyNewDTO(BookDTO book) {
        this.book = book;
        bookState = BookState.NEW;
    }

    public BookCopyNewDTO(BookDTO book, BookState bookState) {
        this.book = book;
        this.bookState = bookState;
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
