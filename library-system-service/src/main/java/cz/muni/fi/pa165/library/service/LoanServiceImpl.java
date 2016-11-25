package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.dao.LoanDao;
import cz.muni.fi.pa165.library.entity.Loan;
import cz.muni.fi.pa165.library.entity.User;
import cz.muni.fi.pa165.library.exception.LibrarySystemDataAccessException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dávid Lupták
 * @version 15.11.2016
 */
@Service
public class LoanServiceImpl implements LoanService {

    @Inject
    private LoanDao loanDao;

    @Override
    public void create(Loan loan) {
        if (loan == null) {
            throw new IllegalArgumentException("Loan cannot be null.");
        }

        try {
            loanDao.create(loan);
        } catch (IllegalArgumentException ex) {
            throw new LibrarySystemDataAccessException("Error during loan create.", ex);
        }
    }

    @Override
    public void update(Loan loan) {
        if (loan == null) {
            throw new IllegalArgumentException("Loan cannot be null.");
        }

        try {
            loanDao.update(loan);
        } catch (IllegalArgumentException ex) {
            throw new LibrarySystemDataAccessException("Error during loan update.", ex);
        }
    }

    @Override
    public void delete(Loan loan) {
        if (loan == null) {
            throw new IllegalArgumentException("Loan cannot be null.");
        }

        try {
            loanDao.delete(loan);
        } catch (IllegalArgumentException ex) {
            throw new LibrarySystemDataAccessException("Error during loan delete.", ex);
        }
    }

    @Override
    public Loan findById(Long id) {
        try {
            return loanDao.findById(id);
        } catch (IllegalArgumentException ex) {
            throw new LibrarySystemDataAccessException("Error during loan find.", ex);
        }
    }

    @Override
    public List<Loan> findByUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        try {
            return loanDao.findByUser(user);
        } catch (IllegalArgumentException ex) {
            throw new LibrarySystemDataAccessException("Error during loan find by user.", ex);
        }
    }

    @Override
    public List<Loan> findAll() {
        try {
            return loanDao.findAll();
        } catch (IllegalArgumentException ex) {
            throw new LibrarySystemDataAccessException("Error during loan find all.", ex);
        }
    }

    @Override
    public List<Loan> findNotReturnedUserLoans(User user) {
        try {
            return loanDao.findByUser(user).stream()
                    .filter(loan -> !loan.isReturned())
                    .sorted((l1,l2) -> l1.getLoanDate().compareTo(l2.getLoanDate()))
                    .collect(Collectors.toList());

        } catch (IllegalArgumentException ex) {
            throw new LibrarySystemDataAccessException("Error during loan findNotReturnedUserLoans.", ex);
        }
    }

    @Override
    public List<Loan> findReturnedUserLoans(User user) {
        try {
            return loanDao.findByUser(user).stream()
                    .filter(loan -> loan.isReturned())
                    .sorted((l1,l2) -> l1.getLoanDate().compareTo(l2.getLoanDate()))
                    .collect(Collectors.toList());
        }catch (IllegalArgumentException ex) {
            throw new LibrarySystemDataAccessException("Error during loan findNotReturnedUserLoans.", ex);
        }
    }
}
