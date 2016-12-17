package cz.muni.fi.pa165.library.web.controllers;

import cz.muni.fi.pa165.library.facade.UserFacade;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Bedrich Said
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Inject
    UserFacade userFacade;

    @RequestMapping(value = {"", "/login", "/login/login"})
    public String init(Model model) {
        return "login/login";
    }
}
