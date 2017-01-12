package cz.muni.fi.pa165.library.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Validation of the ISBN -- can be either ISBN-10 or ISBN-13 format.
 *
 * @author Dávid Lupták
 * @version 12.1.2017
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidIsbnValidator.class)
@Documented
public @interface ValidIsbn {
    String message() default "ISBN validation check failed.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] members() default {};
}
