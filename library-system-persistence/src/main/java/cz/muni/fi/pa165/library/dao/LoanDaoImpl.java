package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.entity.Loan;
import cz.muni.fi.pa165.library.entity.User;
import cz.muni.fi.pa165.library.exceptions.LibraryDAOException;
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
        try {
            em.persist(loan);
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void delete(Loan loan) {
        try {
            em.remove(findById(loan.getId()));
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Loan update(Loan loan) {
        try {
            return em.merge(loan);
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Loan findById(Long id) {
        try {
            return em.find(Loan.class, id);
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<Loan> findByUser(User user) {
        try {
            if (user == null) {
                throw new IllegalArgumentException("User is not valid.");
            }
            return em.createQuery("SELECT loan FROM Loan loan WHERE loan.user = :user", Loan.class).setParameter("user", user).getResultList();
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<Loan> findAll() {
        try {
            return em.createQuery("from Loan", Loan.class).getResultList();
        } catch (Exception e) {
            throw new LibraryDAOException(e.getMessage(), e.getCause());
        }
    }
}
