package it.consoft.budget.web.rest;

import com.codahale.metrics.annotation.Timed;
import it.consoft.budget.domain.Responsabile;
import it.consoft.budget.service.ResponsabileService;
import it.consoft.budget.web.rest.util.HeaderUtil;
import it.consoft.budget.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Responsabile.
 */
@RestController
@RequestMapping("/api")
public class ResponsabileResource {

    private final Logger log = LoggerFactory.getLogger(ResponsabileResource.class);

    private static final String ENTITY_NAME = "responsabile";
        
    private final ResponsabileService responsabileService;

    public ResponsabileResource(ResponsabileService responsabileService) {
        this.responsabileService = responsabileService;
    }

    /**
     * POST  /responsabiles : Create a new responsabile.
     *
     * @param responsabile the responsabile to create
     * @return the ResponseEntity with status 201 (Created) and with body the new responsabile, or with status 400 (Bad Request) if the responsabile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/responsabiles")
    @Timed
    public ResponseEntity<Responsabile> createResponsabile(@Valid @RequestBody Responsabile responsabile) throws URISyntaxException {
        log.debug("REST request to save Responsabile : {}", responsabile);
        if (responsabile.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new responsabile cannot already have an ID")).body(null);
        }
        Responsabile result = responsabileService.save(responsabile);
        return ResponseEntity.created(new URI("/api/responsabiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /responsabiles : Updates an existing responsabile.
     *
     * @param responsabile the responsabile to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated responsabile,
     * or with status 400 (Bad Request) if the responsabile is not valid,
     * or with status 500 (Internal Server Error) if the responsabile couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/responsabiles")
    @Timed
    public ResponseEntity<Responsabile> updateResponsabile(@Valid @RequestBody Responsabile responsabile) throws URISyntaxException {
        log.debug("REST request to update Responsabile : {}", responsabile);
        if (responsabile.getId() == null) {
            return createResponsabile(responsabile);
        }
        Responsabile result = responsabileService.save(responsabile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, responsabile.getId().toString()))
            .body(result);
    }

    /**
     * GET  /responsabiles : get all the responsabiles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of responsabiles in body
     */
    @GetMapping("/responsabiles")
    @Timed
    public ResponseEntity<List<Responsabile>> getAllResponsabiles(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Responsabiles");
        Page<Responsabile> page = responsabileService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/responsabiles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /responsabiles/:id : get the "id" responsabile.
     *
     * @param id the id of the responsabile to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the responsabile, or with status 404 (Not Found)
     */
    @GetMapping("/responsabiles/{id}")
    @Timed
    public ResponseEntity<Responsabile> getResponsabile(@PathVariable Long id) {
        log.debug("REST request to get Responsabile : {}", id);
        Responsabile responsabile = responsabileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(responsabile));
    }

    /**
     * DELETE  /responsabiles/:id : delete the "id" responsabile.
     *
     * @param id the id of the responsabile to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/responsabiles/{id}")
    @Timed
    public ResponseEntity<Void> deleteResponsabile(@PathVariable Long id) {
        log.debug("REST request to delete Responsabile : {}", id);
        responsabileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
