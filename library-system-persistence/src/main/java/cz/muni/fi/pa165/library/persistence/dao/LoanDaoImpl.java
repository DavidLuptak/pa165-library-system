package cz.muni.fi.pa165.library.persistence.dao;

import cz.muni.fi.pa165.library.persistence.entity.Loan;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Bedrich Said
 */
public class LoanDaoImpl implements LoanDao {
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void create(Loan loan) {
        em.persist(loan);
    }

    @Override
    public void delete(Loan loan) {
        //todo
        //em.remove(findById(loan.getId()));
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
