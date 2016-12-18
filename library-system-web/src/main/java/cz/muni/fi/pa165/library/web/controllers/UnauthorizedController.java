package cz.muni.fi.pa165.library.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Bedrich Said
 */
@Controller
public class UnauthorizedController extends LibraryParentController{
    
    @RequestMapping(value = "/unauthorized", method = RequestMethod.GET)
    public String start(Model model) {
        return "unauthorized";
    }
}
