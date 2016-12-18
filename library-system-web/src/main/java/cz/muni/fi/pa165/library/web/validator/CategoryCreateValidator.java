package cz.muni.fi.pa165.library.web.validator;

import cz.muni.fi.pa165.library.dto.CategoryNewDTO;
import cz.muni.fi.pa165.library.exception.NoEntityFoundException;
import cz.muni.fi.pa165.library.facade.CategoryFacade;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Validate the name is unique on create.
 *
 * @author Dávid Lupták
 * @version 17.12.2016
 */
@Named
public class CategoryCreateValidator implements Validator {

    @Inject
    private CategoryFacade categoryFacade;

    @Override
    public boolean supports(Class<?> aClass) {
        return CategoryNewDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CategoryNewDTO categoryNewDTO = CategoryNewDTO.class.cast(target);

        try {
            categoryFacade.findByName(categoryNewDTO.getName());
            errors.rejectValue("name", "category.name_unique");
            // new name is not unique
        } catch (NoEntityFoundException | IllegalArgumentException e) {
            // new name is unique
        }
    }
}
