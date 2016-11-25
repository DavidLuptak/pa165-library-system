package cz.muni.fi.pa165.library.facade;

import cz.muni.fi.pa165.library.dto.LoanDTO;
import cz.muni.fi.pa165.library.dto.LoanNewDTO;
import cz.muni.fi.pa165.library.exception.NoEntityFoundException;

import java.util.List;

/**
 * Facade layer for the Loan entity.
 *
 * @author Dávid Lupták
 * @version 15.11.2016
 */
public interface LoanFacade {

    /**
     * Creates new loan. When multiple bookCopies are assigned for a single loan,
     * multiple loans are created.
     *
     * @param loanNewDTO entity to be created
     * @return list of created loans
     * @throws IllegalArgumentException if loanNewDTO is null
     * @throws IllegalArgumentException if user or bookCopy is null
     * @throws NoEntityFoundException   if user or bookCopy does not exist
     */
    List<Long> create(LoanNewDTO loanNewDTO);

    /**
     * Updates existing loan.
     *
     * @param loanDTO entity to be updated
     * @throws IllegalArgumentException if loanDTO is null
     * @throws NoEntityFoundException   if loan does not exist
     */
    void update(LoanDTO loanDTO);

    /**
     * Deletes existing loan.
     *
     * @param id id of entity to be deleted
     * @throws IllegalArgumentException if id is null
     * @throws NoEntityFoundException   if loan does not exist
     */
    void delete(Long id);

    /**
     * Returns loan with the respective id.
     *
     * @param id id of loan entity to be found
     * @return loan with the respective id
     * @throws IllegalArgumentException if id is null
     * @throws NoEntityFoundException   if loan with the respective id does not exist
     */
    LoanDTO findById(Long id);

    /**
     * Returns loans for the respective user.
     *
     * @param userId id of user for whom the loans to be found
     * @return loans of the respective user
     * @throws IllegalArgumentException if user id is null
     * @throws NoEntityFoundException   if user does not exist
     */
    List<LoanDTO> findByUser(Long userId);

    /**
     * Returns all loans.
     *
     * @return all loans
     */
    List<LoanDTO> findAll();

    /**
     * Returns user's loans which are not returned
     *
     * @param userId id of user for whom the loans to be found
     * @return user's loans which are not returned
     */
    List<LoanDTO> findNotReturnedUserLoans(Long userId);

    /**
     * Returns user's loans which are returned
     *
     * @param userId id of user for whom the loans to be found
     * @return user's loans which are returned
     */
    List<LoanDTO> findReturnedUserLoans(Long userId);

}
