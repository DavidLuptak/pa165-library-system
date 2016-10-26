package cz.muni.fi.pa165.library.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Lenka (433591)
 * @version 22.10.2016
 */
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String author;

    @NotNull
    @Column(nullable = false, unique = true)
    private String isbn;

    @ManyToMany(mappedBy = "books")
    private List<Category> categories;

    @OneToMany
    private List<BookCopy> bookCopies;

    public Book() {
        this.categories = new ArrayList<Category>();
        this.bookCopies = new ArrayList<BookCopy>();

    }

    public Book(String name, String author, String isbn) {
        this();
        this.name = name;
        this.author = author;
        this.isbn = isbn;
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

    public List<Category> getCategories() {
        return Collections.unmodifiableList(categories);
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public List<BookCopy> getBookCopies() {
        return Collections.unmodifiableList(bookCopies);
    }

    public void setBookCopies(List<BookCopy> bookCopies) {
        this.bookCopies = bookCopies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;

        Book book = (Book) o;

        return getIsbn() != null ? getIsbn().equals(book.getIsbn()) : book.getIsbn() == null;

    }

    @Override
    public int hashCode() {
        return getIsbn() != null ? getIsbn().hashCode() : 0;
    }
}