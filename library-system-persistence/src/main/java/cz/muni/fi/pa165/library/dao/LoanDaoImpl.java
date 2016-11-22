package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.entity.Loan;
import cz.muni.fi.pa165.library.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Implementation of the DAO contract for the Loan entity.
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
        em.remove(findById(loan.getId()));
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
    public List<Loan> findByUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is not valid.");
        }
        return em.createQuery("SELECT loan FROM Loan loan WHERE loan.user = :user", Loan.class).setParameter("user", user).getResultList();
    }

    @Override
    public List<Loan> findAll() {
        return em.createQuery("from Loan", Loan.class).getResultList();
    }
}
