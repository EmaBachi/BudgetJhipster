package it.consoft.budget.service;

import it.consoft.budget.domain.Divisione;
import it.consoft.budget.repository.DivisioneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Divisione.
 */
@Service
@Transactional
public class DivisioneService {

    private final Logger log = LoggerFactory.getLogger(DivisioneService.class);
    
    private final DivisioneRepository divisioneRepository;
    
    private final long adminId;

    public DivisioneService(DivisioneRepository divisioneRepository) {
        this.divisioneRepository = divisioneRepository;
        adminId = 3;
    }
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ResponsabileService responsabileService;

    /**
     * Save a divisione.
     *
     * @param divisione the entity to save
     * @return the persisted entity
     */
    public Divisione save(Divisione divisione) {
        log.debug("Request to save Divisione : {}", divisione);
        
        //controllo chi vuole aggiungere la divisione
        if(userService.getUserWithAuthorities(adminId).equals(userService.getUserWithAuthorities()))
        	return divisioneRepository.save(divisione);
        else
        {
        	divisione.setResponsabile(responsabileService.findOne(userService.getUserWithAuthorities()));
        	return divisioneRepository.save(divisione);
        }
    }

    /**
     *  Get all the divisiones.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Divisione> findAll(Pageable pageable) {
        log.debug("Request to get all Divisiones");
        
        //controllo quale utente vuole visualizzare le divisioni
        if(userService.getUserWithAuthorities(adminId).equals(userService.getUserWithAuthorities()))
        	return divisioneRepository.findAll(pageable);
        else
        	return divisioneRepository.findAllByResponsabile(pageable, responsabileService.findOne(userService.getUserWithAuthorities()));
    }

    /**
     *  Get one divisione by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Divisione findOne(Long id) {
        log.debug("Request to get Divisione : {}", id);
        Divisione divisione = divisioneRepository.findOne(id);
        return divisione;
    }

    /**
     *  Delete the  divisione by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Divisione : {}", id);
        divisioneRepository.delete(id);
    }
}
