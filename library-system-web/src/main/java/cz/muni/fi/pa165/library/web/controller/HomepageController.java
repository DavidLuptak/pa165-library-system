package cz.muni.fi.pa165.library.web.controller;

import cz.muni.fi.pa165.library.facade.BookFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

/**
 * @author Bedrich Said
 */
@Controller
public class HomepageController extends LibraryParentController {

    @Inject
    private BookFacade bookFacade;

    @RequestMapping(value = {"", "/", "/index"})
    public String start(Model model) {
        model.addAttribute("books", bookFacade.findAll().size());
        return "index";
    }
}
