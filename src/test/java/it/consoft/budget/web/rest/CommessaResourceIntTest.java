package it.consoft.budget.web.rest;

import it.consoft.budget.BudgetApp;

import it.consoft.budget.domain.Commessa;
import it.consoft.budget.repository.CommessaRepository;
import it.consoft.budget.service.CommessaService;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CommessaResource REST controller.
 *
 * @see CommessaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BudgetApp.class)
public class CommessaResourceIntTest {

    private static final String DEFAULT_CODICE = "AAAAAAAAAA";
    private static final String UPDATED_CODICE = "BBBBBBBBBB";

    @Autowired
    private CommessaRepository commessaRepository;

    @Autowired
    private CommessaService commessaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommessaMockMvc;

    private Commessa commessa;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CommessaResource commessaResource = new CommessaResource(commessaService);
        this.restCommessaMockMvc = MockMvcBuilders.standaloneSetup(commessaResource)
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
    public static Commessa createEntity(EntityManager em) {
        Commessa commessa = new Commessa()
            .codice(DEFAULT_CODICE);
        return commessa;
    }

    @Before
    public void initTest() {
        commessa = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommessa() throws Exception {
        int databaseSizeBeforeCreate = commessaRepository.findAll().size();

        // Create the Commessa
        restCommessaMockMvc.perform(post("/api/commessas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commessa)))
            .andExpect(status().isCreated());

        // Validate the Commessa in the database
        List<Commessa> commessaList = commessaRepository.findAll();
        assertThat(commessaList).hasSize(databaseSizeBeforeCreate + 1);
        Commessa testCommessa = commessaList.get(commessaList.size() - 1);
        assertThat(testCommessa.getCodice()).isEqualTo(DEFAULT_CODICE);
    }

    @Test
    @Transactional
    public void createCommessaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commessaRepository.findAll().size();

        // Create the Commessa with an existing ID
        commessa.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommessaMockMvc.perform(post("/api/commessas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commessa)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Commessa> commessaList = commessaRepository.findAll();
        assertThat(commessaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodiceIsRequired() throws Exception {
        int databaseSizeBeforeTest = commessaRepository.findAll().size();
        // set the field null
        commessa.setCodice(null);

        // Create the Commessa, which fails.

        restCommessaMockMvc.perform(post("/api/commessas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commessa)))
            .andExpect(status().isBadRequest());

        List<Commessa> commessaList = commessaRepository.findAll();
        assertThat(commessaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommessas() throws Exception {
        // Initialize the database
        commessaRepository.saveAndFlush(commessa);

        // Get all the commessaList
        restCommessaMockMvc.perform(get("/api/commessas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commessa.getId().intValue())))
            .andExpect(jsonPath("$.[*].codice").value(hasItem(DEFAULT_CODICE.toString())));
    }

    @Test
    @Transactional
    public void getCommessa() throws Exception {
        // Initialize the database
        commessaRepository.saveAndFlush(commessa);

        // Get the commessa
        restCommessaMockMvc.perform(get("/api/commessas/{id}", commessa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commessa.getId().intValue()))
            .andExpect(jsonPath("$.codice").value(DEFAULT_CODICE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCommessa() throws Exception {
        // Get the commessa
        restCommessaMockMvc.perform(get("/api/commessas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommessa() throws Exception {
        // Initialize the database
        commessaService.save(commessa);

        int databaseSizeBeforeUpdate = commessaRepository.findAll().size();

        // Update the commessa
        Commessa updatedCommessa = commessaRepository.findOne(commessa.getId());
        updatedCommessa
            .codice(UPDATED_CODICE);

        restCommessaMockMvc.perform(put("/api/commessas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommessa)))
            .andExpect(status().isOk());

        // Validate the Commessa in the database
        List<Commessa> commessaList = commessaRepository.findAll();
        assertThat(commessaList).hasSize(databaseSizeBeforeUpdate);
        Commessa testCommessa = commessaList.get(commessaList.size() - 1);
        assertThat(testCommessa.getCodice()).isEqualTo(UPDATED_CODICE);
    }

    @Test
    @Transactional
    public void updateNonExistingCommessa() throws Exception {
        int databaseSizeBeforeUpdate = commessaRepository.findAll().size();

        // Create the Commessa

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCommessaMockMvc.perform(put("/api/commessas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commessa)))
            .andExpect(status().isCreated());

        // Validate the Commessa in the database
        List<Commessa> commessaList = commessaRepository.findAll();
        assertThat(commessaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCommessa() throws Exception {
        // Initialize the database
        commessaService.save(commessa);

        int databaseSizeBeforeDelete = commessaRepository.findAll().size();

        // Get the commessa
        restCommessaMockMvc.perform(delete("/api/commessas/{id}", commessa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Commessa> commessaList = commessaRepository.findAll();
        assertThat(commessaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Commessa.class);
        Commessa commessa1 = new Commessa();
        commessa1.setId(1L);
        Commessa commessa2 = new Commessa();
        commessa2.setId(commessa1.getId());
        assertThat(commessa1).isEqualTo(commessa2);
        commessa2.setId(2L);
        assertThat(commessa1).isNotEqualTo(commessa2);
        commessa1.setId(null);
        assertThat(commessa1).isNotEqualTo(commessa2);
    }
}
