package cz.muni.fi.pa165.library.web.validator;

import cz.muni.fi.pa165.library.dto.LoanDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.inject.Named;

/**
 * @author Martin
 * @version 18.12.2016
 */

@Named
public class LoanReturnDateValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return LoanDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LoanDTO loanDTO= LoanDTO.class.cast(target);

        if (loanDTO.getReturnDate() != null && loanDTO.getReturnDate().isAfter(loanDTO.getLoanDate())) return;

        errors.rejectValue("returnDate", "loan.returnDate_invalid");
    }
}
