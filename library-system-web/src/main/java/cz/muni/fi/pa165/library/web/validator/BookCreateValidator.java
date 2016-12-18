package cz.muni.fi.pa165.library.web.validator;

import cz.muni.fi.pa165.library.dto.BookNewDTO;
import cz.muni.fi.pa165.library.exception.NoEntityFoundException;
import cz.muni.fi.pa165.library.facade.BookFacade;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Validate the ISBN is unique on create.
 *
 * @author Dávid Lupták
 * @version 18.12.2016
 */
@Named
public class BookCreateValidator implements Validator {

    @Inject
    private BookFacade bookFacade;

    @Override
    public boolean supports(Class<?> aClass) {
        return BookNewDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BookNewDTO bookNewDTO = BookNewDTO.class.cast(target);

        try {
            bookFacade.findByIsbn(bookNewDTO.getIsbn());
            errors.rejectValue("isbn", "book.isbn_unique");
            // new isbn is not unique
        } catch (NoEntityFoundException | IllegalArgumentException e) {
            // new isbn is unique
        }
    }
}
