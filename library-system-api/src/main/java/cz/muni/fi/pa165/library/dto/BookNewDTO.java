package cz.muni.fi.pa165.library.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bedrich Said
 */
public class BookNewDTO {
    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank
    @Size(max = 50)
    private String authorName;

    @NotNull
    private String isbn;

    public BookNewDTO() {
    }

    public BookNewDTO(String name, String authorName, String isbn) {
        this.name = name;
        this.authorName = authorName;
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookNewDTO)) return false;

        BookNewDTO book = (BookNewDTO) o;

        return getIsbn() != null ? getIsbn().equals(book.getIsbn()) : book.getIsbn() == null;
    }

    @Override
    public int hashCode() {
        return getIsbn() != null ? getIsbn().hashCode() : 0;
    }
}
