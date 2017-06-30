package it.consoft.budget.service;

import it.consoft.budget.domain.BudgetTemporaneo;
import it.consoft.budget.repository.BudgetTemporaneoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing BudgetTemporaneo.
 */
@Service
@Transactional
public class BudgetTemporaneoService {

    private final Logger log = LoggerFactory.getLogger(BudgetTemporaneoService.class);
    
    private final BudgetTemporaneoRepository budgetTemporaneoRepository;

    public BudgetTemporaneoService(BudgetTemporaneoRepository budgetTemporaneoRepository) {
        this.budgetTemporaneoRepository = budgetTemporaneoRepository;
    }

    /**
     * Save a budgetTemporaneo.
     *
     * @param budgetTemporaneo the entity to save
     * @return the persisted entity
     */
    public BudgetTemporaneo save(BudgetTemporaneo budgetTemporaneo) {
        log.debug("Request to save BudgetTemporaneo : {}", budgetTemporaneo);
        BudgetTemporaneo result = budgetTemporaneoRepository.save(budgetTemporaneo);
        return result;
    }

    /**
     *  Get all the budgetTemporaneos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BudgetTemporaneo> findAll(Pageable pageable) {
        log.debug("Request to get all BudgetTemporaneos");
        Page<BudgetTemporaneo> result = budgetTemporaneoRepository.findAll(pageable);
        return result;
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
