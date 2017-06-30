package it.consoft.budget.web.rest;

import it.consoft.budget.BudgetApp;

import it.consoft.budget.domain.Budget;
import it.consoft.budget.repository.BudgetRepository;
import it.consoft.budget.service.BudgetService;
import it.consoft.budget.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BudgetResource REST controller.
 *
 * @see BudgetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BudgetApp.class)
public class BudgetResourceIntTest {

    private static final BigDecimal DEFAULT_GENNAIO = new BigDecimal(1);
    private static final BigDecimal UPDATED_GENNAIO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_FEBBRAIO = new BigDecimal(1);
    private static final BigDecimal UPDATED_FEBBRAIO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_MARZO = new BigDecimal(1);
    private static final BigDecimal UPDATED_MARZO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_APRILE = new BigDecimal(1);
    private static final BigDecimal UPDATED_APRILE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_MAGGIO = new BigDecimal(1);
    private static final BigDecimal UPDATED_MAGGIO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_GIUGNO = new BigDecimal(1);
    private static final BigDecimal UPDATED_GIUGNO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LUGLIO = new BigDecimal(1);
    private static final BigDecimal UPDATED_LUGLIO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_AGOSTO = new BigDecimal(1);
    private static final BigDecimal UPDATED_AGOSTO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SETTEMBRE = new BigDecimal(1);
    private static final BigDecimal UPDATED_SETTEMBRE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_OTTOBRE = new BigDecimal(1);
    private static final BigDecimal UPDATED_OTTOBRE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_NOVEMBRE = new BigDecimal(1);
    private static final BigDecimal UPDATED_NOVEMBRE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DICEMBRE = new BigDecimal(1);
    private static final BigDecimal UPDATED_DICEMBRE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTALE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTALE = new BigDecimal(2);

    private static final Boolean DEFAULT_MENSILIZZARE = false;
    private static final Boolean UPDATED_MENSILIZZARE = true;

    private static final Long DEFAULT_DIVISIONE = 1L;
    private static final Long UPDATED_DIVISIONE = 2L;

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBudgetMockMvc;

