package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.dao.LoanDao;
import cz.muni.fi.pa165.library.entity.Loan;
import cz.muni.fi.pa165.library.entity.User;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
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
        loanDao.create(loan);

    }

    @Override
    public Loan update(Loan loan) {
        if (loan == null) {
            throw new IllegalArgumentException("Loan cannot be null.");
        }
        return loanDao.update(loan);
    }

    @Override
    public void delete(Loan loan) {
        if (loan == null) {
            throw new IllegalArgumentException("Loan cannot be null.");
        }

        loanDao.delete(loan);
    }

    @Override
    public Loan findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
        return loanDao.findById(id);
    }

    @Override
    public List<Loan> findByUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
        return loanDao.findByUser(user);
    }

    @Override
    public List<Loan> findAll() {
        return loanDao.findAll();

    }

    @Override
    public List<Loan> findNotReturnedUserLoans(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
        return loanDao.findByUser(user).stream()
                .filter(loan -> !loan.isReturned())
                .sorted((l1, l2) -> l1.getLoanDate().compareTo(l2.getLoanDate()))
                .collect(Collectors.toList());


    }

    @Override
    public List<Loan> findReturnedUserLoans(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
        return loanDao.findByUser(user).stream()
                .filter(loan -> loan.isReturned())
                .sorted((l1, l2) -> l1.getLoanDate().compareTo(l2.getLoanDate()))
                .collect(Collectors.toList());
    }
}

