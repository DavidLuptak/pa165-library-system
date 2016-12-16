package cz.muni.fi.pa165.library.facade;

import cz.muni.fi.pa165.library.dto.BookCopyDTO;
import cz.muni.fi.pa165.library.dto.BookDTO;
import cz.muni.fi.pa165.library.dto.BookNewDTO;
import cz.muni.fi.pa165.library.dto.CategoryDTO;
import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.BookCopy;
import cz.muni.fi.pa165.library.entity.Category;
import cz.muni.fi.pa165.library.exception.NoEntityFoundException;
import cz.muni.fi.pa165.library.mapping.BeanMappingService;
import cz.muni.fi.pa165.library.service.BookService;
import cz.muni.fi.pa165.library.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Martin
 * @version 24.11.2016.
 */
@Service
@Transactional
public class BookFacadeImpl implements BookFacade {

    @Inject
    private BookService bookService;

    @Inject
    private CategoryService categoryService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public Long create(BookNewDTO bookNewDTO) {
        if (bookNewDTO == null) {
            throw new IllegalArgumentException("Book cannot be null.");
        }
        Book book = beanMappingService.mapTo(bookNewDTO, Book.class);

        List<Long> categoryIds = bookNewDTO.getCategoryIds();
        List<Category> categories = new ArrayList<>();

        categoryIds.forEach(id -> categories.add(categoryService.findById(id)));
        categories.forEach(category -> category.addBook(book));

        bookService.create(book);
        return book.getId();
    }

    @Override
    public void update(BookDTO bookDTO) {
        if (bookDTO == null) {
            throw new IllegalArgumentException("Book cannot be null.");
        }
        Book book = beanMappingService.mapTo(bookDTO, Book.class);
        if (bookService.findById(book.getId()) == null) {
            throw new NoEntityFoundException("Book not found during update.");
        }
        bookService.update(book);
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Book id cannot be null.");
        }
        Book book = bookService.findById(id);
        if (book == null) {
            throw new NoEntityFoundException("Book not found during delete.");
        }
        bookService.delete(book);
    }

    @Override
    public BookDTO findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Book id cannot be null.");
        }
        Book book = bookService.findById(id);
        if (book == null) {
            throw new NoEntityFoundException("Book not found during findById.");
        }

        return mapOwningSidesToDTO(book);
    }

    @Override
    public List<BookDTO> findByAuthor(String author) {
        if (author == null || author.isEmpty()) {
            throw new IllegalArgumentException("Author name cannot be null.");
        }
        List<Book> books = bookService.findByAuthor(author);
        if (books.size() == 0) {
            throw new NoEntityFoundException("Books not found during findByAuthor.");
        }

        List<BookDTO> bookDTOS = new ArrayList<>();
        books.forEach(book -> bookDTOS.add(mapOwningSidesToDTO(book)));

        return bookDTOS;
    }

    @Override
    public List<BookDTO> findByTitle(String title) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null.");
        }
        List<Book> books = bookService.findByTitle(title);
        if (books.size() == 0) {
            throw new NoEntityFoundException("Books not found during findByTitle.");
        }

        List<BookDTO> bookDTOS = new ArrayList<>();
        books.forEach(book -> bookDTOS.add(mapOwningSidesToDTO(book)));

        return bookDTOS;
    }

    @Override
    public List<BookDTO> findAll() {
        List<Book> books = bookService.findAll();
        List<BookDTO> bookDTOS = new ArrayList<>();

        books.forEach(book -> bookDTOS.add(mapOwningSidesToDTO(book)));

        return bookDTOS;
    }

    private BookDTO mapOwningSidesToDTO(Book book) {
        BookDTO bookDTO = beanMappingService.mapTo(book, BookDTO.class);

        List<Category> categories = book.getCategories();
        List<CategoryDTO> categoryDTOS = beanMappingService.mapTo(categories, CategoryDTO.class);

        categoryDTOS.forEach(bookDTO::addCategory);

        List<BookCopy> bookCopies = book.getBookCopies();
        List<BookCopyDTO> bookCopyDTOS = beanMappingService.mapTo(bookCopies, BookCopyDTO.class);

        bookCopyDTOS.forEach(bookDTO::addBookCopy);
        return bookDTO;
    }
}
