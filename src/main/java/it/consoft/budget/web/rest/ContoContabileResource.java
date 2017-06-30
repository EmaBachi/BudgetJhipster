package it.consoft.budget.web.rest;

import com.codahale.metrics.annotation.Timed;
import it.consoft.budget.domain.ContoContabile;
import it.consoft.budget.service.ContoContabileService;
import it.consoft.budget.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ContoContabile.
 */
@RestController
@RequestMapping("/api")
public class ContoContabileResource {

    private final Logger log = LoggerFactory.getLogger(ContoContabileResource.class);

    private static final String ENTITY_NAME = "contoContabile";
        
    private final ContoContabileService contoContabileService;

    public ContoContabileResource(ContoContabileService contoContabileService) {
        this.contoContabileService = contoContabileService;
    }

    /**
     * POST  /conto-contabiles : Create a new contoContabile.
     *
     * @param contoContabile the contoContabile to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contoContabile, or with status 400 (Bad Request) if the contoContabile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/conto-contabiles")
    @Timed
    public ResponseEntity<ContoContabile> createContoContabile(@RequestBody ContoContabile contoContabile) throws URISyntaxException {
        log.debug("REST request to save ContoContabile : {}", contoContabile);
        if (contoContabile.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new contoContabile cannot already have an ID")).body(null);
        }
        ContoContabile result = contoContabileService.save(contoContabile);
        return ResponseEntity.created(new URI("/api/conto-contabiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /conto-contabiles : Updates an existing contoContabile.
     *
     * @param contoContabile the contoContabile to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contoContabile,
     * or with status 400 (Bad Request) if the contoContabile is not valid,
     * or with status 500 (Internal Server Error) if the contoContabile couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/conto-contabiles")
    @Timed
    public ResponseEntity<ContoContabile> updateContoContabile(@RequestBody ContoContabile contoContabile) throws URISyntaxException {
        log.debug("REST request to update ContoContabile : {}", contoContabile);
        if (contoContabile.getId() == null) {
            return createContoContabile(contoContabile);
        }
        ContoContabile result = contoContabileService.save(contoContabile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contoContabile.getId().toString()))
            .body(result);
    }

    /**
     * GET  /conto-contabiles : get all the contoContabiles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of contoContabiles in body
     */
    @GetMapping("/conto-contabiles")
    @Timed
    public List<ContoContabile> getAllContoContabiles() {
        log.debug("REST request to get all ContoContabiles");
        return contoContabileService.findAll();
    }

    /**
     * GET  /conto-contabiles/:id : get the "id" contoContabile.
     *
     * @param id the id of the contoContabile to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contoContabile, or with status 404 (Not Found)
     */
    @GetMapping("/conto-contabiles/{id}")
    @Timed
    public ResponseEntity<ContoContabile> getContoContabile(@PathVariable Long id) {
        log.debug("REST request to get ContoContabile : {}", id);
        ContoContabile contoContabile = contoContabileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(contoContabile));
    }

    /**
     * DELETE  /conto-contabiles/:id : delete the "id" contoContabile.
     *
     * @param id the id of the contoContabile to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/conto-contabiles/{id}")
    @Timed
    public ResponseEntity<Void> deleteContoContabile(@PathVariable Long id) {
        log.debug("REST request to delete ContoContabile : {}", id);
        contoContabileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
