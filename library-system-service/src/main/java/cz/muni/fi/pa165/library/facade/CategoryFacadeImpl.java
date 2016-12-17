package cz.muni.fi.pa165.library.facade;

import cz.muni.fi.pa165.library.dto.BookDTO;
import cz.muni.fi.pa165.library.dto.CategoryDTO;
import cz.muni.fi.pa165.library.dto.CategoryNewDTO;
import cz.muni.fi.pa165.library.entity.Book;
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
 * @author Lenka (433591)
 * @version 12.11.2016
 */
@Service
@Transactional
public class CategoryFacadeImpl implements CategoryFacade {

    @Inject
    private CategoryService categoryService;

    @Inject
    private BookService bookService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public Long create(CategoryNewDTO categoryNewDTO) {
        if (categoryNewDTO == null) {
            throw new IllegalArgumentException("Category cannot be null.");
        }

        Category category = beanMappingService.mapTo(categoryNewDTO, Category.class);
        categoryService.create(category);
        return category.getId();
    }

    @Override
    public void update(CategoryDTO categoryDTO) {
        if (categoryDTO == null) {
            throw new IllegalArgumentException("Category cannot be null.");
        }

        if (categoryService.findById(categoryDTO.getId()) == null) {
            throw new NoEntityFoundException("Category not found during update.");
        }

        Category category = mapBooksToEntity(categoryDTO);
        categoryService.update(category);
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Category id cannot be null.");
        }

        Category category = categoryService.findById(id);

        if (category == null) {
            throw new NoEntityFoundException("Category not found during delete.");
        }

        categoryService.delete(category);
    }

    @Override
    public CategoryDTO findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Category id cannot be null.");
        }

        Category category = categoryService.findById(id);

        if (category == null) {
            throw new NoEntityFoundException("Category not found during findById.");
        }

        return mapBooksToDTO(category);
    }

    @Override
    public CategoryDTO findByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null.");
        }

        Category category = categoryService.findByName(name);

        if (category == null) {
            throw new NoEntityFoundException("Category not found during findByName.");
        }

        return mapBooksToDTO(category);
    }

    @Override
    public List<CategoryDTO> findAll() {
        List<Category> categories = categoryService.findAll();
        List<CategoryDTO> categoryDTOS = new ArrayList<>();

        categories.forEach(c -> categoryDTOS.add(mapBooksToDTO(c)));

        return categoryDTOS;

    }

    /**
     * Mapping of Category entity to Category DTO.
     *
     * @param category to be mapped to the Category DTO entity
     * @return CategoryDTO entity
     */
    private CategoryDTO mapBooksToDTO(Category category) {
        CategoryDTO categoryDTO = beanMappingService.mapTo(category, CategoryDTO.class);

        List<Book> books = category.getBooks();
        List<BookDTO> bookDTOS = beanMappingService.mapTo(books, BookDTO.class);

        bookDTOS.forEach(categoryDTO::addBook);

        return categoryDTO;
    }

    /**
     * Mapping of Category DTO to Category entity.
     *
     * @param categoryDTO to be mapped to the Category entity
     * @return Category entity
     */
    private Category mapBooksToEntity(CategoryDTO categoryDTO) {
        Category category = beanMappingService.mapTo(categoryDTO, Category.class);

        List<BookDTO> bookDTOS = categoryDTO.getBooks();
        List<Book> books = beanMappingService.mapTo(bookDTOS, Book.class);

        books.forEach(book -> {
            category.addBook(book);
            bookService.update(book);
        });

        return category;
    }
}
