package it.consoft.budget.web.rest;

import it.consoft.budget.BudgetApp;

import it.consoft.budget.domain.BudgetTemporaneo;
import it.consoft.budget.repository.BudgetTemporaneoRepository;
import it.consoft.budget.service.BudgetTemporaneoService;
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
 * Test class for the BudgetTemporaneoResource REST controller.
 *
 * @see BudgetTemporaneoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BudgetApp.class)
public class BudgetTemporaneoResourceIntTest {

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
    private BudgetTemporaneoRepository budgetTemporaneoRepository;

    @Autowired
    private BudgetTemporaneoService budgetTemporaneoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBudgetTemporaneoMockMvc;

    private BudgetTemporaneo budgetTemporaneo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BudgetTemporaneoResource budgetTemporaneoResource = new BudgetTemporaneoResource(budgetTemporaneoService);
        this.restBudgetTemporaneoMockMvc = MockMvcBuilders.standaloneSetup(budgetTemporaneoResource)
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
    public static BudgetTemporaneo createEntity(EntityManager em) {
        BudgetTemporaneo budgetTemporaneo = new BudgetTemporaneo()
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
        return budgetTemporaneo;
    }

    @Before
    public void initTest() {
        budgetTemporaneo = createEntity(em);
    }

    @Test
    @Transactional
    public void createBudgetTemporaneo() throws Exception {
        int databaseSizeBeforeCreate = budgetTemporaneoRepository.findAll().size();

        // Create the BudgetTemporaneo
        restBudgetTemporaneoMockMvc.perform(post("/api/budget-temporaneos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(budgetTemporaneo)))
            .andExpect(status().isCreated());

        // Validate the BudgetTemporaneo in the database
        List<BudgetTemporaneo> budgetTemporaneoList = budgetTemporaneoRepository.findAll();
        assertThat(budgetTemporaneoList).hasSize(databaseSizeBeforeCreate + 1);
        BudgetTemporaneo testBudgetTemporaneo = budgetTemporaneoList.get(budgetTemporaneoList.size() - 1);
        assertThat(testBudgetTemporaneo.getGennaio()).isEqualTo(DEFAULT_GENNAIO);
        assertThat(testBudgetTemporaneo.getFebbraio()).isEqualTo(DEFAULT_FEBBRAIO);
        assertThat(testBudgetTemporaneo.getMarzo()).isEqualTo(DEFAULT_MARZO);
        assertThat(testBudgetTemporaneo.getAprile()).isEqualTo(DEFAULT_APRILE);
        assertThat(testBudgetTemporaneo.getMaggio()).isEqualTo(DEFAULT_MAGGIO);
        assertThat(testBudgetTemporaneo.getGiugno()).isEqualTo(DEFAULT_GIUGNO);
        assertThat(testBudgetTemporaneo.getLuglio()).isEqualTo(DEFAULT_LUGLIO);
        assertThat(testBudgetTemporaneo.getAgosto()).isEqualTo(DEFAULT_AGOSTO);
        assertThat(testBudgetTemporaneo.getSettembre()).isEqualTo(DEFAULT_SETTEMBRE);
        assertThat(testBudgetTemporaneo.getOttobre()).isEqualTo(DEFAULT_OTTOBRE);
        assertThat(testBudgetTemporaneo.getNovembre()).isEqualTo(DEFAULT_NOVEMBRE);
        assertThat(testBudgetTemporaneo.getDicembre()).isEqualTo(DEFAULT_DICEMBRE);
        assertThat(testBudgetTemporaneo.getTotale()).isEqualTo(DEFAULT_TOTALE);
        assertThat(testBudgetTemporaneo.isMensilizzare()).isEqualTo(DEFAULT_MENSILIZZARE);
        assertThat(testBudgetTemporaneo.getDivisione()).isEqualTo(DEFAULT_DIVISIONE);
    }

    @Test
    @Transactional
    public void createBudgetTemporaneoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = budgetTemporaneoRepository.findAll().size();

        // Create the BudgetTemporaneo with an existing ID
        budgetTemporaneo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBudgetTemporaneoMockMvc.perform(post("/api/budget-temporaneos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(budgetTemporaneo)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BudgetTemporaneo> budgetTemporaneoList = budgetTemporaneoRepository.findAll();
        assertThat(budgetTemporaneoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBudgetTemporaneos() throws Exception {
        // Initialize the database
        budgetTemporaneoRepository.saveAndFlush(budgetTemporaneo);

        // Get all the budgetTemporaneoList
        restBudgetTemporaneoMockMvc.perform(get("/api/budget-temporaneos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(budgetTemporaneo.getId().intValue())))
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
    public void getBudgetTemporaneo() throws Exception {
        // Initialize the database
        budgetTemporaneoRepository.saveAndFlush(budgetTemporaneo);

        // Get the budgetTemporaneo
        restBudgetTemporaneoMockMvc.perform(get("/api/budget-temporaneos/{id}", budgetTemporaneo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(budgetTemporaneo.getId().intValue()))
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
    public void getNonExistingBudgetTemporaneo() throws Exception {
        // Get the budgetTemporaneo
        restBudgetTemporaneoMockMvc.perform(get("/api/budget-temporaneos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBudgetTemporaneo() throws Exception {
        // Initialize the database
        budgetTemporaneoService.save(budgetTemporaneo);

        int databaseSizeBeforeUpdate = budgetTemporaneoRepository.findAll().size();

        // Update the budgetTemporaneo
        BudgetTemporaneo updatedBudgetTemporaneo = budgetTemporaneoRepository.findOne(budgetTemporaneo.getId());
        updatedBudgetTemporaneo
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

        restBudgetTemporaneoMockMvc.perform(put("/api/budget-temporaneos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBudgetTemporaneo)))
            .andExpect(status().isOk());

        // Validate the BudgetTemporaneo in the database
        List<BudgetTemporaneo> budgetTemporaneoList = budgetTemporaneoRepository.findAll();
        assertThat(budgetTemporaneoList).hasSize(databaseSizeBeforeUpdate);
        BudgetTemporaneo testBudgetTemporaneo = budgetTemporaneoList.get(budgetTemporaneoList.size() - 1);
        assertThat(testBudgetTemporaneo.getGennaio()).isEqualTo(UPDATED_GENNAIO);
        assertThat(testBudgetTemporaneo.getFebbraio()).isEqualTo(UPDATED_FEBBRAIO);
        assertThat(testBudgetTemporaneo.getMarzo()).isEqualTo(UPDATED_MARZO);
        assertThat(testBudgetTemporaneo.getAprile()).isEqualTo(UPDATED_APRILE);
        assertThat(testBudgetTemporaneo.getMaggio()).isEqualTo(UPDATED_MAGGIO);
        assertThat(testBudgetTemporaneo.getGiugno()).isEqualTo(UPDATED_GIUGNO);
        assertThat(testBudgetTemporaneo.getLuglio()).isEqualTo(UPDATED_LUGLIO);
        assertThat(testBudgetTemporaneo.getAgosto()).isEqualTo(UPDATED_AGOSTO);
        assertThat(testBudgetTemporaneo.getSettembre()).isEqualTo(UPDATED_SETTEMBRE);
        assertThat(testBudgetTemporaneo.getOttobre()).isEqualTo(UPDATED_OTTOBRE);
        assertThat(testBudgetTemporaneo.getNovembre()).isEqualTo(UPDATED_NOVEMBRE);
        assertThat(testBudgetTemporaneo.getDicembre()).isEqualTo(UPDATED_DICEMBRE);
        assertThat(testBudgetTemporaneo.getTotale()).isEqualTo(UPDATED_TOTALE);
        assertThat(testBudgetTemporaneo.isMensilizzare()).isEqualTo(UPDATED_MENSILIZZARE);
        assertThat(testBudgetTemporaneo.getDivisione()).isEqualTo(UPDATED_DIVISIONE);
    }

    @Test
    @Transactional
    public void updateNonExistingBudgetTemporaneo() throws Exception {
        int databaseSizeBeforeUpdate = budgetTemporaneoRepository.findAll().size();

        // Create the BudgetTemporaneo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBudgetTemporaneoMockMvc.perform(put("/api/budget-temporaneos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(budgetTemporaneo)))
            .andExpect(status().isCreated());

        // Validate the BudgetTemporaneo in the database
        List<BudgetTemporaneo> budgetTemporaneoList = budgetTemporaneoRepository.findAll();
        assertThat(budgetTemporaneoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBudgetTemporaneo() throws Exception {
        // Initialize the database
        budgetTemporaneoService.save(budgetTemporaneo);

        int databaseSizeBeforeDelete = budgetTemporaneoRepository.findAll().size();

        // Get the budgetTemporaneo
        restBudgetTemporaneoMockMvc.perform(delete("/api/budget-temporaneos/{id}", budgetTemporaneo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BudgetTemporaneo> budgetTemporaneoList = budgetTemporaneoRepository.findAll();
        assertThat(budgetTemporaneoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BudgetTemporaneo.class);
        BudgetTemporaneo budgetTemporaneo1 = new BudgetTemporaneo();
        budgetTemporaneo1.setId(1L);
        BudgetTemporaneo budgetTemporaneo2 = new BudgetTemporaneo();
        budgetTemporaneo2.setId(budgetTemporaneo1.getId());
        assertThat(budgetTemporaneo1).isEqualTo(budgetTemporaneo2);
        budgetTemporaneo2.setId(2L);
        assertThat(budgetTemporaneo1).isNotEqualTo(budgetTemporaneo2);
        budgetTemporaneo1.setId(null);
        assertThat(budgetTemporaneo1).isNotEqualTo(budgetTemporaneo2);
    }
}
