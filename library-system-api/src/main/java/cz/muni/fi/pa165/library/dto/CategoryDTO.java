package cz.muni.fi.pa165.library.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Lenka (433591)
 * @version 12.11.2016
 */
public class CategoryDTO {

    private Long id;

    private String name;

    private List<BookDTO> books;

    public CategoryDTO() {
        books = new ArrayList<>();
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

    public void setBooks(List<BookDTO> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryDTO)) return false;

        CategoryDTO that = (CategoryDTO) o;

        return getName() != null ? getName().equals(that.getName()) : that.getName() == null;

    }

    @Override
    public int hashCode() {
        return getName() != null ? getName().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "CategoryNewDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}
