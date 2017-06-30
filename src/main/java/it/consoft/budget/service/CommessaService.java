package it.consoft.budget.service;

import it.consoft.budget.domain.Commessa;
import it.consoft.budget.repository.CommessaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Commessa.
 */
@Service
@Transactional
public class CommessaService {

    private final Logger log = LoggerFactory.getLogger(CommessaService.class);
    
    private final CommessaRepository commessaRepository;
    
    private final long adminId;

    public CommessaService(CommessaRepository commessaRepository) {
        this.commessaRepository = commessaRepository;
        this.adminId = 3;
    }
    
    @Autowired
    private UserService userService;
    
     @Autowired
     private ResponsabileService responsabileService;

    /**
     * Save a commessa.
     *
     * @param commessa the entity to save
     * @return the persisted entity
     */
    public Commessa save(Commessa commessa) {
        log.debug("Request to save Commessa : {}", commessa);
        Commessa result = commessaRepository.save(commessa);
        return result;
    }

    /**
     *  Get all the commessas.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Commessa> findAll(Pageable pageable) {
        log.debug("Request to get all Commessas");
        
        //Controllo chi vuole visualizzare le commesse
        if(userService.getUserWithAuthorities(adminId).equals(userService.getUserWithAuthorities()))
        	return commessaRepository.findAll(pageable);
        else
        	return commessaRepository.findAllByDivisioneResponsabile(pageable, responsabileService.findOne(userService.getUserWithAuthorities()));
    }

    /**
     *  Get one commessa by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Commessa findOne(Long id) {
        log.debug("Request to get Commessa : {}", id);
        Commessa commessa = commessaRepository.findOne(id);
        return commessa;
    }

    /**
     *  Delete the  commessa by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Commessa : {}", id);
        commessaRepository.delete(id);
    }
}
