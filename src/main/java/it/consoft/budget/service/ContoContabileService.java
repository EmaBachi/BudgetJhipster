package it.consoft.budget.service;

import it.consoft.budget.domain.ContoContabile;
import it.consoft.budget.repository.ContoContabileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing ContoContabile.
 */
@Service
@Transactional
public class ContoContabileService {

    private final Logger log = LoggerFactory.getLogger(ContoContabileService.class);
    
    private final ContoContabileRepository contoContabileRepository;

    public ContoContabileService(ContoContabileRepository contoContabileRepository) {
        this.contoContabileRepository = contoContabileRepository;
    }

    /**
     * Save a contoContabile.
     *
     * @param contoContabile the entity to save
     * @return the persisted entity
     */
    public ContoContabile save(ContoContabile contoContabile) {
        log.debug("Request to save ContoContabile : {}", contoContabile);
        ContoContabile result = contoContabileRepository.save(contoContabile);
        return result;
    }

    /**
     *  Get all the contoContabiles.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ContoContabile> findAll() {
        log.debug("Request to get all ContoContabiles");
        List<ContoContabile> result = contoContabileRepository.findAll();

        return result;
    }

    /**
     *  Get one contoContabile by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ContoContabile findOne(Long id) {
        log.debug("Request to get ContoContabile : {}", id);
        ContoContabile contoContabile = contoContabileRepository.findOne(id);
        return contoContabile;
    }

    /**
     *  Delete the  contoContabile by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ContoContabile : {}", id);
        contoContabileRepository.delete(id);
    }
}
