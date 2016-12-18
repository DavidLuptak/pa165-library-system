package cz.muni.fi.pa165.library.web.validator;

import cz.muni.fi.pa165.library.dto.CategoryDTO;
import cz.muni.fi.pa165.library.exception.NoEntityFoundException;
import cz.muni.fi.pa165.library.facade.CategoryFacade;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Validate the name is unique on update.
 *
 * @author Dávid Lupták
 * @version 17.12.2016
 */
@Named
public class CategoryUpdateValidator implements Validator {

    @Inject
    private CategoryFacade categoryFacade;

    @Override
    public boolean supports(Class<?> aClass) {
        return CategoryDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CategoryDTO categoryDTO = CategoryDTO.class.cast(target);
        String newName = categoryDTO.getName();
        String previousName = categoryFacade.findById(categoryDTO.getId()).getName();

        if (previousName.equals(newName)) {
            // name is not changed
            return;
        }

        try {
            categoryFacade.findByName(newName);
            errors.rejectValue("name", "category.name_unique");
            // new name is not unique
        } catch (NoEntityFoundException | IllegalArgumentException e) {
            // new name is unique
        }
    }
}
