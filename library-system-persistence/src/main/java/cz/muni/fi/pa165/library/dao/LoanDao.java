package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.entity.Loan;
import java.util.List;

/**
 *
 * @author Bedrich Said
 */
public interface LoanDao {
    //TODO: documentation
    void create(Loan loan);
    
    void delete(Loan loan);
    
    void update(Loan loan);

    Loan findById(Long id);

    List<Loan> findAll();
}
