package cz.muni.fi.pa165.library.web.controllers;

import cz.muni.fi.pa165.library.facade.BookFacade;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Bedrich Said
 */
@Controller
@RequestMapping("/book/copy")
public class BookCopyController {
    @Inject
    private BookFacade bookFacade;

    @RequestMapping(value = {"", "/{id}", "/index/{id}", "/show/{id}"}, method = RequestMethod.GET)
    public String index(@PathVariable long id, Model model) {
        model.addAttribute("bookCopies", bookFacade.findById(id).getBookCopies());
        return "book/copy/show";
    }
}
