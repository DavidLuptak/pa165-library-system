package cz.muni.fi.pa165.library.web.controllers;

import cz.muni.fi.pa165.library.dto.UserDTO;
import cz.muni.fi.pa165.library.exception.NoEntityFoundException;
import cz.muni.fi.pa165.library.facade.LoanFacade;
import cz.muni.fi.pa165.library.facade.UserFacade;
import cz.muni.fi.pa165.library.web.exceptions.WebSecurityException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import java.util.List;

/**
 *
 * @author Bedrich Said
 */
@Controller
@RequestMapping("/user")
public class UserController extends LibraryParentController{
    @Inject
    private UserFacade userFacade;

    @Inject
    private LoanFacade loanFacade;

    @RequestMapping("/{id}")
    public String showUser(
            @PathVariable long id,
            Model model
    ) {
        UserDTO dto;
        try {
            dto = userFacade.findById(id);
        } catch (IllegalArgumentException | NoEntityFoundException e) {
            return "user/noUser";
        }
        try {
            checkCanManageProfile(id);
        } catch (WebSecurityException e) {
            return "user/noUser";
        }
        model.addAttribute("user", dto);
        return "user/detail";
    }

    // not used
    @RequestMapping(path = "/create", method = RequestMethod.GET)
    public String createUserView(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new UserDTO());
        }
        model.addAttribute("action", "Create");
        return "user/create";
    }

    // not used
    @RequestMapping(path = "/{id}/update", method = RequestMethod.GET)
    public String updateUserView(
            @PathVariable long id,
            Model model
    ) {
        try {
            checkCanManageProfile(id);
        } catch(WebSecurityException e) {
            return "user/noUser";
        }
        model.addAttribute("action", "Update");
        if (!model.containsAttribute("user")) {
            UserDTO userDTO;
            try {
                userDTO = userFacade.findById(id);
            } catch(NoEntityFoundException | IllegalArgumentException e) {
                return "user/noUser";
            }
            model.addAttribute("user", userDTO);
        }
        return "user/update";
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public String listView(Model model) {
        try {
            checkIsAdmin();
        } catch(WebSecurityException e) {
            return "unauthorized";
        }
        List<UserDTO> users = userFacade.findAll();
        model.addAttribute("users", users);
        return "user/index";
    }

    @RequestMapping(path = "/{id}/delete")
    public String deleteUser(
            @PathVariable long id,
            Model model
    ) {
        try {
            checkIsAdmin();
        } catch(WebSecurityException e) {
            return "unauthorized";
        }
        try {
            userFacade.delete(id);
        } catch (NoEntityFoundException | IllegalArgumentException e) {
            return "user/noUser";
        }
        return "user/index";
    }

    @RequestMapping(value = {"", "/", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("users", userFacade.findAll());
        return "user/index";
    }

    @RequestMapping(value = {"/{id}/loans", }, method = RequestMethod.GET)
    public String allLoans(@PathVariable long id, Model model) {
        try {
            model.addAttribute("returned", loanFacade.findReturnedUserLoans(id));
            model.addAttribute("loaned", loanFacade.findNotReturnedUserLoans(id));
            model.addAttribute("user", getLoggedUser());
        } catch (IllegalArgumentException | NoEntityFoundException e) {
            return "user/noUser";
        }
        return "loan/index";
    }
    @RequestMapping(value = {"/detail"}, method = RequestMethod.GET)
    public String detail(Model model) {
        model.addAttribute("user", getLoggedUser());
        return "user/detail";
    }

    /**
     * User can view only his own profile, only administrator can see all profiles
     * @param id of the profile that will be managed
     * @throws WebSecurityException when the current user has no rights to manage the profile
     */
    private void checkCanManageProfile(long id) throws WebSecurityException{
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        long currentUserId = userFacade.findByEmail(auth.getName()).getId();
        if (currentUserId != id && !userFacade.findByEmail(auth.getName()).isAdmin()) {
            throw new WebSecurityException("Non-admin cannot see other acounts.");
        }
    }
    
    private void checkIsAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!userFacade.findByEmail(auth.getName()).isAdmin()) {
            throw new WebSecurityException("Only admin can perform this operation.");
        }
    }
}
