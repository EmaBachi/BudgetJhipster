package it.consoft.budget.service;

import it.consoft.budget.domain.Budget;
import it.consoft.budget.repository.BudgetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service Implementation for managing Budget.
 */
@Service
@Transactional
public class BudgetService {

    private final Logger log = LoggerFactory.getLogger(BudgetService.class);
    
    private final BudgetRepository budgetRepository;
    
    private final long adminId;

    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
        adminId = 3;
    }
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ResponsabileService responsabileService;

    /**
     * Save a budget.
     *
     * @param budget the entity to save
     * @return the persisted entity
     */
    public Budget save(Budget budget) {
        log.debug("Request to save Budget : {}", budget);
        
        budget.setDivisione(budget.getCommessa().getId());
        
        if(budget.isMensilizzare() == null) budget.setMensilizzare(false);
        
        if(budget.isMensilizzare()){
        	BigDecimal divisor = new BigDecimal("12");
        	budget.setGennaio(budget.getTotale().divide(divisor, BigDecimal.ROUND_HALF_UP));
        	budget.setFebbraio(budget.getTotale().divide(divisor, BigDecimal.ROUND_HALF_UP));
        	budget.setMarzo(budget.getTotale().divide(divisor, BigDecimal.ROUND_HALF_UP));
        	budget.setAprile(budget.getTotale().divide(divisor, BigDecimal.ROUND_HALF_UP));
        	budget.setMaggio(budget.getTotale().divide(divisor, BigDecimal.ROUND_HALF_UP));
        	budget.setGiugno(budget.getTotale().divide(divisor, BigDecimal.ROUND_HALF_UP));
        	budget.setLuglio(budget.getTotale().divide(divisor, BigDecimal.ROUND_HALF_UP));
        	budget.setAgosto(budget.getTotale().divide(divisor, BigDecimal.ROUND_HALF_UP));
        	budget.setSettembre(budget.getTotale().divide(divisor, BigDecimal.ROUND_HALF_UP));
        	budget.setOttobre(budget.getTotale().divide(divisor, BigDecimal.ROUND_HALF_UP));
        	budget.setNovembre(budget.getTotale().divide(divisor, BigDecimal.ROUND_HALF_UP));
        	budget.setDicembre(budget.getTotale().divide(divisor, BigDecimal.ROUND_HALF_UP));
        	
        	return budgetRepository.save(budget);
         } else {
        	 
        	 return budgetRepository.save(budget);
        }
    }

    /**
     *  Get all the budgets.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Budget> findAll(Pageable pageable) {
        log.debug("Request to get all Budgets");
        
        //controllo chi vuole visualizzare il budget
        if(userService.getUserWithAuthorities(adminId).equals(userService.getUserWithAuthorities()))
        	return budgetRepository.findAll(pageable);
        else
        	return budgetRepository.findAllByCommessaDivisioneResponsabile(pageable, responsabileService.findOne(userService.getUserWithAuthorities()));
    }

    /**
     *  Get one budget by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Budget findOne(Long id) {
        log.debug("Request to get Budget : {}", id);
        Budget budget = budgetRepository.findOne(id);
        return budget;
    }

    /**
     *  Delete the  budget by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Budget : {}", id);
        budgetRepository.delete(id);
    }
}
