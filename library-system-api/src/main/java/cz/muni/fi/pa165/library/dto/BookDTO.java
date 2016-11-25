package cz.muni.fi.pa165.library.dto;

import cz.muni.fi.pa165.library.enums.BookState;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bedrich Said
 */
public class BookDTO {
    private Long id;
    private String name;
    private String authorName;
    private String isbn;
    private List<CategoryDTO> categories;
    private List<BookCopyDTO> bookCopies;

    public BookDTO() {
        categories = new ArrayList<>();
        bookCopies = new ArrayList<>();
    }

    public BookDTO(String name, String authorName, String isbn, List<CategoryDTO> categories, List<BookCopyDTO> bookCopies) {
        this.name = name;
        this.authorName = authorName;
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

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories;
    }

    public List<BookCopyDTO> getBookCopies() {
        return bookCopies;
    }

    public void setBookCopies(List<BookCopyDTO> bookCopies) {
        this.bookCopies = bookCopies;
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
