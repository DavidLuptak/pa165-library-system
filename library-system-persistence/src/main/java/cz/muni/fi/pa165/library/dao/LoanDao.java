package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.entity.Loan;
import java.util.List;

/**
 * Data access object interface for Load entity.
 *
 * @author Bedrich Said
 */
public interface LoanDao {

    /**
     * Add new loan into the database.
     * 
     * @param loan to be created
     */
    void create(Loan loan);
    
    /**
     * Remove given loan from the database.
     * (The book was returned.)
     * 
     * @param loan 
     */
    void delete(Loan loan);
    
    /**
     * Edit the given loan in the database.
     * 
     * @param loan
     */
    void update(Loan loan);

    /**
     * Finds a loan with the given id in the database.
     * 
     * @param id of the loan
     * @return 
     */
    Loan findById(Long id);

    /**
     * Get a list of all loans in the database.
     * 
     * @return list of all loans
     */
    List<Loan> findAll();
}
