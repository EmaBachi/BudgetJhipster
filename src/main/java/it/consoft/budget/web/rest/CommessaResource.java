package it.consoft.budget.web.rest;

import com.codahale.metrics.annotation.Timed;
import it.consoft.budget.domain.Commessa;
import it.consoft.budget.service.CommessaService;
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
 * REST controller for managing Commessa.
 */
@RestController
@RequestMapping("/api")
public class CommessaResource {

    private final Logger log = LoggerFactory.getLogger(CommessaResource.class);

    private static final String ENTITY_NAME = "commessa";
        
    private final CommessaService commessaService;

    public CommessaResource(CommessaService commessaService) {
        this.commessaService = commessaService;
    }

    /**
     * POST  /commessas : Create a new commessa.
     *
     * @param commessa the commessa to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commessa, or with status 400 (Bad Request) if the commessa has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commessas")
    @Timed
    public ResponseEntity<Commessa> createCommessa(@Valid @RequestBody Commessa commessa) throws URISyntaxException {
        log.debug("REST request to save Commessa : {}", commessa);
        if (commessa.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new commessa cannot already have an ID")).body(null);
        }
        Commessa result = commessaService.save(commessa);
        return ResponseEntity.created(new URI("/api/commessas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commessas : Updates an existing commessa.
     *
     * @param commessa the commessa to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commessa,
     * or with status 400 (Bad Request) if the commessa is not valid,
     * or with status 500 (Internal Server Error) if the commessa couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commessas")
    @Timed
    public ResponseEntity<Commessa> updateCommessa(@Valid @RequestBody Commessa commessa) throws URISyntaxException {
        log.debug("REST request to update Commessa : {}", commessa);
        if (commessa.getId() == null) {
            return createCommessa(commessa);
        }
        Commessa result = commessaService.save(commessa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commessa.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commessas : get all the commessas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of commessas in body
     */
    @GetMapping("/commessas")
    @Timed
    public ResponseEntity<List<Commessa>> getAllCommessas(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Commessas");
        Page<Commessa> page = commessaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/commessas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /commessas/:id : get the "id" commessa.
     *
     * @param id the id of the commessa to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commessa, or with status 404 (Not Found)
     */
    @GetMapping("/commessas/{id}")
    @Timed
    public ResponseEntity<Commessa> getCommessa(@PathVariable Long id) {
        log.debug("REST request to get Commessa : {}", id);
        Commessa commessa = commessaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(commessa));
    }

    /**
     * DELETE  /commessas/:id : delete the "id" commessa.
     *
     * @param id the id of the commessa to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commessas/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommessa(@PathVariable Long id) {
        log.debug("REST request to delete Commessa : {}", id);
        commessaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
