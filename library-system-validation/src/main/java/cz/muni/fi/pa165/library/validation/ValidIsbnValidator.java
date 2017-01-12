package cz.muni.fi.pa165.library.validation;

import org.apache.commons.validator.routines.ISBNValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Dávid Lupták
 * @version 12.1.2017
 */
public class ValidIsbnValidator implements ConstraintValidator<ValidIsbn, String> {

    @Override
    public void initialize(ValidIsbn constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        ISBNValidator isbnValidator = new ISBNValidator();
        return isbnValidator.isValid(value);
    }
}
