package it.consoft.budget.web.rest;

import com.codahale.metrics.annotation.Timed;
import it.consoft.budget.domain.Budget;
import it.consoft.budget.service.BudgetService;
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
 * REST controller for managing Budget.
 */
@RestController
@RequestMapping("/api")
public class BudgetResource {

    private final Logger log = LoggerFactory.getLogger(BudgetResource.class);

    private static final String ENTITY_NAME = "budget";
        
    private final BudgetService budgetService;

    public BudgetResource(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    /**
     * POST  /budgets : Create a new budget.
     *
     * @param budget the budget to create
     * @return the ResponseEntity with status 201 (Created) and with body the new budget, or with status 400 (Bad Request) if the budget has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/budgets")
    @Timed
    public ResponseEntity<Budget> createBudget(@RequestBody Budget budget) throws URISyntaxException {
        log.debug("REST request to save Budget : {}", budget);
        if (budget.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new budget cannot already have an ID")).body(null);
        }
        Budget result = budgetService.save(budget);
        return ResponseEntity.created(new URI("/api/budgets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /budgets : Updates an existing budget.
     *
     * @param budget the budget to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated budget,
     * or with status 400 (Bad Request) if the budget is not valid,
     * or with status 500 (Internal Server Error) if the budget couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/budgets")
    @Timed
    public ResponseEntity<Budget> updateBudget(@RequestBody Budget budget) throws URISyntaxException {
        log.debug("REST request to update Budget : {}", budget);
        if (budget.getId() == null) {
            return createBudget(budget);
        }
        Budget result = budgetService.save(budget);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, budget.getId().toString()))
            .body(result);
    }

    /**
     * GET  /budgets : get all the budgets.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of budgets in body
     */
    @GetMapping("/budgets")
    @Timed
    public ResponseEntity<List<Budget>> getAllBudgets(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Budgets");
        Page<Budget> page = budgetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/budgets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /budgets/:id : get the "id" budget.
     *
     * @param id the id of the budget to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the budget, or with status 404 (Not Found)
     */
    @GetMapping("/budgets/{id}")
    @Timed
    public ResponseEntity<Budget> getBudget(@PathVariable Long id) {
        log.debug("REST request to get Budget : {}", id);
        Budget budget = budgetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(budget));
    }

    /**
     * DELETE  /budgets/:id : delete the "id" budget.
     *
     * @param id the id of the budget to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/budgets/{id}")
    @Timed
    public ResponseEntity<Void> deleteBudget(@PathVariable Long id) {
        log.debug("REST request to delete Budget : {}", id);
        budgetService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
