package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.entity.Loan;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 *
 * @author Bedrich Said
 */
@Repository
public class LoanDaoImpl implements LoanDao {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void create(Loan loan) {
        em.persist(loan);
    }

    @Override
    public void delete(Loan loan) {
        em.remove(loan);
    }

    @Override
    public void update(Loan loan) {
        em.merge(loan);
    }

    @Override
    public Loan findById(Long id) {
        return em.find(Loan.class, id);
    }

    @Override
    public List<Loan> findAll() {
        return em.createQuery("from Loan", Loan.class).getResultList();
    }
}
