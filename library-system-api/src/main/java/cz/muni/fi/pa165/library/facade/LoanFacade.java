package cz.muni.fi.pa165.library.facade;

import cz.muni.fi.pa165.library.dto.LoanDTO;
import cz.muni.fi.pa165.library.dto.LoanNewDTO;

import java.util.List;

/**
 * @author Dávid Lupták
 * @version 15.11.2016
 */
public interface LoanFacade {

    Long create(LoanNewDTO loanDTO);
    void update(LoanDTO loanDTO);
    void delete(Long id);
    LoanDTO findById(Long id);
    List<LoanDTO> findByUser(Long userId);
    List<LoanDTO> findAll();
}
