package cz.muni.fi.pa165.library.facade;

import cz.muni.fi.pa165.library.dto.LoanDTO;
import cz.muni.fi.pa165.library.dto.LoanNewDTO;
import cz.muni.fi.pa165.library.entity.Loan;
import cz.muni.fi.pa165.library.entity.User;
import cz.muni.fi.pa165.library.exception.NoEntityFoundException;
import cz.muni.fi.pa165.library.mapping.BeanMappingService;
import cz.muni.fi.pa165.library.service.BookCopyService;
import cz.muni.fi.pa165.library.service.LoanService;
import cz.muni.fi.pa165.library.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Dávid Lupták
 * @version 15.11.2016
 */
@Service
@Transactional
public class LoanFacadeImpl implements LoanFacade {

    @Inject
    private LoanService loanService;

    @Inject
    private UserService userService;

    @Inject
    private BookCopyService bookCopyService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public Long create(LoanNewDTO loanDTO) {
        if (loanDTO == null) {
            throw new IllegalArgumentException("Loan cannot be null.");
        }

        Loan loan = beanMappingService.mapTo(loanDTO, Loan.class);
        loan.setUser(userService.findById(loanDTO.getUserId()));
        loan.setBookCopy(bookCopyService.findById(loanDTO.getBookCopyId()));

        loanService.create(loan);
        return loan.getId();
    }

    @Override
    public void update(LoanDTO loanDTO) {
        if (loanDTO == null) {
            throw new IllegalArgumentException("Loan cannot be null.");
        }

        Loan loan = beanMappingService.mapTo(loanDTO, Loan.class);

        if (loanService.findById(loan.getId()) == null) {
            throw new NoEntityFoundException("Loan not found during update.");
        }

        loanService.update(loan);
    }

    @Override
    public void delete(Long id) {
        Loan loan = loanService.findById(id);
        if (loan == null) {
            throw new NoEntityFoundException("Loan not found during delete.");
        }

        loanService.delete(loan);
    }

    @Override
    public LoanDTO findById(Long id) {
        Loan loan = loanService.findById(id);
        if (loan == null) {
            throw new NoEntityFoundException("Loan not found during findById.");
        }

        return beanMappingService.mapTo(loan, LoanDTO.class);
    }

    @Override
    public List<LoanDTO> findByUser(Long userId) {
        User user = userService.findById(userId);
        if (user == null) {
            throw new NoEntityFoundException("User not found during findByUser.");
        }

        return beanMappingService.mapTo(loanService.findByUser(user), LoanDTO.class);
    }

    @Override
    public List<LoanDTO> findAll() {
        return beanMappingService.mapTo(loanService.findAll(), LoanDTO.class);
    }
}
