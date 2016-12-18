package cz.muni.fi.pa165.library.web.controllers;

import cz.muni.fi.pa165.library.facade.UserFacade;
import javax.inject.Inject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Bedrich Said
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Inject
    UserFacade userFacade;

    @RequestMapping(value = {"", "/"})
    public String init(Model model) {
        return "/login/login";
    }
    
    @RequestMapping(value = {"/validate"}, method = RequestMethod.POST)
    public String validate(Model model) {
        return "/login/validate";
    }
    
    @RequestMapping(value = {"/logout"})
    public String logout(Model model) {
        return "/login/logout";
    }
}
