package cz.muni.fi.pa165.library.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bedrich Said
 */
public class BookNewDTO {

    @NotBlank(message = "Please enter a value.")
    @Size(max = 100, message = "Enter no more than 100 characters.")
    private String title;

    @NotBlank(message = "Please enter a value.")
    @Size(max = 50, message = "Enter no more than 50 characters.")
    private String author;

    @NotBlank(message = "Please enter a value.")
    private String isbn;

    @Min(value = 0, message = "Number of copies must be more than 0.")
    private int copies;

    private List<Long> categoryIds = new ArrayList<>();

    public BookNewDTO() {
    }

    public BookNewDTO(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public List<Long> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Long> categoryIds) {
        this.categoryIds = categoryIds;
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
