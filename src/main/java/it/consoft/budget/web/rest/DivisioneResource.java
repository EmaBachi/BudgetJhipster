package it.consoft.budget.web.rest;

import com.codahale.metrics.annotation.Timed;
import it.consoft.budget.domain.Divisione;
import it.consoft.budget.service.DivisioneService;
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
 * REST controller for managing Divisione.
 */
@RestController
@RequestMapping("/api")
public class DivisioneResource {

    private final Logger log = LoggerFactory.getLogger(DivisioneResource.class);

    private static final String ENTITY_NAME = "divisione";
        
    private final DivisioneService divisioneService;

    public DivisioneResource(DivisioneService divisioneService) {
        this.divisioneService = divisioneService;
    }

    /**
     * POST  /divisiones : Create a new divisione.
     *
     * @param divisione the divisione to create
     * @return the ResponseEntity with status 201 (Created) and with body the new divisione, or with status 400 (Bad Request) if the divisione has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/divisiones")
    @Timed
    public ResponseEntity<Divisione> createDivisione(@Valid @RequestBody Divisione divisione) throws URISyntaxException {
        log.debug("REST request to save Divisione : {}", divisione);
        if (divisione.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new divisione cannot already have an ID")).body(null);
        }
        Divisione result = divisioneService.save(divisione);
        return ResponseEntity.created(new URI("/api/divisiones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /divisiones : Updates an existing divisione.
     *
     * @param divisione the divisione to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated divisione,
     * or with status 400 (Bad Request) if the divisione is not valid,
     * or with status 500 (Internal Server Error) if the divisione couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/divisiones")
    @Timed
    public ResponseEntity<Divisione> updateDivisione(@Valid @RequestBody Divisione divisione) throws URISyntaxException {
        log.debug("REST request to update Divisione : {}", divisione);
        if (divisione.getId() == null) {
            return createDivisione(divisione);
        }
        Divisione result = divisioneService.save(divisione);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, divisione.getId().toString()))
            .body(result);
    }

    /**
     * GET  /divisiones : get all the divisiones.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of divisiones in body
     */
    @GetMapping("/divisiones")
    @Timed
    public ResponseEntity<List<Divisione>> getAllDivisiones(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Divisiones");
        Page<Divisione> page = divisioneService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/divisiones");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /divisiones/:id : get the "id" divisione.
     *
     * @param id the id of the divisione to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the divisione, or with status 404 (Not Found)
     */
    @GetMapping("/divisiones/{id}")
    @Timed
    public ResponseEntity<Divisione> getDivisione(@PathVariable Long id) {
        log.debug("REST request to get Divisione : {}", id);
        Divisione divisione = divisioneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(divisione));
    }

    /**
     * DELETE  /divisiones/:id : delete the "id" divisione.
     *
     * @param id the id of the divisione to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/divisiones/{id}")
    @Timed
    public ResponseEntity<Void> deleteDivisione(@PathVariable Long id) {
        log.debug("REST request to delete Divisione : {}", id);
        divisioneService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
