package it.consoft.budget.service;

import it.consoft.budget.domain.Conto;
import it.consoft.budget.repository.ContoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Conto.
 */
@Service
@Transactional
public class ContoService {

    private final Logger log = LoggerFactory.getLogger(ContoService.class);
    
    private final ContoRepository contoRepository;

    public ContoService(ContoRepository contoRepository) {
        this.contoRepository = contoRepository;
    }

    /**
     * Save a conto.
     *
     * @param conto the entity to save
     * @return the persisted entity
     */
    public Conto save(Conto conto) {
        log.debug("Request to save Conto : {}", conto);
        Conto result = contoRepository.save(conto);
        return result;
    }

    /**
     *  Get all the contos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Conto> findAll(Pageable pageable) {
        log.debug("Request to get all Contos");
        Page<Conto> result = contoRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one conto by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Conto findOne(Long id) {
        log.debug("Request to get Conto : {}", id);
        Conto conto = contoRepository.findOne(id);
        return conto;
    }

    /**
     *  Delete the  conto by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Conto : {}", id);
        contoRepository.delete(id);
    }
}
