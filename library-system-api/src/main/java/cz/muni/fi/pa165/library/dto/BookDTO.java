package cz.muni.fi.pa165.library.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Bedrich Said
 */
public class BookDTO {
    private Long id;
    private String name;
    private String author;
    private String isbn;
    private List<CategoryDTO> categories;
    private List<BookCopyDTO> bookCopies;

    public BookDTO() {
        categories = new ArrayList<>();
        bookCopies = new ArrayList<>();
    }

    public BookDTO(String name, String author, String isbn, List<CategoryDTO> categories, List<BookCopyDTO> bookCopies) {
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
