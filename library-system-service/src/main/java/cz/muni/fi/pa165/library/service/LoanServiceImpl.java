package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.dao.BookCopyDao;
import cz.muni.fi.pa165.library.dao.LoanDao;
import cz.muni.fi.pa165.library.entity.BookCopy;
import cz.muni.fi.pa165.library.entity.Loan;
import cz.muni.fi.pa165.library.entity.User;
import cz.muni.fi.pa165.library.exception.LibraryDAOException;
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

    @Inject
    private BookCopyDao bookCopyDao;

    @Override
    public void create(Loan loan) {
        if (loan == null) {
            throw new IllegalArgumentException("Loan cannot be null.");
        }
        if (loan.getReturnBookState() != null){
            throw new IllegalArgumentException("New loans bookState has to be null");
        }
        if (loan.getReturnDate() != null){
            throw new IllegalArgumentException("New loans returnDate has to be null");
        }
        try {
            loanDao.create(loan);
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }

    }

    @Override
    public Loan update(Loan loan) {
        if (loan == null) {
            throw new IllegalArgumentException("Loan cannot be null.");
        }
        try {
            return loanDao.update(loan);
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public void delete(Loan loan) {
        if (loan == null) {
            throw new IllegalArgumentException("Loan cannot be null.");
        }
        try {
            loanDao.delete(loan);
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public Loan findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
        try {
            return loanDao.findById(id);
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public List<Loan> findByUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
        try {
            return loanDao.findByUser(user);
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public List<Loan> findAll() {
        try {
            return loanDao.findAll();
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public List<Loan> findAllReturned() {
        try {
            return loanDao.findAll().stream()
                .filter(loan -> loan.isReturned())
                .sorted((l1, l2) -> l1.getLoanDate().compareTo(l2.getLoanDate()))
                .collect(Collectors.toList());
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public List<Loan> findAllNotReturned() {
        try {
            return loanDao.findAll().stream()
                .filter(loan -> !loan.isReturned())
                .sorted((l1, l2) -> l1.getLoanDate().compareTo(l2.getLoanDate()))
                .collect(Collectors.toList());
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public List<Loan> findNotReturnedUserLoans(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
        try {
            return loanDao.findByUser(user).stream()
                    .filter(loan -> !loan.isReturned())
                    .sorted((l1, l2) -> l1.getLoanDate().compareTo(l2.getLoanDate()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public List<Loan> findReturnedUserLoans(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
        try {
            return loanDao.findByUser(user).stream()
                    .filter(loan -> loan.isReturned())
                    .sorted((l1, l2) -> l1.getLoanDate().compareTo(l2.getLoanDate()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public void returnLoan(Loan loan) {
        if(loan == null){
            throw new IllegalArgumentException("loan is null");
        }
        if(loan.getReturnDate() == null){
            throw new IllegalArgumentException("returnDate of loan has to be set");
        }
        if(loan.getReturnBookState() == null){
            throw new IllegalArgumentException("bookState of loan has to be set");
        }
        if(loan.getLoanDate().isAfter(loan.getReturnDate())){
            throw new IllegalArgumentException("returnDate of loan has to be after loanDate");
        }
        if(loan.getReturnBookState().isLighter(loan.getBookCopy().getBookState())){
            throw new IllegalArgumentException("returningBookState cannot be lighter than loaningBookState");
        }
        update(loan);
        BookCopy bookCopy = loan.getBookCopy();
        bookCopy.setBookState(loan.getReturnBookState());
        bookCopyDao.update(bookCopy);
    }
}

