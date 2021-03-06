package cz.muni.fi.pa165.library.dto;

import org.hibernate.validator.constraints.NotBlank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Lenka (433591)
 * @version 12.11.2016
 */
public class CategoryDTO {

    private Long id;

    @NotBlank
    private String name;

    private List<BookDTO> books;

    public CategoryDTO() {
        books = new ArrayList<>();
    }

    public CategoryDTO(String name, List<BookDTO> books) {
        this.name = name;
        this.books = books;
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

    public List<BookDTO> getBooks() {
        return Collections.unmodifiableList(books);
    }

    public void addBook(BookDTO book) {
        this.books.add(book);
    }

    public void removeBook(BookDTO book) {
        this.books.remove(book);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryDTO)) return false;

        CategoryDTO category = (CategoryDTO) o;

        return getName() != null ? getName().equals(category.getName()) : category.getName() == null;
    }

    @Override
    public int hashCode() {
        return getName() != null ? getName().hashCode() : 0;
    }
}
