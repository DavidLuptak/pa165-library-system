package cz.muni.fi.pa165.library.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Bedrich Said
 */
public class BookDTO {
    private Long id;

    @NotBlank(message = "Can't be empty!")
    @Size(max = 100, message = "Enter no more than 100 characters.")
    private String title;

    @NotBlank(message = "Can't be empty!")
    @Size(max = 50, message = "Enter no more than 50 characters.")
    private String author;

    @NotBlank(message = "Can't be empty!")
    private String isbn;

    private List<String> categoryNames;
    private List<BookCopyDTO> bookCopies;

    public BookDTO() {
        categoryNames = new ArrayList<>();
        bookCopies = new ArrayList<>();
    }

    public BookDTO(String title, String author, String isbn, List<String> categoryNames, List<BookCopyDTO> bookCopies) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.categoryNames = categoryNames;
        this.bookCopies = bookCopies;
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

    public List<String> getCategoryNames() {
        return categoryNames;
    }

    public void addCategory(String categoryName) {
        this.categoryNames.add(categoryName);
    }

    public void removeCategory(String categoryName) {
        this.categoryNames.remove(categoryName);
    }

    public List<BookCopyDTO> getBookCopies() {
        return Collections.unmodifiableList(bookCopies);
    }

    public void addBookCopy(BookCopyDTO bookCopy) {
        this.bookCopies.add(bookCopy);
    }

    public void removeBookCopy(BookCopyDTO bookCopy) {
        this.bookCopies.remove(bookCopy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookDTO)) return false;

        BookDTO book = (BookDTO) o;

        return getIsbn() != null ? getIsbn().equals(book.getIsbn()) : book.getIsbn() == null;
    }

    @Override
    public int hashCode() {
        return getIsbn() != null ? getIsbn().hashCode() : 0;
    }
}
