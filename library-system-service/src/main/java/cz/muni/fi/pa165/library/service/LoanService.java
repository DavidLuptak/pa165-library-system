package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.entity.Loan;
import cz.muni.fi.pa165.library.entity.User;

import java.util.List;

/**
 * @author Dávid Lupták
 * @version 15.11.2016
 */
public interface LoanService {
    void create(Loan loan);

    void update(Loan loan);

    void delete(Loan loan);

    Loan findById(Long id);

    List<Loan> findByUser(User user);

    List<Loan> findAll();
}
