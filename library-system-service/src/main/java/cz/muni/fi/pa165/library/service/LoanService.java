package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.entity.Loan;
import cz.muni.fi.pa165.library.entity.User;
import cz.muni.fi.pa165.library.exception.LibrarySystemDataAccessException;

import java.util.List;

/**
 * Service layer (business logic) for the Loan entity.
 *
 * @author Dávid Lupták
 * @version 15.11.2016
 */
public interface LoanService {

    /**
     * Creates new loan.
     *
     * @param loan to be created
     * @throws IllegalArgumentException         if loan is null
     * @throws LibrarySystemDataAccessException if  some error during persisting an entity occurs
     */
    void create(Loan loan);

    /**
     * Updates existing loan.
     *
     * @param loan to be updated
     * @throws IllegalArgumentException         if loan is null
     * @throws LibrarySystemDataAccessException if  some error during persisting an entity occurs
     */
    void update(Loan loan);

    /**
     * Deletes existing loan.
     *
     * @param loan
     * @throws IllegalArgumentException         if loan is null
     * @throws LibrarySystemDataAccessException if some error during persisting an entity occurs
     */
    void delete(Loan loan);

    /**
     * Returns loan with the respective id.
     *
     * @param id id of loan entity to be found
     * @return loan with the respective id
     * @throws IllegalArgumentException         if id is null
     * @throws LibrarySystemDataAccessException if some data access error occurs
     */
    Loan findById(Long id);

    /**
     * Returns loans for the respective user.
     *
     * @param user user for whom the loans to be found
     * @return loans of the respective user
     * @throws IllegalArgumentException         if user is null
     * @throws LibrarySystemDataAccessException if some data access error occurs
     */
    List<Loan> findByUser(User user);

    /**
     * Returns all loans.
     *
     * @return all loans
     * @throws LibrarySystemDataAccessException if some data access error occurs
     */
    List<Loan> findAll();


    List<Loan> findNotReturnedUserLoans(User user);

    List<Loan> findReturnedUserLoans(User user);

}