    private Budget budget;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BudgetResource budgetResource = new BudgetResource(budgetService);
        this.restBudgetMockMvc = MockMvcBuilders.standaloneSetup(budgetResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Budget createEntity(EntityManager em) {
        Budget budget = new Budget()
            .gennaio(DEFAULT_GENNAIO)
            .febbraio(DEFAULT_FEBBRAIO)
            .marzo(DEFAULT_MARZO)
            .aprile(DEFAULT_APRILE)
            .maggio(DEFAULT_MAGGIO)
            .giugno(DEFAULT_GIUGNO)
            .luglio(DEFAULT_LUGLIO)
            .agosto(DEFAULT_AGOSTO)
            .settembre(DEFAULT_SETTEMBRE)
            .ottobre(DEFAULT_OTTOBRE)
            .novembre(DEFAULT_NOVEMBRE)
            .dicembre(DEFAULT_DICEMBRE)
            .totale(DEFAULT_TOTALE)
            .mensilizzare(DEFAULT_MENSILIZZARE)
            .divisione(DEFAULT_DIVISIONE);
        return budget;
    }

    @Before
    public void initTest() {
        budget = createEntity(em);
    }

    @Test
    @Transactional
    public void createBudget() throws Exception {
        int databaseSizeBeforeCreate = budgetRepository.findAll().size();

        // Create the Budget
        restBudgetMockMvc.perform(post("/api/budgets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(budget)))
            .andExpect(status().isCreated());

        // Validate the Budget in the database
        List<Budget> budgetList = budgetRepository.findAll();
        assertThat(budgetList).hasSize(databaseSizeBeforeCreate + 1);
        Budget testBudget = budgetList.get(budgetList.size() - 1);
        assertThat(testBudget.getGennaio()).isEqualTo(DEFAULT_GENNAIO);
        assertThat(testBudget.getFebbraio()).isEqualTo(DEFAULT_FEBBRAIO);
        assertThat(testBudget.getMarzo()).isEqualTo(DEFAULT_MARZO);
        assertThat(testBudget.getAprile()).isEqualTo(DEFAULT_APRILE);
        assertThat(testBudget.getMaggio()).isEqualTo(DEFAULT_MAGGIO);
        assertThat(testBudget.getGiugno()).isEqualTo(DEFAULT_GIUGNO);
        assertThat(testBudget.getLuglio()).isEqualTo(DEFAULT_LUGLIO);
        assertThat(testBudget.getAgosto()).isEqualTo(DEFAULT_AGOSTO);
        assertThat(testBudget.getSettembre()).isEqualTo(DEFAULT_SETTEMBRE);
        assertThat(testBudget.getOttobre()).isEqualTo(DEFAULT_OTTOBRE);
        assertThat(testBudget.getNovembre()).isEqualTo(DEFAULT_NOVEMBRE);
        assertThat(testBudget.getDicembre()).isEqualTo(DEFAULT_DICEMBRE);
        assertThat(testBudget.getTotale()).isEqualTo(DEFAULT_TOTALE);
        assertThat(testBudget.isMensilizzare()).isEqualTo(DEFAULT_MENSILIZZARE);
        assertThat(testBudget.getDivisione()).isEqualTo(DEFAULT_DIVISIONE);
    }

    @Test
    @Transactional
    public void createBudgetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = budgetRepository.findAll().size();

        // Create the Budget with an existing ID
        budget.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBudgetMockMvc.perform(post("/api/budgets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(budget)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Budget> budgetList = budgetRepository.findAll();
        assertThat(budgetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBudgets() throws Exception {
        // Initialize the database
        budgetRepository.saveAndFlush(budget);

        // Get all the budgetList
        restBudgetMockMvc.perform(get("/api/budgets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(budget.getId().intValue())))
            .andExpect(jsonPath("$.[*].gennaio").value(hasItem(DEFAULT_GENNAIO.intValue())))
            .andExpect(jsonPath("$.[*].febbraio").value(hasItem(DEFAULT_FEBBRAIO.intValue())))
            .andExpect(jsonPath("$.[*].marzo").value(hasItem(DEFAULT_MARZO.intValue())))
            .andExpect(jsonPath("$.[*].aprile").value(hasItem(DEFAULT_APRILE.intValue())))
            .andExpect(jsonPath("$.[*].maggio").value(hasItem(DEFAULT_MAGGIO.intValue())))
            .andExpect(jsonPath("$.[*].giugno").value(hasItem(DEFAULT_GIUGNO.intValue())))
            .andExpect(jsonPath("$.[*].luglio").value(hasItem(DEFAULT_LUGLIO.intValue())))
            .andExpect(jsonPath("$.[*].agosto").value(hasItem(DEFAULT_AGOSTO.intValue())))
            .andExpect(jsonPath("$.[*].settembre").value(hasItem(DEFAULT_SETTEMBRE.intValue())))
            .andExpect(jsonPath("$.[*].ottobre").value(hasItem(DEFAULT_OTTOBRE.intValue())))
            .andExpect(jsonPath("$.[*].novembre").value(hasItem(DEFAULT_NOVEMBRE.intValue())))
            .andExpect(jsonPath("$.[*].dicembre").value(hasItem(DEFAULT_DICEMBRE.intValue())))
            .andExpect(jsonPath("$.[*].totale").value(hasItem(DEFAULT_TOTALE.intValue())))
            .andExpect(jsonPath("$.[*].mensilizzare").value(hasItem(DEFAULT_MENSILIZZARE.booleanValue())))
            .andExpect(jsonPath("$.[*].divisione").value(hasItem(DEFAULT_DIVISIONE.intValue())));
    }

    @Test
    @Transactional
    public void getBudget() throws Exception {
        // Initialize the database
        budgetRepository.saveAndFlush(budget);

        // Get the budget
        restBudgetMockMvc.perform(get("/api/budgets/{id}", budget.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(budget.getId().intValue()))
            .andExpect(jsonPath("$.gennaio").value(DEFAULT_GENNAIO.intValue()))
            .andExpect(jsonPath("$.febbraio").value(DEFAULT_FEBBRAIO.intValue()))
            .andExpect(jsonPath("$.marzo").value(DEFAULT_MARZO.intValue()))
            .andExpect(jsonPath("$.aprile").value(DEFAULT_APRILE.intValue()))
            .andExpect(jsonPath("$.maggio").value(DEFAULT_MAGGIO.intValue()))
            .andExpect(jsonPath("$.giugno").value(DEFAULT_GIUGNO.intValue()))
            .andExpect(jsonPath("$.luglio").value(DEFAULT_LUGLIO.intValue()))
            .andExpect(jsonPath("$.agosto").value(DEFAULT_AGOSTO.intValue()))
            .andExpect(jsonPath("$.settembre").value(DEFAULT_SETTEMBRE.intValue()))
            .andExpect(jsonPath("$.ottobre").value(DEFAULT_OTTOBRE.intValue()))
            .andExpect(jsonPath("$.novembre").value(DEFAULT_NOVEMBRE.intValue()))
            .andExpect(jsonPath("$.dicembre").value(DEFAULT_DICEMBRE.intValue()))
            .andExpect(jsonPath("$.totale").value(DEFAULT_TOTALE.intValue()))
            .andExpect(jsonPath("$.mensilizzare").value(DEFAULT_MENSILIZZARE.booleanValue()))
            .andExpect(jsonPath("$.divisione").value(DEFAULT_DIVISIONE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBudget() throws Exception {
        // Get the budget
        restBudgetMockMvc.perform(get("/api/budgets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBudget() throws Exception {
        // Initialize the database
        budgetService.save(budget);

        int databaseSizeBeforeUpdate = budgetRepository.findAll().size();

        // Update the budget
        Budget updatedBudget = budgetRepository.findOne(budget.getId());
        updatedBudget
            .gennaio(UPDATED_GENNAIO)
            .febbraio(UPDATED_FEBBRAIO)
            .marzo(UPDATED_MARZO)
            .aprile(UPDATED_APRILE)
            .maggio(UPDATED_MAGGIO)
            .giugno(UPDATED_GIUGNO)
            .luglio(UPDATED_LUGLIO)
            .agosto(UPDATED_AGOSTO)
            .settembre(UPDATED_SETTEMBRE)
            .ottobre(UPDATED_OTTOBRE)
            .novembre(UPDATED_NOVEMBRE)
            .dicembre(UPDATED_DICEMBRE)
            .totale(UPDATED_TOTALE)
            .mensilizzare(UPDATED_MENSILIZZARE)
            .divisione(UPDATED_DIVISIONE);

        restBudgetMockMvc.perform(put("/api/budgets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBudget)))
            .andExpect(status().isOk());

        // Validate the Budget in the database
        List<Budget> budgetList = budgetRepository.findAll();
        assertThat(budgetList).hasSize(databaseSizeBeforeUpdate);
        Budget testBudget = budgetList.get(budgetList.size() - 1);
        assertThat(testBudget.getGennaio()).isEqualTo(UPDATED_GENNAIO);
        assertThat(testBudget.getFebbraio()).isEqualTo(UPDATED_FEBBRAIO);
        assertThat(testBudget.getMarzo()).isEqualTo(UPDATED_MARZO);
        assertThat(testBudget.getAprile()).isEqualTo(UPDATED_APRILE);
        assertThat(testBudget.getMaggio()).isEqualTo(UPDATED_MAGGIO);
        assertThat(testBudget.getGiugno()).isEqualTo(UPDATED_GIUGNO);
        assertThat(testBudget.getLuglio()).isEqualTo(UPDATED_LUGLIO);
        assertThat(testBudget.getAgosto()).isEqualTo(UPDATED_AGOSTO);
        assertThat(testBudget.getSettembre()).isEqualTo(UPDATED_SETTEMBRE);
        assertThat(testBudget.getOttobre()).isEqualTo(UPDATED_OTTOBRE);
        assertThat(testBudget.getNovembre()).isEqualTo(UPDATED_NOVEMBRE);
        assertThat(testBudget.getDicembre()).isEqualTo(UPDATED_DICEMBRE);
        assertThat(testBudget.getTotale()).isEqualTo(UPDATED_TOTALE);
        assertThat(testBudget.isMensilizzare()).isEqualTo(UPDATED_MENSILIZZARE);
        assertThat(testBudget.getDivisione()).isEqualTo(UPDATED_DIVISIONE);
    }

    @Test
    @Transactional
    public void updateNonExistingBudget() throws Exception {
        int databaseSizeBeforeUpdate = budgetRepository.findAll().size();

        // Create the Budget

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBudgetMockMvc.perform(put("/api/budgets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(budget)))
            .andExpect(status().isCreated());

        // Validate the Budget in the database
        List<Budget> budgetList = budgetRepository.findAll();
        assertThat(budgetList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBudget() throws Exception {
        // Initialize the database
        budgetService.save(budget);

        int databaseSizeBeforeDelete = budgetRepository.findAll().size();

        // Get the budget
        restBudgetMockMvc.perform(delete("/api/budgets/{id}", budget.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Budget> budgetList = budgetRepository.findAll();
        assertThat(budgetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Budget.class);
        Budget budget1 = new Budget();
        budget1.setId(1L);
        Budget budget2 = new Budget();
        budget2.setId(budget1.getId());
        assertThat(budget1).isEqualTo(budget2);
        budget2.setId(2L);
        assertThat(budget1).isNotEqualTo(budget2);
        budget1.setId(null);
        assertThat(budget1).isNotEqualTo(budget2);
    }
}
