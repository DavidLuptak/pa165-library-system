package cz.muni.fi.pa165.library.persistence.dao;

import cz.muni.fi.pa165.library.persistence.entity.Loan;
import java.util.List;

/**
 *
 * @author Bedrich Said
 */
public interface LoanDao {

    void create(Loan loan);

    void update(Loan loan);

    Loan findById(Long id);

    List<Loan> findAll();

    void delete(Loan loan);
}
