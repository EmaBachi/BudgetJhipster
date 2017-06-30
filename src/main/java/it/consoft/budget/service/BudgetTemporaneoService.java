package it.consoft.budget.service;

import it.consoft.budget.domain.Budget;
import it.consoft.budget.domain.BudgetTemporaneo;
import it.consoft.budget.repository.BudgetTemporaneoRepository;
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
 * Service Implementation for managing BudgetTemporaneo.
 */
@Service
@Transactional
public class BudgetTemporaneoService {

    private final Logger log = LoggerFactory.getLogger(BudgetTemporaneoService.class);
    
    private final BudgetTemporaneoRepository budgetTemporaneoRepository;
    
    private final long adminId;

    public BudgetTemporaneoService(BudgetTemporaneoRepository budgetTemporaneoRepository) {
        this.budgetTemporaneoRepository = budgetTemporaneoRepository;
        adminId = 3;
    }
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ResponsabileService responsabileService;

    /**
     * Save a budgetTemporaneo.
     *
     * @param budgetTemporaneo the entity to save
     * @return the persisted entity
     */
    public BudgetTemporaneo save(BudgetTemporaneo budget) {
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
        	
        	return budgetTemporaneoRepository.save(budget);
         } else {
        	 
        	 return budgetTemporaneoRepository.save(budget);
        }
    }

    /**
     *  Get all the budgetTemporaneos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BudgetTemporaneo> findAll(Pageable pageable) {
        log.debug("Request to get all Budgets");
        
        //controllo chi vuole visualizzare il budget
        if(userService.getUserWithAuthorities(adminId).equals(userService.getUserWithAuthorities()))
        	return budgetTemporaneoRepository.findAll(pageable);
        else
        	return budgetTemporaneoRepository.findAllByCommessaDivisioneResponsabile(pageable, responsabileService.findOne(userService.getUserWithAuthorities()));
    }

    /**
     *  Get one budgetTemporaneo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public BudgetTemporaneo findOne(Long id) {
        log.debug("Request to get BudgetTemporaneo : {}", id);
        BudgetTemporaneo budgetTemporaneo = budgetTemporaneoRepository.findOne(id);
        return budgetTemporaneo;
    }

    /**
     *  Delete the  budgetTemporaneo by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete BudgetTemporaneo : {}", id);
        budgetTemporaneoRepository.delete(id);
    }
}
