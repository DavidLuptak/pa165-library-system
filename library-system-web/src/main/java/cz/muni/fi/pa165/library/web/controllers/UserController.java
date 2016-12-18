package cz.muni.fi.pa165.library.web.controllers;

import cz.muni.fi.pa165.library.dto.LoanDTO;
import cz.muni.fi.pa165.library.dto.LoanNewDTO;
import cz.muni.fi.pa165.library.dto.UserDTO;
import cz.muni.fi.pa165.library.facade.BookFacade;
import cz.muni.fi.pa165.library.facade.LoanFacade;
import cz.muni.fi.pa165.library.facade.UserFacade;
import cz.muni.fi.pa165.library.web.adapters.UserDetailsAdapter;
import cz.muni.fi.pa165.library.web.exceptions.WebSecurityException;
import java.util.List;
import javassist.NotFoundException;
import javax.inject.Inject;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
@RequestMapping("/user")
public class UserController extends LibraryParentController{
    @Inject
    private UserFacade userFacade;

    @Inject
    private LoanFacade loanFacade;

    @Inject
    private BookFacade bookFacade;

    @RequestMapping("/{id}")
    public String showUser(
            @AuthenticationPrincipal UserDetailsAdapter currentUser,
            @PathVariable long id,
            Model model
    ) throws NotFoundException {
        checkCanManageProfile(currentUser, id);
        UserDTO dto = userFacade.findById(id);
        if (dto == null) {
            throw new NotFoundException("User with the given ID not found.");
        }
        List<LoanDTO> allLoans = userFacade.getAllUserLoans(dto.getId());
        model.addAttribute("user", dto);
        model.addAttribute("loans", allLoans);
        return "user/show";
    }

    @RequestMapping(path = "/create", method = RequestMethod.GET)
    public String createUserView(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new UserDTO());
        }
        model.addAttribute("action", "Create");
        return "user/create";
    }

    //todo: Do we need to support a password update?
    @RequestMapping(path = "/{id}/update", method = RequestMethod.GET)
    public String updateUserView(
            @AuthenticationPrincipal UserDetailsAdapter currentUser,
            @PathVariable long id,
            Model model
    ) {
        checkCanManageProfile(currentUser, id);
        model.addAttribute("action", "Update");
        if (!model.containsAttribute("user")) {
            UserDTO userDTO = userFacade.findById(id);
            model.addAttribute("user", userDTO);
        }
        return "user/update";
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public String listView(@AuthenticationPrincipal UserDetailsAdapter currentUser, Model model) {
        checkIsAdmin(currentUser);
        List<UserDTO> users = userFacade.findAll();
        model.addAttribute("users", users);
        return "user/list";
    }

    @RequestMapping(path = "/{id}/delete")
    public String deleteUser(
            @AuthenticationPrincipal UserDetailsAdapter currentUser,
            @PathVariable long id,
            Model model
    ) {
        checkCanManageProfile(currentUser, id);
        userFacade.delete(id);
        return "redirect:/";
    }

    @RequestMapping(value = {"", "/", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("users", userFacade.findAll());
        return "user/index";
    }

    @RequestMapping(value = {"/{id}/loans", }, method = RequestMethod.GET)
    public String allLoans(@PathVariable long id, Model model) {
        model.addAttribute("returned", loanFacade.findReturnedUserLoans(id));
        model.addAttribute("loaned", loanFacade.findNotReturnedUserLoans(id));
        model.addAttribute("user", getLoggedUser());
        return "loan/index";
    }
    @RequestMapping(value = {"/detail"}, method = RequestMethod.GET)
    public String detail(Model model) {
        model.addAttribute("user", getLoggedUser());
        return "user/detail";
    }

    /**
     * User can view only his own profile, only administrator can see all profiles
     * @param currentUser currently logged user
     * @param id of the profile that will be managed
     * @throws WebSecurityException when the current user has no rights to manage the profile
     */
    private void checkCanManageProfile(UserDetailsAdapter currentUser, long id) throws WebSecurityException{
        if (!(currentUser.getDto().getId() == id || currentUser.getDto().isAdmin())) {
            throw new WebSecurityException("Non-admin cannot see other acounts.");
        }
    }
    
    private void checkIsAdmin(UserDetailsAdapter currentUser) {
        if (!currentUser.getDto().isAdmin()) {
            throw new WebSecurityException("Only admin can perform this operation.");
        }
    }
}