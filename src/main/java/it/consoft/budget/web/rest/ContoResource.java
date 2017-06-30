package it.consoft.budget.web.rest;

import com.codahale.metrics.annotation.Timed;
import it.consoft.budget.domain.Conto;
import it.consoft.budget.service.ContoService;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Conto.
 */
@RestController
@RequestMapping("/api")
public class ContoResource {

    private final Logger log = LoggerFactory.getLogger(ContoResource.class);

    private static final String ENTITY_NAME = "conto";
        
    private final ContoService contoService;

    public ContoResource(ContoService contoService) {
        this.contoService = contoService;
    }

    /**
     * POST  /contos : Create a new conto.
     *
     * @param conto the conto to create
     * @return the ResponseEntity with status 201 (Created) and with body the new conto, or with status 400 (Bad Request) if the conto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contos")
    @Timed
    public ResponseEntity<Conto> createConto(@RequestBody Conto conto) throws URISyntaxException {
        log.debug("REST request to save Conto : {}", conto);
        if (conto.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new conto cannot already have an ID")).body(null);
        }
        Conto result = contoService.save(conto);
        return ResponseEntity.created(new URI("/api/contos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contos : Updates an existing conto.
     *
     * @param conto the conto to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated conto,
     * or with status 400 (Bad Request) if the conto is not valid,
     * or with status 500 (Internal Server Error) if the conto couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contos")
    @Timed
    public ResponseEntity<Conto> updateConto(@RequestBody Conto conto) throws URISyntaxException {
        log.debug("REST request to update Conto : {}", conto);
        if (conto.getId() == null) {
            return createConto(conto);
        }
        Conto result = contoService.save(conto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, conto.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contos : get all the contos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of contos in body
     */
    @GetMapping("/contos")
    @Timed
    public ResponseEntity<List<Conto>> getAllContos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Contos");
        Page<Conto> page = contoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/contos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /contos/:id : get the "id" conto.
     *
     * @param id the id of the conto to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the conto, or with status 404 (Not Found)
     */
    @GetMapping("/contos/{id}")
    @Timed
    public ResponseEntity<Conto> getConto(@PathVariable Long id) {
        log.debug("REST request to get Conto : {}", id);
        Conto conto = contoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(conto));
    }

    /**
     * DELETE  /contos/:id : delete the "id" conto.
     *
     * @param id the id of the conto to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contos/{id}")
    @Timed
    public ResponseEntity<Void> deleteConto(@PathVariable Long id) {
        log.debug("REST request to delete Conto : {}", id);
        contoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
