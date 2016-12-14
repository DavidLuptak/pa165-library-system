package cz.muni.fi.pa165.library.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * @author Bedrich Said
 */
public class BookNewDTO {

    private Long id;

    @NotBlank(message = "Please enter value.")
    @Size(max = 50, message = "Enter no more than 50 characters.")
    private String title;

    @NotBlank(message = "Please enter value.")
    @Size(max = 50, message = "Enter no more than 50 characters.")
    private String author;

    @NotBlank(message = "Please enter value.")
    private String isbn;

    public BookNewDTO() {
    }

    public BookNewDTO(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
