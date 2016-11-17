package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.entity.Loan;
import cz.muni.fi.pa165.library.entity.User;

import java.util.List;

/**
 * DAO contract for the Loan entity.
 *
 * @author Bedrich Said
 */
public interface LoanDao {

    /**
     * Saves the given loan into the database
     *
     * @param loan to be saved
     */
    void create(Loan loan);

    /**
     * Updates the given loan in the database
     *
     * @param loan to be updated
     */
    void update(Loan loan);

    /**
     * Deletes the given loan from the database
     *
     * @param loan to be deleted
     */
    void delete(Loan loan);

    /**
     * Finds a loan with the given id in the database
     *
     * @param id of searched loan
     * @return found loan
     */
    Loan findById(Long id);

    /**
     * Finds loans for the respective user
     *
     * @param user    for whom the loans will be found
     * @return  list of all user's loans
     */
    List<Loan> findByUser(User user);

    /**
     * Finds all loans in the database
     *
     * @return list of all loans
     */
    List<Loan> findAll();
}
