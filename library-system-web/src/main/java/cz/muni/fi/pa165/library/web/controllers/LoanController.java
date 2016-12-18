package cz.muni.fi.pa165.library.web.controllers;

import cz.muni.fi.pa165.library.dto.LoanDTO;
import cz.muni.fi.pa165.library.dto.LoanNewDTO;
import cz.muni.fi.pa165.library.dto.UserDTO;
import cz.muni.fi.pa165.library.enums.BookState;
import cz.muni.fi.pa165.library.enums.UserRole;
import cz.muni.fi.pa165.library.exception.NoEntityFoundException;
import cz.muni.fi.pa165.library.facade.BookFacade;
import cz.muni.fi.pa165.library.facade.LoanFacade;
import cz.muni.fi.pa165.library.facade.UserFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Martin
 * @version 14.12.2016
 */

@Controller
@RequestMapping("/loan")
public class LoanController extends LibraryParentController{

    final static Logger log = LoggerFactory.getLogger(LoanController.class);

    @Inject
    private LoanFacade loanFacade;

    @Inject
    private UserFacade userFacade;

    @Inject
    private BookFacade bookFacade;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        UserDTO loggedUser = getLoggedUser();
        LoanNewDTO loan = new LoanNewDTO(loggedUser.getId(), null, LocalDateTime.now());
        model.addAttribute("loan", loan);
        model.addAttribute("books", bookFacade.findLoanableBooks());
        return "loan/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute LoanNewDTO loan, BindingResult br,
                         RedirectAttributes redirectAttributes,
                         UriComponentsBuilder uriBuilder) {
        if (br.hasErrors()) {

            return "loan/create";
        }
        else {
            try {
                loanFacade.create(loan);
                redirectAttributes.addFlashAttribute("alert_info", "Loan was successfully created.");
            }
            catch (NoEntityFoundException | IllegalArgumentException e) {
                redirectAttributes.addFlashAttribute("alert_danger", "Loan could not be created.");
            }

            return "redirect:" + uriBuilder.path("/loan").toUriString();
        }
    }

    @RequestMapping(value = {"", "/", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {
        UserDTO loggedUser = getLoggedUser();
        model.addAttribute("user", loggedUser);
        if (loggedUser.getUserRole() == UserRole.ADMIN) {
            model.addAttribute("returned", loanFacade.findAllReturned());
            model.addAttribute("loaned", loanFacade.findAllNotReturned());
        }
        else{
            model.addAttribute("returned", loanFacade.findReturnedUserLoans(loggedUser.getId()));
            model.addAttribute("loaned", loanFacade.findNotReturnedUserLoans(loggedUser.getId()));
        }
        return "loan/index";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model, RedirectAttributes redirectAttributes,
                         UriComponentsBuilder uriBuilder) {
        try {
            model.addAttribute("loan", loanFacade.findById(id));
        } catch (NoEntityFoundException e) {
            redirectAttributes.addFlashAttribute("alert_danger", "Loan " + id + " was not found");
            return "redirect:" + uriBuilder.path("/loan").toUriString();
        }
        return "loan/detail";
    }


    @RequestMapping(value = "/return/{id}", method = RequestMethod.GET)
    public String ret(@PathVariable long id, Model model, RedirectAttributes redirectAttributes,
                      UriComponentsBuilder uriBuilder) {
        try {
            model.addAttribute("loan", loanFacade.findById(id));
        } catch (NoEntityFoundException | IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("alert_danger", "Loan was not found");
            return "redirect:" + uriBuilder.path("/loan/detail/" + id).toUriString();
        }
        return "loan/return";
    }

    @RequestMapping(value = "/return", method = RequestMethod.POST)
    public String ret(@Valid @ModelAttribute("loan") LoanDTO loan,
                      BindingResult br, RedirectAttributes redirectAttributes,
                      UriComponentsBuilder uriBuilder) {
        if (br.hasErrors()) {
            return "loan/return";
        }
        loanFacade.returnLoan(loan);
        redirectAttributes.addFlashAttribute("alert_info", "Loan was updated");
        return "redirect:" + uriBuilder.path("/loan/detail/" + loan.getId()).toUriString();
    }

    @ModelAttribute("bookStates")
    public List<BookState> bookStates() {
        log.debug("all book states");
        return BookState.all();
    }
}
