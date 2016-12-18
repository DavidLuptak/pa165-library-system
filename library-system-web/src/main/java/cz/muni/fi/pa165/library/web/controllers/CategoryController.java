package cz.muni.fi.pa165.library.web.controllers;

import cz.muni.fi.pa165.library.dto.BookDTO;
import cz.muni.fi.pa165.library.dto.CategoryDTO;
import cz.muni.fi.pa165.library.dto.CategoryNewDTO;
import cz.muni.fi.pa165.library.entity.Category;
import cz.muni.fi.pa165.library.exception.NoEntityFoundException;
import cz.muni.fi.pa165.library.facade.CategoryFacade;
import cz.muni.fi.pa165.library.web.validator.CategoryCreateValidator;
import cz.muni.fi.pa165.library.web.validator.CategoryUpdateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Spring MVC controller for the {@link Category} entity.
 *
 * @author Dávid Lupták
 * @version 12.12.2016
 */
@Controller
@RequestMapping("/category")
public class CategoryController extends LibraryParentController {

    final static Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

    @Inject
    private CategoryFacade categoryFacade;

    @Inject
    private CategoryCreateValidator categoryCreateValidator;

    @Inject
    private CategoryUpdateValidator categoryUpdateValidator;

    /**
     * Show a list of categories or just one category specified by name.
     *
     * @param name optional argument to filter category of the desired name
     * @return JSP page category/index
     */
    @RequestMapping(value = {"", "/", "/index"}, method = RequestMethod.GET)
    public String index(Model model,
                        @RequestParam(value = "name", required = false) String name,
                        RedirectAttributes redirectAttributes,
                        UriComponentsBuilder uriComponentsBuilder) {
        LOGGER.debug("category index");

        List<CategoryDTO> categories = new ArrayList<>();

        if (name == null || name.isEmpty()) {
            categories = categoryFacade.findAll();
        } else {
            try {
                categories.add(categoryFacade.findByName(name));
            } catch (NoEntityFoundException | IllegalArgumentException ex) {
                LOGGER.debug("Category of name {} not found.", name);

                redirectAttributes.addFlashAttribute("alert_warning", "Category of name " + name + " not found.");
                return "redirect:" + uriComponentsBuilder.path("/category").toUriString();
            }
        }

        model.addAttribute("categories", categories);
        return "category/index";
    }

    /**
     * Show a detail of the category.
     *
     * @param id category identifier
     * @return JSP page category/detail/{id}
     */
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id,
                         Model model,
                         RedirectAttributes redirectAttributes,
                         UriComponentsBuilder uriComponentsBuilder) {
        LOGGER.debug("category {} detail", id);

        try {
            model.addAttribute("category", categoryFacade.findById(id));
        } catch (NoEntityFoundException | IllegalArgumentException ex) {
            LOGGER.debug("Category of id {} not found.", id);

            redirectAttributes.addFlashAttribute("alert_warning", "Category not found.");
            return "redirect:" + uriComponentsBuilder.path("/category").toUriString();
        }

        return "category/detail";
    }

    /**
     * Show form for creating a new category.
     *
     * @return JSP page category/create
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        LOGGER.debug("category create GET");

        model.addAttribute("category", new CategoryNewDTO());
        return "category/create";
    }

    /**
     * Create a new category by POST method.
     *
     * @param categoryNewDTO category DTO object to be created
     * @return JSP page category/detail/{id}
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("category") CategoryNewDTO categoryNewDTO,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes,
                         UriComponentsBuilder uriComponentsBuilder) {
        LOGGER.debug("category {} create POST", categoryNewDTO);

        if (bindingResult.hasErrors()) {
            addValidationErrors(bindingResult, model);
            return "category/create";
        }

        Long id = categoryFacade.create(categoryNewDTO);

        redirectAttributes.addFlashAttribute("alert_success", "Category " + categoryNewDTO.getName() + " created.");

        return "redirect:" + uriComponentsBuilder.path("/category").buildAndExpand(id).toUriString();
    }

    /**
     * Show form for updating the category.
     *
     * @param id category identifier
     * @return JSP page category/edit
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable long id,
                       Model model,
                       RedirectAttributes redirectAttributes,
                       UriComponentsBuilder uriComponentsBuilder) {
        LOGGER.debug("category {} edit GET", id);

        try {
            model.addAttribute("category", categoryFacade.findById(id));
            return "category/edit";
        } catch (NoEntityFoundException | IllegalArgumentException e) {
            LOGGER.debug("Category of id {} not found.", id);

            redirectAttributes.addFlashAttribute("alert_warning", "Category not found.");
            return "redirect:" + uriComponentsBuilder.path("/category").toUriString();
        }
    }

    /**
     * Update the category by POST method.
     *
     * @param categoryDTO category DTO object to be updated
     * @param id          category identifier
     * @return JSP page category/detail/{id}
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String edit(@Valid @ModelAttribute("category") CategoryDTO categoryDTO,
                       BindingResult bindingResult,
                       @PathVariable long id,
                       Model model,
                       RedirectAttributes redirectAttributes,
                       UriComponentsBuilder uriComponentsBuilder) {
        LOGGER.debug("category edit POST of {}", categoryDTO);

        List<BookDTO> bookDTOS = categoryFacade.findById(id).getBooks();
        bookDTOS.forEach(categoryDTO::addBook);

        if (bindingResult.hasErrors()) {
            addValidationErrors(bindingResult, model);
            return "category/edit";
        }

        categoryFacade.update(categoryDTO);

        redirectAttributes.addFlashAttribute("alert_success", "Category " + categoryDTO.getName() + " updated.");

        return "redirect:" + uriComponentsBuilder.path("/category/detail/{id}").buildAndExpand(id).toUriString();
    }

    /**
     * Delete the category by POST method.
     *
     * @param id category identifier
     * @return JSP page category/index
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable long id,
                         RedirectAttributes redirectAttributes,
                         UriComponentsBuilder uriComponentsBuilder) {
        LOGGER.debug("category {} delete GET", id);

        try {
            categoryFacade.delete(id);
            redirectAttributes.addFlashAttribute("alert_success", "Category deleted.");
        } catch (NoEntityFoundException | IllegalArgumentException ex) {
            LOGGER.debug("Category of id {} not found.", id);

            redirectAttributes.addFlashAttribute("alert_danger", "Category cannot be deleted.");
        }

        return "redirect:" + uriComponentsBuilder.path("/category/index").toUriString();

    }

    @InitBinder
    protected void initUniqueConstraintBinder(WebDataBinder binder) {

        if (binder.getTarget() instanceof CategoryNewDTO) {
            binder.addValidators(categoryCreateValidator);
        }

        if (binder.getTarget() instanceof CategoryDTO) {
            binder.addValidators(categoryUpdateValidator);
        }
    }

}
