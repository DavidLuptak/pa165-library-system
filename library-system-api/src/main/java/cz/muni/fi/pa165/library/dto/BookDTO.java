package cz.muni.fi.pa165.library.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Bedrich Said
 */
public class BookDTO {
    private Long id;

    @NotBlank(message = "Can't be empty!")
    @Size(max = 50, message = "Enter no more than 50 characters.")
    private String title;

    @NotBlank(message = "Can't be empty!")
    @Size(max = 50, message = "Enter no more than 50 characters.")
    private String author;

    @NotBlank(message = "Can't be empty!")
    private String isbn;

    private List<CategoryDTO> categories;
    private List<BookCopyDTO> bookCopies;

    public BookDTO() {
        categories = new ArrayList<>();
        bookCopies = new ArrayList<>();
    }

    public BookDTO(String title, String author, String isbn, List<CategoryDTO> categories, List<BookCopyDTO> bookCopies) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.categories = categories;
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

    public List<CategoryDTO> getCategories() {
        return Collections.unmodifiableList(categories);
    }

    public void addCategory(CategoryDTO category) {
        this.categories.add(category);
    }

    public void removeCategory(CategoryDTO category) {
        this.categories.remove(category);
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
