package cz.muni.fi.pa165.library.web.controllers;

import cz.muni.fi.pa165.library.dto.UserDTO;
import cz.muni.fi.pa165.library.facade.UserFacade;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.inject.Inject;

/**
 * Parent controller for all application controllers
 */
public class LibraryParentController {

    @Inject
    private UserFacade userFacade;

    @ModelAttribute("loggedUser")
    protected UserDTO getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        String email = authentication.getName();

        if (email == null || email.equals("anonymousUser")) {
            return null;
        }

        UserDTO user = userFacade.findByEmail(email);
        return user;
    }

    protected void addValidationErrors(BindingResult bindingResult, Model model) {
        for (FieldError fe : bindingResult.getFieldErrors()) {
            model.addAttribute(fe.getField() + "_error", true);
        }
    }

}