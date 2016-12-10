package cz.muni.fi.pa165.library.web.controllers;

import javax.inject.Inject;
import javax.validation.Valid;

import com.sun.org.apache.xpath.internal.operations.Mod;
import cz.muni.fi.pa165.library.dto.BookDTO;
import cz.muni.fi.pa165.library.dto.BookNewDTO;
import cz.muni.fi.pa165.library.dto.CategoryDTO;
import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.exception.NoEntityFoundException;
import cz.muni.fi.pa165.library.facade.BookFacade;
import cz.muni.fi.pa165.library.facade.CategoryFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Lenka (433591)
 * @version 10.12.2016
 */
@Controller
@RequestMapping("/book")
public class BookController {

    final static Logger log = LoggerFactory.getLogger(BookController.class);

    @Inject
    private BookFacade bookFacade;

    @Inject
    private CategoryFacade categoryFacade;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("books", bookFacade.findAll());
        return "book/list";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model, RedirectAttributes redirectAttributes,
                         UriComponentsBuilder uriBuilder) {
        try {
            model.addAttribute("book", bookFacade.findById(id));
        } catch (NoEntityFoundException e ) {
            redirectAttributes.addAttribute("alert_danger", "Book " + id + " was not found");
            return "redirect:" + uriBuilder.path("/book").toUriString();
        }
        return "book/detail";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String edit(Model model) {

        model.addAttribute("book", new BookNewDTO());
        return "book/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("book") BookNewDTO book,
                       BindingResult br, RedirectAttributes redirectAttributes,
                       UriComponentsBuilder uriBuilder) {
        if (br.hasErrors()) {
            return "book/create";
        }
        bookFacade.create(book);
        redirectAttributes.addFlashAttribute("alert_info", "Book " + book.getTitle() + " was created");
        return "redirect:" + uriBuilder.path("/book/list").toUriString();
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable long id, Model model, RedirectAttributes redirectAttributes,
                       UriComponentsBuilder uriBuilder) {
        try {
            model.addAttribute("book", bookFacade.findById(id));
        } catch (NoEntityFoundException e ) {
            redirectAttributes.addAttribute("alert_danger", "Book " + id + " was not found");
            return "redirect:" + uriBuilder.path("/book/detail/" + id).toUriString();
        }
        return "book/edit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(@Valid @ModelAttribute("book") BookDTO book,
                       BindingResult br, RedirectAttributes redirectAttributes,
                       UriComponentsBuilder uriBuilder) {
        if (br.hasErrors()) {
            return "book/edit";
        }
        bookFacade.update(book);
        redirectAttributes.addFlashAttribute("alert_info", "Book " + book.getTitle() + " was updated");
        return "redirect:" + uriBuilder.path("/book/detail/" + book.getId()).toUriString();
    }

}
