package cz.muni.fi.pa165.library.web.controllers;

import cz.muni.fi.pa165.library.dto.*;
import cz.muni.fi.pa165.library.entity.BookCopy;
import cz.muni.fi.pa165.library.exception.NoEntityFoundException;
import cz.muni.fi.pa165.library.facade.BookCopyFacade;
import cz.muni.fi.pa165.library.facade.BookFacade;
import cz.muni.fi.pa165.library.facade.CategoryFacade;
import cz.muni.fi.pa165.library.facade.LoanFacade;
import cz.muni.fi.pa165.library.mapping.BeanMappingService;
import cz.muni.fi.pa165.library.web.validator.BookCreateValidator;
import cz.muni.fi.pa165.library.web.validator.BookUpdateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javafx.util.Pair;

/**
 * @author Lenka (433591)
 * @version 10.12.2016
 */
@Controller
@RequestMapping("/book")
public class BookController extends LibraryParentController {

    final static Logger log = LoggerFactory.getLogger(BookController.class);

    @Inject
    private BookFacade bookFacade;

    @Inject
    private BookCopyFacade bookCopyFacade;

    @Inject
    private CategoryFacade categoryFacade;
    
    @Inject
    private LoanFacade loanFacade;

    @Inject
    private BookCreateValidator bookCreateValidator;

    @Inject
    private BookUpdateValidator bookUpdateValidator;

    @Inject
    private BeanMappingService beanMappingService;

    @RequestMapping(value = {"", "/", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("books", bookFacade.findAll());
        return "book/index";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model, RedirectAttributes redirectAttributes,
                         UriComponentsBuilder uriBuilder) {
        try {
            model.addAttribute("book", bookFacade.findById(id));
            
            List< Pair<BookCopyDTO, String> > bookCopies = new LinkedList<>();
            for(BookCopyDTO copy : bookFacade.findById(id).getBookCopies()) {
                String available = "Yes";
                for(LoanDTO loan : loanFacade.findAllNotReturned()) {
                    if(copy.equals(loan.getBookCopy())) {
                        available = "No";
                    }
                }
                bookCopies.add(new Pair<>(copy, available));
            }
            model.addAttribute("copies", bookCopies);
            
        } catch (NoEntityFoundException e) {
            redirectAttributes.addFlashAttribute("alert_warning", "Book " + id + " was not found");
            return "redirect:" + uriBuilder.path("/book").toUriString();
        }
        return "book/detail";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String edit(Model model) {

        model.addAttribute("book", new BookNewDTO());
        return "book/create";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable long id, RedirectAttributes redirectAttributes,
                         UriComponentsBuilder uriBuilder) {

        try {
            bookFacade.delete(id);
            redirectAttributes.addFlashAttribute("alert_info", "Book was deleted");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("alert_danger", "Book could not be deleted");
        }
        return "redirect:" + uriBuilder.path("/book/index").toUriString();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("book") BookNewDTO book,
                         BindingResult br, RedirectAttributes redirectAttributes,
                         UriComponentsBuilder uriBuilder, Model model) {
        if (br.hasErrors()) {
            addValidationErrors(br, model);
            return "book/create";
        }

        bookFacade.create(book);

        redirectAttributes.addFlashAttribute("alert_info", "Book " + book.getTitle() + " was created");
        return "redirect:" + uriBuilder.path("/book/index").toUriString();
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable long id, Model model, RedirectAttributes redirectAttributes,
                       UriComponentsBuilder uriBuilder) {
        try {
            BookUpdateDTO bookUpdateDTO = mapBookDTOtoBookNewOrUpdateDTO(bookFacade.findById(id), BookUpdateDTO.class);
            model.addAttribute("book", bookUpdateDTO);
        } catch (NoEntityFoundException e) {
            redirectAttributes.addFlashAttribute("alert_warning", "Book " + id + " was not found");
            return "redirect:" + uriBuilder.path("/book/index").toUriString();
        }
        return "book/edit";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String edit(@Valid @ModelAttribute("book") BookUpdateDTO book,
                       BindingResult br,
                       @PathVariable long id,
                       Model model,
                       RedirectAttributes redirectAttributes,
                       UriComponentsBuilder uriBuilder) {
        if (br.hasErrors()) {
            addValidationErrors(br, model);
            return "book/edit";
        }

        try {
            BookDTO bookDTO = mapNewOrUpdateBookDTOtoBookDTO(book);
            bookFacade.update(bookDTO);
            redirectAttributes.addFlashAttribute("alert_info", "Book " + book.getTitle() + " was updated");
        } catch (NoEntityFoundException e) {
            redirectAttributes.addFlashAttribute("alert_danger", "Book " + book.getTitle() + " cannot be updated");
        }

        return "redirect:" + uriBuilder.path("/book/detail/" + book.getId()).toUriString();
    }

    @RequestMapping(value = "/addCopy/{id}", method = RequestMethod.GET)
    public String addCopy(@PathVariable long id, RedirectAttributes redirectAttributes,
                          UriComponentsBuilder uriBuilder) {
        try {
            BookDTO book = bookFacade.findById(id);
            bookCopyFacade.create(new BookCopyNewDTO(book));
            redirectAttributes.addFlashAttribute("alert_info", "Copy added.");
        } catch (NoEntityFoundException e) {
            redirectAttributes.addFlashAttribute("alert_danger", "Book not found.");
        }
        return "redirect:" + uriBuilder.path("/book/detail/" + id).toUriString();
    }

    @ModelAttribute("categories")
    public List<CategoryDTO> categories() {
        log.debug("categories()");
        return categoryFacade.findAll();
    }

    private <T extends BookNewDTO> T mapBookDTOtoBookNewOrUpdateDTO(BookDTO bookDTO, Class<T> type) {
        T dto = beanMappingService.mapTo(bookDTO, type);

        List<String> categoryNames = bookDTO.getCategoryNames();

        List<CategoryDTO> categories = new ArrayList<>();
        categoryNames.forEach(c -> categories.add(categoryFacade.findByName(c)));

        List<Long> categoryIds = new ArrayList<>();
        categories.forEach(c -> categoryIds.add(c.getId()));

        dto.setCategoryIds(categoryIds);

        return dto;
    }

    private <T> BookDTO mapNewOrUpdateBookDTOtoBookDTO(T dto) {
        BookDTO bookDTO = beanMappingService.mapTo(dto, BookDTO.class);

        BookNewDTO bookNewDTO = (BookNewDTO) dto;
        List<Long> categoryIds = bookNewDTO.getCategoryIds();

        List<CategoryDTO> categories = new ArrayList<>();
        categoryIds.forEach(id -> categories.add(categoryFacade.findById(id)));

        List<String> categoryNames = bookDTO.getCategoryNames();
        categories.forEach(c -> categoryNames.add(c.getName()));

        return bookDTO;
    }

    @InitBinder
    protected void initUniqueConstraintBinder(WebDataBinder binder) {

        if (binder.getTarget() instanceof BookNewDTO
                && !(binder.getTarget() instanceof BookUpdateDTO)) {
            binder.addValidators(bookCreateValidator);
        }

        if (binder.getTarget() instanceof BookUpdateDTO) {
            binder.addValidators(bookUpdateValidator);
        }
    }
}
