package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.entity.Loan;
import cz.muni.fi.pa165.library.entity.User;

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
     * @throws IllegalArgumentException if loan is null
     * @throws IllegalArgumentException if loan has set returnDate
     * @throws IllegalArgumentException if loan has set bookState
     */
    void create(Loan loan);

    /**
     * Updates existing loan.
     *
     * @param loan to be updated
     * @return updated Loan entity
     * @throws IllegalArgumentException if loan is null
     */
    Loan update(Loan loan);

    /**
     * Deletes existing loan.
     *
     * @param loan to be deleted
     * @throws IllegalArgumentException if loan is null
     */
    void delete(Loan loan);

    /**
     * Returns loan with the respective id.
     *
     * @param id id of loan entity to be found
     * @return loan with the respective id
     * @throws IllegalArgumentException if id is null
     */
    Loan findById(Long id);

    /**
     * Returns loans for the respective user.
     *
     * @param user user for whom the loans to be found
     * @return loans of the respective user
     * @throws IllegalArgumentException if user is null
     */
    List<Loan> findByUser(User user);

    /**
     * Returns all loans.
     *
     * @return all loans
     */
    List<Loan> findAll();

    List<Loan> findAllReturned();

    List<Loan> findAllNotReturned();


    /**
     * Returns user's loans which are not returned
     *
     * @param user for whom the loans will be found
     * @return user's loans which are not returned
     */
    List<Loan> findNotReturnedUserLoans(User user);

    /**
     * Returns user's loans which are returned
     *
     * @param user for whom the loans will be found
     * @return user's loans which are returned
     */
    List<Loan> findReturnedUserLoans(User user);

    /**
     * Returns user's loan
     *
     * @param loan loan to be returned
     */
    void ret(Loan loan);

}
