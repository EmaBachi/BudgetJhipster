package it.consoft.budget.service;

import it.consoft.budget.domain.Responsabile;
import it.consoft.budget.domain.User;
import it.consoft.budget.repository.ResponsabileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Responsabile.
 */
@Service
@Transactional
public class ResponsabileService {

    private final Logger log = LoggerFactory.getLogger(ResponsabileService.class);
    
    private final ResponsabileRepository responsabileRepository;
    
    private final long adminId;
    
    @Autowired
    private UserService userService;

    public ResponsabileService(ResponsabileRepository responsabileRepository) {
        this.responsabileRepository = responsabileRepository;
        adminId = 3;
    }

    /**
     * Save a responsabile.
     *
     * @param responsabile the entity to save
     * @return the persisted entity
     */
    public Responsabile save(Responsabile responsabile) {
        log.debug("Request to save Responsabile : {}", responsabile);
        
        //Controllo quale utente sta aggiungendo il responsabile
        if(userService.getUserWithAuthorities(adminId).equals(userService.getUserWithAuthorities()))
        	return responsabileRepository.save(responsabile);
        else
        {
        	responsabile.setUser(userService.getUserWithAuthorities());
        	return responsabileRepository.save(responsabile);
        }
    }

    /**
     *  Get all the responsabiles.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Responsabile> findAll(Pageable pageable) {
        log.debug("Request to get all Responsabiles");
        
        //controllo quale utente vuole visualizzare i responsabili
        if(userService.getUserWithAuthorities(adminId).equals(userService.getUserWithAuthorities()))
        	return responsabileRepository.findAll(pageable);
        else
        	return responsabileRepository.findAllByUser(pageable, userService.getUserWithAuthorities());
    }

    /**
     *  Get one responsabile by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Responsabile findOne(Long id) {
        log.debug("Request to get Responsabile : {}", id);
        Responsabile responsabile = responsabileRepository.findOne(id);
        return responsabile;
    }

    /**
     *  Delete the  responsabile by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Responsabile : {}", id);
        responsabileRepository.delete(id);
    }

    @Transactional(readOnly = true)
	public Responsabile findOne(User user) {
		return responsabileRepository.findOneByUser(user);
	}
}
