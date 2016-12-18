package cz.muni.fi.pa165.library.facade;

import cz.muni.fi.pa165.library.dto.LoanDTO;
import cz.muni.fi.pa165.library.dto.LoanNewDTO;
import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.BookCopy;
import cz.muni.fi.pa165.library.entity.Loan;
import cz.muni.fi.pa165.library.entity.User;
import cz.muni.fi.pa165.library.exception.NoEntityFoundException;
import cz.muni.fi.pa165.library.mapping.BeanMappingService;
import cz.muni.fi.pa165.library.service.BookCopyService;
import cz.muni.fi.pa165.library.service.BookService;
import cz.muni.fi.pa165.library.service.LoanService;
import cz.muni.fi.pa165.library.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
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
    private BookService bookService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public List<Long> create(LoanNewDTO loanNewDTO) {
        if (loanNewDTO == null) {
            throw new IllegalArgumentException("Loan cannot be null.");
        }

        List<Long> loans = new ArrayList<>();
        User user = checkNullAndExistenceOfUser(loanNewDTO.getUserId());

        for (Long id : loanNewDTO.getBookIds()) {
            Loan loan = beanMappingService.mapTo(loanNewDTO, Loan.class);
            loan.setUser(user);
            loan.setBookCopy(checkNullAndExistenceOfBookCopy(id));

            loanService.create(loan);
            loans.add(loan.getId());
        }

        return loans;
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
        if (id == null) {
            throw new IllegalArgumentException("Loan id cannot be null.");
        }
        Loan loan = loanService.findById(id);
        if (loan == null) {
            throw new NoEntityFoundException("Loan not found during delete.");
        }

        loanService.delete(loan);
    }

    @Override
    public LoanDTO findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Loan id cannot be null.");
        }
        Loan loan = loanService.findById(id);
        if (loan == null) {
            throw new NoEntityFoundException("Loan not found during findById.");
        }

        return beanMappingService.mapTo(loan, LoanDTO.class);
    }

    @Override
    public List<LoanDTO> findByUser(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User id cannot be null.");
        }
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

    @Override
    public List<LoanDTO> findAllReturned() {
        return beanMappingService.mapTo(loanService.findAllReturned(), LoanDTO.class);
    }

    @Override
    public List<LoanDTO> findAllNotReturned() {
        return beanMappingService.mapTo(loanService.findAllNotReturned(), LoanDTO.class);
    }

    @Override
    public List<LoanDTO> findNotReturnedUserLoans(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User id cannot be null.");
        }
        User user = userService.findById(userId);
        if (user == null) {
            throw new NoEntityFoundException("User not found during findByUser.");
        }

        return beanMappingService.mapTo(loanService.findNotReturnedUserLoans(user), LoanDTO.class);
    }

    @Override
    public List<LoanDTO> findReturnedUserLoans(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User id cannot be null.");
        }
        User user = userService.findById(userId);
        if (user == null) {
            throw new NoEntityFoundException("User not found during findByUser.");
        }

        return beanMappingService.mapTo(loanService.findReturnedUserLoans(user), LoanDTO.class);
    }

    @Override
    public void returnLoan(LoanDTO loanDTO) {
        if (loanDTO == null) {
            throw new IllegalArgumentException("Loan cannot be null.");
        }

        Loan loan = beanMappingService.mapTo(loanDTO, Loan.class);

        if (loanService.findById(loan.getId()) == null) {
            throw new NoEntityFoundException("Loan not found during return.");
        }

        loanService.returnLoan(loan);
    }

    /**
     * Argument checking for the User entity.
     *
     * @param id id of user as an argument
     * @return existing user
     * @throws IllegalArgumentException if user id is null
     * @throws NoEntityFoundException   if user does not exist
     */
    private User checkNullAndExistenceOfUser(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("User id cannot be null.");
        }
        User user = userService.findById(id);
        if (user == null) {
            throw new NoEntityFoundException("User cannot be found.");
        }
        return user;
    }

    private BookCopy checkNullAndExistenceOfBookCopy(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Book id cannot be null.");
        }
        Book book = bookService.findById(id);
        BookCopy bookCopy = bookCopyService.findLoanableByBook(book);
        if (bookCopy == null) {
            throw new NoEntityFoundException("BookCopy cannot be found.");
        }
        return bookCopy;
    }
}
