package cz.muni.fi.pa165.library.web.controllers;

import cz.muni.fi.pa165.library.facade.BookFacade;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Bedrich Said
 */
@Controller
public class HomepageController extends LibraryParentController{
    
    @Inject
    private BookFacade bookFacade;
    
    @RequestMapping(value = {"", "/", "/index"})
    public String start(Model model) {
        model.addAttribute("books", bookFacade.findAll().size());
        return "index";
    }
}
