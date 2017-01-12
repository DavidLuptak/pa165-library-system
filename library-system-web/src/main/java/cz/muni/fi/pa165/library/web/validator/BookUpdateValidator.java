package cz.muni.fi.pa165.library.web.validator;

import cz.muni.fi.pa165.library.dto.BookUpdateDTO;
import cz.muni.fi.pa165.library.exception.NoEntityFoundException;
import cz.muni.fi.pa165.library.facade.BookFacade;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Validate the ISBN is unique and valid on update.
 *
 * @author Dávid Lupták
 * @version 18.12.2016
 */
@Named
public class BookUpdateValidator implements Validator {

    @Inject
    private BookFacade bookFacade;

    @Override
    public boolean supports(Class<?> aClass) {
        return BookUpdateDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BookUpdateDTO bookUpdateDTO = BookUpdateDTO.class.cast(target);
        String newIsbn = bookUpdateDTO.getIsbn();
        String previousIsbn = bookFacade.findById(bookUpdateDTO.getId()).getIsbn();

        if (previousIsbn.equals(newIsbn)) {
            // isbn is not changed
            return;
        }

        try {
            bookFacade.findByIsbn(newIsbn);
            errors.rejectValue("isbn", "book.isbn_unique");
            // new isbn is not unique
        } catch (NoEntityFoundException | IllegalArgumentException e) {
            // new isbn is unique
        }
    }
}
