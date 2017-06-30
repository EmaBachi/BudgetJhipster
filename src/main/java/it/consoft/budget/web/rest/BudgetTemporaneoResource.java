package it.consoft.budget.web.rest;

import com.codahale.metrics.annotation.Timed;
import it.consoft.budget.domain.BudgetTemporaneo;
import it.consoft.budget.service.BudgetTemporaneoService;
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
 * REST controller for managing BudgetTemporaneo.
 */
@RestController
@RequestMapping("/api")
public class BudgetTemporaneoResource {

    private final Logger log = LoggerFactory.getLogger(BudgetTemporaneoResource.class);

    private static final String ENTITY_NAME = "budgetTemporaneo";
        
    private final BudgetTemporaneoService budgetTemporaneoService;

    public BudgetTemporaneoResource(BudgetTemporaneoService budgetTemporaneoService) {
        this.budgetTemporaneoService = budgetTemporaneoService;
    }

    /**
     * POST  /budget-temporaneos : Create a new budgetTemporaneo.
     *
     * @param budgetTemporaneo the budgetTemporaneo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new budgetTemporaneo, or with status 400 (Bad Request) if the budgetTemporaneo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/budget-temporaneos")
    @Timed
    public ResponseEntity<BudgetTemporaneo> createBudgetTemporaneo(@RequestBody BudgetTemporaneo budgetTemporaneo) throws URISyntaxException {
        log.debug("REST request to save BudgetTemporaneo : {}", budgetTemporaneo);
        if (budgetTemporaneo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new budgetTemporaneo cannot already have an ID")).body(null);
        }
        BudgetTemporaneo result = budgetTemporaneoService.save(budgetTemporaneo);
        return ResponseEntity.created(new URI("/api/budget-temporaneos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /budget-temporaneos : Updates an existing budgetTemporaneo.
     *
     * @param budgetTemporaneo the budgetTemporaneo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated budgetTemporaneo,
     * or with status 400 (Bad Request) if the budgetTemporaneo is not valid,
     * or with status 500 (Internal Server Error) if the budgetTemporaneo couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/budget-temporaneos")
    @Timed
    public ResponseEntity<BudgetTemporaneo> updateBudgetTemporaneo(@RequestBody BudgetTemporaneo budgetTemporaneo) throws URISyntaxException {
        log.debug("REST request to update BudgetTemporaneo : {}", budgetTemporaneo);
        if (budgetTemporaneo.getId() == null) {
            return createBudgetTemporaneo(budgetTemporaneo);
        }
        BudgetTemporaneo result = budgetTemporaneoService.save(budgetTemporaneo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, budgetTemporaneo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /budget-temporaneos : get all the budgetTemporaneos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of budgetTemporaneos in body
     */
    @GetMapping("/budget-temporaneos")
    @Timed
    public ResponseEntity<List<BudgetTemporaneo>> getAllBudgetTemporaneos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of BudgetTemporaneos");
        Page<BudgetTemporaneo> page = budgetTemporaneoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/budget-temporaneos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /budget-temporaneos/:id : get the "id" budgetTemporaneo.
     *
     * @param id the id of the budgetTemporaneo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the budgetTemporaneo, or with status 404 (Not Found)
     */
    @GetMapping("/budget-temporaneos/{id}")
    @Timed
    public ResponseEntity<BudgetTemporaneo> getBudgetTemporaneo(@PathVariable Long id) {
        log.debug("REST request to get BudgetTemporaneo : {}", id);
        BudgetTemporaneo budgetTemporaneo = budgetTemporaneoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(budgetTemporaneo));
    }

    /**
     * DELETE  /budget-temporaneos/:id : delete the "id" budgetTemporaneo.
     *
     * @param id the id of the budgetTemporaneo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/budget-temporaneos/{id}")
    @Timed
    public ResponseEntity<Void> deleteBudgetTemporaneo(@PathVariable Long id) {
        log.debug("REST request to delete BudgetTemporaneo : {}", id);
        budgetTemporaneoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
